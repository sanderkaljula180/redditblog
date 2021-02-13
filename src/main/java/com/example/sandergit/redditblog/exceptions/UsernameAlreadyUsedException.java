package com.example.sandergit.redditblog.exceptions;

public class UsernameAlreadyUsedException extends RuntimeException {

    public UsernameAlreadyUsedException() {
        super("Login name already used");
    }
}
