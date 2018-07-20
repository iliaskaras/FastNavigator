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

public class DeleteService {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void DeleteHTTPData(String urlString, String json){
        HttpURLConnection httpURLConnection = null;

        try{
            URL url = new URL(urlString);
            httpURLConnection = (HttpURLConnection)url.openConnection();

            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.setDoOutput(true);

            byte[] out = json.getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            httpURLConnection.setFixedLengthStreamingMode(length);
            httpURLConnection.setRequestProperty("Content-Type","application/json; charset-UTF-8");
            httpURLConnection.connect();

            try(OutputStream os = httpURLConnection.getOutputStream()){
                os.write(out);
            }

            InputStream response = httpURLConnection.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(httpURLConnection!=null){
                httpURLConnection.disconnect();
            }
        }

    }
}
