package com.Expense.Tracker.Entites;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {
    @Id
    private String user_Id;
    private String user_Email;
   // private String userName;
    private String password;
    private float user_Budget;
private String firstName;
private String lastName;
private String phoneNumber;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Transaction> transactionList;

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList=transactionList;
        for( Transaction transaction:transactionList){
            transaction.setUser(this);
        }
    }

    public String getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(String user_Id) {
        this.user_Id = user_Id;
    }

    public String getUser_Email() {
        return user_Email;
    }

    public void setUser_Email(String user_Email) {
        this.user_Email = user_Email;
    }

//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getUser_Budget() {
        return user_Budget;
    }

    public void setUser_Budget(float user_Budget) {
        this.user_Budget = user_Budget;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
