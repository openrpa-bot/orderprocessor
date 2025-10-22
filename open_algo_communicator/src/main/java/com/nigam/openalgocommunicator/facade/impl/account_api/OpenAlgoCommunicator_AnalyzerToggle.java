package com.nigam.openalgocommunicator.facade.impl.account_api;

import com.nigam.openalgocommunicator.Util.APICallers;
import com.nigam.openalgocommunicator.Util.PayloadCreator;
import com.nigam.openalgocommunicator.Util.URLCreator;
import com.nigam.openalgocommunicator.dto.CommonInputValue;
import com.nigam.openalgocommunicator.dto.CommonOutputValue;
import com.nigam.openalgocommunicator.facade.OpenAlgoCommunicator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

// Communicator Class
@Service
public class OpenAlgoCommunicator_AnalyzerToggle implements OpenAlgoCommunicator {
     public static final String API_PositionBook = "api/v1/funds";

    @Override
    public CommonOutputValue postRequest(@Valid CommonInputValue commonInputValue) throws Exception {
        String jsonRequest = "{" + PayloadCreator.createPayload(commonInputValue) + "}";
        return new CommonOutputValue( APICallers.CallAPI(URLCreator.createURL(commonInputValue, API_PositionBook), jsonRequest) );
    }
}