package com.virtuslab.internship.discount;

import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.Receipt;
import com.virtuslab.internship.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FifteenPercentDiscountTest {

    @Test
    void shouldApply15PercentDiscountAnd10PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var steak = productDb.getProduct("Steak");
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(steak, 1));
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 6));

        var receipt = new Receipt(receiptEntries);
        var expectedTotalPrice = cheese.price().add(steak.price()).add(bread.price())
                .add(cereals.price().multiply(BigDecimal.valueOf(6))).multiply(BigDecimal.valueOf(0.85).multiply(BigDecimal.valueOf(0.9)));

        // When
        var receiptAfterFifteenPercentDiscount = FifteenPercentDiscount.apply(receipt);
        var receiptAfterTenPercentDiscount = TenPercentDiscount.apply(receiptAfterFifteenPercentDiscount);
        // Then
        assertEquals(expectedTotalPrice, receiptAfterTenPercentDiscount.totalPrice());
        assertEquals(2, receiptAfterTenPercentDiscount.discounts().size());
    }

    @Test
    void shouldApply15PercentDiscountAndNotApply10PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 6));

        var receipt = new Receipt(receiptEntries);
        var expectedTotalPrice = bread.price().multiply(BigDecimal.valueOf(1)).add(cereals.price()
                .multiply(BigDecimal.valueOf(6))).multiply(BigDecimal.valueOf(0.85));

        // When
        var receiptAfterFifteenPercentDiscount = FifteenPercentDiscount.apply(receipt);
        var receiptAfterTenPercentDiscount = TenPercentDiscount.apply(receiptAfterFifteenPercentDiscount);
        // Then
        assertEquals(expectedTotalPrice, receiptAfterTenPercentDiscount.totalPrice());
        assertEquals(1, receiptAfterTenPercentDiscount.discounts().size());
    }

    @Test
    void shouldApply10PercentDiscountAndNotApply15PercentDiscount() {
        // Given
        var productDb = new ProductDb();
        var cheese = productDb.getProduct("Cheese");
        var steak = productDb.getProduct("Steak");
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(cheese, 1));
        receiptEntries.add(new ReceiptEntry(steak, 1));
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 1));

        var receipt = new Receipt(receiptEntries);
        var expectedTotalPrice = bread.price().add(cereals.price())
                .add(cheese.price()).add(steak.price()).multiply(BigDecimal.valueOf(0.9));

        // When
        var receiptAfterFifteenPercentDiscount = FifteenPercentDiscount.apply(receipt);
        var receiptAfterTenPercentDiscount = TenPercentDiscount.apply(receiptAfterFifteenPercentDiscount);
        // Then
        assertEquals(expectedTotalPrice, receiptAfterTenPercentDiscount.totalPrice());
        assertEquals(1, receiptAfterTenPercentDiscount.discounts().size());
    }

    @Test
    void shouldNotApplyDiscounts() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 1));
        receiptEntries.add(new ReceiptEntry(cereals, 1));

        var receipt = new Receipt(receiptEntries);
        var expectedTotalPrice = bread.price().add(cereals.price());

        // When
        var receiptAfterFifteenPercentDiscount = FifteenPercentDiscount.apply(receipt);
        var receiptAfterTenPercentDiscount = TenPercentDiscount.apply(receiptAfterFifteenPercentDiscount);
        // Then
        assertEquals(expectedTotalPrice, receiptAfterTenPercentDiscount.totalPrice());
        assertEquals(0, receiptAfterTenPercentDiscount.discounts().size());
    }
}
