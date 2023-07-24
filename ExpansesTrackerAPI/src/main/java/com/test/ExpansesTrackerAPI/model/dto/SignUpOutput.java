package com.test.ExpansesTrackerAPI.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpOutput {
    private String outputMessage;
    private Boolean outPutStatus;
}
