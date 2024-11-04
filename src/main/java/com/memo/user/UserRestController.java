package com.memo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memo.common.EncryptUtils;
import com.memo.user.bo.UserBO;
import com.memo.user.entity.UserEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/user")
@RestController
public class UserRestController {
	@Autowired
	private UserBO userBO;
	
	/**
	 * 아이디 중복확인 api<br>
	 * 
	 * @param loginId 
	 * @return
	 */
	@GetMapping("/is-duplicate-id")
	public Map<String, Object> isDuplicateId(
			@RequestParam("loginId") String loginId) {
		// DB Select (JPA)
		UserEntity user = userBO.getUserEntityByLoginId(loginId);
		boolean isDuplicate = false; // 중복 아님
		if (user != null) {
			isDuplicate = true;
		}
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		result.put("code", 200);
		result.put("is_duplicate_id", isDuplicate);
		
		return result;
	}
	
	/**
	 * 회원가입 API<br>
	 * @param loginId
	 * @param password
	 * @param name
	 * @param email
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String, Object> signUp(
			@RequestParam("loginId") String loginId, 
			@RequestParam("password") String password, 
			@RequestParam("name") String name, 
			@RequestParam("email") String email) {
		
		// 암호화 (md5 알고리즘)
		// 암호화 알고리즘 여러 가지 찾아보기.
		// md5는 같은 값을 넣으면 항상 같은 hasing된 값을 도출함.
		// aaaa => 74b8733745420d4d33f80c4663dc5e5 (항상)
		String hashedPassword = EncryptUtils.md5(password);
		
		
		
		// db insert
		UserEntity user = userBO.addUser(loginId, hashedPassword, name, email);
		
		
		// 응답값
		Map<String, Object> result = new HashMap<>();
		
		if (user != null) { 
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 500);
			result.put("error_message", "실패했습니다.");
		}
		
		return result;
	}
	
	
	@PostMapping("/sign-in")
	public Map<String, Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpServletRequest request // HttpSession 을 바로 불러와도 됨.
			) {
		
		// db select
		UserEntity user = userBO.getUserEntityByLoginIdPassword(loginId, password);
		
		Map<String, Object> result = new HashMap<>();
		if (user != null) {
			// 세션에 사용자 각각의 정보를 담는다.(사용자 각각을)
			// 세션에 담은 정보는 컨트롤러, bo 등 어디서나 쓸 수 있음.
			HttpSession session = request.getSession();
			session.setAttribute("userId", user.getId());
			session.setAttribute("userLoginId", user.getLoginId());
			session.setAttribute("userName", user.getName());
	
			result.put("code", 200);
			result.put("result", "성공");
		} else {
			result.put("code", 300);
			result.put("error_message", "존재하지 않는 사용자입니다.");
		}
		
		return result;
	}
	
}
