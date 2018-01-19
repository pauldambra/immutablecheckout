package com.homeworkclub;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Basket {
    private final Map<String, Long> countedItems;

    Basket(final List<String> scannedItems) {
        this.countedItems = scannedItems.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public boolean contains(final String itemCode) {
        return countedItems.containsKey(itemCode);
    }

    public int count(final String itemCode) {
        return Math.toIntExact(countedItems.getOrDefault(itemCode, 0L));
    }
}
