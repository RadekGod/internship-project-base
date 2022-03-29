package com.virtuslab.internship.service;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.springframework.stereotype.Service;

@Service
public class ReceiptService {

    public Receipt calculateReceipt(Basket basket) {
        var receiptGenerator = new ReceiptGenerator();
        var receipt = receiptGenerator.generate(basket);
        return applyDiscounts(receipt);
    }

    private Receipt applyDiscounts(Receipt receipt) {
        var fifteenPercentDiscount = new FifteenPercentDiscount();
        var tenPercentDiscount = new TenPercentDiscount();
        return tenPercentDiscount.apply(fifteenPercentDiscount.apply(receipt));
    }
}
