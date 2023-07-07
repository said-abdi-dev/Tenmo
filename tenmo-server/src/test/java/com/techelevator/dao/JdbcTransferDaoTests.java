package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.*;

public class JdbcTransferDaoTests extends BaseDaoTests{

    private JdbcTransferDao sut;

    Transfer testTransfer = new Transfer(3002,1,1,2003,2001,20.0);
    @Before
    public void setup(){
        sut = new JdbcTransferDao(dataSource);
    }

    @Test
    public void getTransferFromTransferId_works(){
        Assert.assertEquals(1000, sut.getTransferFromTransferId(3002).getAmount(), .009);
    }
    @Test
    public void getTransfersFromAccountToId_works(){
        Assert.assertEquals(2, sut.getTransfersFromAccountToId(2002).size());
    }
    @Test
    public void getTransfersFromAccountFromId_works(){
        Assert.assertEquals(1, sut.getTransfersFromAccountFromId(2002).size());

    }


    @Test
    public void createTransfer_creates_transfer(){
        Transfer transfer = sut.createTransfer(testTransfer);
        Assert.assertEquals(20.0, transfer.getAmount(), 0.009);
    }

}
