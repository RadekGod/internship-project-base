package com.virtuslab.internship.controller;


import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.service.ReceiptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AppController {

    private final ReceiptService receiptService = new ReceiptService();

    @GetMapping("/receipt")
    public Receipt receiptData(@RequestBody Basket basket) {
        return receiptService.calculateReceipt(basket);
    }


}
