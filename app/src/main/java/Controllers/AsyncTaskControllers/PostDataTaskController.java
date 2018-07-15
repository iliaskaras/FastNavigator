package Controllers.AsyncTaskControllers;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import Model.Dustbin;
import Services.PostService;

/**
 * Created by ILIAS on 6/7/2018.
 */

public class PostDataTaskController extends AsyncTask<String,String,String> {

    ProgressDialog progressDialog;
    String userName;
    private Dustbin dustbin;

    public DownloadTaskListener mListener;

    public PostDataTaskController(ProgressDialog progressDialog, String userName) {
        this.progressDialog = progressDialog;
        this.userName = userName;
    }

    public PostDataTaskController(ProgressDialog progressDialog, String dustbinLocation, String dustbinLatitude, String dustbinLongitude) {
        this.progressDialog = progressDialog;

    }

    public PostDataTaskController(ProgressDialog progressDialog, Dustbin dustbin) {
        this.progressDialog = progressDialog;
        this.dustbin = dustbin;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        /** Pre Process */
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(mListener != null)
        {
            mListener.onDownloadFinish(s);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];

        PostService postService = new PostService();
        String json = "{\"location\":\""+this.dustbin.getLocation()+"\"," +
                "\"coordinates\":{" +
                "\"lat\":"+this.dustbin.getCoordinates().getLat()+"," +
                "\"longitude\":"+this.dustbin.getCoordinates().getLongitude()+"}}";

        postService.PostHTTPData(urlString,json);

        return "";
    }

}
