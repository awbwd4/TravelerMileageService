package com.triple.triplePointApi.exception;

public class NoUserPointDataException extends Exception{

    public NoUserPointDataException() {
        super();
    }
    public NoUserPointDataException(String message) {
        super(message);
    }
    public NoUserPointDataException(String message, Throwable cause) {
        super(message,cause);
    }
    public NoUserPointDataException(Throwable cause) {
        super(cause);
    }


}
