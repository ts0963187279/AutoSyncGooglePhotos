package com.walton.java.autosyncgooglephotos.model;
public class AccessGoogleData {
    private String clientID;
    private String clientSecret;
    private String userName;
    private String authCode;
    private String Scope;
    private String firebaseToken;
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }
    public void setScope(String scope) {
        Scope = scope;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getClientID() {
        return clientID;
    }
    public String getClientSecret() {
        return clientSecret;
    }

    public String getAuthCode() {
        return authCode;
    }

    public String getFirebaseToken() {
        return firebaseToken;
    }

    public String getScope() {
        return Scope;
    }

    public String getUserName() {
        return userName;
    }
}
