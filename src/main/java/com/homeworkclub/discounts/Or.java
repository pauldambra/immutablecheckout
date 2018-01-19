package com.homeworkclub.discounts;

import com.homeworkclub.Basket;

import java.util.Map;

public class Or implements Discount {
    private final Discount one;
    private final Discount two;

    public Or(Discount one, Discount two) {
        this.one = one;
        this.two = two;
    }

    @Override
    public boolean appliesTo(Basket basketItems) {
        return one.appliesTo(basketItems) || two.appliesTo(basketItems);
    }

    @Override
    public double forBasket(final Basket basketItems, final Map<String, Integer> priceList) {
        if (one.appliesTo(basketItems)) return one.forBasket(basketItems, priceList);
        if (two.appliesTo(basketItems)) return two.forBasket(basketItems, priceList);
        return 0;
    }
}
