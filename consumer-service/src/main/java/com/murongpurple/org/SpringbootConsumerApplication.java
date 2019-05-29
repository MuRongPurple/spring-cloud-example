package com.murongpurple.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Denny
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SpringbootConsumerApplication {

	@Bean
	@LoadBalanced //开启负载均衡注解
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootConsumerApplication.class, args);
	}

}
