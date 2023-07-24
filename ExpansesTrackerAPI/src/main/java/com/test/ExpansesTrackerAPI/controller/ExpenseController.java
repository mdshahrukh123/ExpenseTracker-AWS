package com.test.ExpansesTrackerAPI.controller;

import com.test.ExpansesTrackerAPI.model.ExpenseProduct;
import com.test.ExpansesTrackerAPI.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    @GetMapping("expenses")
    public List<ExpenseProduct> getAllExpenseProduct(){
        return expenseService.getExpenseProduct();
    }
}
