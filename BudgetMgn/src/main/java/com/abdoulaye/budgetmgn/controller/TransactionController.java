package com.abdoulaye.budgetmgn.controller;


import com.abdoulaye.budgetmgn.dto.TotalDto;
import com.abdoulaye.budgetmgn.entity.Transaction;
import com.abdoulaye.budgetmgn.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @GetMapping()
    public ResponseEntity<List<Transaction>> getAllTransaction (){
        return ResponseEntity.ok(transactionService.getAllTransaction());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTranstionById(@PathVariable String id){
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid  @RequestBody Transaction transaction){
        transaction.setDate(LocalDate.now());
        Transaction transactionToSave = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(transactionToSave, HttpStatus.CREATED);
    }

    @GetMapping("/calculate")
    public ResponseEntity<TotalDto> calculateTransaction(){
        TotalDto restant = transactionService.calculateTotal();
        return new ResponseEntity<>(restant, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable String id,@Valid @RequestBody Transaction transaction){
        return ResponseEntity.ok(transactionService.updateTransaction(id, transaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable String id){
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
