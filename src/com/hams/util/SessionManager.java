package com.hams.util;

import com.hams.model.User;

public class SessionManager {

    private static User currentUser;

    // Set logged-in user
    public static void setUser(User user) {
        currentUser = user;
    }

    // Get logged-in user
    public static User getUser() {
        return currentUser;
    }

    // Clear session (logout)
    public static void clear() {
        currentUser = null;
    }
}
