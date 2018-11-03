package com.dongnaoedu.tony.dispatch;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DispatchApplication {
	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(DispatchApplication.class).web(WebApplicationType.SERVLET).run(args);
		System.out.println("#系统启动完毕");
	}
}
