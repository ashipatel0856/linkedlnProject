package com.ashish.linkedlnProject.notification_service.PostsService.event;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostCreated {
    private Long ownerUserId;
    private Long postId;
    private Long userId;
    private String content;

}
