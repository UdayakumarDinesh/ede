package com.vts.ems.login;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
