package Controllers.AsyncTaskControllers;

/**
 * Created by ILIAS on 6/7/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import Model.Dustbin;
import Services.DeleteService;

/** Delete Data */
public class DeleteDataTaskController extends AsyncTask<String,String,String> implements ITaskStrategy {

    ProgressDialog progressDialog;
    Dustbin dustbin;

    private Context mContext;
    private GetDataTaskController getData;
    public DownloadTaskListener mListener;

    public DeleteDataTaskController(ProgressDialog progressDialog, Context mContext, Dustbin dustbin) {
        this.dustbin = dustbin;
        this.progressDialog = progressDialog;
        this.mContext = mContext;
        setmListener((DownloadTaskListener)mContext);

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

        DeleteService deleteService = new DeleteService();
        String json = "{\"location\":\""+this.dustbin.getLocation()+"\"}";
        deleteService.DeleteHTTPData(urlString,json);

        return "";
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
