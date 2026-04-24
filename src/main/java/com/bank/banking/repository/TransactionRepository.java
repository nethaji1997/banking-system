package com.bank.banking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.banking.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromAccountIdOrToAccountId(Long fromId, Long toId);

}
