package com.nigam.openalgocommunicator.facade;

import com.nigam.openalgocommunicator.dto.CommonInputValue;
import com.nigam.openalgocommunicator.dto.CommonOutputValue;
import lombok.Value;

public interface OpenAlgoCommunicator {
    CommonOutputValue postRequest(CommonInputValue commonInputValue) throws Exception;
}
