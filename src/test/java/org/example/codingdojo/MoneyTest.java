package org.example.codingdojo;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    void reduceMoneyDifferentCurrency() {
        final var bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        final var result = bank.reduce(Money.franc(2), "USD");
        assertEquals(Money.dollar(1), result);
    }

    @Test
    void identityRate() {
        assertEquals(1, new Bank().rate("USD", "USD"));
    }

    @Test
    void mixedAddition() {
        final Expression fiveBucks = Money.dollar(5);
        final Expression tenFrancs = Money.franc(10);
        final var bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        final Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
        assertEquals(Money.dollar(10), result);
    }

    @Test
    void sumPlusMoney() {
        final Expression fiveBucks = Money.dollar(5);
        final Expression tenFrancs = Money.franc(10);
        final var bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        final Expression sum = new Sum(fiveBucks, tenFrancs).plus(fiveBucks);
        final Money result = sum.reduce(bank, "USD");
        assertEquals(Money.dollar(15), result);
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

        public Expression times(int multiplier) {
            return new Money(amount * multiplier, currency);
        }

        public Expression plus(Expression addend) {
            return new Sum(this, addend);
        }

        public Money reduce(Bank bank, String to) {
            final var rate = bank.rate(currency, to);
            return new Money(amount / rate, to);
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
        Money reduce(Bank bank, String to);

        Expression plus(Expression addend);
    }

    private static class Bank {
        private final Map<Pair, Integer> rates = new HashMap<>();

        public Money reduce(Expression source, String to) {
            return source.reduce(this, to);
        }

        void addRate(String from, String to, int rate) {
            rates.put(new Pair(from, to), rate);
        }

        public int rate(String from, String to) {
            if (from.equals(to)) {
                return 1;
            }
            return rates.get(new Pair(from, to));
        }
    }

    private static class Sum implements Expression {
        Expression augend;
        Expression addend;

        public Sum(Expression augend, Expression addend) {
            this.augend = augend;
            this.addend = addend;
        }

        public Money reduce(Bank bank, String to) {
            final var amount = augend.reduce(bank, to).amount + addend.reduce(bank, to).amount;
            return new Money(amount, to);
        }

        @Override
        public Expression plus(Expression addend) {
            return new Sum(this, addend);
        }
    }

    private static class Pair {
        private final String from;
        private final String to;

        private Pair(String from, String to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object obj) {
            final var pair = (Pair) obj;
            return from.equals(pair.from) && to.equals(pair.to);
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }
}
