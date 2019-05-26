package com.murongpurple.org.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
public class TestRestTemplateController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DiscoveryClient discoveryClient;

    @ResponseBody
    @GetMapping("/msg")
    public Object getMsgFromUserService(){
        List<ServiceInstance> serviceInstanceList =   discoveryClient.getInstances("user-service");
        ServiceInstance serviceInstance = serviceInstanceList.get(0);
        String url = serviceInstance.getUri().toString()+"/index2";
        String msg = restTemplate.getForObject(url,String.class);
        return msg;
    }
}
