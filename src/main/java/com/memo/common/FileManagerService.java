package com.memo.common;

import java.io.File;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
		
		return filePath;
	}
}
