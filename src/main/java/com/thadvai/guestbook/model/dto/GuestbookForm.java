package com.thadvai.guestbook.model.dto;

import javax.validation.constraints.NotBlank;

import com.thadvai.guestbook.model.GuestbookEntry;

public class GuestbookForm {
	
	@NotBlank
	private final String name;
	
	@NotBlank
	private final String text;
	
	public GuestbookForm(String name, String text) {
		this.name = name;
		this.text = text;
	}
	
	public String getName() {
		return name;
	}
	
	public String getText() {
		return text;
	}
	
	/*GuestbookEntry toNewEntry() {
		return new GuestbookEntry(getName(), getText());
	}*/
	
}
