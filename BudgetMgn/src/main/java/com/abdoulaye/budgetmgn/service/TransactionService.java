package com.abdoulaye.budgetmgn.service;


import com.abdoulaye.budgetmgn.dto.TotalDto;
import com.abdoulaye.budgetmgn.entity.Transaction;
import com.abdoulaye.budgetmgn.entity.Type;
import com.abdoulaye.budgetmgn.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {


    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransaction () {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById (String id) {
        return transactionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("impossible de trouver la transaction avec cette id" + id)
        );
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(String id, Transaction transaction) {
        Transaction transactionToUpdate = getTransactionById(id);
        transactionToUpdate.setDescription(transaction.getDescription());
        transactionToUpdate.setTransactionType(transaction.getTransactionType());
        transactionToUpdate.setAmount(transaction.getAmount());
        transactionToUpdate.setDate(transaction.getDate());

        return transactionRepository.save(transactionToUpdate);
    }

    public TotalDto calculateTotal() {

        double totalRevenues  = getAllTransaction().stream().filter(
                transaction -> transaction.getTransactionType() == Type.REVENU
        ).mapToDouble(Transaction :: getAmount).sum();

        double totalDepenses  = getAllTransaction().stream().filter(
                transaction -> transaction.getTransactionType() == Type.DEPENSE
        ).mapToDouble(Transaction :: getAmount).sum();

        double restant = totalRevenues - totalDepenses;

        return new TotalDto(totalRevenues,totalDepenses,restant);
    }

    public void deleteTransaction(String id) {
        Transaction transaction = getTransactionById(id);
        transactionRepository.delete(transaction);
    }
}
