package com.nigam.dbcrawler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalResistenceSupport_Response {
    private boolean isDataFound;
    private String status;
    private FinalResistenceSupport_RequestBody data;
}
