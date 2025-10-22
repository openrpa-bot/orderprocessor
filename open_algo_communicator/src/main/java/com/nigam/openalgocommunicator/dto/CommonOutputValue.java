package com.nigam.openalgocommunicator.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonOutputValue {
    private JsonNode jsonNode;
}
