package Model;

/**
 * Created by ILIAS on 3/7/2018.
 */

public class Dustbin {

    private String location;
    private Id _id;
    private Coordinates coordinates = new Coordinates();

    public Dustbin() {
    }

    public Dustbin(String location, Coordinates coordinates) {
        this.location = location;
        this.coordinates = coordinates;
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
