package Exceptions.ErrorCodes;

/**
 * Created by ILIAS on 13/7/2018.
 */

public enum ExceptionErrorCodes implements IErrorCodeConstant {

    WRONG_COORDINATES(CODE_WRONG_COORDINATES, "You gave wrong coordinates."),
    WRONG_LAT(CODE_WRONG_LAT, "You gave invalid latitude."),
    WRONG_LONG(CODE_WRONG_LONG, "You gave invalid longitude."),
    WRONG_LOCATION(CODE_WRONG_LOCATION, "You gave invalid location.");

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
