package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice // 모든 예외를 가로챈다.
public class ControllerExceptionHandler {

	// javascript 를 리턴
	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) {
		// 1.  클라이언트에게 응답 시 Script가 좋음.
		// 2. Ajax(api), Android 통신 - DTO가 좋음. 
		return Script.back(e.getErrorMap().toString());
	}
	
	// 데이터를 리턴
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST); 
	}
	
//	@ExceptionHandler(CustomValidationException.class)
//	<?> - 타입 추론 ( 리턴 타입에 맞춰서 실행 시 자동으로 바뀐다 )
//	public CMRespDto<?> validationException(CustomValidationException e) {
//		return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap()); // 여기도 <Map<String, String>> -> <> 로 생략 가능
//	}
	
}
