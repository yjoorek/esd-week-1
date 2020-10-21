package banking;

import java.math.*;

public class BankAccount
{
    private String firstName;
    private String lastName;
    private final String number;
    private final String code;
    private BigDecimal balance;
    public BigDecimal temp;
    public static final BigDecimal INTEREST = new BigDecimal(3);
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public BankAccount(String accName, String accSurname, String accNo, String sortCode, BigDecimal bal)
    {
        firstName = accName;
        lastName = accSurname;
        number = accNo;
        code = sortCode;
        balance = bal;
    }
    public String getName(){
        return firstName;
    }
    public String getSurname(){
        return lastName;
    }
    public String getNumber(){
        return number;
    }
    public String getCode(){
        return code;
    }
    public BigDecimal getBalance(){
        return balance;
    }
    public void setName(String accName){
        firstName = accName;
    }
    public void setSurname(String accSurname){
        lastName = accSurname;
    }
    public void setBalance(BigDecimal bal){
        balance = bal;
    }
    public void addInterest()
    {
        temp = balance;
        balance = (balance.multiply(INTEREST));
        balance = (balance.divide(ONE_HUNDRED));
        balance = (temp.add(balance));
        balance = balance.setScale(2, RoundingMode.HALF_DOWN);
    }
    @Override
    public String toString()
    {
        String accountString = "Account Name: " + getName() + " " + getSurname()
                + "\nAccount Number: " + getNumber()
                + "\nSort Code: " + getCode()
                + "\nAccount Balance: £" + getBalance();
        return accountString;
    }
}