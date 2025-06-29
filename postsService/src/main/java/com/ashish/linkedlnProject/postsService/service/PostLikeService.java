package com.ashish.linkedlnProject.postsService.service;

import com.ashish.linkedlnProject.postsService.auth.AuthContextHolder;
import com.ashish.linkedlnProject.postsService.entity.Post;
import com.ashish.linkedlnProject.postsService.entity.PostLike;
import com.ashish.linkedlnProject.postsService.event.PostLiked;
import com.ashish.linkedlnProject.postsService.exception.BadRequestException;
import com.ashish.linkedlnProject.postsService.exception.ResourceNotFoundException;
import com.ashish.linkedlnProject.postsService.repository.PostLikeRepository;
import com.ashish.linkedlnProject.postsService.repository.PostsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service

public class PostLikeService {
    private static final Logger log = LoggerFactory.getLogger(PostLikeService.class);

    private final PostLikeRepository postLikeRepository;
    private final ModelMapper modelMapper;
    private final PostsRepository postsRepository;
    private final KafkaTemplate<String, PostLiked> postLikedKafkaTemplate;

    public PostLikeService(PostLikeRepository postLikeRepository, ModelMapper modelMapper, PostsRepository postsRepository, KafkaTemplate<String, PostLiked> postLikedKafkaTemplate) {
        this.postLikeRepository = postLikeRepository;
        this.modelMapper = modelMapper;
        this.postsRepository = postsRepository;
        this.postLikedKafkaTemplate = postLikedKafkaTemplate;
    }

    @Transactional
    public void likePost(Long postId) {
        Long userId = AuthContextHolder.getCurrentUserId();
        log.info("user with ID:{} Liking post with id {}", postId, postId);

        Post post= postsRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id:" + postId));


        boolean hasAlreadyLinked = PostLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (hasAlreadyLinked) throw new BadRequestException("you can not like this post again");

        PostLike postLike = new PostLike();
        postLike.setUserId(userId);
        postLike.setPostId(postId);
        postLikeRepository.save(postLike);


        //  SEND NOTIFICATION TO THE OWNER OF THE POST`
        PostLiked postLiked = PostLiked.builder()
                .postId(postId)
                .likedByUserId(userId)
                .ownerUserId(post.getUserId())
                .build();
        postLikedKafkaTemplate.send("post-liked-topic", postLiked);
    }

    @Transactional
    public void unlikePost(Long postId) {
        Long userId = 1l;
        log.info("user with ID:{} Liking post with id {}", postId, postId);
         postsRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id:" + postId));


        boolean hasAlreadyLinked = PostLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (hasAlreadyLinked) throw new BadRequestException("you can not unlike this post again");

        postLikeRepository.deleteByUserIdAndPostId(userId,postId);


    }
}
