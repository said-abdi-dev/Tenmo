package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Scanner;

public class TenmoService {

    private final RestTemplate restTemplate = new RestTemplate();

    private String apiBaseUrl;

    private AuthenticatedUser currentUser;

    public TenmoService(String apiBaseUrl, AuthenticatedUser currentUser){
        this.apiBaseUrl = apiBaseUrl;
        this.currentUser = currentUser;
    }

    public Account getAccountForUser() {
        String url = apiBaseUrl + "accounts";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(currentUser.getToken());
        HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);

        ResponseEntity<Account> response = restTemplate.exchange(url, HttpMethod.GET, request, Account.class);
        Account accountReturnedFromApi = response.getBody();
        return accountReturnedFromApi;
    }

    public Account getAccountFromUserId(int userId){
        String url = apiBaseUrl + "accounts/" + userId;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(currentUser.getToken());
        HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);

        ResponseEntity<Account> response = restTemplate.exchange(url, HttpMethod.GET, request, Account.class);
        Account accountReturnedFromApi = response.getBody();
        return accountReturnedFromApi;
    }

    public User getUserFromAccountId(int accountId){
        String url = apiBaseUrl + "accounts/account/" + accountId;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(currentUser.getToken());
        HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);

        ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, request, User.class);
        User userReturnedFromApi = response.getBody();
        return userReturnedFromApi;
    }

    public User[] getNoneUserUsers(){
        String url = apiBaseUrl + "accounts/transfers";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(currentUser.getToken());
        HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);

        ResponseEntity<User[]> response = restTemplate.exchange(url, HttpMethod.GET, request, User[].class);
        User[] userListFromApi = response.getBody();
        return  userListFromApi;
    }

    public void addTransfer(Transfer transfer){
        String url = apiBaseUrl + "transfers";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(currentUser.getToken());
        HttpEntity<Transfer> request = new HttpEntity<Transfer>(transfer, httpHeaders);
        restTemplate.exchange(url, HttpMethod.POST, request, Transfer.class);
    }

    public Transfer[] getTransferList(){
        String url = apiBaseUrl + "transfers";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(currentUser.getToken());
        HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);

        ResponseEntity<Transfer[]> response = restTemplate.exchange(url, HttpMethod.GET, request, Transfer[].class);
        Transfer[] transferListFromApi = response.getBody();
        return transferListFromApi;
    }

    public Transfer getTransferFromTransferId(int transferId) {
        String url = apiBaseUrl + "transfers/" + transferId;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(currentUser.getToken());
        HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);

        ResponseEntity<Transfer> response = restTemplate.exchange(url, HttpMethod.GET, request, Transfer.class);
        Transfer transferReturnedFromApi = response.getBody();
        return transferReturnedFromApi;
    }



}
