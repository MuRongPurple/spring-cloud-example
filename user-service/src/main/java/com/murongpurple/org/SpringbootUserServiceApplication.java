package com.murongpurple.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Denny
 */
@EnableDiscoveryClient //兼容多种服务注册服务，eureka、zookeeper等
@SpringBootApplication
public class SpringbootUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootUserServiceApplication.class, args);
	}

}
