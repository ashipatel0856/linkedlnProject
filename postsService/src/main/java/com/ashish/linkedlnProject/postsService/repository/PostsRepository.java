package com.ashish.linkedlnProject.postsService.repository;

import com.ashish.linkedlnProject.postsService.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId);
}
