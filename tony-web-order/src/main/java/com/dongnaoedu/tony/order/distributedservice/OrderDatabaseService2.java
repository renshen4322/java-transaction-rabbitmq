package com.dongnaoedu.tony.order.distributedservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

/**
 * 订单数据相关操作的service
 * 
 * @author Tony
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderDatabaseService2 {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * 1.保存订单记录 2.保存本地消息表
	 * 
	 * @param userId
	 *            用户ID
	 * @param orderId
	 *            订单编号
	 * @param orderContent
	 *            订单内容
	 * @throws Exception
	 *             抛个异常
	 */
	public void saveOrder(JSONObject orderInfo) throws Exception {
		String sql = "insert into table_order (order_id, user_id, order_content, create_time) values (?, ?, ?,now())";
		// 1. 添加订单记录
		int count = jdbcTemplate.update(sql, orderInfo.get("orderId"), orderInfo.get("userId"),
				orderInfo.get("orderContent"));

		if (count != 1) {
			throw new Exception("订单创建失败，原因[数据库操作失败]");
		}

		// 2. 本地消息表(表示这个数据要通知到其他系统)
		saveLocalMessage(orderInfo);
	}

	/**
	 * 保存本地消息表信息
	 * 
	 * @param orderId
	 *            订单编号，此处当做消息唯一编号
	 * @param messageContent
	 *            消息内容
	 * @throws Exception
	 */
	public void saveLocalMessage(JSONObject orderInfo) throws Exception {
		String sql = "insert into tb_distributed_message (unique_id, msg_content, msg_status, create_time) values (?, ?, ?, now())";
		int count = jdbcTemplate.update(sql, orderInfo.get("orderId"), orderInfo.toJSONString(), 0);

		if (count != 1) {
			throw new Exception("出现异常，原因[数据库操作失败]");
		}
	}
}
