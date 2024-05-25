package com.example.picpay_backend_challenge.respository;

import com.example.picpay_backend_challenge.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
