package com.ashish.linkedlnProject.postsService.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostLiked {
    private Long postId;
    private Long likedByUserId;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getLikedByUserId() {
        return likedByUserId;
    }

    public void setLikedByUserId(Long likedByUserId) {
        this.likedByUserId = likedByUserId;
    }
}
