package com.ashish.linkedlnProject.postsService.auth;

import feign.RequestTemplate;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {


   @Override
   public void apply(RequestTemplate requestTemplate) {

       Long userId = AuthContextHolder.getCurrentUserId();
               if(userId != null){
                   requestTemplate.header("X-User-Id", userId.toString());
               }

   }
}
