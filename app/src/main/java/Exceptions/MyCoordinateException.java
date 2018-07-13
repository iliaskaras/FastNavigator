package Exceptions;

/**
 * Created by ILIAS on 3/7/2018.
 */

public class MyCoordinateException extends Exception {

    private String detailMessage = null;

    public MyCoordinateException() {

    }

    public MyCoordinateException(String message) {
        super(message);
    }

    public MyCoordinateException(Throwable cause) {
        super(cause);
    }

    public MyCoordinateException(String message, Throwable cause) {
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
