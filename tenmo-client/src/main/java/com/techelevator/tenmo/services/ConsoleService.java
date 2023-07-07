package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printBalance(double balance){
        System.out.printf("Your current account balance is: $%1.2f \n", balance);
    }

    public int printListOfOtherUsers(User[] accountListFromApi){
        System.out.println("-------------------------------------------");
        System.out.println("Users");
        System.out.printf("%-8s %-10s\n", "ID", "Name");
        System.out.println("-------------------------------------------");

        for(User user : accountListFromApi){
            System.out.printf("%-8s %-10s \n", user.getId(), user.getUsername());
        }
        System.out.println("---------");
        while(true){
            int id = promptForInt("Please enter transfer ID to view details (0 to cancel): ");
            for(User user : accountListFromApi){
                if(user.getId() == id){
                    return id;
                } else if(id == 0){
                    return 0;
                }
            }
            System.out.println("Invalid selection");
        }

    }
    public void printListOfTransfersHeader(){
        System.out.println("-------------------------------------------");
        System.out.println("Transfers");
        System.out.printf("%-8s %-10s %-12s %-8s \n", "ID","From/To","", "Amount");
        System.out.println("-------------------------------------------");

    }

    public void printATransfer(int transferId, String toFrom, String name, double balance){
        System.out.printf("%-8s %-10s %-12s %1.2f \n", transferId, toFrom, name, balance);
    }

    public int askForTransferId(Transfer[] transferList) {
        while(true){
            int id = promptForInt("Please enter transfer ID to view details (0 to cancel): ");
            for(Transfer transfer : transferList){
                if(transfer.getTransferId() == id){
                    return id;
                } else if(id == 0){
                    return 0;
                }
            }
            System.out.println("Invalid selection");
        }
    }

    public void printTransferDetails(int transferId, String fromName, String toName, String status, String type, double amount){
        System.out.println("--------------------------------------------");
        System.out.println("Transfer Details");
        System.out.println("--------------------------------------------");
        System.out.println("Id: " + transferId);
        System.out.println("From: " + fromName);
        System.out.println("To: " + toName);
        System.out.println("Type: " + type);
        System.out.println("Status: " + status);
        System.out.printf("Amount: $%1.2f \n", amount);
    }
}
