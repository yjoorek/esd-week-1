package banking;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    
    static ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();
    static Scanner input = new Scanner(System.in);
    
    ////Function to display main menu////
    public static void main(String[] args) 
    {
        boolean runProgram = true;
        while (runProgram){
            System.out.println("Press 1 to create a new bank account");
            System.out.println("Press 2 to manage existing bank account");
            System.out.println("Press 3 to exit banking app");
        
            switch(input.nextLine())
            {
                case "1":
                    createAccount();
                    break;
                case "2":
                    selectAccount();
                    break;
                case "3":
                    runProgram = false;
                    continue;
                default:
                    System.out.println("Invalid input, please try again");
            }
        }    
    }
    
    ////Function to create account object after correct details are entered////
    public static void createAccount()
    {
        String firstName = "", lastName = "", number = "", sort = "", temp;
        BigDecimal balance = new BigDecimal("0.00");
        String procedure = "firstName";
        boolean validInput = false;
        while (!validInput){
            switch(procedure){
                case "firstName":
                    System.out.println("Please enter the new account first name:");
                    temp = input.nextLine();
                    if (!allLetters(temp)){
                        System.out.println("Please ensure the account first name consists of only letters");
                        continue;
                    }
                    else{
                        firstName = temp;
                        procedure = "secondName";
                        continue;
                    }
                case "secondName":
                    System.out.println("Please enter the new account surname:");
                    temp = input.nextLine();
                    if (!allLetters(temp)){
                        System.out.println("Please ensure the account surname consists of only letters");
                        continue;
                    }
                    else{
                        lastName = temp;
                        procedure = "number";
                        continue;
                    }
                case "number":
                    System.out.println("Please enter the account number:");
                    temp = input.nextLine();
                    if (!allNumbers(temp)){
                        System.out.println("Please ensure the account number consists of only numbers");
                        continue;
                    }
                    else{
                        number = temp;
                        procedure = "sort";
                        continue;
                    }
                case "sort":
                    System.out.println("Please enter the account sort code:");
                    temp = input.nextLine();
                    if (!allNumbers(temp)){
                        System.out.println("Please ensure the account sort code consists of only numbers");
                        continue;
                    }
                    else{
                        sort = temp;
                        procedure = "balance";
                        continue;
                    }
                case "balance":
                    System.out.println("Please confirm the opening balance");
                    if (input.hasNextBigDecimal())
                    {
                        balance = input.nextBigDecimal();
                        if (balance.scale() > 2)
                        {
                            System.out.println("Please enter a number formatted to no more than 2 digital places");
                            continue;
                        }
                    }
                    else{
                        System.out.println("Please enter a numerical amount");
                        input.nextLine();
                        continue;
                    }
                    procedure = "end";
            }
            validInput = true;
        }
        BankAccount newAccount = new BankAccount(firstName, lastName, number, sort, balance);
        bankAccounts.add(newAccount);
        System.out.println("Account successfully created");
        System.out.println(newAccount);
        input.nextLine();
    }
    
    ////Function to select which account to manage, by account number////
    public static void selectAccount()
    {
        boolean exit = false;
        String search;
        int index = 0;
        while (!exit)
        {
            System.out.println("Please enter the account number of the account to manage:");
            search = input.nextLine();
            if (!allNumbers(search)){
                System.out.println("Please enter a valid number");
                continue;
            }
            else
                index = findBankAccount(search);
            if (index == 0){
                System.out.println("No bank account found, please try again");
                break;
            }
            index -= 1;
            break;
        }
        manageAccount(index);
    }
    
    ////Function to locate bank account, if it exists////
    public static int findBankAccount(String search)
    {
        for (int x = 0; x < bankAccounts.size(); x++)
        {
            if (bankAccounts.get(x).getNumber().equals(search))
            {
                return x + 1;
            }
        }
    return 0;
    }
    
    ////Function to display account managing options////
    public static void manageAccount(int index)
    {
        BigDecimal temp;
        boolean exit = false;
        System.out.println(bankAccounts.get(index).toString());
        while (!exit)
        {
            
            System.out.println("Press 1 to make a deposit");
            System.out.println("Press 2 to make a withdrawal");
            System.out.println("Press 3 to add 3% interest to the account");
            System.out.println("Press 4 to return to the main menu");
            switch(input.nextLine())
            {
                case "1":
                    System.out.println("Please enter the amount to deposit: ");
                    if (input.hasNextBigDecimal()){
                        temp = input.nextBigDecimal();
                        if (temp.scale() > 2)
                        {
                            System.out.println("Please enter a number formatted to no more than 2 digital places");
                            continue;
                        }
                    }
                    else{
                        System.out.println("Please enter a numerical amount");
                        input.nextLine();
                        continue;
                    }
                    editBalance(index, true, temp);
                    break;
                case "2":
                    System.out.println("Please enter the amount to withdraw");
                    if (input.hasNextBigDecimal())
                    {
                        temp = input.nextBigDecimal();
                        if (temp.scale() > 2)
                        {
                            System.out.println("Please enter a number formatted to no more than 2 digital places");
                            continue;
                        }
                    }
                    else{
                        System.out.println("Please enter a numerical amount");
                        input.nextLine();
                        continue;
                    }
                    editBalance(index, false, temp);
                    break;
                case "3":
                    bankAccounts.get(index).addInterest();
                    System.out.println("3% interest added. The new balance is £" + bankAccounts.get(index).getBalance());
                    break;
                case "4":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid input, please try again");
            }
        }
        
    }
    
    ////Function to deposit or withdraw funds to account////
    public static void editBalance(int x, boolean deposit, BigDecimal amount)
    {
        if (deposit)
        {
            bankAccounts.get(x).setBalance(bankAccounts.get(x).getBalance().add(amount));
        }
        else
        {
            if (bankAccounts.get(x).getBalance().compareTo(amount) >= 0)
            {
                bankAccounts.get(x).setBalance(bankAccounts.get(x).getBalance().subtract(amount));
            }
            else
            {
                System.out.println("Insufficient funds to complete the withdrawal");
                input.nextLine();
                return;
            }
        }
        input.nextLine();
        System.out.println("Balance updated");
        System.out.println("New balance is: £" + bankAccounts.get(x).getBalance());
    }
    
    ////Functions to verify input is either all letters or numbers////
    public static boolean allLetters(String name)
    {
        return name.matches("[a-zA-Z]+");
    }
    public static boolean allNumbers(String number)
    {
        return number.matches("[0-9]+");
    }
}