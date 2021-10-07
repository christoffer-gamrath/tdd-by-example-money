package org.example.codingdojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTest {
    @Test
    void multiplication() {
        final var five = new Dollar(5);
        five.times(2);
        assertEquals(10, five.amount);
    }

    private static class Dollar {
        int amount;

        public Dollar(int amount) {
        }

        void times(int multiplier) {
            amount = 5 * 2;
        }
    }
}
