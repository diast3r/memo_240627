package com.memo.post.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.memo.post.domain.Post;

@Mapper
public interface PostMapper {
	// input: X output: List<Map<String, Object>>
	public List<Map<String, Object>> selectPostList();
	
	
	// input:int(loginId) output:List<Post>
	public List<Post> selectPostListByUserId(int userId);
	
	// input:params output:int or void
	public int insertPost(
			@Param(value="userId") int userId, 
			@Param(value="subject") String subject, 
			@Param(value="content") String content, 
			@Param(value="imagePath") String imagePath
			);
	
	// input: postId, userId
	// output: Post or null
	public Post selectPostByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
}
