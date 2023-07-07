package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    Account getAccountFromUserId(int userId);

    Account getAccountFromAccountId(int accountId);
    void updateAccountBalance(Account account);
}
