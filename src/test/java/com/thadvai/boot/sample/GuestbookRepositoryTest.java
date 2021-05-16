package com.thadvai.boot.sample;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.thadvai.guestbook.model.GuestbookEntry;
import com.thadvai.guestbook.model.Role;
import com.thadvai.guestbook.model.User;
import com.thadvai.guestbook.persistence.GuestbookEntryRepository;
import com.thadvai.guestbook.persistence.RoleRepository;
import com.thadvai.guestbook.persistence.UserRepository;

@SpringBootTest
@Transactional
public class GuestbookRepositoryTest {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GuestbookEntryRepository repository;
	
	@Test
	void persistsGuestbookEntry() {
		
	    User user = userRepository.findByName("user");
		GuestbookEntry entry = repository.save(new GuestbookEntry(user, "Integration test By JUnit"));
		
		assertThat(repository.findAll()).contains(entry);
	}
	
}
