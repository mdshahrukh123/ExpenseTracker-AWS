package com.test.ExpansesTrackerAPI.controller;

import com.test.ExpansesTrackerAPI.model.ExpenseProduct;
import com.test.ExpansesTrackerAPI.model.User;
import com.test.ExpansesTrackerAPI.model.dto.ExpenseInput;
import com.test.ExpansesTrackerAPI.model.dto.SignInInput;
import com.test.ExpansesTrackerAPI.model.dto.SignUpOutput;
import com.test.ExpansesTrackerAPI.service.AuthenticationTokenService;
import com.test.ExpansesTrackerAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationTokenService authenticationTokenService;
    /// for user sign up
    @PostMapping("user/signUp")
    public SignUpOutput signUpUser(@RequestBody User user){
        return userService.createUserSignUp(user);
    }
  // for user sign in
    @PostMapping("user/signIn")
    public String userSignIn(@RequestBody SignInInput signInInput){
        return userService.signInUser(signInInput);
    }
     // for user sign out
    @DeleteMapping("user/SignOut")
    public String userSignOut(@RequestParam String email, @RequestParam String token){
        if(authenticationTokenService.authenticate(email,token)){
            return userService.signOutUser(email);
        }else{
            return "SignOut not allowed for non authenticated user...";
        }
    }
    // for expense add by user
    @PostMapping("user/expense")
    public String createUserExpense(@RequestParam String email, @RequestParam String token, @RequestBody ExpenseProduct expenseProduct){
        if(authenticationTokenService.authenticate(email,token)){
            return userService.createExpense(email,expenseProduct);
        }else{
            return "Expenses can't create non authenticated user...";
        }
    }
    // for expense get by user
    @GetMapping("user/expenses")
    public ExpenseInput getAllExpenseBasedOnUser(@RequestParam String email, @RequestParam String token){
          String expAuthMessage=null;
          List<ExpenseProduct> expense = null;
        if(authenticationTokenService.authenticate(email,token)){
            expAuthMessage = "User Authenticated...!!!!";
            List<ExpenseProduct> listExpense = userService.getAllExpense(email);
            expense = listExpense;
            return new ExpenseInput(expense,expAuthMessage);
        }else{
            expAuthMessage="Non Authentication User...!!!!";
            return new ExpenseInput(expense,expAuthMessage);
        }
    }

    // for delete expense by user
    @DeleteMapping("user/expense/id/{expenseId}")
    public String deleteExpenseByUser(@RequestParam String email, @RequestParam String token,@PathVariable Integer expenseId){
        if(authenticationTokenService.authenticate(email,token)){
            return userService.deleteExpense(email,expenseId);
        }else{
            return "Expenses can't delete non authenticated user...";
        }
    }

    // for update price expense by user
    @PutMapping("user/expense/id/{expenseId}/price/{newPrice}")
    public String updateExpenseByUser(@RequestParam String email, @RequestParam String token,@PathVariable Integer expenseId, @PathVariable Double newPrice){
        if(authenticationTokenService.authenticate(email,token)){
            return userService.updateExpense(email,expenseId,newPrice);
        }else{
            return "Expenses can't update non authenticated user...";
        }
    }
}
