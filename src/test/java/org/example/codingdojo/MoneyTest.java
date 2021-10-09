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

    @Test
    void simpleAddition() {
        final var five = Money.dollar(5);
        final var bank = new Bank();
        final Expression sum = five.plus(five);
        final Money reduced = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(10), reduced);
    }

    @Test
    void plusReturnsSum() {
        final var five = Money.dollar(5);
        final Expression result = five.plus(five);
        final var sum = (Sum) result;
        assertEquals(five, sum.augend);
        assertEquals(five, sum.addend);
    }

    @Test
    void reduceSum() {
        final Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
        final var bank = new Bank();
        final Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(7), result);
    }

    @Test
    void reduceMoney() {
        final var bank = new Bank();
        final var result = bank.reduce(Money.dollar(1), "USD");
        assertEquals(Money.dollar(1), result);
    }

    private static class Money implements Expression {
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

        public Expression plus(Money addend) {
            return new Sum(this, addend);
        }

        public Money reduce(String to) {
            return this;
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

    private interface Expression {
        Money reduce(String to);
    }

    private static class Bank {
        public Money reduce(Expression source, String to) {
            if (source instanceof Money) {
                return source.reduce(to);
            }
            final var sum = (Sum) source;
            return sum.reduce(to);
        }
    }

    private static class Sum implements Expression {
        Money augend;
        Money addend;

        public Sum(Money augend, Money addend) {
            this.augend = augend;
            this.addend = addend;
        }

        public Money reduce(String to) {
            final var amount = augend.amount + addend.amount;
            return new Money(amount, to);
        }
    }
}
