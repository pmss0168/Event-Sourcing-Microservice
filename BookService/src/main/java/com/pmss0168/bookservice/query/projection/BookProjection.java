package com.pmss0168.bookservice.query.projection;

import com.pmss0168.bookservice.command.data.Book;
import com.pmss0168.bookservice.command.data.BookRepository;
import com.pmss0168.bookservice.query.queries.GetAllBookQuery;
import com.pmss0168.commonservice.model.BookResponseModel;
import com.pmss0168.commonservice.query.GetBookDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBookQuery query) {
        List<Book> books = bookRepository.findAll();
        List<BookResponseModel> results = books.stream().map((book) -> {
            BookResponseModel bookResponseModel = new BookResponseModel();
            BeanUtils.copyProperties(book, bookResponseModel);
            return bookResponseModel;
        }).toList();
        return results;
    }

    @QueryHandler
    public BookResponseModel handle(GetBookDetailQuery query) {
        Book book = bookRepository.findById(query.getId()).orElseThrow(() -> new RuntimeException("Book Not Found"));
        BookResponseModel bookResponseModel = new BookResponseModel();
        BeanUtils.copyProperties(book, bookResponseModel);
        return bookResponseModel;
    }
}
