package com.murongpurple.org.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "user-service",fallback = UserClientFallBack.class)
public interface UserClient {
    @GetMapping("/api/user/{id}")
    String queryById(@PathVariable("id") Long id);//注意，返回值必须和远程端一致，否则报错
}
