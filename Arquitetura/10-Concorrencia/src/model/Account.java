package model;

/**
 * Created by vinicius.camargo on 10/07/2018
 */
public class Account {
    private Integer balance;

    public Account(Integer balance) {
        this.balance = balance;
    }

    public void withdraw(int amount) {
        if (balance - amount > 0) {
            balance -= amount;
        }
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public String printBalance() {
        return balance.toString();
    }

    public void applyTax(Double tax) {
        balance = Math.toIntExact(Math.round(balance * (1 - tax)));
    }
}
