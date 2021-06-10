package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
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
	
	
	
}
