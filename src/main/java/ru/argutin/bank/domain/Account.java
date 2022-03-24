package ru.argutin.bank.domain;

import lombok.*;
import ru.argutin.bank.ex.NotEnoughMoneyException;

@Data
@AllArgsConstructor
public class Account {
    private String accountNumber;
    private volatile long balance;

    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            balance = balance - amount;
        } else {
            throw new NotEnoughMoneyException();
        }
    }

    public synchronized void deposit(int amount) {
        balance = balance + amount;
    }
}
