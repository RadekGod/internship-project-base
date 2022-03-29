package com.virtuslab.internship.service;

import com.virtuslab.internship.basket.Basket;
import com.virtuslab.internship.discount.FifteenPercentDiscount;
import com.virtuslab.internship.discount.TenPercentDiscount;
import com.virtuslab.internship.product.ProductDb;
import com.virtuslab.internship.receipt.ReceiptGenerator;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReceiptServiceTest {



        @Test
        void shouldCalculateReceiptFromGivenBasketAndApply10PercentDiscount() {
            // Given
            var productDb = new ProductDb();
            var cheese = productDb.getProduct("Cheese");
            var steak = productDb.getProduct("Steak");
            var bread = productDb.getProduct("Bread");
            var cereals = productDb.getProduct("Cereals");
            var basket = new Basket();
            basket.addProduct(cheese);
            basket.addProduct(steak);
            basket.addProduct(bread);
            basket.addProduct(cereals);
            var receiptGenerator = new ReceiptGenerator();
            var fifteenPercentDiscount = new FifteenPercentDiscount();
            var tenPercentDiscount = new TenPercentDiscount();
            var expectedTotalPrice = cheese.price().add(steak.price()).add(bread.price())
                    .add(cereals.price()).multiply(BigDecimal.valueOf(0.9));

            // When
            var receipt = receiptGenerator.generate(basket);
            var receiptAfterFifteenPercentDiscount = fifteenPercentDiscount.apply(receipt);
            var receiptAfterTenPercentDiscount = tenPercentDiscount.apply(receiptAfterFifteenPercentDiscount);
            // Then
            assertEquals(expectedTotalPrice, receiptAfterTenPercentDiscount.totalPrice());
            assertEquals(1, receiptAfterTenPercentDiscount.discounts().size());
        }
    }
