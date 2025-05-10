package com.Expense.Tracker.ServiceImpl;

import com.Expense.Tracker.Dto.ResponseDto;
import com.Expense.Tracker.Dto.TransactionDto;
import com.Expense.Tracker.Dto.TransactionModifyDto;
import com.Expense.Tracker.Dto.TransactionViewDto;
import com.Expense.Tracker.Entites.Transaction;
import com.Expense.Tracker.Entites.User;
import com.Expense.Tracker.Repository.TransactionRepository;
import com.Expense.Tracker.Repository.UserRepository;
import com.Expense.Tracker.Service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    TransactionRepository transactionRepo;
@Autowired
    UserRepository userRepo;
@Autowired
ModelMapper modelMapper;


    public void addTransaction(List<TransactionDto> transactionDtoList){
        Optional<User> user=userRepo.findById(transactionDtoList.get(0).getUserId());
List<Transaction> transactionList=new ArrayList<>();
        for(TransactionDto transactionDto:transactionDtoList){
            UUID transactionId=UUID.randomUUID();
            Transaction newTransaction=new Transaction();
            newTransaction.setTransactionId(String.valueOf(transactionId));
            newTransaction.setAmount(transactionDto.getAmount());
            newTransaction.setTitle(transactionDto.getTitle());
            if(transactionDto.getDate()==null) {
                newTransaction.setDate(LocalDateTime.now());
            }else{
                newTransaction.setDate(transactionDto.getDate());
            }
if(user.isPresent()){
    newTransaction.setUser(user.get());
   user.get().setUser_Budget(user.get().getUser_Budget()- newTransaction.getAmount());

}
transactionList.add(newTransaction);
        }
        transactionRepo.saveAll(transactionList);
        User existUser=user.get();
        existUser.setTransactionList(transactionList);
        userRepo.save(existUser);
    }

    @Override
    public ResponseDto getTransactions(String userId) {

        List<Transaction>transactionList=transactionRepo.findByUserId(userId);
        if(transactionList.size()>0) {
            List<TransactionViewDto> resultList = transactionList.stream().map(transaction -> modelMapper.map(transaction, TransactionViewDto.class)).collect(Collectors.toList());
            ResponseDto responseDto = new ResponseDto();
            responseDto.setData(resultList);
            responseDto.setMessage("Success");
            responseDto.setHttpStatus(HttpStatus.OK);
            responseDto.setStatusCode(200);
            return responseDto;
        }else{
            ResponseDto responseDto = new ResponseDto();
            responseDto.setData(null);
            responseDto.setMessage("No recored found");
            responseDto.setHttpStatus(HttpStatus.BAD_REQUEST);
            responseDto.setStatusCode(400);
            return responseDto;
        }
    }

    @Override
    public ResponseDto deleteTransaction(String id) {
        Optional<Transaction> optionalTransaction=transactionRepo.findById(id);
        if(optionalTransaction.isPresent()){
            Transaction existingTransaction=optionalTransaction.get();
            Optional<User> optionalUser=userRepo.findById(existingTransaction.getUser().getUser_Id());
            if(optionalUser.isPresent()) {
                User existingUser = optionalUser.get();
                float amount = existingTransaction.getAmount();
                existingUser.setUser_Budget(existingUser.getUser_Budget() + amount);
                existingUser.getTransactionList().remove(existingTransaction);
                transactionRepo.delete(existingTransaction);
                userRepo.save(existingUser);
                ResponseDto responseDto = new ResponseDto();
                responseDto.setData(existingTransaction);
                responseDto.setMessage("Record Deleted");
                responseDto.setHttpStatus(HttpStatus.OK);
                responseDto.setStatusCode(200);
                return responseDto;
            }else{
                ResponseDto responseDto = new ResponseDto();
                responseDto.setData(null);
                responseDto.setMessage("No User Found");
                responseDto.setHttpStatus(HttpStatus.BAD_REQUEST);
                responseDto.setStatusCode(400);
                return responseDto;
            }

        }else {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setData(null);
            responseDto.setMessage("No Record Found");
            responseDto.setHttpStatus(HttpStatus.BAD_REQUEST);
            responseDto.setStatusCode(400);
            return responseDto;
        }
    }

    @Override
    public ResponseDto modifyTransaction(TransactionModifyDto transactionModifyDto) {

            String transactionId=transactionModifyDto.getTransactionId();
            Optional<Transaction> optionalTransaction = transactionRepo.findById(transactionId);
            if(optionalTransaction.isPresent()){
                Transaction existingTransaction=optionalTransaction.get();
                Optional<User> optionalUser=userRepo.findById(existingTransaction.getUser().getUser_Id());
                if(optionalUser.isPresent()) {
                    User existingUser = optionalUser.get();
                    float amount = existingTransaction.getAmount();
                    existingUser.setUser_Budget(existingUser.getUser_Budget() + amount- transactionModifyDto.getAmount());
                   // existingUser.getTransactionList().remove(existingTransaction);
                    if(transactionModifyDto.getTitle()!=null){
                        existingTransaction.setTitle(transactionModifyDto.getTitle());
                    }
                        existingTransaction.setAmount(transactionModifyDto.getAmount());
                    if(transactionModifyDto.getDate()!=null){
                        existingTransaction.setLastModifiedDate(transactionModifyDto.getDate());
                    }else{
                        existingTransaction.setLastModifiedDate(LocalDateTime.now());
                    }

//existingUser.getTransactionList().add(existingTransaction);
                    transactionRepo.save(existingTransaction);
                    userRepo.save(existingUser);
                    ResponseDto responseDto = new ResponseDto();
                    responseDto.setData(existingTransaction);
                    responseDto.setMessage("Record Updated");
                    responseDto.setHttpStatus(HttpStatus.OK);
                    responseDto.setStatusCode(200);
                    return responseDto;
                }else{
                    ResponseDto responseDto = new ResponseDto();
                    responseDto.setData(null);
                    responseDto.setMessage("No User Found");
                    responseDto.setHttpStatus(HttpStatus.BAD_REQUEST);
                    responseDto.setStatusCode(400);
                    return responseDto;
                }
            }else{
                ResponseDto responseDto = new ResponseDto();
                responseDto.setData(null);
                responseDto.setMessage("No Record Found");
                responseDto.setHttpStatus(HttpStatus.BAD_REQUEST);
                responseDto.setStatusCode(400);
                return responseDto;
            }

    }

}
