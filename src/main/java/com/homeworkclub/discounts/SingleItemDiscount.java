package com.homeworkclub.discounts;

import com.homeworkclub.Basket;

import java.util.Map;

public class SingleItemDiscount implements Discount {

    private final String itemCode;
    private final double discount;

    public SingleItemDiscount(final String itemCode, double discount) {
        this.itemCode = itemCode;
        this.discount = discount;
    }

    @Override
    public boolean appliesTo(final Basket basketItems) {
        return basketItems.contains(itemCode)
                && basketItems.count(itemCode) >= 1;
    }

    @Override
    public double forBasket(final Basket basketItems, final Map<String, Integer> priceList) {
        return priceList.getOrDefault(itemCode, 0) * discount;
    }
}
