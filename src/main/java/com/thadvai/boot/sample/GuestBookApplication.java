package com.thadvai.boot.sample;

import java.util.Arrays;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.thadvai.boot.config.GuestbookSecurityConfig;
import com.thadvai.boot.config.GuestbookWebMVCConfiguration;
import com.thadvai.guestbook.model.GuestbookEntry;
import com.thadvai.guestbook.model.Role;
import com.thadvai.guestbook.model.User;
import com.thadvai.guestbook.persistence.GuestbookEntryRepository;
import com.thadvai.guestbook.persistence.RoleRepository;
import com.thadvai.guestbook.persistence.UserRepository;

@SpringBootApplication
@ComponentScan("com.thadvai")
@EnableJpaRepositories("com.thadvai.guestbook")
@EntityScan("com.thadvai.guestbook.model")
public class GuestBookApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(GuestBookApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(new Class[] { GuestBookApplication.class, GuestbookSecurityConfig.class, GuestbookWebMVCConfiguration.class }, args);
	}
	
	/*@Bean
	CommandLineRunner init(RoleRepository role) {

		return args -> {

			Stream.of( //
					new Role("ROLE_ADMIN"),
					new Role("ROLE_USER"))
					.forEach(role::save);
		};
	}
	
	@Bean
	CommandLineRunner init(UserRepository user, RoleRepository role) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String password [] = {"pass"};
		return args -> {

			Stream.of( //
					new User("admin", passwordEncoder.encode(password[0]), true, Arrays.asList(role.findByName("ROLE_ADMIN"))),
					new User("user", passwordEncoder.encode(password[0]), true, Arrays.asList(role.findByName("ROLE_USER"))))
					.forEach(user::save);
		};
	}
	
	@Bean
	CommandLineRunner init(UserRepository userRepo, GuestbookEntryRepository guestbook) {

		return args -> {

			Stream.of( //
					new GuestbookEntry(userRepo.findByName("user1"), "first!!!"), //
					new GuestbookEntry(userRepo.findByName("user1"), "Hasta la vista, baby"))
					.forEach(guestbook::save);
		};
	}*/

}
