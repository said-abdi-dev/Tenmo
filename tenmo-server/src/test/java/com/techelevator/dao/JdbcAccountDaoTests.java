package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcAccountDaoTests extends BaseDaoTests{
    private static final Account testAccount = new Account(1001, 1002, 100.0);

    private JdbcAccountDao sut;

    @Before
    public void setup() {
        sut = new JdbcAccountDao(dataSource);
    }

    @Test
    public void getAccountFromId_works(){
        Account account = sut.getAccountFromUserId(1001);
        Assert.assertEquals(2001, account.getAccount_id());
    }

    @Test
    public void getBalanceFromAccount_returns_balance(){
        Account account = sut.getAccountFromUserId(1001);
        Assert.assertEquals(1000, account.getBalance(), .009);
    }

    @Test
    public void updateBalance_changes_balance(){
        testAccount.setBalance(200);
        sut.updateAccountBalance(testAccount);
        Assert.assertEquals(200, testAccount.getBalance(), .009);
    }
}
