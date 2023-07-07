package com.techelevator.tenmo.model;

public class Account {
    private double balance;

    private int user_id;
    private int account_id;

    public Account(){}

    public Account(int account_id, int user_id, double balance){
        this.account_id = account_id;
        this.user_id = user_id;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }
    public void addBalance(double amount){
        balance += amount;
    }
    public void removeBalance(double amount){
        balance -= amount;
    }
}
