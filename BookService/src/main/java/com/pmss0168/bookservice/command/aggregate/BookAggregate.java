package com.pmss0168.bookservice.command.aggregate;

import com.pmss0168.bookservice.command.command.CreateBookCommand;
import com.pmss0168.bookservice.command.command.DeleteBookCommand;
import com.pmss0168.bookservice.command.command.UpdateBookCommand;
import com.pmss0168.bookservice.command.event.BookCreateEvent;
import com.pmss0168.bookservice.command.event.BookDeleteEvent;
import com.pmss0168.bookservice.command.event.BookUpdateEvent;
import com.pmss0168.commonservice.command.RollBackStatusBookCommand;
import com.pmss0168.commonservice.command.UpdateStatusBookCommand;
import com.pmss0168.commonservice.event.BookRollBackStatusEvent;
import com.pmss0168.commonservice.event.BookUpdateStatusEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@Getter
@Setter
@NoArgsConstructor  //Yêu cầu bắt buộc hàm xây dựng không tham số từ Axon
public class BookAggregate {
    @AggregateIdentifier
    private String id;

    private String name;

    private String author;

    private Boolean isReleased;

    /**
    * Xu ly su kien them moi mot book
    * */
    @CommandHandler
    public BookAggregate(CreateBookCommand command) {
        BookCreateEvent bookCreateEvent = new BookCreateEvent();
        BeanUtils.copyProperties(command, bookCreateEvent);
        AggregateLifecycle.apply(bookCreateEvent);
    }

    @EventSourcingHandler
    public void on(BookCreateEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReleased = event.getIsReleased();
    }

    /**
     * Xu ly su kien cap nhat mot book
     * */
    @CommandHandler
    public void handle(UpdateBookCommand command) {
        BookUpdateEvent bookUpdateEvent = new BookUpdateEvent();
        BeanUtils.copyProperties(command, bookUpdateEvent);
        AggregateLifecycle.apply(bookUpdateEvent);
    }

    @EventSourcingHandler
    public void on(BookUpdateEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.author = event.getAuthor();
        this.isReleased = event.getIsReleased();
    }

    /**
     * Xu ly su kien xoa mot book da co
     * */
    @CommandHandler
    public void handle(DeleteBookCommand command) {
        BookDeleteEvent bookDeleteEvent = new BookDeleteEvent();
        BeanUtils.copyProperties(command, bookDeleteEvent);
        AggregateLifecycle.apply(bookDeleteEvent);
    }

    @EventSourcingHandler
    public void on(BookDeleteEvent event) {
        this.id = event.getId();
    }

    @CommandHandler
    public void handle(UpdateStatusBookCommand command) {
        BookUpdateStatusEvent event = new BookUpdateStatusEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BookUpdateStatusEvent event) {
        this.id = event.getBookId();
        this.isReleased = event.getIsReleased();
    }

    @CommandHandler
    public void handle(RollBackStatusBookCommand command){
        BookRollBackStatusEvent event = new BookRollBackStatusEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BookRollBackStatusEvent event) {
        this.id = event.getBookId();
        this.isReleased = event.getIsReleased();
    }
}
