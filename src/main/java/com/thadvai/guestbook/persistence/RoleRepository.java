package com.thadvai.guestbook.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import com.thadvai.guestbook.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(String name);

    @Override
    void delete(Role role);
	
}
