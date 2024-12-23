package com.pmss0168.borrowingservice.command.event;

import com.pmss0168.borrowingservice.command.data.Borrowing;
import com.pmss0168.borrowingservice.command.data.BorrowingRepository;
import org.axonframework.eventhandling.DisallowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BorrowingEventHandler {
    @Autowired
    private BorrowingRepository borrowingRepository;

    @EventHandler
    public void handle(BorrowingCreateEvent event) {
        Borrowing borrowing = new Borrowing();
        borrowing.setId(event.getId());
        borrowing.setBookId(event.getBookId());
        borrowing.setEmployeeId(event.getEmployeeId());
        borrowing.setBorrowingDate(event.getBorrowingDate());
        borrowingRepository.save(borrowing);
    }


    @EventHandler
    @DisallowReplay
    public void handle(BorrowingDeleteEvent event) {
        Borrowing borrowing = borrowingRepository.findById(event.getId()).orElseThrow(() -> new RuntimeException("Borrowing not found"));
        borrowingRepository.deleteById(event.getId());
    }
}
