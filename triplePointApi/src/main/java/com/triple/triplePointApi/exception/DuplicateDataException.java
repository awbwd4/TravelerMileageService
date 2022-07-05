package com.triple.triplePointApi.exception;

public class DuplicateDataException extends Exception{

    public DuplicateDataException() {
        super();
    }
    public DuplicateDataException(String message) {
        super(message);
    }
    public DuplicateDataException(String message, Throwable cause) {
        super(message,cause);
    }
    public DuplicateDataException(Throwable cause) {
        super(cause);
    }


}
