package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

// nativeQuery 는 void 나 int/Integer 로만 리턴할 수 있다.
//	@Modifying
//	@Query(value = "INSERT INTO comment(content, imageId, userId, createDate) VALUES(:content, :imageId, :principalId, now())", nativeQuery = true)
//	Comment mSave(String content, int imageId, int principalId);
}
