package org.example.codingdojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTest {
    @Test
    void multiplication() {
        final var five = new Dollar(5);
        var product = five.times(2);
        assertEquals(10, product.amount);
        product = five.times(3);
        assertEquals(15, product.amount);
    }

    private static class Dollar {
        int amount;

        public Dollar(int amount) {
            this.amount = amount;
        }

        Dollar times(int multiplier) {
            return new Dollar(amount * multiplier);
        }
    }
}
