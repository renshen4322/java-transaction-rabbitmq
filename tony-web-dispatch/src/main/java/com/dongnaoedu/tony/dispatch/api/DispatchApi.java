package com.dongnaoedu.tony.dispatch.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dongnaoedu.tony.dispatch.service.DispatchService;

/**
 * 调度系统http API
 * 
 * @author Tony
 *
 */
@RestController
@RequestMapping("/dispatch-api")
public class DispatchApi {

	@Autowired
	private DispatchService dispatchService;

	// 下订单后，添加调度信息
	@GetMapping("/dispatch")
	public String lock(String orderId) {
		try {
			Thread.sleep(3000L);
			// 调用service
			dispatchService.dispatch(orderId);
			return "ok";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "exception";
	}

	
}
