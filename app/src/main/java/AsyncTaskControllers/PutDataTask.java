package AsyncTaskControllers;

/**
 * Created by ILIAS on 6/7/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import Model.Dustbin;
import Services.PutService;

/** Edit Data */
public class PutDataTask extends AsyncTask<String,String,String> {

    ProgressDialog progressDialog;
    Dustbin dustbinSelected;

    private Context mContext;
    private GetDataTask getData;
    public DownloadTaskListener mListener;

    public PutDataTask(ProgressDialog progressDialog, Context mContext, Dustbin dustbinSelected) {
        this.dustbinSelected = dustbinSelected;
        this.progressDialog = progressDialog;
        this.mContext = mContext;
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

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];

        PutService putService = new PutService();
        String json = "{\"location\":\"edited"+this.dustbinSelected.getLocation()+"\"," +
                "\"coordinates\":{" +
                "\"lat\":"+this.dustbinSelected.getCoordinates().getLat()+"," +
                "\"longitude\":"+this.dustbinSelected.getCoordinates().getLongitude()+"}}";

        putService.PutHTTPData(urlString,json);

        return "";
    }
}
