package com.cos.controllerdemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HttpRespController {

	@GetMapping("/txt")
	public String txt() {
		return "a.txt"; // 프레임워크 사용 (틀이 이미 정해져 있음) - 일반 정적 파일들은 resources/static 폴더 내부가 디폴트 경로이다.
	}
	
	@GetMapping("/mus")
	public String mus() {
		return "b";  // 머스태치 템플릿 엔진 라이브러리 등록 완료 - templates 폴더안에 .mustache 를 두면 확장자 없이 파일명만 적어도 자동으로 찾아간다.
	}

	@GetMapping("/jsp")
	public String jsp() {
		return "c";  // jsp 는 따로 지원하지 않아서 기본적으로 경로생성 및 설정파일(뷰리졸버)을 설정해줘야 한다. (src/main/webapp 폴더가 디폴트 경로)
	}
}
