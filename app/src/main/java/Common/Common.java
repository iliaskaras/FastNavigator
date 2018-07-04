package Common;

import Model.Dustbin;

/**
 * Created by ILIAS on 4/7/2018.
 */

public class Common {

    private static String DB_NAME = "navigation_map";
    private static String COLLECTION_NAME = "dustbin";
    public static String API_KEY = "waqGYV92hh5RC-PbUb8VI_TFkXnKxGZH";


    public static String getAddressSingle(Dustbin dustbin){
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,COLLECTION_NAME);
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("/"+dustbin.get_id()
                .getOid()
                +"?apiKey="+API_KEY);

        return stringBuilder.toString();
    }

    public static String getAddressAPI(){
        String baseUrl = String.format("https://api.mlab.com/api/1/databases/%s/collections/%s",DB_NAME,COLLECTION_NAME);
        StringBuilder stringBuilder = new StringBuilder(baseUrl);
        stringBuilder.append("?apiKey="+API_KEY);

        return stringBuilder.toString();
    }
}
