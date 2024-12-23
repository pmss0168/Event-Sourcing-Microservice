package com.pmss0168.borrowingservice.command.saga;

import com.pmss0168.borrowingservice.command.command.DeleteBorrowingCommand;
import com.pmss0168.borrowingservice.command.event.BorrowingCreateEvent;
import com.pmss0168.borrowingservice.command.event.BorrowingDeleteEvent;
import com.pmss0168.commonservice.command.RollBackStatusBookCommand;
import com.pmss0168.commonservice.command.UpdateStatusBookCommand;
import com.pmss0168.commonservice.event.BookRollBackStatusEvent;
import com.pmss0168.commonservice.event.BookUpdateStatusEvent;
import com.pmss0168.commonservice.model.BookResponseModel;
import com.pmss0168.commonservice.model.EmployeeResponseModel;
import com.pmss0168.commonservice.query.GetBookDetailQuery;
import com.pmss0168.commonservice.query.GetEmployeeDetailQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Saga
public class BorrowingSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(BorrowingCreateEvent event){
        log.info("BorrowingCreatedEvent in saga for BookId: {} : EmployeeId: {}", event.getBookId(), event.getEmployeeId());
        try {
            GetBookDetailQuery getBookDetailQuery = new GetBookDetailQuery(event.getBookId());
            BookResponseModel bookResponseCommonModel = queryGateway
                    .query(getBookDetailQuery, ResponseTypes.instanceOf(BookResponseModel.class)).join();
            if(!bookResponseCommonModel.getIsReleased()){
                throw new Exception("Book is not released");
            }else{
                SagaLifecycle.associateWith("bookId",event.getBookId());
                UpdateStatusBookCommand command = UpdateStatusBookCommand.builder()
                        .bookId(event.getBookId())
                        .isReleased(false)
                        .employeeId(event.getEmployeeId())
                        .borrowingId(event.getId())
                        .build();
                commandGateway.sendAndWait(command);
            }
        }catch (Exception ex){
            rollbackBorrowingRecord(event.getId());
            log.error(ex.getMessage());
        }
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handler(BookUpdateStatusEvent event){
        log.info("BookUpdateStatusEvent in Saga for BookId: {}", event.getBookId());
        try {
            GetEmployeeDetailQuery query = new GetEmployeeDetailQuery(event.getEmployeeId());
            EmployeeResponseModel employeeModel = queryGateway
                    .query(query,ResponseTypes.instanceOf(EmployeeResponseModel.class)).join();
            if(employeeModel.getIsDiscipline()){
                throw new Exception("Employee is discipline");
            }else{
                log.info("Borrowing successful");
                SagaLifecycle.end();
            }
        }catch (Exception ex){
            rollBackBookStatus(event.getBookId(), event.getEmployeeId(), event.getBorrowingId());
            log.error(ex.getMessage());
        }

    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookRollBackStatusEvent event){
        log.info("BookRollBackStatusEvent in Saga for book Id : {} ", event.getBookId());
        rollbackBorrowingRecord(event.getBorrowingId());
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    private void handle(BorrowingDeleteEvent event){
        log.info("BorrowDeletedEvent in Saga for Borrowing Id : {} ", event.getId());
        SagaLifecycle.end();
    }

    private void rollbackBorrowingRecord(String id){
        DeleteBorrowingCommand command = new DeleteBorrowingCommand(id);
        commandGateway.sendAndWait(command);
    }

    private void rollBackBookStatus(String bookId, String employeeId, String borrowingId){
        SagaLifecycle.associateWith("bookId",bookId);
        RollBackStatusBookCommand command = RollBackStatusBookCommand.builder()
                .bookId(bookId)
                .isReleased(true)
                .employeeId(employeeId)
                .borrowingId(borrowingId)
                .build();
        commandGateway.sendAndWait(command);
    }
}
