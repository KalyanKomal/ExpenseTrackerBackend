package com.Expense.Tracker.Repository;

import com.Expense.Tracker.Entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    @Query("Select u from User u where u.user_Email = :emailId")
    public Optional<User> findByemailId(@Param("emailId") String emailId);
}
