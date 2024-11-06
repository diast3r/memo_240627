package com.memo.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memo.post.bo.PostBO;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class PostRestController {

	@Autowired
	private PostBO postBO;
	
	
	@PostMapping("/create")
	public Map<String, Object> create(
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpSession session) {
		
		// session에서 글쓴이 번호 가져옴
		int userId = (int)session.getAttribute("userId"); // (int)로 형변환 => 로그인 안 된 사람은 에러 발생(의도)
		String userLoginId = (String)session.getAttribute("userLoginId");
		
		// db insert
		int rowCount = postBO.addPost(userId, userLoginId, subject, content, file);
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		if (rowCount > 0) {
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "글을 저장하는데 실패했습니다. 관리자에게 문의해주세요.");
		}
		
		return result;
	}
}
