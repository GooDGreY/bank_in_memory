package ru.argutin.bank;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Bank bank = new Bank();
        int n=0;
        while (n<1000) {
            executor.submit(bank::addAccount);
            n++;
        }
        executor.awaitTermination(5L, TimeUnit.SECONDS);
        System.out.println(bank.accountList.size());;
    }
}
