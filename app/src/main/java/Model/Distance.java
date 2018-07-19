package Model;

import com.google.android.gms.maps.model.LatLng;


/**
 * Created by ILIAS on 18/7/2018.
 */

public class Distance
        implements Comparable<Distance>
        {

    private Double distance;
    private String destLocation;
    private LatLng location;

    public Distance(Double distance, String destLocation, LatLng location) {
        this.distance = distance;
        this.destLocation = destLocation;
        this.location = location;

    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getDestLocation() {
        return destLocation;
    }

    public void setDestLocation(String destLocation) {
        this.destLocation = destLocation;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }


    @Override
    public int compareTo(Distance o) {
        double thisDistance = getDistance();
        double anotherDistance = o.getDistance();
        return (thisDistance<anotherDistance ? -1 : (thisDistance==anotherDistance ? 0 : 1));
    }


}
