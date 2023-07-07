package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(int id);

    User findUserByAccountId(int accountId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);
    List<User> getUsersOtherThanSelf(int userId);

}
