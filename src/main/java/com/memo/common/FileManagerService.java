package com.memo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileManagerService {
	// 실제 업로드 된 이미지가 저장될 서버 경로
	// 이 프로젝트에선 workspace\images
	// 상수로 저장하기
	//***********경로 마지막에 '/' 붙일 것.
	public static final String FILE_UPLOAD_PATH = "D:\\메가스터디it아카데미\\6_Spring_project\\memo\\memo_workspace\\images/"; 
	
	// input: MultipartFile, userLoginId
	// output: String(이미지 경로)
	public String uploadFile(MultipartFile file, String loginId) {
		// images 하위 디렉토리 경로
		// 예: D:\\메가스터디it아카데미\\6_Spring_project\\memo\\memo_workspace\\images/didwnsgugh_01982370950/sun.png
		String directoryName = loginId + "_" + System.currentTimeMillis(); // didwnsgugh_01982370950
		String filePath = FILE_UPLOAD_PATH + directoryName + "/"; // D:\\메가스터디.......\\images/didwnsgugh_01982370950/
		
		// 폴더 생성
		File directory = new File(filePath); // java.io.util 패키지 선택
		if (directory.mkdir() == false) { // 폴더 생성 시 실패하면 경로를 null로 리턴하고 에러는 없게 구현
			return null;
		}
		
		// 파일 업로드
		try {
			byte[] bytes = file.getBytes();
			// TODO 파일명을 영어로 변환
			// ★★★★★★★★★한글이 들어간 이미지명은 업로드 안 됨. 나중에 파일을 영문자로 변경할 것.★★★★★★★★★★★★★★
			Path path = Paths.get(filePath + file.getOriginalFilename()); // D:\\메가스터디.......\\images/didwnsgugh_01982370950/sun.png
			// 파일 업로드
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null; // 이미지 업로드 시 실패하면 경로를 null로 리턴(에러 아님) 
		} // 예외처리: try-catch. bo에서 에러를 처리하는 것은 이상하므로.
		
		
		// 파일 업로드가 성공ㅇ하면 이미지 url path 리턴
		// 주소는 이렇게 될 것이다-를 정해주는 것임.(예언)
		// /images/didwnsgugh_01982370950/sun.png
		// => /images/directoryName/file.getOriginalFilename()
		return "/images/" + directoryName + "/" + file.getOriginalFilename();
	}
	
	
	// 서버에 업로드된 이미지 삭제
	// input: db에 저장된 경로(imagePath)
	// output: X
	public void deleteFile(String imagePath) {
		// D:\\메가스터디it아카데미\\6_Spring_project\\memo\\memo_workspace\\images//images/asdf_1730889214464/test.png (FILE_UPLOAD_PATH + imagePath)
		// D:\\메가스터디it아카데미\\6_Spring_project\\memo\\memo_workspace\\images/ (FILE_UPLOAD_PATH)
		// /images/asdf_1730889214464/test.png (db 에 저장된 경로)
		// "/images/" 겹치므로 제거해야 함.
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		
		// 삭제할 이미지가 있는가?
		if (Files.exists(path)) {
			// 이미지 삭제 
			try {
				Files.delete(path);
			} catch (IOException e) {
				log.info("[파일매니저 파일삭제] imagePath:{}", imagePath);
				return;
			}
			
			// 폴더(directory) 삭제
			// 현재 path(이미지 파일) 의 상위 경로(디렉토리) 구하기
			path = path.getParent();
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					log.info("[파일매니저 폴더삭제] imagePath:{}", imagePath);
				}
			}
			
		}
		
	}
}
