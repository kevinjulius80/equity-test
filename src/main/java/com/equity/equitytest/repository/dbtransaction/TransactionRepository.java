package com.equity.equitytest.repository.dbtransaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.equity.equitytest.model.transaction.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {
    
}
