package com.example.AuthServer.service;

public interface EmailService {

    void sendTemporaryPasswordEmail(String to, String temporaryPassword);

}
