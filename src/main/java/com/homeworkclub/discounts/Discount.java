package com.homeworkclub.discounts;

import com.homeworkclub.Basket;

import java.util.Map;

public interface Discount {
    boolean appliesTo(Basket basketItems);
    double forBasket(Basket basketItems, final Map<String, Integer> priceList);
}
