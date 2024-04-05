package edu.fiu.cen4010.group3.model;

public class CreditCard {
    private String cardNumber;
    private String expirationDate;

    // Getters and setters for credit card fields
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}