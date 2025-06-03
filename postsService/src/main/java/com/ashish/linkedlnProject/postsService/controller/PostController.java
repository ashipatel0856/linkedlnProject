package com.ashish.linkedlnProject.postsService.controller;

import com.ashish.linkedlnProject.postsService.auth.AuthContextHolder;
import com.ashish.linkedlnProject.postsService.dto.PostCreateRequestDto;
import com.ashish.linkedlnProject.postsService.dto.PostDto;
import com.ashish.linkedlnProject.postsService.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.html.HTMLTableCaptionElement;

import java.util.List;

@RestController
@RequestMapping("/core")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postCreateRequestDto,
                                                                   HttpServletRequest  httpServletRequest){
        PostDto postDto = postService.createPost(postCreateRequestDto,1L);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        Long userId = AuthContextHolder.getCurrentUserId();
        PostDto postDto = postService.getPostById(postId);
//        return new ResponseEntity<>(postDto, HttpStatus.OK);
        return ResponseEntity.ok(postDto);
    }


    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable Long userId){
        List<PostDto> posts = postService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(posts);
    }

}
