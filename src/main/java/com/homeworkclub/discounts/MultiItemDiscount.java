package com.homeworkclub.discounts;

import com.homeworkclub.Basket;

import java.util.Map;

public class MultiItemDiscount implements Discount {
    private final String itemCode;
    private final int quantity;
    private final int discount;

    public MultiItemDiscount(final String itemCode, int quantity, int discount) {
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.discount = discount;
    }

    @Override
    public boolean appliesTo(Basket basketItems) {
        return basketItems.contains(itemCode)
                 && basketItems.count(itemCode) >= quantity;
    }

    @Override
    public double forBasket(final Basket basketItems, final Map<String, Integer> priceList) {
        if (appliesTo(basketItems)) {
            return discount * (basketItems.count(itemCode) / quantity);
        } else {
            return 0;
        }
    }
}
