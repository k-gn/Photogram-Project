package com.cos.photogramstart.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;

@Controller
public class UserController {

	@PreAuthorize("#id == principal.user.id")
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id) {
		System.out.println(id);
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
