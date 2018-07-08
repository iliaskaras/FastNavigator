package AsyncTaskControllers;

/**
 * Created by ILIAS on 6/7/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import Services.GetService;


/** Process Data */
public class GetData extends AsyncTask<String,Void,String> {


    ProgressDialog progressDialog;

    public DownloadTaskListener mListener;

    public GetData(ProgressDialog progressDialog, Context mContext) {
        this.progressDialog = progressDialog;
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

    @Override
    protected String doInBackground(String... params) {

        String stream = null;
        String urlString = params[0];

        GetService getService = new GetService();
        stream = getService.GetHTTPData(urlString);

        return stream;
    }
}
