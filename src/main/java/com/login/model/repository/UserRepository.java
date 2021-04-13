package com.login.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.login.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByIsActive(boolean flag);

	User findByUserIdAndIsActive(long userId, boolean flag);

	User findByUserEmailAndIsActive(String userEmail, boolean b);

}