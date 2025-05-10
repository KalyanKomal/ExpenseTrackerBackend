package com.Expense.Tracker.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {
   @Id
    private String transactionId;
   private String title;
   private float amount;

   public String getTransactionId() {
      return transactionId;
   }

   public void setTransactionId(String transactionId) {
      this.transactionId = transactionId;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public float getAmount() {
      return amount;
   }

   public void setAmount(float amount) {
      this.amount = amount;
   }

   public LocalDateTime getDate() {
      return date;
   }

   public void setDate(LocalDateTime date) {
      this.date = date;
   }

   public User getUser() {
      return user;
   }

   public void setUser(User user) {
      this.user = user;
   }

   private LocalDateTime date;
   private LocalDateTime lastModifiedDate;

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @ManyToOne
   @JoinColumn(name = "user_id")
   @JsonIgnore
   private User user;
}
