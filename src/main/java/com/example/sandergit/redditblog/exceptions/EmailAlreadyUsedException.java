package com.example.sandergit.redditblog.exceptions;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException() {
        super("Email already used!");
    }
}
