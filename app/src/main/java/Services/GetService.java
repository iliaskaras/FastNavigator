package Services;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ILIAS on 5/7/2018.
 */

public class GetService {

    private static String stream = null;

    public GetService(){
    }

    public String GetHTTPData(String urlString){
        try{
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            /** Connection status checking
             *  With response code equals to 200 then we are ok. */
            if(urlConnection.getResponseCode() == 200){

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while((line=bufferedReader.readLine())!=null)
                    stringBuilder.append(line);

                stream = stringBuilder.toString();

                urlConnection.disconnect();
            } else {

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stream;
    }

}
