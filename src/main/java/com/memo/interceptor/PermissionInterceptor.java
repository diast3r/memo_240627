package com.memo.interceptor;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PermissionInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler) throws IOException {
		
		// 요청 uri(url) path
		String uri = request.getRequestURI();
//		log.info("[@@@@@@@@@@ preHandle] uri:{}", uri);
		
		// 로그인 여부 -> 세션
		HttpSession session = request.getSession();
		Integer userId = (Integer)session.getAttribute("userId");
		
		// /post 로 시작하고 비로그인 => 로그인 화면으로 이동 (redirect 302), 컨트롤러 수행 방지
		if (uri.startsWith("/post") && userId == null) {
			// redirect
			response.sendRedirect("/user/sign-in-view");
			
			// 컨트롤러 수행 방지 (안 하면 꼬였을 때 이상하게 동작될 수 있음.
			return false;
		}
		
		// /user로 시작 && 로그인 => 글 목록으로 이동, 컨트롤러 수행 방지
		if (uri.startsWith("/user") && userId != null) {
			response.sendRedirect("/post/post-list-view");
			
			return false;
		}
			
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler,
			ModelAndView mav) {
		
		// view, model이 있다는건 html이 해석되기 전
//		log.info("[$$$$$$$$$ postHandle]");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, 
			HttpServletResponse response, Object handler,
			Exception ex) {
		
		// html이 렌더링이 끝난 상태
//		log.info("[########## afterCompletion]");
	}
}
