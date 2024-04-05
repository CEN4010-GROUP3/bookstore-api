package org.example;
package edu.fiu.cen4010.group3.view;

import io.javalin.Javalin;
import java.util.HashMap;
import java.util.Map;
import edu.fiu.cen4010.group3.data.UserProfileDAO;

class UserProfileApp {

    static Map<String, UserProfile> userProfiles = new HashMap<>();
    static Map<String, CreditCard> userCreditCards = new HashMap<>();
    
    public static void createUser(Context ctx){
        UserProfile userProfile = ctx.bodyAsClass(UserProfile.class);
            userProfiles.put(userProfile.getUsername(), userProfile);
            ctx.status(201).result("User profile created successfully");
    }
    
    public static void retrieveUser(Context ctx){
         String username = ctx.pathParam("username");
            UserProfile userProfile = userProfiles.get(username);
            if (userProfile != null) {
                ctx.json(userProfile);
            } else {
                ctx.status(404).result("User profile not found");
            }
    }

    public static void updateUser(Context ctx){
         String username = ctx.pathParam("username");
            UserProfile userProfile = userProfiles.get(username);
            if (userProfile != null) {
                UserProfile updatedProfile = ctx.bodyAsClass(UserProfile.class);
                userProfile.setName(updatedProfile.getName());
                userProfile.setAddress(updatedProfile.getAddress());
                userProfile.setEmail(updatedProfile.getEmail());
                userProfile.setPassword(updatedProfile.getPassword());
                ctx.status(204).result("User profile updated successfully");
            } else {
                ctx.status(404).result("User profile not found");
            }
        }

    public static void creditCard(Context ctx){
         String username = ctx.pathParam("username");
            UserProfile existingProfile = userProfiles.get(username);
            if (existingProfile != null) {
                // Extract credit card information from request body
                CreditCard creditCard = ctx.bodyAsClass(CreditCard.class);
                // Update credit card information in user's profile
                existingProfile.setCreditCard(creditCard);
                ctx.result("Credit card information updated successfully");
            } else {
                ctx.status(404).result("User profile not found");
            }
        }
       
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
