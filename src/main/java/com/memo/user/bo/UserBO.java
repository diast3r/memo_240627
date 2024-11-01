package com.memo.user.bo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.memo.common.EncryptUtils;
import com.memo.user.entity.UserEntity;
import com.memo.user.repository.UserRepository;

@Service
public class UserBO {
	@Autowired
	private UserRepository userRepository;
	
	// 컨트롤러한테
	// input: loginId
	// output: UserEntity
	public UserEntity getUserEntityByLoginId(String loginId) {
		return userRepository.findByLoginId(loginId);
	}
	
	// input: 파라미터 4개
	// output: UserEntity
	public UserEntity addUser(String loginId, String password, 
			String name, String email) {
		return userRepository.save( // save 메소드는 null을 리턴할 수 없으므로 try-catch로 처리하는 게 더 정확함. 
				UserEntity.builder()
				.loginId(loginId)
				.password(password)
				.name(name)
				.email(email)
				.build()); // lombok의 builder로 UserEntity 구성.
	}
	
	public UserEntity getUserEntityByLoginIdPassword(String loginId, String password) {
		// 비밀번호 해싱
		String hashedPassword = EncryptUtils.md5(password);
		
		// 조회
		return userRepository.findByLoginIdAndPassword(loginId, hashedPassword);
		
		
	}
	
}
