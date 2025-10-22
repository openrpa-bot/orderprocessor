package com.nigam.openalgocommunicator.facade.impl.Data_API;

import com.nigam.openalgocommunicator.Util.APICallers;
import com.nigam.openalgocommunicator.Util.PayloadCreator;
import com.nigam.openalgocommunicator.Util.URLCreator;
import com.nigam.openalgocommunicator.dto.CommonInputValue;
import com.nigam.openalgocommunicator.dto.CommonOutputValue;
import com.nigam.openalgocommunicator.facade.OpenAlgoCommunicator;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class OpenAlgoCommunicator_Intervals implements OpenAlgoCommunicator {
    public static final String API_History = "api/v1/intervals";

    @Override
    public CommonOutputValue postRequest(@Valid CommonInputValue commonInputValue) throws Exception {
        String Additional_Payload = "";
        String jsonRequest = "{" + PayloadCreator.createPayload(commonInputValue) + Additional_Payload + "}";
        return new CommonOutputValue( APICallers.CallAPI(URLCreator.createURL(commonInputValue, API_History), jsonRequest) );
    }
}