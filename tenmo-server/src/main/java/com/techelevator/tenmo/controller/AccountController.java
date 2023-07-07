package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path="/accounts")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao accountDao;
    private UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(path="", method= RequestMethod.GET)
    public Account getAccountForUser(Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        Account account = accountDao.getAccountFromUserId(userId);
        return account;
    }

    @RequestMapping(path="/{userId}", method= RequestMethod.GET)
    public Account getAccountForUser(@PathVariable int userId) {
        Account account = accountDao.getAccountFromUserId(userId);
        return account;
    }

    @RequestMapping(path="/account/{accountId}", method=RequestMethod.GET)
    public User getUserFromAccountId(@PathVariable int accountId){
        User user = userDao.findUserByAccountId(accountId);
        return user;
    }

    @RequestMapping(path="/transfers", method=RequestMethod.GET)
    public List<User> getNoneUserUsers(Principal principal){
        int userId = userDao.findIdByUsername(principal.getName());
        return userDao.getUsersOtherThanSelf(userId);
    }

    @RequestMapping(path="/transfers", method=RequestMethod.POST)
    public void updateAccount(Account account){
        accountDao.updateAccountBalance(account);
    }

}