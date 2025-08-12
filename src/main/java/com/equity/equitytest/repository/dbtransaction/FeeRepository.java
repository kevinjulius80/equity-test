package com.equity.equitytest.repository.dbtransaction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.equity.equitytest.model.transaction.Fee;

@Repository
public interface FeeRepository extends CrudRepository<Fee, Integer> {

}
