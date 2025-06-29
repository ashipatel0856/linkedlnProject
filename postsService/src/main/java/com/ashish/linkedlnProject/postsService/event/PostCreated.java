package com.ashish.linkedlnProject.postsService.event;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostCreated {
    private Long ownerUserId;
    private Long postId;
    private Long userId;
    private String content;
//
//    public Long getOwnerUserId() {
//        return ownerUserId;
//    }
//
//    public void setOwnerUserId(Long ownerUserId) {
//        this.ownerUserId = ownerUserId;
//    }
//
//    public Long getPostId() {
//        return postId;
//    }
//
//    public void setPostId(Long postId) {
//        this.postId = postId;
//    }
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
}
