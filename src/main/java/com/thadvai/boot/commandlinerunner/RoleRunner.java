package com.thadvai.boot.commandlinerunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.thadvai.guestbook.model.Role;
import com.thadvai.guestbook.persistence.RoleRepository;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RoleRunner implements CommandLineRunner {
	
	@Autowired
	RoleRepository role;

	@Override
	public void run(String... args) throws Exception {
		role.save(new Role("ROLE_ADMIN"));
		role.save(new Role("ROLE_USER"));
	}
	
	
	
}
