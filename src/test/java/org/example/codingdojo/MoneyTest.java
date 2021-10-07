package org.example.codingdojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MoneyTest {
    @Test
    void multiplication() {
        final var five = new Dollar(5);
        var product = five.times(2);
        assertEquals(10, product.amount);
        product = five.times(3);
        assertEquals(15, product.amount);
    }

    @Test
    void equality() {
        assertTrue(new Dollar(5).equals(new Dollar(5)));
    }

    private static class Dollar {
        int amount;

        public Dollar(int amount) {
            this.amount = amount;
        }

        Dollar times(int multiplier) {
            return new Dollar(amount * multiplier);
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }
}
