package com.thadvai.guestbook.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.thadvai.guestbook.model.GuestbookEntry;
import com.thadvai.guestbook.model.User;
import com.thadvai.guestbook.persistence.GuestbookEntryRepository;
import com.thadvai.guestbook.persistence.UserRepository;

@Service
public class GuestbookEntryService {
	
	@Autowired
    private GuestbookEntryRepository guestbookEntryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<GuestbookEntry> findAllEntries() {
        return this.guestbookEntryRepository.findAll();
    }

    public GuestbookEntry findGuestBookEntryById(Long id) {
        return this.guestbookEntryRepository.findGuestbookEntryById(id);
    }

    public List<GuestbookEntry> findGuestBookEntriesByUser(User user) {
        return this.guestbookEntryRepository.findGuestbookEntriesByUser(user);
    }

    public void deleteGuestBookEntryById(Long id) {
        this.guestbookEntryRepository.deleteGuestbookEntryById(id);
    }

    public void deleteGuestBookEntry(GuestbookEntry entry) {
        this.guestbookEntryRepository.delete(entry);
    }

    public void save(GuestbookEntry newEntry) {
        this.guestbookEntryRepository.save(newEntry);
    }
    
    public List<GuestbookEntry> findEntriesByLoggedinUser() {
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		
		if(principal instanceof User) {
		  username = ((User)principal).getName();
		} else {
		  username = principal.toString();
		}
		
		if(((org.springframework.security.core.userdetails.User)principal).getAuthorities()
				.contains(new SimpleGrantedAuthority("ROLE_ADMIN")))	{
			return findAllEntries();
		} else {	
			User user = userRepository.findByName(
							((org.springframework.security.core.userdetails.User)principal)
							.getUsername()
						);
			return this.guestbookEntryRepository.findGuestbookEntriesByUser(user)
					.stream().filter(e -> e.isApprove()).collect(Collectors.toList());
		}
    }
    
    public User findCurrentLoggedinUser() {		
		return userRepository.findByName(getCurrentUsername());		
    }
    
    public String getCurrentUsername() {
    	
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		
		if(principal instanceof User) {
		  username = ((User)principal).getName();
		} else {
		  username = principal.toString();
		}
    	
		return ((org.springframework.security.core.userdetails.User) principal)
				.getUsername();
    }
	
}
