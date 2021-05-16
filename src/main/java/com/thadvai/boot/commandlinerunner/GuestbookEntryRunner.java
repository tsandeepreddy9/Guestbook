package com.thadvai.boot.commandlinerunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.thadvai.boot.sample.GuestBookApplication;
import com.thadvai.guestbook.model.GuestbookEntry;
import com.thadvai.guestbook.persistence.GuestbookEntryRepository;
import com.thadvai.guestbook.persistence.UserRepository;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class GuestbookEntryRunner implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(GuestbookEntryRunner.class);
	
	@Autowired
	GuestbookEntryRepository guestbookEntryRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		logger.info("user details {}",userRepository.findByName("user"));
		guestbookEntryRepository.save(new GuestbookEntry(userRepository.findByName("user"), 
				"First book Entry"));
		guestbookEntryRepository.save(new GuestbookEntry(userRepository.findByName("user"), 
				"User second Entry"));
		
		guestbookEntryRepository.findAll().forEach((city) -> {
            logger.info("{}", city.getUser());
        });
	}
	
}
