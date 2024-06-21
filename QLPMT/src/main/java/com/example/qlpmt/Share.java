package com.example.qlpmt;

public class Share {
    private static Share instance = null;
    private String sharedVariable;

    private Share() {
        // Private constructor to prevent instantiation
    }

    public static Share getInstance() {
        if (instance == null) {
            instance = new Share();
        }
        return instance;
    }

    public String getSharedVariable() {
        return sharedVariable;
    }

    public void setSharedVariable(String sharedVariable) {
        this.sharedVariable = sharedVariable;
    }
}
