package edu.fiu.cen4010.group3.data;

import edu.fiu.cen4010.group3.model.UserProfile;
import java.util.HashMap;
import java.util.Map;


public class UserProfileDao {
    // In-memory database to store user profiles
    private Map<String, UserProfile> userProfiles = new HashMap<>();

    // Constructor to initialize the in-memory database
    public UserProfileDao() {
    }

    // Create a new user profile
    public void createUserProfile(UserProfile userProfile) {
        userProfiles.put(userProfile.getUsername(), userProfile);
    }

    // Retrieve a user profile by username
    public UserProfile getUserProfile(String username) {
        return userProfiles.get(username);
    }

    // Update an existing user profile
    public void updateUserProfile(String username, UserProfile updatedProfile) {
        userProfiles.put(username, updatedProfile);
    }

    // Delete a user profile by username
    public void deleteUserProfile(String username) {
        userProfiles.remove(username);
    }
}
