package org.example;
package edu.fiu.cen4010.group3.data;

import java.util.HashMap;
import java.util.Map;


public class UserProfileDAO {
    // In-memory database to store user profiles
    private Map<String, UserProfileApp.UserProfile> userProfiles = new HashMap<>();

    // Constructor to initialize the in-memory database
    public UserProfileDAO() {
    }

    // Create a new user profile
    public void createUserProfile(UserProfileApp.UserProfile userProfile) {
        userProfiles.put(userProfile.getUsername(), userProfile);
    }

    // Retrieve a user profile by username
    public UserProfileApp.UserProfile getUserProfile(String username) {
        return userProfiles.get(username);
    }

    // Update an existing user profile
    public void updateUserProfile(String username, UserProfileApp.UserProfile updatedProfile) {
        userProfiles.put(username, updatedProfile);
    }

    // Delete a user profile by username
    public void deleteUserProfile(String username) {
        userProfiles.remove(username);
    }
}
