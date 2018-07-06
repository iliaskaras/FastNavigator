package Exceptions;

/**
 * Created by ILIAS on 3/7/2018.
 */

public class CoordinateException extends Exception {

    private String detailMessage = null;

    public CoordinateException() {

    }

    public CoordinateException(String message) {
        super(message);
    }

    public CoordinateException(Throwable cause) {
        super(cause);
    }

    public CoordinateException(String message, Throwable cause) {
        super (message, cause);
    }

//    public CoordinateException(double latitude, double longitude) {
//
////        if(latidute == 0.0)
//        detailMessage += (latitude == 0.0 ? null : " | empty latitude | ");
//        detailMessage += (longitude == 0.0 ? detailMessage : " | empty longitude | ");
//
//        throw new NullPointerException(detailMessage);
////        super(detailMessage);
//    }

}
