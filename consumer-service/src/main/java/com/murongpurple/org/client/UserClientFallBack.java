package com.murongpurple.org.client;

import org.springframework.stereotype.Component;

/**
 * 熔断类
 */
@Component
public class UserClientFallBack implements UserClient {
    @Override
    public String queryById(Long id) {
        return "服务器异常";
    }
}
