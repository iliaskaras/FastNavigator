package Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ILIAS on 3/7/2018.
 */

public class Dustbin implements Parcelable {

    private String location;
    private Id _id;
    private Coordinates coordinates = new Coordinates();
    private LatLng latLng;

    public Dustbin() {
    }

    public Dustbin(String location, Coordinates coordinates) {
        this.location = location;
        this.coordinates = coordinates;
    }

    protected Dustbin(Parcel in) {
        location = in.readString();
        coordinates = (Coordinates) in.readSerializable();
        _id = (Id) in.readSerializable();
    }

    public Dustbin(String location, Id _id, Coordinates coordinates, LatLng latLng) {
        this.location = location;
        this._id = _id;
        this.coordinates = coordinates;
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public static Creator<Dustbin> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<Dustbin> CREATOR = new Creator<Dustbin>() {
        @Override
        public Dustbin createFromParcel(Parcel in) {
            return new Dustbin(in);
        }

        @Override
        public Dustbin[] newArray(int size) {
            return new Dustbin[size];
        }
    };

    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getLocation());
        dest.writeSerializable(getCoordinates());
        dest.writeSerializable(get_id());

    }
}
