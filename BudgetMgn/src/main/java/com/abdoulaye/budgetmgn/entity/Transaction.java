package com.abdoulaye.budgetmgn.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@DynamicInsert
@DynamicUpdate

@Table(name="transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotEmpty(message = "description ne doit pas etre vide")
    private String description;

    @NotNull(message = "Ne doit pas etre null")
    @Enumerated(EnumType.STRING)
    private Type transactionType;

    @Positive(message = "doit avoir une valeur positive")
    private Double amount;


    private LocalDate date;

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public Type getTransactionType() {
        return transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransactionType(Type transactionType) {
        this.transactionType = transactionType;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
