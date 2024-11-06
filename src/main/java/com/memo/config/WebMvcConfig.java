package com.memo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.memo.common.FileManagerService;

@Configuration // 설정 관련 Spring Bean 등록
public class WebMvcConfig implements WebMvcConfigurer {
	
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
