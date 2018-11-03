package com.dongnaoedu.tony.order.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongnaoedu.tony.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	private final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	OrderService orderService;

	/**
	 * 创建订单
	 * 
	 * @param userId
	 *            用户编号
	 * @param orderContent
	 *            订单内容(购买了什么东西)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/create")
	public Object createOrder(String userId, String orderContent) {
		// 调用service方法获取
		try {
			orderService.createOrder(userId, orderContent);
		} catch (Exception e) {
			logger.error("出错啦", e);
			return ">>>>>>>>>>>>>>>>>failed<<<<<<<<<<<<<<<<<<<";
		}
		return ">>>>>>>>>>>>>>>>>successfully<<<<<<<<<<<<<<<<<<<";
	}

}
