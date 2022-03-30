package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.Product;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import java.math.BigDecimal;

public class FifteenPercentDiscount {

    public static String NAME = "FifteenPercentDiscount";

    public static Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private static boolean shouldApply(Receipt receipt) {
        return receipt.entries().stream()
                .filter(receiptEntry -> receiptEntry.product().type().equals(Product.Type.GRAINS))
                .map(ReceiptEntry::quantity)
                .mapToInt(Integer::intValue).sum() >= 3;
    }
}
