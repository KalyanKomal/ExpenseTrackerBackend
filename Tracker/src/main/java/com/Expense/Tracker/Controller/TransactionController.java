package com.Expense.Tracker.Controller;

import com.Expense.Tracker.Dto.ResponseDto;
import com.Expense.Tracker.Dto.TransactionDto;
import com.Expense.Tracker.Dto.TransactionModifyDto;
import com.Expense.Tracker.Dto.TransactionViewDto;
import com.Expense.Tracker.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("addTransaction")
    public void addTransaction(@RequestBody List<TransactionDto> transactionDto) {
        transactionService.addTransaction(transactionDto);
    }

    @GetMapping("/getAllTransactions")
    public ResponseDto getAllTransaction(String userId){
    return transactionService.getTransactions(userId);
}
@PostMapping("/deleteTransaction")
    public ResponseDto deleteRecord(@RequestParam String id){
        return transactionService.deleteTransaction(id);
}

@PostMapping("/modifyTransaction")
    public ResponseDto modifyRecord(@RequestBody TransactionModifyDto transactionModifyDto){
        return transactionService.modifyTransaction(transactionModifyDto);
}

}
