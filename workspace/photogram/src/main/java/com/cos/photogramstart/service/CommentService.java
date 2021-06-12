package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public Comment write(String content, int imageId, int principalId) {
		
		// Tip. 객체를 만들 때 id 값만 담아서 insert 할 수 있다. (하나하나 findBy 하기 힘들 때 쓴다.)
		// 단. return 시에 image 객체와 user 객체는 id값만 가지고 있는 빈 객체를 리턴한다.
		Image image = new Image();
		image.setId(imageId);
		// 빈 객체가 아닌 데이터가 필요할 경우는 DB 에서 직접 가져와야 한다.
		User userEntity = userRepository.findById(principalId).orElseThrow(() -> new CustomApiException("유저 아이디를 찾을 수 없습니다."));
		
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setImage(image);
		comment.setUser(userEntity);
		
		return commentRepository.save(comment);
	}
	
	@Transactional
	public void remove(int id) {
		try {
			commentRepository.deleteById(id);
		} catch (Exception e) {
			throw new CustomApiException(e.getMessage());
		}
	}
	
}
