package com.thadvai.boot.sample;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.thadvai.guestbook.model.GuestbookEntry;
import com.thadvai.guestbook.persistence.UserRepository;

@SpringBootTest
public class GuestbookEntryUnitTest {
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	void rejectsEmptyName() {
		assertThatExceptionOfType(IllegalArgumentException.class)//
			.isThrownBy(() -> new GuestbookEntry(null, "Guestbook Entry Unit test without name"));
	}
	
	@Test
	void rejectsEmptyText() {

		assertThatExceptionOfType(IllegalArgumentException.class)//
				.isThrownBy(() -> new GuestbookEntry(userRepository.findByName("user"), ""));
	}
	
}
