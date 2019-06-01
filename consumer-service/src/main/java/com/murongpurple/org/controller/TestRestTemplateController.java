package com.murongpurple.org.controller;

import com.murongpurple.org.client.UserClient;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@DefaultProperties(defaultFallback = "getMsgFromUserServiceFallbackMethod") //该controller下熔断发生后调用的方法
public class TestRestTemplateController {

    @Autowired
    UserClient userClient;

    @Autowired
    RestTemplate restTemplate;

    /**
     * eureka组件，可以获取ip端口信息
     */
    @Autowired
    DiscoveryClient discoveryClient;

    /**
     * 使用eureka组件获取ip信息
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/msg1")
    public Object getMsgFromUserService1() {
        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances("user-service");
        ServiceInstance serviceInstance = serviceInstanceList.get(0);
        String url = serviceInstance.getUri().toString() + "/api/index2";
        String msg = restTemplate.getForObject(url, String.class);
        return msg;
    }

    /**
     * restTemplate开启负载均衡ribbon后
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/msg2")
    public Object getMsgFromUserService2() {
        //内部会根据user-service去找对应的ip port并且是负载均衡的
        String url = "http://user-service/api/index2";
        String msg = restTemplate.getForObject(url, String.class);
        return msg;
    }

    /**
     * 启用熔断hystrix，单个方法配置
     *
     * @return
     */
    @HystrixCommand(fallbackMethod = "getMsgFromUserService3FallbackMethod") //可以自定义该方法的熔断回调方法
    @ResponseBody
    @GetMapping("/msg3")
    public Object getMsgFromUserService3() {
        //内部会根据user-service去找对应的ip port并且是负载均衡的
        String url = "http://user-service/api/index2";
        String msg = restTemplate.getForObject(url, String.class);
        return msg;
    }

    /**
     * 启用熔断hystrix，单个方法配置(自定义熔断时间)
     *
     * @return
     */
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    @ResponseBody
    @GetMapping("/msg4")
    public Object getMsgFromUserService4() {
        //内部会根据user-service去找对应的ip port并且是负载均衡的
        String url = "http://user-service/api/index2";
        String msg = restTemplate.getForObject(url, String.class);
        return msg;
    }

    /**
     * 启用熔断hystrix，单个方法配置(配置断路，恢复的时间条件)
     *
     * @return
     */
    @HystrixCommand(commandProperties = {//断路条件，恢复时间一般不修改，直接默认配置
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),//每10次请求统计一次，默认20次统计
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),//开启断路模式closed 10秒，默认5秒
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")//失败次数超过60%开启断路模式
    })
    @ResponseBody
    @GetMapping("/msg5")
    public Object getMsgFromUserService5(int id) {
        //内部会根据user-service去找对应的ip port并且是负载均衡的
        String url = "http://user-service/api/index3" + "?id=" + id;
        String msg = restTemplate.getForObject(url, String.class);
        return msg;
    }

    //@HystrixCommand  //可以使用spring的熔断，也可以使用feign的熔断,同时使用，优先使用feign的熔断
    @ResponseBody
    @GetMapping("/msg/{id}")
    public Object getMsgFromUserService6(@PathVariable("id") Long id) {
        String msg = userClient.queryById(id);
        return msg;
    }

    /**
     * msg3熔断方法
     *
     * @return
     */
    public Object getMsgFromUserService3FallbackMethod() {
        return "3服务器繁忙，请稍后重试";
    }

    /**
     * controller下所有的熔断方法
     *
     * @return
     */
    public Object getMsgFromUserServiceFallbackMethod() {
        return "服务器繁忙，请稍后重试";
    }
}
