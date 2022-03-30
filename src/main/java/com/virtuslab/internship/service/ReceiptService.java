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
        var receipt = ReceiptGenerator.generate(basket);
        return applyDiscounts(receipt);
    }

    private Receipt applyDiscounts(Receipt receipt) {
        return TenPercentDiscount.apply(FifteenPercentDiscount.apply(receipt));
    }
}
