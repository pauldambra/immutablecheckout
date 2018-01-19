package com.homeworkclub;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckoutTests {

    @Test
    public void anACosts50() {
        final ImmutableList<String> itemsScanned = ImmutableList.of("A");
        final ImmutableMap<String, Integer> prices = ImmutableMap.of("A", 50);
        final ImmutableList<Discount> discounts = ImmutableList.of();
        final int actualTotal = getTotal(itemsScanned, prices, discounts);
        assertThat(actualTotal).isEqualTo(50);
    }

    @Test
    public void twoAsCost100() {
        final ImmutableList<String> itemsScanned = ImmutableList.of("A", "A");
        final ImmutableMap<String, Integer> prices = ImmutableMap.of("A", 50);
        final ImmutableList<Discount> discounts = ImmutableList.of();
        final int actualTotal = getTotal(itemsScanned, prices, discounts);
        assertThat(actualTotal).isEqualTo(100);
    }

    @Test
    public void anAAndABCosts80() {
        final ImmutableList<String> itemsScanned = ImmutableList.of("A", "B");
        final ImmutableMap<String, Integer> prices = ImmutableMap.of("A", 50, "B", 30);
        final ImmutableList<Discount> discounts = ImmutableList.of();
        final int actualTotal = getTotal(itemsScanned, prices, discounts);
        assertThat(actualTotal).isEqualTo(80);
    }

    @Test
    public void twoAsAndABCosts130() {
        final ImmutableList<String> itemsScanned = ImmutableList.of("A", "A", "B");
        final ImmutableMap<String, Integer> prices = ImmutableMap.of("A", 50, "B", 30);
        final ImmutableList<Discount> discounts = ImmutableList.of();
        final int actualTotal = getTotal(itemsScanned, prices, discounts);
        assertThat(actualTotal).isEqualTo(130);
    }

    class Discount {
        private final String itemCode;
        private final int quantity;
        private final int discount;

        Discount(final String itemCode, int quantity, int discount) {
            this.itemCode = itemCode;
            this.quantity = quantity;
            this.discount = discount;
        }
    }

    @Test
    public void twoBsCost45() {
        final ImmutableList<String> itemsScanned = ImmutableList.of("B", "B");
        final ImmutableMap<String, Integer> prices = ImmutableMap.of("A", 50, "B", 30);
        final ImmutableList<Discount> discounts = ImmutableList.of(new Discount("B", 2, 15));
        final int actualTotal = getTotal(
                itemsScanned,
                prices,
                discounts);
        assertThat(actualTotal).isEqualTo(45);
    }

    @Test
    public void fourBsCost90() {
        final ImmutableList<String> itemsScanned = ImmutableList.of("B", "B", "B", "B");
        final ImmutableMap<String, Integer> prices = ImmutableMap.of("A", 50, "B", 30);
        final ImmutableList<Discount> discounts = ImmutableList.of(new Discount("B", 2, 15));
        final int actualTotal = getTotal(
                itemsScanned,
                prices,
                discounts);
        assertThat(actualTotal).isEqualTo(90);
    }

    private int getTotal(List<String> items, Map<String, Integer> prices, List<Discount> discounts) {
        final int sum = items.stream().map(prices::get).mapToInt(Integer::intValue).sum();

        final Map<String, Long> countedSkus = items.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        final int discountTotal = discounts
                .stream()
                .filter(d -> countedSkus.containsKey(d.itemCode))
                .filter(d -> countedSkus.get(d.itemCode) >= d.quantity)
                .mapToInt(d -> d.discount * (Math.toIntExact(countedSkus.get(d.itemCode)) / d.quantity))
                .sum();

        return sum - discountTotal;
    }
}
