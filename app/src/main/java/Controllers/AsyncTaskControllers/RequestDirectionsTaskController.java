package Controllers.AsyncTaskControllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.android.gms.maps.GoogleMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import Services.GetService;


/**
 * Created by ILIAS on 26/7/2018.
 */

public class RequestDirectionsTaskController extends AsyncTask<String, Void, String> implements ITaskStrategy {

    private Context mContext;
    public DownloadTaskListener mListener;
    private GoogleMap mMap;

    public RequestDirectionsTaskController( Context mContext, GoogleMap mMap) {
        this.mContext = mContext;
        this.mMap = mMap;
        setmListener((DownloadTaskListener)mContext);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //Parse json here
        ParserTaskController taskParser = new ParserTaskController(this.mContext, this.mMap);
        taskParser.execute(s);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... params) {
        String responseString = "";
        GetService getService = new GetService();

        try{
            responseString = getService.GetHTTPData(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseString;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void executeOnClick(String... params) {
        this.execute(params);
    }

    public DownloadTaskListener getmListener() {
        return mListener;
    }

    public void setmListener(DownloadTaskListener mListener) {
        this.mListener = mListener;
    }

  
}
