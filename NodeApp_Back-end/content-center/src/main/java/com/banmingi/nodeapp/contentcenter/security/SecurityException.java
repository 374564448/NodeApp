package com.banmingi.nodeapp.contentcenter.security;

/**
 * @auther 半命i 2020/4/22
 * @description
 */
public class SecurityException extends RuntimeException {

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
