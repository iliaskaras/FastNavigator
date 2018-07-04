package Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ILIAS on 4/7/2018.
 */

public class Id {
    @SerializedName("$oid")
    private String oid;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }


}
