package com.memo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.memo.post.bo.PostBO;
import com.memo.post.domain.Post;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostBO postBO;
	
	@GetMapping("/post-list-view")
	public String postListView(Model model, HttpSession session) {
		// 로그인 여부 확인(권한 검사)
		// TODO
		
		// Object로 리턴하기 때문에 다운캐스팅하기.
		// 변수 선언할 때 int로 하면 null에 대해 에러 발생하기 때문에 Integer로 저장.
		Integer userId = (Integer)session.getAttribute("userId");
		
		if (userId == null) {
			// 로그인 페이지로 이동
			return "redirect:/user/sign-in-view";
		}
		
		// db select => 로그인된 사람이 쓴 글만
		List<Post> postList = postBO.getPostListByUserId(userId);
		
		// Model에 담기
		model.addAttribute("postList", postList);
		
		return "post/postList";
	}
}
