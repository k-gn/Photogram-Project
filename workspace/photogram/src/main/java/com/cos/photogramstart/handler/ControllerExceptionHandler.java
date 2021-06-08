package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice // 모든 예외를 가로챈다.
public class ControllerExceptionHandler {

	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) {
		// 1.  클라이언트에게 응답 시 Script가 좋음.
		// 2. Ajax , Android 통신 - DTO가 좋음. 
		return Script.back(e.getErrorMap().toString());
	}
	
//	@ExceptionHandler(CustomValidationException.class)
	// <?> - 타입 추론 ( 리턴 타입에 맞춰서 실행 시 자동으로 바뀐다 )
//	public CMRespDto<?> validationException(CustomValidationException e) {
//		return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap());
//	}
	
}
