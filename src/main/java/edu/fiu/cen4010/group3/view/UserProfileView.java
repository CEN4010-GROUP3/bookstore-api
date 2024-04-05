package org.example;
package edu.fiu.cen4010.group3.view;

import io.javalin.Javalin;
import java.util.HashMap;
import java.util.Map;
import edu.fiu.cen4010.group3.data.BookDao;
import edu.fiu.cen4010.group3.data.WishlistDao;
import edu.fiu.cen4010.group3.model.Book;
import edu.fiu.cen4010.group3.model.Wishlist;

class UserProfileApp {

    static Map<String, UserProfile> userProfiles = new HashMap<>();
    static Map<String, CreditCard> userCreditCards = new HashMap<>();
  static class UserProfile {
        private String username;
        private String password;
        private String name;
        private String email;
        private String address;
        private CreditCard creditCard;

        // Default constructor
        public UserProfile() {
        }

        // Constructor with parameters
        public UserProfile(String username, String password, String name, String email, String address) {
            this.username = username;
            this.password = password;
            this.name = name;
            this.email = email;
            this.address = address;
        }

        // Getters and setters for all fields
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public CreditCard getCreditCard() {
            return creditCard;
        }

        public void setCreditCard(CreditCard creditCard) {
            this.creditCard = creditCard;
        }
    }

    static class CreditCard {
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
}
