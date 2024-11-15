package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;
import com.memo.interceptor.PermissionInterceptor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration // 설정 관련 Spring Bean 등록
public class WebMvcConfig implements WebMvcConfigurer {
	
	private final PermissionInterceptor interceptor;
	
	// 인터셉터 설정
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry
		.addInterceptor(interceptor)
		.addPathPatterns("/**") // 모든 주소에 대해서
		.excludePathPatterns("/css/**", "/img/**", "images/**", "/user/sign-out"); // 정적자원에 대한 요청은 인터셉터 동작 안 하도록 설정
	}
	
	// 예언된 이미지 path와 서버에 업로드된 실제 이미지 매핑.
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
		.addResourceHandler("/images/**") // path  http://localhost/images/asdf_1730889214464/test.png 로 들어온 요청을 잡아줌.
		.addResourceLocations("file:///" + FileManagerService.FILE_UPLOAD_PATH); // file:// => 맥 또는 리눅스 file:/// => 윈도우
		
		// addREsourceHandler(String) => 요청에 대한 매핑
		// addResourceLocations(String) => 요청에 응답해줄 자원에 대한 매핑
	}

}
