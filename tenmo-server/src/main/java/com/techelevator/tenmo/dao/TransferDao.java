package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    Transfer getTransferFromTransferId(int transferId);
    List<Transfer> getTransfersFromAccountToId(int accountToId);
    List<Transfer> getTransfersFromAccountFromId(int accountFromId);
    Transfer createTransfer(Transfer transfer);


}
