package com.cos.photogramstart.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer> {

	// 스토리 페이지 이미지 목록 가져오기 ( 내가 구독한 사람의 스토리 목록 )
	@Query(value = "SELECT * FROM image WHERE userId IN (SELECT toUserId FROM subscribe WHERE fromUserId = :principalId) ORDER BY id DESC",
					nativeQuery = true) // 페이징 처리 시 nativeQuery 일 경우 size(limit) 외에 설정은 직접 적어줘야된다.
	Page<Image> mStory(int principalId, Pageable pageable); // Pageable 객체를 같이 주면 알아서 페이징 처리를 한다.
}
