package com.example.demo.exception;

/**
 * 権限不足の例外を表現するクラスです。
 */
public class InsufficientPermissionException extends RuntimeException {

    public InsufficientPermissionException(String message) {
        super(message);
    }

}
