package com.memo.post.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data // getter, setter 포함하는 어노테이션
public class Post {
	private int id;
	private int userId;
	private String subject;
	private String content;
	private String imagePath;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
