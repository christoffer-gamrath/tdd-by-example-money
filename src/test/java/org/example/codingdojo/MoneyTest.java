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
        final var five = new Franc(5);
        assertEquals(new Franc(10), five.times(2));
        assertEquals(new Franc(15), five.times(3));
    }

    @Test
    void equality() {
        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        assertFalse(Money.dollar(5).equals(Money.dollar(6)));
        assertTrue(new Franc(5).equals(new Franc(5)));
        assertFalse(new Franc(5).equals(new Franc(6)));
        assertFalse(new Franc(5).equals(Money.dollar(5)));
    }

    private static abstract class Money {
        protected int amount;

        public static Money dollar(int amount) {
            return new Dollar(amount);
        }

        public abstract Money times(int multiplier);

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

        public Money times(int multiplier) {
            return new Dollar(amount * multiplier);
        }
    }

    private static class Franc extends Money {
        public Franc(int amount) {
            this.amount = amount;
        }

        public Money times(int multiplier) {
            return new Franc(amount * multiplier);
        }
    }
}
