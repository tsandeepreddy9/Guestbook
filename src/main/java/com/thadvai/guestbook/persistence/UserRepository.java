package com.thadvai.guestbook.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thadvai.guestbook.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByName(String name);
	
}
