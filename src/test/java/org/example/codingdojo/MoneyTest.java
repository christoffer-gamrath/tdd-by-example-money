package org.example.codingdojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {
    @Test
    void multiplication() {
        final var five = Money.dollar(5);
        assertEquals(Money.dollar(10), five.times(2));
        assertEquals(Money.dollar(15), five.times(3));
    }

    @Test
    void equality() {
        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        assertFalse(Money.dollar(5).equals(Money.dollar(6)));
        assertFalse(Money.franc(5).equals(Money.dollar(5)));
    }

    @Test
    void currency() {
        assertEquals("USD", Money.dollar(1).currency());
        assertEquals("CHF", Money.franc(1).currency());
    }

    private static class Money {
        protected int amount;
        protected String currency;

        public Money(int amount, String currency) {
            this.currency = currency;
            this.amount = amount;
        }

        public static Money dollar(int amount) {
            return new Money(amount, "USD");
        }

        public static Money franc(int amount) {
            return new Money(amount, "CHF");
        }

        public Money times(int multiplier) {
            return new Money(amount * multiplier, currency);
        }

        public String currency() {
            return currency;
        }

        @Override
        public boolean equals(Object obj) {
            final var money = (Money) obj;
            return amount == money.amount && currency().equals(money.currency());
        }

        @Override
        public String toString() {
            return amount + " " + currency;
        }
    }

    private static class Franc extends Money {
        public Franc(int amount, String currency) {
            super(amount, currency);
        }
    }
}
