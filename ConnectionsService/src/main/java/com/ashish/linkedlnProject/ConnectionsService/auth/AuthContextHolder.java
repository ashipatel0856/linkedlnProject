package com.ashish.linkedlnProject.ConnectionsService.auth;

public class AuthContextHolder {

    private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();

    public static Long getCurrentUser() {
        return currentUserId.get();
    }

    static void setCurrentUserId(Long userId) {
        currentUserId.set(userId);
    }

    static void clear() {
        currentUserId.remove();
    }


}
