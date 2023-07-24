package com.test.ExpansesTrackerAPI.service;

import com.test.ExpansesTrackerAPI.model.ExpenseProduct;
import com.test.ExpansesTrackerAPI.model.User;
import com.test.ExpansesTrackerAPI.repository.IExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    @Autowired
    IExpenseRepo expenseRepo;

    public String createUser(ExpenseProduct expense) {
        expenseRepo.save(expense);
        return "Expense Added Successfully....!!!!!";
    }

    public List<ExpenseProduct> getExpense(Integer existUserId) {
       return  expenseRepo.getExpenseByUserFkId(existUserId);
    }

    public String removeExpense(Integer existUserId, Integer expenseId) {
        Optional<ExpenseProduct> optExpense = expenseRepo.findById(expenseId);
        if(optExpense.isEmpty()){
            return "Expense Id does not exist...!!!!";
        }
        ExpenseProduct expenseProduct = optExpense.get();

        Integer expenseFkId = expenseProduct.getUser().getUserId();
        if(expenseFkId == existUserId){
            expenseRepo.deleteById(expenseId);
            return "Deleted Successfully.....!!!!";
        }else{
            return "Sorry....expense id does not equal with authenticate user id..";
        }
    }

    public String updateExpense(Integer existUserId, Integer expenseId, Double newPrice) {
        Optional<ExpenseProduct> optExpense = expenseRepo.findById(expenseId);
        if(optExpense.isEmpty()){
            return "Expense Id does not exist...!!!!";
        }
        ExpenseProduct expenseProduct = optExpense.get();
        Integer expenseFkId = expenseProduct.getUser().getUserId();
        if(expenseFkId == existUserId){
            expenseProduct.setPrice(newPrice);
            expenseRepo.save(expenseProduct);
            return "Updated Successfully.....!!!!";
        }else{
            return "Sorry....expense id does not equal with authenticate user id..";
        }
    }

    public List<ExpenseProduct> getExpenseProduct() {
        return expenseRepo.findAll();
    }
}
