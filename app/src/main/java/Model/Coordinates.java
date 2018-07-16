package Model;

import java.io.Serializable;

/**
 * Created by ILIAS on 3/7/2018.
 */

public class Coordinates implements Serializable{

    private double lat;
    private double longitude;

    public Coordinates(double latitude, double longitude) {
        this.lat = latitude;
        this.longitude = longitude;
    }

    public Coordinates() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
