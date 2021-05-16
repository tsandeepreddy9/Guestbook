package com.thadvai.guestbook.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.util.Assert;

@Entity
public class GuestbookEntry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String text;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	private boolean approve;
	
	private LocalDateTime date;
	
	public GuestbookEntry() {
        super();
    }
	
	public GuestbookEntry(User user, String text) {
		super();
		
		Assert.notNull(user, "User must not be null or empty!");
		Assert.hasText(text, "Text must not be null or empty!");

		this.user = user;
		this.text = text;
		this.date = LocalDateTime.now();
		this.approve = false;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
        this.user = user;
    }

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
        this.id = id;
    }

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
        this.text = text;
    }

	public boolean isApprove() {
		return approve;
	}

	public void setApprove(boolean approve) {
		this.approve = approve;
	}
	
}
