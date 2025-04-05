package com.ashish.linkedlnProject.postsService.repository;

import com.ashish.linkedlnProject.postsService.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
