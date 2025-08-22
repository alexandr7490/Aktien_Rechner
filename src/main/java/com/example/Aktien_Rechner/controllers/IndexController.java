package com.example.Aktien_Rechner.controllers;


import com.example.Aktien_Rechner.services.ShareService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final ShareService shareService;

    public IndexController(ShareService shareService) {
        this.shareService = shareService;
    }


    @RequestMapping({"","/shares"})
    public String getSharesPage(Model model){
        model.addAttribute("shares", shareService.getShares());
        return "index";
    }

}
