package Controllers.AsyncTaskControllers;

/**
 * Created by ILIAS on 6/7/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;

import Services.GetService;


/** Process Data */
public class GetDataTaskController extends AsyncTask<String,Void,String> implements ITaskStrategy {


    ProgressDialog progressDialog;

    public DownloadTaskListener mListener;

    public GetDataTaskController(ProgressDialog progressDialog, Context mContext) {
        this.progressDialog = progressDialog;
        setmListener((DownloadTaskListener)mContext);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        /** Pre Process */
        progressDialog.setTitle("Please wait...");
        progressDialog.show();
    }

    public DownloadTaskListener getmListener() {
        return mListener;
    }

    public void setmListener(DownloadTaskListener mListener) {
        this.mListener = mListener;
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
        try {
            stream = getService.GetHTTPData(urlString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stream;
    }

    @Override
    public void executeOnClick(String... params) {
        this.execute(params);
    }


}
