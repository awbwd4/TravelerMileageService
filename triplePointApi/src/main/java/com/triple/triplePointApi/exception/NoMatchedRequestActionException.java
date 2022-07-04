package com.triple.triplePointApi.exception;

public class NoMatchedRequestActionException extends RuntimeException{

    public NoMatchedRequestActionException() {
        super();
    }
    public NoMatchedRequestActionException(String message) {
        super(message);
    }
    public NoMatchedRequestActionException(String message, Throwable cause) {
        super(message,cause);
    }
    public NoMatchedRequestActionException(Throwable cause) {
        super(cause);
    }


}
