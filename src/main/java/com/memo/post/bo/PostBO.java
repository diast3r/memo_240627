package com.memo.post.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.memo.common.FileManagerService;
import com.memo.post.domain.Post;
import com.memo.post.mapper.PostMapper;

@Service
public class PostBO {
	@Autowired
	private PostMapper postMapper;
	@Autowired
	private FileManagerService fileManagerService;
	
	public List<Post> getPostListByUserId(int userId) {
		return postMapper.selectPostListByUserId(userId);
	}
	
	
	// input: userId(컨트롤러가 세션에서 꺼내서), userLoginId(컨트롤러가 세션에서 꺼내서), subject, content, file => imgPath
	// output: int(성공한 행 개수)
	public int addPost(int userId, String userLoginId, 
			String subject, String content, MultipartFile file) {
		
		String imagePath = null;
		
		// file to imagePath
		// file이 있을 때에만 업로드 => 있을 시 imagePath를 얻어냄
		// imagePath를 얻는 코드는 common 패키지 안에 클래스를 만들어서 여러 곳에서 가져다 쓸 것임.
		if (file != null) {
			imagePath = fileManagerService.uploadFile(file, userLoginId);
		}
		
		return 0;
		//return postMapper.insertPost(userId, subject, content, imagePath);
		
	}
	
}
