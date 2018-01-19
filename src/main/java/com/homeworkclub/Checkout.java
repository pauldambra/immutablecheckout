package com.homeworkclub;

import com.homeworkclub.discounts.Discount;

import java.util.List;
import java.util.Map;

public class Checkout {

    public static double getTotal(List<String> scannedItems, Map<String, Integer> priceList, List<Discount> currentDiscounts) {
        final int sum = scannedItems.stream().map(priceList::get).mapToInt(Integer::intValue).sum();

        final Basket basket = new Basket(scannedItems);

        final double discountTotal = currentDiscounts
                .stream()
                .mapToDouble(discount -> discount.forBasket(basket, priceList))
                .sum();

        return sum - discountTotal;
    }
}
