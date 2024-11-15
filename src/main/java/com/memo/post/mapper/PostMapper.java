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
	
	
	// input:int(loginId) + 페이징 정보 output:List<Post>
	public List<Post> selectPostListByUserId(
			@Param("userId") int userId, 
			@Param("standardId") Integer standardId, 
			@Param("direction") String direction, 
			@Param("limit") int limit);
	
	public int selectIdByUserIdAsSort(
			@Param("userId") int userId,
			@Param("sort") String sort);
	
	// input:params output:int or void
	public int insertPost(
			@Param(value="userId") int userId, 
			@Param(value="subject") String subject, 
			@Param(value="content") String content, 
			@Param(value="imagePath") String imagePath);
	
	// input: postId, userId
	// output: Post or null
	public Post selectPostByPostIdUserId(
			@Param("postId") int postId,
			@Param("userId") int userId);
	
	// input: postId, subject, content, imagePath    output: int or void
	public void updatePostByPostId(
			@Param("postId") int postId,
			@Param(value="subject") String subject, 
			@Param(value="content") String content, 
			@Param(value="imagePath") String imagePath);
			
	
	// input: postId
	// output: int(삭제된 행 개수)
	public int deletePostByPostId(int postId);
}
