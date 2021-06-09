package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscribeService {

	private final SubscribeRepository subscribeRepository;
	
	@Transactional // 보통 DB에 영향을 줄 때 써준다.
	public void subscribe(int fromUserId, int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		} catch (Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}
	}
	
	@Transactional
	public void unSubscribe(int fromUserId, int toUserId) {
			subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
	
}
