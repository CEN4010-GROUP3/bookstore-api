package edu.fiu.cen4010.group3.view;

import edu.fiu.cen4010.group3.model.CreditCard;
import edu.fiu.cen4010.group3.model.UserProfile;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class UserProfileView {

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
       



}
