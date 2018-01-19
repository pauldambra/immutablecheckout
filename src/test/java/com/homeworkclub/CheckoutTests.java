package com.homeworkclub;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.homeworkclub.discounts.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckoutTests {

    class TestCase {
        private final ImmutableList<String> itemsScanned;
        private final ImmutableMap<String, Integer> prices;
        private final ImmutableList<Discount> discounts;
        private final int expectedTotal;

        TestCase(final ImmutableList<String> itemsScanned, final ImmutableMap<String, Integer> prices, final ImmutableList<Discount> discounts, final int expectedTotal) {
            this.itemsScanned = itemsScanned;
            this.prices = prices;
            this.discounts = discounts;
            this.expectedTotal = expectedTotal;
        }

        ImmutableList<String> getItemsScanned() {
            return itemsScanned;
        }

        ImmutableMap<String,Integer> getPrices() {
            return prices;
        }

        ImmutableList<Discount> getDiscounts() {
            return discounts;
        }

        int getExpectedTotal() {
            return expectedTotal;
        }
    }

    @Test
    public void baskets_without_discounts_multiply_item_quantity_by_item_price() {
        final ImmutableMap<String, Integer> priceList = ImmutableMap.of(
                "A", 50,
                "B", 30,
                "C", 20,
                "D", 15);
        final ImmutableList<Discount> noDiscounts = ImmutableList.of();

        ImmutableList.of(
                new TestCase(
                        ImmutableList.of("A"), priceList, noDiscounts, priceList.get("A") * 1
                ),
                new TestCase(
                        ImmutableList.of("A", "A"), priceList, noDiscounts, priceList.get("A") * 2
                ),
                new TestCase(
                        ImmutableList.of("A", "A", "A"), priceList, noDiscounts, priceList.get("A") * 3
                ),
                new TestCase(
                        ImmutableList.of("B"), priceList, noDiscounts, priceList.get("B") * 1
                ),
                new TestCase(
                        ImmutableList.of("B", "B"), priceList, noDiscounts, priceList.get("B") * 2
                ),
                new TestCase(
                        ImmutableList.of("B", "B", "B"), priceList, noDiscounts, priceList.get("B") * 3
                ),
                new TestCase(
                        ImmutableList.of("C"), priceList, noDiscounts, priceList.get("C") * 1
                ),
                new TestCase(
                        ImmutableList.of("D"), priceList, noDiscounts, priceList.get("D") * 1
                ),
                new TestCase(
                        ImmutableList.of("A", "B"), priceList, noDiscounts, priceList.get("A") + priceList.get("B")
                ),
                new TestCase(
                        ImmutableList.of("A", "A", "B"), priceList, noDiscounts, (priceList.get("A") * 2) + priceList.get("B")
                )
        ).forEach(tc -> assertThat(Checkout.getTotal(tc.getItemsScanned(), tc.getPrices(), tc.getDiscounts())).isEqualTo(tc.getExpectedTotal()));
    }

    @Test
    public void twoBsCost45() {
        final ImmutableList<String> itemsScanned = ImmutableList.of("B", "B");
        final ImmutableMap<String, Integer> prices = ImmutableMap.of("A", 50, "B", 30);
        final ImmutableList<Discount> discounts = ImmutableList.of(
                new MultiItemDiscount("B", 2, 15)
        );
        final double actualTotal = Checkout.getTotal(
                itemsScanned,
                prices,
                discounts);
        assertThat(actualTotal).isEqualTo(45);
    }

    @Test
    public void fourBsCost90() {
        final ImmutableList<String> itemsScanned = ImmutableList.of("B", "B", "B", "B");
        final ImmutableMap<String, Integer> prices = ImmutableMap.of("A", 50, "B", 30);
        final ImmutableList<Discount> discounts = ImmutableList.of(new MultiItemDiscount("B", 2, 15));
        final double actualTotal = Checkout.getTotal(
                itemsScanned,
                prices,
                discounts);
        assertThat(actualTotal).isEqualTo(90);
    }

    @Test
    public void ten_percent_off_an_a_or_b() {
        final ImmutableList<String> itemsScanned = ImmutableList.of("B");
        final ImmutableMap<String, Integer> prices = ImmutableMap.of("B", 30);
        final ImmutableList<Discount> discounts = ImmutableList.of(
                new Or(new SingleItemDiscount("A", 0.1),
                        new SingleItemDiscount("B", 0.1))
        );
        final double actualTotal = Checkout.getTotal(
                itemsScanned,
                prices,
                discounts);
        assertThat(actualTotal).isEqualTo(27);
    }

    @Test
    public void ten_percent_off_a_meal_deal() {
        final ImmutableList<String> itemsScanned = ImmutableList.of("B", "D");
        final ImmutableMap<String, Integer> prices = ImmutableMap.of("B", 30, "D", 15);
        final ImmutableList<Discount> discounts = ImmutableList.of(
                new And(
                        new Or(new SingleItemDiscount("A", 0.1),
                                new SingleItemDiscount("B", 0.1)),
                        new Or(new SingleItemDiscount("C", 0.1),
                                new SingleItemDiscount("D", 0.1))
                )
        );
        final double actualTotal = Checkout.getTotal(
                itemsScanned,
                prices,
                discounts);
        assertThat(actualTotal).isEqualTo(40.5);
    }

}
