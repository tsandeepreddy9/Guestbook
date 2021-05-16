package com.thadvai.boot.commandlinerunner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.thadvai.guestbook.model.User;
import com.thadvai.guestbook.persistence.RoleRepository;
import com.thadvai.guestbook.persistence.UserRepository;

@Component
@Order(2)
public class UserRunner implements CommandLineRunner {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository role;

	@Override
	public void run(String... args) throws Exception {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String password [] = {"pass"};
	    
	    userRepository.save(new User("admin", passwordEncoder.encode(password[0]), true, 
	    		Arrays.asList(role.findByName("ROLE_ADMIN"))));
	    userRepository.save(new User("user", passwordEncoder.encode(password[0]), true, 
	    		Arrays.asList(role.findByName("ROLE_USER"))));
		
	}

}
