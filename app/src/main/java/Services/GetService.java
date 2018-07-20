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

    public String GetHTTPData(String urlString) throws IOException {
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try{
            URL url = new URL(urlString);
            httpURLConnection = (HttpURLConnection)url.openConnection();

            /** Connection status checking
             *  With response code equals to 200 then we are ok. */
            if(httpURLConnection.getResponseCode() == 200){

                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while((line=bufferedReader.readLine())!=null)
                    stringBuilder.append(line);

                stream = stringBuilder.toString();

                bufferedReader.close();
                httpURLConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }  finally {
            if(inputStream!=null){
                inputStream.close();
            }
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
        }

        return stream;
    }

}
