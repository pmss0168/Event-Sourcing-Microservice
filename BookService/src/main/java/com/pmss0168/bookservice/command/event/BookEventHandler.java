package com.pmss0168.bookservice.command.event;

import com.pmss0168.bookservice.command.data.Book;
import com.pmss0168.bookservice.command.data.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.DisallowReplay;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookEventHandler {
    @Autowired
    private BookRepository bookRepository;

    @EventHandler
    public void on(BookCreateEvent event) {
        log.info("Book Created");
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdateEvent event) {
        log.info("Book Updated");
        Book book = bookRepository.findById(event.getId()).orElseThrow(() -> new RuntimeException("Book Not Found"));
        book.setName(event.getName());
        book.setAuthor(event.getAuthor());
        book.setIsReleased(event.getIsReleased());
        bookRepository.save(book);
    }

    @EventHandler
    @DisallowReplay
    public void on(BookDeleteEvent event) {
        try {
            log.info("Book Deleted");
            Book book = bookRepository.findById(event.getId()).orElseThrow(() -> new RuntimeException("Book Not Found"));
            bookRepository.delete(book);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }
}
