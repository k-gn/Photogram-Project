package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserApiController {

	private final UserService userService;
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(
			@PathVariable int id, 
			@Valid UserUpdateDto dto, 
			BindingResult bindingResult, // 꼭 @Valid 다음에 파라미터로 적어야 동작한다.
			@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				errMap.put(error.getField(), error.getDefaultMessage());
				System.out.println(error.getDefaultMessage());
			}
			throw new CustomValidationApiException("유효성 검사 실패", errMap);
		}else {
			User userEntity = userService.update(id, dto.toEntity());
			principalDetails.setUser(userEntity);
			return new CMRespDto<>(1, "회원수정완료", userEntity);
		}
	}
}
