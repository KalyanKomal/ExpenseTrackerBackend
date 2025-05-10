package com.Expense.Tracker.Service;

import com.Expense.Tracker.Dto.ResponseDto;
import com.Expense.Tracker.Dto.TransactionDto;
import com.Expense.Tracker.Dto.TransactionModifyDto;
import com.Expense.Tracker.Dto.TransactionViewDto;

import java.util.List;

public interface TransactionService {
    public void addTransaction(List<TransactionDto> transactionDto);
    public ResponseDto getTransactions(String userId);

   public ResponseDto deleteTransaction(String id);

    public ResponseDto modifyTransaction(TransactionModifyDto transactionModifyDto);
}
