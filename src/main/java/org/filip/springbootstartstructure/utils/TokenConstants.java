package org.filip.springbootstartstructure.utils;

public enum TokenConstants {


    TOKEN_INVALID("invalidToken"),
    TOKEN_EXPIRED("expired"),
    TOKEN_VALID("valid");


    private String tokenName;

    TokenConstants(String tokenName){
        this.tokenName = tokenName;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }
}
