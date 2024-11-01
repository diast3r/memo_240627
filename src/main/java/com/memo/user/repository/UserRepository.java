package com.memo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.memo.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	public UserEntity findByLoginId(String loginId); // 단건을 조회할 때는 null 또는 Entity 반환하므로 Optional<Entity>로 하는 게 정석임.
	
	public UserEntity findByLoginIdAndPassword(String loginId, String password); // password는 hasing된 값일 것임.
}
