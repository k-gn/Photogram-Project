package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {
	
	// 외부에 upload 폴더를 만든 이유 : 내부에 폴더를 만들면 target 으로 배포되는 시간과 페이지가 로딩되는 시간 차이 때문에 엑박이 뜰 수 있다.
	// (외부에 두면 배포를 안하고 찾아온다.)
	@Value("${file.path}") // yml or properties 에 내가 만든 key 값을 가져올 수 있다.
	private String uploadFolder;

	private final ImageRepository imageRepository;
	
	@Transactional // 일의 최소 단위, DB에 변형을 주는 일을 할 때 붙여주는 습관을 들이자
	public void upload(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		
		UUID uuid = UUID.randomUUID(); // 파일 구분을 위한 값 (유일성이 보장된 암호)
		String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
		
		Path imageFilePath = Paths.get(uploadFolder + imageFileName);
		
		// 통신, I/O -> 예외가 발생할 수 있다.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes()); // 해당 경로에 파일 저장하기
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		imageRepository.save(image);
		
	}

	// @Transactional : 영속성 세션 영역을 컨트롤러단 영역까지 유지시킬 수 있는 기능도 있다.
	@Transactional(readOnly = true) // 영속성 컨텍스트 변경 감지를 해서, 더티체킹, flush(반영) X
	public Page<Image> imageStory(int principalId, Pageable pageable){
		Page<Image> images = imageRepository.mStory(principalId, pageable);
		
		images.forEach(image -> {
			image.setLikeCount(image.getLikes().size());
			image.getLikes().forEach(like -> {
				if(like.getUser().getId() == principalId) { // 해당 이미지에 내가 좋아요를 했다면
					image.setLikeState(true);
				}
			});
		});
		
		return images;
	}

	@Transactional(readOnly = true)
	public List<Image> popular() {
		return imageRepository.mPopular();
	}
	
	// 테이블 삭제 시 연관관계가 있다면 자식테이블 부터 삭제해야 한다.
	
}
