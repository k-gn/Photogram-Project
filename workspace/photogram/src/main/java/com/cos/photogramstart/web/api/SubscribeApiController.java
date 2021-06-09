package com.cos.photogramstart.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

// 데이터만 RETURN 하는 Controller - API Controller
@RestController
@RequiredArgsConstructor
public class SubscribeApiController {
	
	private final SubscribeService subscribeService;

	// 구독하기
	@PostMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDedetails, @PathVariable int toUserId) {
		subscribeService.subscribe(principalDedetails.getUser().getId(), toUserId);
		return new ResponseEntity<>(new CMRespDto<>(1, "구독하기 성공!", null), HttpStatus.OK);
	}
	
	// 구독 취소하기
	@DeleteMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> unSubscribe(@AuthenticationPrincipal PrincipalDetails principalDedetails, @PathVariable int toUserId) {
	    subscribeService.unSubscribe(principalDedetails.getUser().getId(), toUserId);
		return new ResponseEntity<>(new CMRespDto<>(1, "구독 취소하기 성공!", null), HttpStatus.OK);
	}
}
