package com.cos.controllerdemo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.controllerdemo.domain.User;

@RestController
public class HttpResponseJsonController {

	@GetMapping("/resp/json")
	public String respJson() {
		// 자동으로 json 형태로 리턴된다.
		return "{\"username\" : \"cos\"}";
	}
	
	@GetMapping("/resp/json/object")
	public User respJsonObject() {
		User user = new User();
		user.setUsername("홍길동");
		// 자동으로 json 형태로 리턴된다.
		return user; // 1. MessageConverter (objectMapper) 가 자동으로 JavaObject를 Json으로 변경해서 응답해준다.
	}						// 2. RestController 일 때만 작동한다.
}
