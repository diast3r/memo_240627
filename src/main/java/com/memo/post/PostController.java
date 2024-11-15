package com.memo.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.post.bo.PostBO;
import com.memo.post.domain.Post;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/post")
public class PostController {
	
	@Autowired
	private PostBO postBO;
	
	@GetMapping("/post-list-view")
	public String postListView(
			@RequestParam(value = "prevId", required = false) Integer prevIdParam, // 이전 페이지 페이징을 위한 파라미터 
			@RequestParam(value = "nextId", required = false) Integer nextIdParam, // 다음 페이지 페이징을 위한 파라미터
			Model model, HttpSession session) {
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
		List<Post> postList = postBO.getPostListByUserId(userId, prevIdParam, nextIdParam);
		int prevId = 0;
		int nextId = 0;
		
		if (postList.isEmpty() == false) { // postList가 비어있지 않은 경우 페이징 정보 세팅, MyBatis는 List로 null을 반환하지 않으므로 size()나 isEmpty()사용 
			nextId = postList.get(postList.size() - 1).getId(); // postList의 마지막 post의 id 가져오기
			prevId = postList.get(0).getId(); // postList의 마지막 post의 id 가져오기
			
			// 이전 페이지가 없는가? -> 그렇다면 0
			// select `id` from `post` where `userId` = ? order by `id` desc limit 1 => post 테이블의 가장 큰(마지막) id
			if (postBO.isPrevLastPageByUserId(userId, prevId)) { // boolean return
				prevId = 0;
			}
			
			// 다음 페이지가 없는가? -> 그렇다면 0
			// select `id` from `post` where `userId` = ? order by `id` desc limit 1 => post 테이블의 가장 작은(첫 번째) id
			if (postBO.isNextLastPageByUserId(userId, nextId)) { // boolean return
				nextId = 0;
			}
		}
		
		// Model에 담기
		model.addAttribute("postList", postList);
		model.addAttribute("nextId", nextId);
		model.addAttribute("prevId", prevId);
		
		return "post/postList";
	}
	
	
	/**
	 * 글쓰기 화면
	 * @return
	 */
	@GetMapping("/post-create-view")
	public String postCreateView() {
		
		
		return "post/postCreate";
	}
	
	/**
	 * 글 상세 화면<br>
	 * 
	 * @param postId
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/post-detail-view")
	public String postDetailView(
			@RequestParam("postId") int postId, 
			HttpSession session,
			Model model) {
		
		
		// db select(Mybatis) -> postId, userID 로 조회 (글 작성자인지 확인하기 위해)
		int userId = (int)session.getAttribute("userId");
		Post post = postBO.getPostByPostIdUserId(postId, userId);
		
		
		// model 담기
		model.addAttribute("post", post);
		
		
		return "post/postDetail";
	}
}
