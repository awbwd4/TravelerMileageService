package com.triple.triplePointApi.exception;

public class NoSuitableReviewException extends Exception{

    public NoSuitableReviewException() {
        super();
    }
    public NoSuitableReviewException(String message) {
        super(message);
    }
    public NoSuitableReviewException(String message, Throwable cause) {
        super(message,cause);
    }
    public NoSuitableReviewException(Throwable cause) {
        super(cause);
    }


}
