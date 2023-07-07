package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Transfer getTransferFromTransferId(int transferId){
        Transfer transfer = new Transfer();
        String sql = "SELECT transfer_id, transfer_type_id, \n" +
                "transfer_status_id, account_from, account_to, amount\n" +
                "FROM transfer WHERE transfer_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
        while(result.next()){
            transfer = mapRowToTransfer(result);
        }
        return transfer;
    }

    @Override
    public List<Transfer> getTransfersFromAccountToId(int accountToId){
        List<Transfer> transferList = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, \n" +
                "transfer_status_id, account_from, account_to, amount\n" +
                "FROM transfer WHERE account_to = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountToId);
        while(result.next()){
            transferList.add(mapRowToTransfer(result));
        }
        return transferList;
    }

    @Override
    public List<Transfer> getTransfersFromAccountFromId(int accountFromId){
        List<Transfer> transferList = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, \n" +
                "transfer_status_id, account_from, account_to, amount\n" +
                "FROM transfer WHERE account_from = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, accountFromId);
        while(result.next()){
            transferList.add(mapRowToTransfer(result));
        }
        return transferList;
    }

    @Override
    public Transfer createTransfer(Transfer transfer) {
        Transfer newTransfer = null;
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, \n" +
                "account_from, account_to, amount) \n" +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";

        try{
            int newTransferId = jdbcTemplate.queryForObject(sql, int.class, transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                    transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
            newTransfer = getTransferFromTransferId(newTransferId);
        } catch(CannotGetJdbcConnectionException e){
            System.out.println("Unable to connect to server or database");
        } catch (BadSqlGrammarException e) {
            System.out.println("SQL syntax error");
        } catch (DataIntegrityViolationException e) {
            System.out.println("Data integrity violation");
        }

        return newTransfer;
    }

    private Transfer mapRowToTransfer(SqlRowSet result) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(result.getInt("transfer_id"));
        transfer.setTransferTypeId(result.getInt("transfer_type_id"));
        transfer.setTransferStatusId(result.getInt("transfer_status_id"));
        transfer.setAccountTo(result.getInt("account_to"));
        transfer.setAccountFrom(result.getInt("account_from"));
        transfer.setAmount(result.getDouble("amount"));

        return transfer;
    }
}
