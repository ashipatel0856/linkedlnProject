package com.ashish.linkedlnProject.postsService.service;

import com.ashish.linkedlnProject.postsService.dto.PostCreateRequestDto;
import com.ashish.linkedlnProject.postsService.dto.PostDto;
import com.ashish.linkedlnProject.postsService.entity.Post;
import com.ashish.linkedlnProject.postsService.exception.ResourceNotFoundException;
import com.ashish.linkedlnProject.postsService.repository.PostsRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    private final PostsRepository postsRepository;
    private final ModelMapper modelMapper;

    public PostService(PostsRepository postsRepository, ModelMapper modelMapper) {
        this.postsRepository = postsRepository;
        this.modelMapper = modelMapper;
    }

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId) {
        log.info("creating post for user with id: {}", userId);
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);
        post = postsRepository.save(post);
        return modelMapper.map(post, PostDto.class);

    }

    public PostDto getPostById(Long postId) {
        log.info("getting the post with id: {}", postId);
        Post post = postsRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post with id " + postId + " not found"));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        log.info("getting all the posts of user with id: {}", userId);
       List<Post> postList =  postsRepository.findByUserId(userId);

       return postList
               .stream()
               .map((element) -> modelMapper.map (element,PostDto.class))
               .collect(Collectors.toList());
    }
}
