package com.ashish.linkedlnProject.ConnectionsService.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long userId = AuthContextHolder.getCurrentUser();
        if(userId != null) {
            requestTemplate.header("X-User-Id", userId.toString());
        }
    }
}
