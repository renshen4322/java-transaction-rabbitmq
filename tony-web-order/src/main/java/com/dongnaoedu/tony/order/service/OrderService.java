package com.dongnaoedu.tony.order.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author Tony
 *
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderService {

	private final Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	OrderDatabaseService orderDatabaseService;

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
		// 2. 提供调度数据
		RestTemplate restTemplate = createRestTemplate();
		String httpUrl = "http://127.0.0.1:8080/dispatch-api/dispatch?orderId=" + orderId;
		String result = restTemplate.getForObject(httpUrl, String.class);
		if (!"ok".equals(result)) {
			throw new Exception("订单创建失败，原因[分配送餐员接口失败]");
		}

		System.out.println("订单创建成功");

	}

	// 创建一个HTTP请求工具类
	public RestTemplate createRestTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		// 链接超时时间 > 3秒
		requestFactory.setConnectTimeout(3000);
		// 处理超时时间 > 2 秒
		requestFactory.setReadTimeout(2000);
		return new RestTemplate(requestFactory);
	}
}
