package org.example.codingdojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {
    @Test
    void multiplication() {
        final Money five = Money.dollar(5);
        assertEquals(Money.dollar(10), five.times(2));
        assertEquals(Money.dollar(15), five.times(3));
    }

    @Test
    void francMultiplication() {
        final var amount = 5;
        final var five = Money.franc(amount);
        assertEquals(Money.franc(10), five.times(2));
        assertEquals(Money.franc(15), five.times(3));
    }

    @Test
    void equality() {
        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        assertFalse(Money.dollar(5).equals(Money.dollar(6)));
        assertTrue(Money.franc(5).equals(Money.franc(5)));
        assertFalse(Money.franc(5).equals(Money.franc(6)));
        assertFalse(Money.franc(5).equals(Money.dollar(5)));
    }

    @Test
    void currency() {
        assertEquals("USD", Money.dollar(1).currency());
        assertEquals("CHF", Money.franc(1).currency());
    }

    private static abstract class Money {
        protected int amount;

        public static Money dollar(int amount) {
            return new Dollar(amount);
        }

        public static Money franc(int amount) {
            return new Franc(amount);
        }

        public abstract Money times(int multiplier);

        public abstract String currency();

        @Override
        public boolean equals(Object obj) {
            final var money = (Money) obj;
            return amount == money.amount && getClass().equals(money.getClass());
        }
    }

    private static class Dollar extends Money {
        private final String currency;

        public Dollar(int amount) {
            currency = "USD";
            this.amount = amount;
        }

        public Money times(int multiplier) {
            return new Dollar(amount * multiplier);
        }

        @Override
        public String currency() {
            return currency;
        }
    }

    private static class Franc extends Money {
        private final String currency;

        public Franc(int amount) {
            currency = "CHF";
            this.amount = amount;
        }

        public Money times(int multiplier) {
            return new Franc(amount * multiplier);
        }

        @Override
        public String currency() {
            return currency;
        }
    }
}
