package com.ashish.linkedlnProject.postsService.service;

import com.ashish.linkedlnProject.postsService.auth.AuthContextHolder;
import com.ashish.linkedlnProject.postsService.client.ConnectionsServiceClient;
import com.ashish.linkedlnProject.postsService.dto.PersonDto;
import com.ashish.linkedlnProject.postsService.dto.PostCreateRequestDto;
import com.ashish.linkedlnProject.postsService.dto.PostDto;
import com.ashish.linkedlnProject.postsService.entity.Post;
import com.ashish.linkedlnProject.postsService.event.PostCreated;
import com.ashish.linkedlnProject.postsService.exception.ResourceNotFoundException;
import com.ashish.linkedlnProject.postsService.repository.PostsRepository;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.POST;
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
    private final ConnectionsServiceClient connectionsServiceClient;
    private final KafkaTemplate<Long, PostCreated> postCreatedKafkaTemplate;

    public PostService(PostsRepository postsRepository, ModelMapper modelMapper, ConnectionsServiceClient connectionsServiceClient) {
        this.postsRepository = postsRepository;
        this.modelMapper = modelMapper;
        this.connectionsServiceClient = connectionsServiceClient;
    }

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId) {
        log.info("creating post for user with id: {}", userId);
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);
        post = postsRepository.save(post);

        List<PersonDto> personDtoList = connectionsServiceClient.getFirstDegreeConnections(userId);
        for(PersonDto person : personDtoList) {
            PostCreated postCreated = PostCreated.builder()
                    .postId(post.getId())
                    .content(post.getContent())
                    .userId(userId)
                    .build();
            postCreatedKafkaTemplate.send("post_created_topics",postCreated);
        }
        return modelMapper.map(post, PostDto.class);

    }

    public PostDto getPostById(Long postId) {
        log.info("getting the post with id: {}", postId);
        Long userId = AuthContextHolder.getCurrentUserId();

        // call the connections serice from the posts service and pass the userid inside the header
//        List<PersonDto> personDtoList = connectionsServiceClient.getFirstDegreeConnections(userId);


        Post post = postsRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post with id " + postId + " not found"));
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
