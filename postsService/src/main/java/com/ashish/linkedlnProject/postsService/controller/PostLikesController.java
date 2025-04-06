package com.ashish.linkedlnProject.postsService.controller;

import com.ashish.linkedlnProject.postsService.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class PostLikesController {

    private final PostLikeService postLikeService;

    public PostLikesController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId) {
        postLikeService.likePost(postId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId) {
        postLikeService.unlikePost(postId);
        return ResponseEntity.noContent().build();
    }

}
