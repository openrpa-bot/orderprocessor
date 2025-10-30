package com.nigam.controller;

import com.nigam.openalgocommunicator.facade.impl.Data_API.OpenAlgoCommunicator_History;
import com.nigam.openalgocommunicator.facade.impl.Data_API.OpenAlgoCommunicator_Intervals;
import com.nigam.openalgocommunicator.services.AllHoldings;
import lombok.extern.slf4j.Slf4j;
import com.nigam.openalgocommunicator.dto.CommonInputValue;
import com.nigam.openalgocommunicator.dto.CommonOutputValue;
import com.nigam.openalgocommunicator.facade.impl.account_api.OpenAlgoCommunicator_AnalyzerStatus;
import com.nigam.openalgocommunicator.facade.impl.account_api.OpenAlgoCommunicator_Funds;
import com.nigam.openalgocommunicator.facade.impl.account_api.OpenAlgoCommunicator_PositionBook;
import com.nigam.openalgocommunicator.facade.impl.account_api.OpenAlgoCommunicator_Holdings;
import com.nigam.openalgocommunicator.facade.impl.account_api.OpenAlgoCommunicator_OrderBook;
import com.nigam.openalgocommunicator.facade.impl.account_api.OpenAlgoCommunicator_Ping;
import com.nigam.openalgocommunicator.facade.impl.account_api.OpenAlgoCommunicator_TradeBook;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static java.lang.Thread.sleep;

@Slf4j
@RestController
@RequestMapping("/ltp-calculator/v1/data1")
public class HomeController {
        @PostMapping
        public String receiveJson(@RequestBody String jsonPayload) {
            log.info("Received JSON payload: " + jsonPayload);
            String serverIP = "192.168.1.112";
            String serverPort = "5001";
            String apiKey = "a79902ef310ad435a5ec8b9d46701dd8c164a4ae9aab107b0a7f66c1a91b8c12";
            // Process the JSON payload
            //CommonInputValue commonInputValue = new CommonInputValue( "false", "127.0.0.1", "5001", "2914287258181b553024e3251fdd67c9303b8448e03e4884d478f302e5827a90", "" );
            CommonInputValue commonInputValue = new CommonInputValue( "false", serverIP, serverPort, apiKey, "", "" );
            //AllHoldings allHoldings = new AllHoldings();
            try {
                CommonOutputValue commonOutputValue_Intervals  = new OpenAlgoCommunicator_Intervals().postRequest(commonInputValue);
                sleep(2000);
                CommonOutputValue commonOutputValue_History  = new OpenAlgoCommunicator_History().postRequest(commonInputValue);
                sleep(2000);
               /* commonInputValue = new CommonInputValue( "false", serverIP, serverPort, apiKey, "" , "");
                CommonOutputValue commonOutputValue_AnalyzerStatus  = new OpenAlgoCommunicator_AnalyzerStatus().postRequest(commonInputValue);
                sleep(2000);
                CommonOutputValue commonOutputValue_Funds           = new OpenAlgoCommunicator_Funds().postRequest(commonInputValue);
                sleep(2000);
                CommonOutputValue commonOutputValue_PositionBook    = new OpenAlgoCommunicator_PositionBook().postRequest(commonInputValue);
                sleep(2000);
                CommonOutputValue commonOutputValue_Holdings = new OpenAlgoCommunicator_Holdings().postRequest(commonInputValue);
                sleep(2000);
                CommonOutputValue commonOutputValue_OrderBook = new OpenAlgoCommunicator_OrderBook().postRequest(commonInputValue);
                sleep(2000);
                CommonOutputValue commonOutputValue_Ping = new OpenAlgoCommunicator_Ping().postRequest(commonInputValue);
                sleep(2000);
                CommonOutputValue commonOutputValue_TradeBook = new OpenAlgoCommunicator_TradeBook().postRequest(commonInputValue);
*/
               /*  */

                // Handle the response
               // System.out.println("Processing successful: " + allHoldings.clear(commonInputValue));
            } catch (Exception e) {
                // Handle or log the exception
                System.err.println("An error occurred while processing: " + e.getMessage());
                e.printStackTrace();
            }
            return "Received JSON payload: " + jsonPayload;
        }
}
