package com.dongnaoedu.tony.order.distributedservice;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author Tony
 *
 */
@Service
public class OrderService2 {

	private final Logger logger = LoggerFactory.getLogger(OrderService2.class);

	@Autowired
	OrderDatabaseService2 orderDatabaseService;

	@Autowired
	MQService mqService;
	
	/**
	 * 创建订单
	 * 
	 * @throws Exception
	 */
	public void createOrder(String userId, String orderContent) throws Exception {
		// 订单号生成
		String orderId = UUID.randomUUID().toString();
		JSONObject orderInfo = new JSONObject();
		orderInfo.put("orderId", orderId);
		orderInfo.put("userId", userId);
		orderInfo.put("orderContent", orderContent);
		
		// 1. 数据库操作
		orderDatabaseService.saveOrder(orderInfo);
		// 2. 马上发送MQ消息
		mqService.sendMsg(orderInfo);
		
		logger.warn("订单创建成功");

	}
}
