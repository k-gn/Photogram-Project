package com.cos.controllerdemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller // File을 응답하는 컨트롤러 (클라이언트가 브라우저면 .html)
@RestController // Data를 응답하는 컨트롤러로 만들기 (클라이언트가 핸드폰이면 data)
public class HttpController {

	@GetMapping("/get")
	public String get() {
		
		return "get 요청됨";
	}
	
	@PostMapping("/post")
	public String post() {
		
		return "post 요청됨";
	}
	
	@PutMapping("/put")
	public String put() {
		
		return "put 요청됨";
	}
	
	@DeleteMapping("/delete")
	public String delete() {
		
		return "delete 요청됨";
	}
}
