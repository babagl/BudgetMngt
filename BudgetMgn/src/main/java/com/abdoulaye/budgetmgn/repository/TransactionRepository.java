package com.abdoulaye.budgetmgn.repository;


import com.abdoulaye.budgetmgn.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository  extends JpaRepository<Transaction , String> {
}
