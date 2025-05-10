package com.Expense.Tracker.ServiceImpl;

import com.Expense.Tracker.Dto.ResponseDto;
import com.Expense.Tracker.Dto.SignupDto;
import com.Expense.Tracker.Entites.User;
import com.Expense.Tracker.Repository.UserRepository;
import com.Expense.Tracker.Service.UserService;
import com.Expense.Tracker.Utils.JwtUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    JwtUtil jwtUtil;
    @Override
    public User saveLoginDetails(String userName, String password) {
        UUID id=UUID.randomUUID();
        User newUser=new User();
        //newUser.setUserName(userName);
        newUser.setPassword(password);
        newUser.setUser_Id(String.valueOf(id));
       User result= userRepo.save(newUser);
return result;

    }

    @Override
    public User saveUser(String userId, float budget) {

        Optional<User> existUser = userRepo.findById(userId);
        if(existUser.isPresent()){
            User user=existUser.get();
//            user.setUser_Email(email);
            user.setUser_Budget(budget);
            return userRepo.save(user);
        }
        return null;
    }

    @Override
    public User saveUserDetails(SignupDto signupDto) {
        UUID id=UUID.randomUUID();
        User newUser= modelMapper.map(signupDto,User.class);
        newUser.setUser_Id(String.valueOf(id));
        User result= userRepo.save(newUser);

        return result;
    }

    @Override
    public ResponseDto checkUser(String emailId, String password) {
        Optional<User> existingOptionalUser= userRepo.findByemailId(emailId);
        ResponseDto responseDto=new ResponseDto();
        if(existingOptionalUser.isPresent()){
          User  existingUser=existingOptionalUser.get();
            if(password!=null && existingUser.getPassword().equals(password)) {
                String token = jwtUtil.generateToken(existingUser.getUser_Email());
                responseDto.setStatusCode(200);
                responseDto.setMessage("Success");
                responseDto.setHttpStatus(HttpStatus.OK);
                responseDto.setData(token);
                return responseDto;
//            if(password.equals(existingOptionalUser.get().getPassword())){
//                return true;
//            }else{
//                return false;
//            }
            }else{
                responseDto.setData(null);
                responseDto.setMessage("Incorrect Password");
                responseDto.setStatusCode(404);
                responseDto.setHttpStatus(HttpStatus.BAD_REQUEST);
                return responseDto;
            }
        }
        responseDto.setData(null);
        responseDto.setMessage("Error user not found");
        responseDto.setStatusCode(404);
        responseDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        return responseDto;
    }

    @Override
    public ResponseDto getUser(String emailId) {
        Optional<User> existingOptionalUser= userRepo.findByemailId(emailId);
        ResponseDto responseDto=new ResponseDto();
        if(existingOptionalUser.isPresent()) {
            User existingUser = existingOptionalUser.get();
            responseDto.setStatusCode(200);
            responseDto.setMessage("Success");
            responseDto.setHttpStatus(HttpStatus.OK);
            responseDto.setData(existingUser);
            return responseDto;
        }
        responseDto.setData(null);
        responseDto.setMessage("Error user not found");
        responseDto.setStatusCode(404);
        responseDto.setHttpStatus(HttpStatus.BAD_REQUEST);
        return responseDto;
    }
}
