package com.ashish.linkedlnProject.postsService.repository;

import com.ashish.linkedlnProject.postsService.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {


    static boolean existsByUserIdAndPostId(Long userId, Long postId) {
        return false;
    }

    void deleteByUserIdAndPostId(Long userId, Long postId);
}
