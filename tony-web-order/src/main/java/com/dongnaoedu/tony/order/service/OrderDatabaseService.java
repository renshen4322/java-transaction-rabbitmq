package com.dongnaoedu.tony.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

/**
 * 数据库操作相关的service
 * 
 * @author Tony
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderDatabaseService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * 1.保存订单记录
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
	}
}
