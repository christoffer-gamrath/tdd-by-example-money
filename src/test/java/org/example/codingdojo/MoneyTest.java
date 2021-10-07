package org.example.codingdojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {
    @Test
    void multiplication() {
        final var five = new Dollar(5);
        assertEquals(new Dollar(10), five.times(2));
        assertEquals(new Dollar(15), five.times(3));
    }

    @Test
    void equality() {
        assertTrue(new Dollar(5).equals(new Dollar(5)));
        assertFalse(new Dollar(5).equals(new Dollar(6)));
    }

    private static class Dollar {
        private int amount;

        public Dollar(int amount) {
            this.amount = amount;
        }

        public Dollar times(int multiplier) {
            return new Dollar(amount * multiplier);
        }

        @Override
        public boolean equals(Object obj) {
            final var dollar = (Dollar) obj;
            return amount == dollar.amount;
        }
    }
}
