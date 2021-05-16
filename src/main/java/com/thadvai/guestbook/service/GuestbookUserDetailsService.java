package com.thadvai.guestbook.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thadvai.guestbook.model.Role;
import com.thadvai.guestbook.model.User;
import com.thadvai.guestbook.persistence.UserRepository;

@Service
@Transactional
public class GuestbookUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = userRepository.findByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), user.getEnabled(), true, true, true, getAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
		List<SimpleGrantedAuthority> authorities = new ArrayList();
        
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

}
