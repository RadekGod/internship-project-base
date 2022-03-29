package com.virtuslab.internship.receipt;

import com.virtuslab.internship.basket.Basket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        basket.getProducts()
        .stream()
        .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(x -> 1)))
                .forEach((product, quantity) -> receiptEntries.add(new ReceiptEntry(product, quantity)));
        return new Receipt(receiptEntries);
    }
}
