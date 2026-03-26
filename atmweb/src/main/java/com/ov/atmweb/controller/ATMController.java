package com.ov.atmweb.controller;

import com.ov.atmweb.service.ATMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ATMController {

    @Autowired
    private ATMService atmService;

    private String currentCard;
    private String lastReceipt = "";

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String card,
                        @RequestParam String pin) {

        if (atmService.validateUser(card, pin)) {
            currentCard = card;
            return "redirect:/dashboard";
        }

        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("balance",
                atmService.getBalance(currentCard));

        model.addAttribute("history",
                atmService.getHistory(currentCard));

        model.addAttribute("name",
                atmService.getUserName(currentCard));

        model.addAttribute("card", currentCard);

        return "dashboard";
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam double amount) {

        lastReceipt =
                atmService.deposit(currentCard, amount);

        return "redirect:/receipt";
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam double amount) {

        lastReceipt =
                atmService.withdraw(currentCard, amount);

        return "redirect:/receipt";
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam double amount,
                           @RequestParam String account) {

        lastReceipt =
                atmService.transfer(currentCard, account, amount);

        return "redirect:/receipt";
    }

    @GetMapping("/receipt")
    public String receipt(Model model) {

        model.addAttribute("receipt", lastReceipt);
        model.addAttribute("balance",
                atmService.getBalance(currentCard));

        return "receipt";
    }

    @GetMapping("/logout")
    public String logout() {

        currentCard = null;

        return "redirect:/";
    }
}