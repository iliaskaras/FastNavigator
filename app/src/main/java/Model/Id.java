package Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ILIAS on 4/7/2018.
 */

public class Id implements Serializable{
    @SerializedName("$oid")
    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Id() {
    }

    public Id(String oid) {
        this.oid = oid;
    }


}
