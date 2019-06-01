package com.murongpurple.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Denny
 */
//@EnableCircuitBreaker  //开启熔断功能
//@EnableDiscoveryClient //开启eureka注册发现功能
//@SpringBootApplication
@SpringCloudApplication //包含了上述三种注解
@EnableFeignClients
public class SpringbootConsumerApplication {

	@Bean
	@LoadBalanced //开启负载均衡注解(msg1中的方式就访问失败了)
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringbootConsumerApplication.class, args);
	}

}
