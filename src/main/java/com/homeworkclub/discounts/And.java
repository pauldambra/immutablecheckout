package com.homeworkclub.discounts;

import com.homeworkclub.Basket;

import java.util.Map;

public class And implements Discount {
    private final Discount one;
    private final Discount two;

    public And(Discount one, Discount two) {
        this.one = one;
        this.two = two;
    }

    @Override
    public boolean appliesTo(Basket basketItems) {
        return one.appliesTo(basketItems) && two.appliesTo(basketItems);
    }

    @Override
    public double forBasket(final Basket basketItems, final Map<String, Integer> priceList) {
        if (appliesTo(basketItems)) {
            return one.forBasket(basketItems, priceList) + two.forBasket(basketItems, priceList);
        } else {
            return 0;
        }

    }
}
