package com.clay.c_thread_safety_synchronization.thread_safety;

import java.math.BigDecimal;

/**
 * In the below example:
 *
 * The BankAccount class is declared as final to prevent subclassing and overriding methods, ensuring immutability.
 * The class has private final fields, such as accountNumber, accountHolder, and balance, which are initialized through
 * the constructor and cannot be modified afterward.
 * Getter methods are provided to access the account details but not to modify them.
 * The class does not expose any methods that can change the internal state of the object.
 * By designing the BankAccount class as immutable, we ensure that once an account object is created, its state remains
 * constant throughout its lifetime. This brings several benefits:
 *
 * Thread safety: Immutable objects are inherently thread-safe as multiple threads can safely access and share immutable
 * objects without the need for explicit synchronization.
 * Simplicity: Immutable objects simplify the code by eliminating the complexity of handling mutable state and potential race conditions.
 * Consistency: Immutable objects maintain consistency because their state cannot be modified after creation, preventing
 * unexpected changes or inconsistencies.
 */
final class BankAccount {
    private final String accountNumber;
    private final String accountHolder;
    private final BigDecimal balance;

    public BankAccount(String accountNumber, String accountHolder, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("123456789", "John Doe", new BigDecimal("1000.00"));

        String accountNumber = account.getAccountNumber();
        String accountHolder = account.getAccountHolder();
        BigDecimal balance = account.getBalance();

        System.out.println("accountNumber : "+accountNumber+" , accountHolder "+accountHolder +" , balance "+balance);
    }
}
