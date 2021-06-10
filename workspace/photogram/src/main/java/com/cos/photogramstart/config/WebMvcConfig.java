package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer { // Web 설정 파일

	@Value("${file.path}")
	private String uploadFolder;
	
	// 정적인 데이터를 서빙하는 역할을하는 리소스 핸들러
	@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			WebMvcConfigurer.super.addResourceHandlers(registry);
			
			registry
				.addResourceHandler("/upload/**") // jsp 페이지에서 /upload/* 주소 패턴이 나오면 발동
				.addResourceLocations("file:///" + uploadFolder) // 이 경로로 바뀐다.
				.setCachePeriod(60 * 10 * 6) // 1시간 캐시
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
		}
}
