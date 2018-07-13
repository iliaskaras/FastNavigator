package Exceptions;

import Exceptions.ErrorCodes.ExceptionErrorCodes;

/**
 * Created by ILIAS on 13/7/2018.
 */

public class MyDustbinException extends Exception {

    private final ExceptionErrorCodes code;

    public MyDustbinException(ExceptionErrorCodes code) {
        super(code.getErrorMessage());
        this.code = code;
    }

    public MyDustbinException(String message, Throwable cause, ExceptionErrorCodes code) {
        super(message, cause);
        this.code = code;
    }

    public MyDustbinException(String message, ExceptionErrorCodes code) {
        super(message);
        this.code = code;
    }

    public MyDustbinException(Throwable cause, ExceptionErrorCodes code) {
        super(code.getErrorMessage(), cause);
        this.code = code;
    }

    public ExceptionErrorCodes getCode() {
        return code;
    }
}
