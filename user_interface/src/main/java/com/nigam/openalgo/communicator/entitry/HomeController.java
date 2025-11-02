package com.nigam.openalgo.communicator.entitry;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToHoldings() {
        return "redirect:/config";
    }
}
