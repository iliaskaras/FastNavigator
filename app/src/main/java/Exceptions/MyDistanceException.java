package Exceptions;

import Exceptions.ErrorCodes.ExceptionErrorCodes;

/**
 * Created by ILIAS on 19/7/2018.
 */

public class MyDistanceException extends Exception {

    private final ExceptionErrorCodes code;

    public MyDistanceException(ExceptionErrorCodes code) {
        super(code.getErrorMessage());
        this.code = code;
    }

    public MyDistanceException(String message, Throwable cause, ExceptionErrorCodes code) {
        super(message, cause);
        this.code = code;
    }

    public MyDistanceException(String message, ExceptionErrorCodes code) {
        super(message);
        this.code = code;
    }

    public MyDistanceException(Throwable cause, ExceptionErrorCodes code) {
        super(code.getErrorMessage(), cause);
        this.code = code;
    }

    public ExceptionErrorCodes getCode() {
        return code;
    }
}
