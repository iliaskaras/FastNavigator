package Services;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by ILIAS on 5/7/2018.
 */

public class PutService {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void PutHTTPData(String urlString, String newValue){
        try{
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);

            byte[] out = newValue.getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            urlConnection.setFixedLengthStreamingMode(length);
            urlConnection.setRequestProperty("Content-Type","application/json; charset-UTF-8");
            urlConnection.connect();

            try(OutputStream os = urlConnection.getOutputStream()){
                os.write(out);

            }

            InputStream response = urlConnection.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
