package com.thadvai.guestbook.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.thadvai.guestbook.model.GuestbookEntry;
import com.thadvai.guestbook.model.User;

public interface GuestbookEntryRepository extends JpaRepository<GuestbookEntry, Long> {
	
	GuestbookEntry findGuestbookEntryById(Long id);

    List<GuestbookEntry> findGuestbookEntriesByUser(User user);

    @Transactional
    void deleteGuestbookEntryById(Long id);

}
