package Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ILIAS on 3/7/2018.
 */

public class Dustbin {

//    @SerializedName("$location")
    private String location;

//    @SerializedName("$oid")
    private Id _id;

//    @SerializedName("$coordinates")
    private Coordinates coordinates = new Coordinates();

    public Dustbin(String location, Coordinates coordinates,  Id _id) {
        this.location = location;
        this.coordinates = coordinates;
        this._id = _id;
        try{

        }catch(Exception ex){
            System.out.println("exception caught");
        }
    }

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
}
