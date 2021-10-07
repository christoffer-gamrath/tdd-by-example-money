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
    void francMultiplication() {
        final var five = new Franc(5);
        assertEquals(new Franc(10), five.times(2));
        assertEquals(new Franc(15), five.times(3));
    }

    @Test
    void equality() {
        assertTrue(new Dollar(5).equals(new Dollar(5)));
        assertFalse(new Dollar(5).equals(new Dollar(6)));
        assertTrue(new Franc(5).equals(new Franc(5)));
        assertFalse(new Franc(5).equals(new Franc(6)));
        assertFalse(new Franc(5).equals(new Dollar(5)));
    }

    private static class Money {
        protected int amount;

        @Override
        public boolean equals(Object obj) {
            final var money = (Money) obj;
            return amount == money.amount && getClass().equals(money.getClass());
        }
    }

    private static class Dollar extends Money {

        public Dollar(int amount) {
            this.amount = amount;
        }

        public Dollar times(int multiplier) {
            return new Dollar(amount * multiplier);
        }
    }

    private static class Franc extends Money {
        public Franc(int amount) {
            this.amount = amount;
        }

        public Franc times(int multiplier) {
            return new Franc(amount * multiplier);
        }
    }
}
