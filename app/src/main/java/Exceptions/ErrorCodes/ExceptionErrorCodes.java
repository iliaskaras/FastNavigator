package Exceptions.ErrorCodes;

/**
 * Created by ILIAS on 13/7/2018.
 */

public enum ExceptionErrorCodes {

//    DATABASE(0, "A database error has occured."),
//    DUPLICATE_USER(1, "This user already exists.");

    WRONG_COORDINATES(2, "You gave wrong coordinates."),
    WRONG_LAT(3, "You gave invalid latitude."),
    WRONG_LONG(3, "You gave invalid longitude.");

    private final int errorCode;
    private final String errorMessage;

    ExceptionErrorCodes(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return errorCode + " : " + errorMessage;
    }
}
