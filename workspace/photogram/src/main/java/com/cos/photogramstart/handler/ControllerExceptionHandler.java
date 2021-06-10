package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice // 모든 예외를 가로챈다.
public class ControllerExceptionHandler {
	
	// 1.  클라이언트에게 응답 시 Script가 좋음.
	// 2. Ajax(api), Android 통신 - DTO가 좋음. 
	
	// javascript 를 리턴
	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) {
		if(e.getErrorMap() == null) {
			return Script.back(e.getMessage());
		}else {
			return Script.back(e.getErrorMap().toString());
		}
	}
	
	@ExceptionHandler(CustomException.class)
	public String exception(CustomException e) {
			return Script.back(e.getMessage());
	}
	
	// 데이터를 리턴
	@ExceptionHandler(CustomValidationApiException.class)
	public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST); 
	}
	
	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<?> apiException(CustomApiException e) {
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST); 
	}
	
//	@ExceptionHandler(CustomValidationException.class)
//	<?> - 타입 추론 ( 리턴 타입에 맞춰서 실행 시 자동으로 바뀐다 )
//	public CMRespDto<?> validationException(CustomValidationException e) {
//		return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap()); // 여기도 <Map<String, String>> -> <> 로 생략 가능
//	}
	
}
