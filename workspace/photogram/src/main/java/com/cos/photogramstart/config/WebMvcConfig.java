package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer { // Web 설정 파일 (웹과 관련된 처리 및 자동구성된 스프링 MVC 구성에 Formatter , MessageConverter 등을 추가등록할 수 있다)

	@Value("${file.path}")
	private String uploadFolder;
	
	// 정적인 데이터를 서빙하는 역할을하는 리소스 핸들러
	// 리소스 등록 및 핸들러를 관리하는 객체인 ResourceHandlerRegistry를 통해 리소스 위치와 이 리소스와 매칭될 url을 등록
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			WebMvcConfigurer.super.addResourceHandlers(registry);
			
			registry
				.addResourceHandler("/upload/**") // jsp 페이지에서 /upload/* 주소 패턴이 나오면 발동
				.addResourceLocations("file:///" + uploadFolder) // 이 경로로 바뀐다.
				.setCachePeriod(60 * 10 * 6); // 캐시가 얼마나 지속할 지 정하는 메서드
		}
}
