package com.Expense.Tracker.Service;

import com.Expense.Tracker.Dto.ResponseDto;
import com.Expense.Tracker.Dto.SignupDto;
import com.Expense.Tracker.Entites.User;

public interface UserService {


    public User saveLoginDetails(String userName, String password);

    public ResponseDto saveUser(String userId,float budget);

    public User saveUserDetails(SignupDto signupDto);

   public ResponseDto checkUser(String emailId, String password);

   public  ResponseDto getUser(String emailId);
}
