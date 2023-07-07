package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.TenmoService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    public static final int EXIT_APP = 0;
    public static final int VIEW_CURRENT_BALANCE = 1;
    public static final int VIEW_PAST_TRANSFERS = 2;
    public static final int VIEW_PENDING_REQUESTS = 3;
    public static final int SEND_TE_BUCKS = 4;
    public static final int REQUEST_TE_BUCKS = 5;
    public static final int REGISTER_USER = 1;
    public static final int LOGIN_USER = 2;


    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    private TenmoService tenmoService;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            // TODO: Instantiate services that require the current user to exist here
            tenmoService = new TenmoService(API_BASE_URL, currentUser);



            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != EXIT_APP && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == REGISTER_USER) {
                handleRegister();
            } else if (menuSelection == LOGIN_USER) {
                handleLogin();
            } else if (menuSelection != EXIT_APP) {
                consoleService.printMessage("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        consoleService.printMessage("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            consoleService.printMessage("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != EXIT_APP) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == VIEW_CURRENT_BALANCE) {
                viewCurrentBalance();
            } else if (menuSelection == VIEW_PAST_TRANSFERS) {
                viewTransferHistory();
            } else if (menuSelection == VIEW_PENDING_REQUESTS) {
                viewPendingRequests();
            } else if (menuSelection == SEND_TE_BUCKS) {
                sendBucks();
            } else if (menuSelection == REQUEST_TE_BUCKS) {
                requestBucks();
            } else if (menuSelection == EXIT_APP) {
                continue;
            } else {
                consoleService.printMessage("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        Account account = tenmoService.getAccountForUser();
        consoleService.printBalance(account.getBalance());
	}

	private void viewTransferHistory() {
        int userAccountId = tenmoService.getAccountForUser().getAccount_id();
		Transfer[] transfers = tenmoService.getTransferList();
        consoleService.printListOfTransfersHeader();
        for(Transfer transfer : transfers){
            int id = transfer.getTransferId();
            String toFrom = "To: ";
            User otherUser = tenmoService.getUserFromAccountId(transfer.getAccountTo());
            if(transfer.getAccountTo() == userAccountId){
                toFrom = "From: ";
                otherUser = tenmoService.getUserFromAccountId(transfer.getAccountFrom());
            }
            String name = otherUser.getUsername();
            double amount = transfer.getAmount();
            consoleService.printATransfer(id, toFrom, name, amount);
        }

        int transferId = consoleService.askForTransferId(transfers);
        Transfer transfer = tenmoService.getTransferFromTransferId(transferId);
        int id = transfer.getTransferId();
        String from = tenmoService.getUserFromAccountId(transfer.getAccountFrom()).getUsername();
        String to = tenmoService.getUserFromAccountId(transfer.getAccountTo()).getUsername();
        String type = "Request";
        if(transfer.getTransferTypeId() == 2){
            type = "Send";
        }
        String status  = "Pending";
        if(transfer.getTransferStatusId() == 2){
            status = "Approved";
        } else if (transfer.getTransferStatusId() == 3) {
            status = "Rejected";
        }
        double amount = transfer.getAmount();
        consoleService.printTransferDetails(id, from, to, status, type, amount);
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        int transferToId = consoleService.printListOfOtherUsers(tenmoService.getNoneUserUsers());
        if(transferToId != 0){
            transferToId = tenmoService.getAccountFromUserId(transferToId).getAccount_id();
            double amount = 0;
            while(true) {
                amount = consoleService.promptForBigDecimal("Enter the amount you would like to send: ").doubleValue();
                if(amount > 0 && amount <= tenmoService.getAccountForUser().getBalance()){
                    break;
                }
                System.out.println("Amount must be greater than 0 and no more than your current balance");
            }
            int transferFromId = tenmoService.getAccountForUser().getAccount_id();
            int transferType = 2;
            int transferStatus = 2;
            Transfer transfer = new Transfer(0,transferType, transferStatus, transferToId, transferFromId, amount);
            tenmoService.addTransfer(transfer);

        }
		
	}

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
