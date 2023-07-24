package com.test.ExpansesTrackerAPI.service;

import com.test.ExpansesTrackerAPI.model.AuthenticationToken;
import com.test.ExpansesTrackerAPI.model.ExpenseProduct;
import com.test.ExpansesTrackerAPI.model.User;
import com.test.ExpansesTrackerAPI.model.dto.SignInInput;
import com.test.ExpansesTrackerAPI.model.dto.SignUpOutput;
import com.test.ExpansesTrackerAPI.repository.IUserRepo;
import com.test.ExpansesTrackerAPI.service.hasingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;

    @Autowired
    AuthenticationTokenService authTokenService;

    @Autowired
    ExpenseService expenseService;


    public SignUpOutput createUserSignUp(User user) {

        String outputMessage = null;
        Boolean outputStatus;
        // check email
        String newEmail = user.getEmail();
        if(newEmail == null){
            outputMessage = "Invalid email...!!!";
            outputStatus = false;
            return new SignUpOutput(outputMessage,outputStatus);
        }

        // check if email exist
        User existuser = userRepo.findByEmail(newEmail);
        if(existuser != null){
            outputMessage = "Email already exist...!!!!";
            outputStatus = false;
            return new SignUpOutput(outputMessage,outputStatus);
        }
        try {
            String encryptPass = PasswordEncrypter.encryptPassword(user.getPassword());
            user.setPassword(encryptPass);
            userRepo.save(user);
        } catch (NoSuchAlgorithmException e) {
            outputMessage = "Internal Error occurred...!!!!";
            outputStatus = false;
            return new SignUpOutput(outputMessage,outputStatus);
        }
        outputMessage = "User Register Successfully...!!!!";
        outputStatus = true;
        return new SignUpOutput(outputMessage,outputStatus);
    }

    public String signInUser(SignInInput signInInput) {
        // check email valid
        String newEmail = signInInput.getEmail();
        if(newEmail == null){
           return "Invalid email...!!!";
        }

        // check if email is not exist
        User existuser = userRepo.findByEmail(newEmail);
        if(existuser == null){
           return  "Email does not exist...!!!!";
        }
        try {
            String encryptPass = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(encryptPass.equals(existuser.getPassword())){
                AuthenticationToken authToken = new AuthenticationToken(existuser);
                authTokenService.saveAuthToken(authToken);
                return "SignIn Successfully....Authentication Token is ::  "+authToken.getTokenValue();
            }else{
                return "Email or Password invalid....!!!";
            }
        } catch (NoSuchAlgorithmException e) {
            return "Internal error occurred....!!!!!";
        }
    }

    public String signOutUser(String email) {
       User user = userRepo.findFirstByEmail(email);
       AuthenticationToken token = authTokenService.findFirstByUser(user);
       authTokenService.removeToken(token);
       return "User Signed Out Successfully...!!!";
    }

    public String createExpense(String email, ExpenseProduct expenseProduct) {
        User user = userRepo.findFirstByEmail(email);
        expenseProduct.setUser(user);
        return expenseService.createUser(expenseProduct);
    }

    public List<ExpenseProduct> getAllExpense(String email) {
        User user = userRepo.findByEmail(email);
        Integer existUserId = user.getUserId();
        return expenseService.getExpense(existUserId);
    }

    public String deleteExpense(String email, Integer expenseId) {
        User user = userRepo.findByEmail(email);
        Integer existUserId = user.getUserId();
        return expenseService.removeExpense(existUserId,expenseId);
    }

    public String updateExpense(String email, Integer expenseId, Double newPrice) {
        User user = userRepo.findByEmail(email);
        Integer existUserId = user.getUserId();
        return expenseService.updateExpense(existUserId,expenseId,newPrice);
    }


}
