package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikesService {

	private final LikesRepository likesRepository;

	@Transactional
	public void likes(int imageId, Integer principalId) {
		likesRepository.mLikes(imageId, principalId);
	}
	
	@Transactional
	public void unLikes(int imageId, Integer principalId) {
		likesRepository.mUnLikes(imageId, principalId);
	}
	
}
