package com.triple.triplePointApi.exception;

public class AbnormalPointException extends RuntimeException{

    public AbnormalPointException() {
        super();
    }
    public AbnormalPointException(String message) {
        super(message);
    }
    public AbnormalPointException(String message, Throwable cause) {
        super(message,cause);
    }
    public AbnormalPointException(Throwable cause) {
        super(cause);
    }


}
