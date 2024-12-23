package com.pmss0168.bookservice.query.controller;

import com.pmss0168.bookservice.query.queries.GetAllBookQuery;
import com.pmss0168.commonservice.model.BookResponseModel;
import com.pmss0168.commonservice.query.GetBookDetailQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {
    @Autowired
    QueryGateway queryGateway;

    @GetMapping
    public List<BookResponseModel> getAllBook() {
        GetAllBookQuery query = new GetAllBookQuery();
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join(); //Locking luong doi ket qua tra ve
    }

    @GetMapping("/{bookId}")
    public BookResponseModel getBook(@PathVariable String bookId) {
        GetBookDetailQuery query = new GetBookDetailQuery(bookId);
        return queryGateway.query(query, ResponseTypes.instanceOf(BookResponseModel.class)).join(); //Locking luong doi ket qua tra ve

    }
}
