package ru.argutin.bank;

import ru.argutin.bank.domain.Account;
import ru.argutin.bank.ex.NotEnoughMoneyException;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Bank {
    public final AtomicLong accountId = new AtomicLong(0);
    public final ConcurrentHashMap<String, Account> accountList = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public void addAccount() {
        String value = String.valueOf(accountId.addAndGet(1));
        accountList.put(value, new Account(value, 0L));
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    public void transfer(String fromAccountNum, String toAccountNum, int amount) {
        Account fromAccount = accountList.get(fromAccountNum);
        Account toAccount = accountList.get(toAccountNum);
        if (fromAccount.getAccountNumber().compareTo(toAccount.getAccountNumber()) > 0) {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        } else {
            toAccount.deposit(amount);
            try {
                fromAccount.withdraw(amount);
            } catch (NotEnoughMoneyException e) {
                toAccount.withdraw(amount);
                //may cause overdraft
                //двойная бухгалтерия
            }
        }
    }

    public long getBalance(String accountNum) {
        return accountList.get(accountNum).getBalance();
    }
}
