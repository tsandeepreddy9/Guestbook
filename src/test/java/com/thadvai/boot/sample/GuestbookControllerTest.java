package com.thadvai.boot.sample;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.thadvai.guestbook.model.GuestbookEntry;
import com.thadvai.guestbook.persistence.GuestbookEntryRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class GuestbookControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@Autowired
	GuestbookEntryRepository repository;
	
	@Test
	void redirectsToLoginPageForSecuredResource() throws Exception {

		GuestbookEntry entry = repository.findAll().iterator().next();

		mvc.perform(delete("/delete/{id}", entry.getId())) //
				.andExpect(status().is3xxRedirection()) //
				.andExpect(header().string("Location", endsWith("/login")));
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	void returnsModelAndViewForSecuredUriAfterAuthentication() throws Exception {

		long numberOfEntries = repository.count();
		GuestbookEntry entry = repository.findAll().iterator().next();

		mvc.perform(get("/delete/{id}", entry.getId())) //
				.andExpect(status().is3xxRedirection()) //
				.andExpect(view().name("redirect:/"));

		assertThat(repository.count()).isEqualTo(numberOfEntries - 1);
	}
	
}
