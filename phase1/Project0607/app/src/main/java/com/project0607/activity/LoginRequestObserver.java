package com.project0607.activity;

public interface LoginRequestObserver {
    void validateCredentials(String username, String password);
}
