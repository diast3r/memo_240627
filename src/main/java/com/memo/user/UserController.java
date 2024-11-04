package com.memo.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	
	/**
	 * 회원가입 화면
	 * @return
	 */
	@GetMapping("/sign-up-view")
	public String signUpView() {
		//  defaultLayout 에 들어갈 레이아웃 조각(signUp.html) 경로만 내려주면 defaultLayout이 자동으로 채워짐. 
		return "user/signUp"; 
	}
	
	/**
	 * 로그인 화면
	 * @return
	 */
	@GetMapping("/sign-in-view")
	public String signInView() {
		return "user/signIn";
	}
	
	@GetMapping("/sign-out")
	public String signOut(HttpSession session) {
		// session 내용 비우기
		// User객체를 가져와서 파라미터로 넣으면 한 번에 지울 수도 있음.
		// 민감한 정보 들어있는 객체 가져오는 것은 안 좋을 수도 있으므로 
		session.removeAttribute("userId");
		session.removeAttribute("userLoginId");
		session.removeAttribute("userName");
		
		return "redirect:/user/sign-in-view";
	}
	
}
