package com.dongnaoedu.tony.dispatch.api;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.dongnaoedu.tony.dispatch.service.DispatchService;
import com.rabbitmq.client.Channel;

/**
 * 消费者，取调度队列
 * 
 * @author Tony
 *
 */
@Component
public class OrderDispatchConsumer {
	private final Logger logger = LoggerFactory.getLogger(OrderDispatchConsumer.class);

	@Autowired
	DispatchService dispatchService;

	@RabbitListener(queues = "orderDispatchQueue")
	public void messageConsumer(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
			throws Exception {
		// 执行业务操作
		try {
			// mq里面的数据转为json对象
			JSONObject orderInfo = JSONObject.parseObject(message);
			logger.warn("收到MQ里面的消息：" + orderInfo.toJSONString());
			String orderId = orderInfo.getString("orderId");
			dispatchService.dispatch(orderId);
			// ack - 告诉MQ，我已经收到啦
			channel.basicAck(tag, false);
		} catch (SQLException exception) {
			logger.error("MQ消费者报错啦，这个错误我们自己处理，不需要再发了", exception);
			// Nack - 告诉MQ，我处理有点问题，但是这个问题我能处理，不用继续给我发了 丢弃或者丢到死信队列
			channel.basicNack(tag, false, false);
		} catch (Exception e) {
			logger.error("出现了意料之外的异常，再重发一次", e);
			// Nack - 告诉MQ，我收到了，但是有意料不到的异常，再给我发一次。
			// requeue: true是继续， false是丢弃或者丢到死信队列
			channel.basicNack(tag, false, true);
			// 根据不同的异常，和业务需要，采取不通的措施
		}
		// 如果不给回复，就等这个consumer断开链接后再继续

	}
}
