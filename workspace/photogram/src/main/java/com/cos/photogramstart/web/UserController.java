package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
//	@PreAuthorize("#id == principal.user.id")
	@GetMapping("/user/{pageUserId}")
	public String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		UserProfileDto dto = userService.userProfile(pageUserId, principalDetails.getUser().getId());
		model.addAttribute("dto", dto);
		
		return "user/profile";
	}

	@GetMapping("/user/{id}/update")
	public String update(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//		System.out.println("세션 정보 : " + principalDetails.getUser());
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
//		System.out.println("직접 찾은 세션 정보 : " + mPrincipalDetails.getUser());
		
		return "user/update";
	}
	
}
