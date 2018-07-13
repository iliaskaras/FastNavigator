package Exceptions;


import Exceptions.ErrorCodes.ExceptionErrorCodes;

/**
 * Created by ILIAS on 3/7/2018.
 */

public class MyCoordinateException extends Exception {

    private final ExceptionErrorCodes code;

    public MyCoordinateException(ExceptionErrorCodes code) {
        super(code.getErrorMessage());
        this.code = code;
    }

    public MyCoordinateException(String message, Throwable cause, ExceptionErrorCodes code) {
        super(message, cause);
        this.code = code;
    }

    public MyCoordinateException(String message, ExceptionErrorCodes code) {
        super(message);
        this.code = code;
    }

    public MyCoordinateException(Throwable cause, ExceptionErrorCodes code) {
        super(code.getErrorMessage(), cause);
        this.code = code;
    }

    public ExceptionErrorCodes getCode() {
        return code;
    }

}
