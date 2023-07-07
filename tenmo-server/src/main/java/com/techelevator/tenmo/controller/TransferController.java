package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="transfers")
@PreAuthorize("isAuthenticated()")
public class TransferController {
    private TransferDao transferDao;
    private UserDao userDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, UserDao userDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.userDao = userDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Transfer> getTransfersForUser(Principal principal) {
        String username = principal.getName();
        int userId = userDao.findIdByUsername(username);
        int accountId = accountDao.getAccountFromUserId(userId).getAccount_id();
        List<Transfer> transferFromList = transferDao.getTransfersFromAccountFromId(accountId);
        List<Transfer> transferToList = transferDao.getTransfersFromAccountToId(accountId);
        List<Transfer> transferList = transferFromList;
        for (Transfer transfer : transferToList) {
            transferList.add(transfer);
        }
        return transferList;
    }

    @RequestMapping(method=RequestMethod.POST)
    public void addTransfer(@RequestBody Transfer transfer, Principal principal) {
        int userId = userDao.findIdByUsername(principal.getName());
        Account fromAccount = accountDao.getAccountFromUserId(userId);
        Account toAccount = accountDao.getAccountFromUserId(transfer.getAccountTo());
        if(transfer.getAmount() > 0 && transfer.getAmount() <= fromAccount.getBalance()){
            fromAccount.removeBalance(transfer.getAmount());
            toAccount.addBalance(transfer.getAmount());
            accountDao.updateAccountBalance(fromAccount);
            accountDao.updateAccountBalance(toAccount);
            transferDao.createTransfer(transfer);
        }


    }

    @RequestMapping(path="/{transferId}", method=RequestMethod.GET)
    public Transfer getTransferFromTransferId(@PathVariable int transferId) {
        return transferDao.getTransferFromTransferId(transferId);
    }

}
