package com.thadvai.guestbook.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.thadvai.guestbook.model.GuestbookEntry;
import com.thadvai.guestbook.model.User;
import com.thadvai.guestbook.persistence.UserRepository;
import com.thadvai.guestbook.service.GuestbookEntryService;

@Controller
public class GuestbookController {
	
	private static final String ENTRIES_TEMPLATE_ID = "entries";
	private static final String NEW_ENTRY_TEMPLATE_ID = "newEntry";
	
	private static final String GUESTBOOK_FORM_HEADER_ID = "formHeader";
	
	private static final String GUESTBOOK_TEMPLATE = "guestBook";
	private static final String HOMEPAGE_REDIRECT = "redirect:/";
	
	@Autowired
	private GuestbookEntryService guestbookEntryService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public String index() {
		return "redirect:/guestbook";
	}
	
	@GetMapping(path = "/guestbook")
    public String list(Model model) {
		
		model.addAttribute(ENTRIES_TEMPLATE_ID, this.guestbookEntryService.findEntriesByLoggedinUser());
		GuestbookEntry newEntry = new GuestbookEntry();
		newEntry.setUser(new User(guestbookEntryService.getCurrentUsername()));
		
		model.addAttribute(NEW_ENTRY_TEMPLATE_ID, newEntry);
		model.addAttribute(GUESTBOOK_FORM_HEADER_ID, "Add a New Entry");
		model.addAttribute("methodUrl", "/saveComment");
		
        return GUESTBOOK_TEMPLATE;
    }
	
	@PostMapping("/saveComment")
    public String addComment (Model model,
                              @Valid @ModelAttribute (NEW_ENTRY_TEMPLATE_ID) GuestbookEntry newEntry,
                               BindingResult bindingResult) {//@RequestParam("file") MultipartFile file,

        if (!bindingResult.hasErrors()) {
        	newEntry.setUser(this.guestbookEntryService.findCurrentLoggedinUser());
        	newEntry.setDate(LocalDateTime.now());
            this.guestbookEntryService.save(newEntry);
            
            model.addAttribute(GUESTBOOK_FORM_HEADER_ID, "Entry Inserted");
            model.addAttribute(NEW_ENTRY_TEMPLATE_ID, new GuestbookEntry());
            model.addAttribute(ENTRIES_TEMPLATE_ID, this.guestbookEntryService.findEntriesByLoggedinUser());
            
            return HOMEPAGE_REDIRECT;
        }
        else {
            model.addAttribute(GUESTBOOK_FORM_HEADER_ID, "Please Correct the Entry");
            model.addAttribute(ENTRIES_TEMPLATE_ID, this.guestbookEntryService.findEntriesByLoggedinUser());
            return GUESTBOOK_TEMPLATE;
        }
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("update/{id}")
    public String editComment(Model model, @PathVariable Long id) {

        model.addAttribute(GUESTBOOK_FORM_HEADER_ID, "Please Change the Entry");
        model.addAttribute(ENTRIES_TEMPLATE_ID, this.guestbookEntryService.findEntriesByLoggedinUser());
        model.addAttribute(NEW_ENTRY_TEMPLATE_ID, this.guestbookEntryService.findGuestBookEntryById(id));
        model.addAttribute("methodUrl", "/update/"+id);

        return GUESTBOOK_TEMPLATE;
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("update/{id}")
    public String updateComment(Model model,
            @PathVariable Long id,
            @Valid @ModelAttribute (NEW_ENTRY_TEMPLATE_ID) GuestbookEntry newEntry,
            BindingResult bindingResult) {

		if (!bindingResult.hasErrors ()) {
            GuestbookEntry current = this.guestbookEntryService.findGuestBookEntryById(id);

            current.setUser(userRepository.findByName(newEntry.getUser().getName()));
            current.setText(newEntry.getText());

            this.guestbookEntryService.save(current);
            
            model.addAttribute(GUESTBOOK_FORM_HEADER_ID, "Entry Updated");
            model.addAttribute(NEW_ENTRY_TEMPLATE_ID, new GuestbookEntry());
            model.addAttribute(ENTRIES_TEMPLATE_ID, this.guestbookEntryService.findEntriesByLoggedinUser());
            
            return HOMEPAGE_REDIRECT;
        }
        else {
            model.addAttribute(GUESTBOOK_FORM_HEADER_ID, "Please Correct the Entry");
            model.addAttribute(ENTRIES_TEMPLATE_ID, this.guestbookEntryService.findEntriesByLoggedinUser());
            return GUESTBOOK_TEMPLATE;
        }
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(path = "/approve/{id}")
	String approveEntry(Model model, @PathVariable Long id) {
		
		GuestbookEntry current = this.guestbookEntryService.findGuestBookEntryById(id);
        current.setApprove(true);
        
        this.guestbookEntryService.save(current);
		
		model.addAttribute(GUESTBOOK_FORM_HEADER_ID, "Entry Approved");
        model.addAttribute(NEW_ENTRY_TEMPLATE_ID, new GuestbookEntry());
        model.addAttribute(ENTRIES_TEMPLATE_ID, this.guestbookEntryService.findEntriesByLoggedinUser());
		
		return HOMEPAGE_REDIRECT;

	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(path = "delete/{id}")
	String removeEntry(Model model, @PathVariable Long id) {
		
		guestbookEntryService.deleteGuestBookEntryById(id);
		
		model.addAttribute(GUESTBOOK_FORM_HEADER_ID, "Entry Deleted");
        model.addAttribute(NEW_ENTRY_TEMPLATE_ID, new GuestbookEntry());
        model.addAttribute(ENTRIES_TEMPLATE_ID, this.guestbookEntryService.findEntriesByLoggedinUser());
		
		return HOMEPAGE_REDIRECT;
		
	}
	
}
