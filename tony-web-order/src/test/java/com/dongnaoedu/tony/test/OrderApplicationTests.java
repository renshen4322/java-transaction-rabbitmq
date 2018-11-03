package com.dongnaoedu.tony.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dongnaoedu.tony.order.OrderApplication;
import com.dongnaoedu.tony.order.distributedservice.OrderService2;
import com.dongnaoedu.tony.order.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class OrderApplicationTests {

	@Autowired
	OrderService2 orderService2;
	
	@Autowired
	OrderService orderService;

	@Before
	public void start() {
		System.out.println("开始测试##############");
	}

	@After
	public void finish() {
		System.out.println("结束##############");
	}

	@Test
	public void orderCreate() throws Exception {
		orderService.createOrder("tony", "买了根黄瓜");
	}
	
	@Test
	public void orderCreateDistributed() throws Exception {
		orderService2.createOrder("tony", "买了根黄瓜");
	}
}
