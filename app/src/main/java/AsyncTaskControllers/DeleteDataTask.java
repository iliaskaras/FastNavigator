package AsyncTaskControllers;

/**
 * Created by ILIAS on 6/7/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;

import Common.Common;
import Model.Dustbin;
import Services.DeleteService;
import gr.onetouchaway.findeverything.fast_navigator.MainActivity;

/** Delete Data */
public class DeleteDataTask extends AsyncTask<String,String,String> {

    ProgressDialog progressDialog;
    Dustbin dustbin;

    private Context mContext;
    private GetData getData;
    public DownloadTaskListener mListener;

    public DeleteDataTask(ProgressDialog progressDialog, Context mContext, Dustbin dustbin) {
        this.dustbin = dustbin;
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

        /** Refresh Data */

//        new GetData(progressDialog,mContext).execute(Common.getAddressAPI());
//
//        progressDialog.dismiss();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected String doInBackground(String... params) {
        String urlString = params[0];

        DeleteService deleteService = new DeleteService();
        String json = "{\"location\":\""+this.dustbin.getLocation()+"\"}";
//        String json = "{\"_id\":{\"$oid\":\""+this.dustbin.get_id()+"\"}";
        deleteService.DeleteHTTPData(urlString,json);

        return "";
    }
}
