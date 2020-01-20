package com.automatodev.gcadmin.provider;

public class FidelityProvider {
    String userUid;
    String userName;
    int totalOrders;

    public FidelityProvider(String userUid, String userName, int totalOrders) {
        this.userUid = userUid;
        this.userName = userName;
        this.totalOrders = totalOrders;
    }

    public FidelityProvider() {
    }

    public String getUserUid() {
        return userUid;
    }

    public String getUserName() {
        return userName;
    }

    public int getTotalOrders() {
        return totalOrders;
    }
}
















































