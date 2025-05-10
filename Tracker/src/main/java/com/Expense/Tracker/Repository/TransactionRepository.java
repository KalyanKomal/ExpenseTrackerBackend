package com.Expense.Tracker.Repository;

import com.Expense.Tracker.Entites.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,String> {

    @Query(value="Select t from Transaction t where t.user.user_Id =:userId")
    public List<Transaction> findByUserId(@Param("userId") String userId);


}
