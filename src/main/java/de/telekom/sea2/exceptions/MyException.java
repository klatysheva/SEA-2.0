package de.telekom.sea2.exceptions;

public class MyException extends RuntimeException {
    private String message;

    public MyException(String message) {
        System.out.println(message);
    }
}
