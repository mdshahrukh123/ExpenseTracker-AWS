package com.test.ExpansesTrackerAPI.model.dto;

import com.test.ExpansesTrackerAPI.model.ExpenseProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseInput {
    private List<ExpenseProduct> expList;
    private String authenticationMessage;
}
