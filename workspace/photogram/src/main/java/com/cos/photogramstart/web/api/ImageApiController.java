package com.cos.photogramstart.web.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.service.LikesService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ImageApiController {

	private final ImageService imageService;
	private final LikesService likesService;
	
	@GetMapping("/api/image")
	public ResponseEntity<?> imageStory(
			@AuthenticationPrincipal PrincipalDetails principalDetails, 
			@PageableDefault(size=3) Pageable pageable){ // Pageable 을 쓸 때, 사이즈는 얼마, 정렬은 어떻게 등을 기본으로 잡아주기 위해 @PageableDefault 를 사용
		Page<Image> images =  imageService.imageStory(principalDetails.getUser().getId(), pageable);
		return new ResponseEntity<>(new CMRespDto<>(1, "성공", images), HttpStatus.OK);
	}
	
	@PostMapping("/api/image/{imageId}/likes")
	public ResponseEntity<?> likes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		likesService.likes(imageId, principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 성공", null), HttpStatus.CREATED);
	}

	@DeleteMapping("/api/image/{imageId}/likes")
	public ResponseEntity<?> unLikes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		likesService.unLikes(imageId, principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1, "좋아요 취소 성공", null), HttpStatus.OK);
	}
}
