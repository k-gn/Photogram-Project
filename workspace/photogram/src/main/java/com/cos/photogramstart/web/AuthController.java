package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@Controller 
@RequiredArgsConstructor // final 필드를 DI 할 때 사용
public class AuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto dto, BindingResult bindingResult) {
		// @Valid 체크 후 문제가 생기면 BindingResult 로 매핑(getFieldErrors)된다.
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errMap.put(error.getField(), error.getDefaultMessage());
				System.out.println(error.getDefaultMessage());
			}
			throw new CustomValidationException("유효성 검사 실패", errMap);
		}else {
			User user = dto.toEntity();
			User userEntity = authService.signup(user);
			System.out.println(userEntity);
			return "auth/signin";
		}
	}
}
