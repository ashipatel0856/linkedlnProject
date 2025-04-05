package com.ashish.linkedlnProject.postsService.dto;

import lombok.Data;

@Data
public class PostCreateRequestDto {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
