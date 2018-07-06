package AsyncTaskControllers;

/**
 * Created by ILIAS on 6/7/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Adapters.CustomAdapter;
import Model.Dustbin;
import Services.GetService;

import static android.content.ContentValues.TAG;

/** Process Data */
public class GetData extends AsyncTask<String,Void,String> {

    private static final String TAG = "AARestTask";
    public static final String HTTP_RESPONSE = "httpResponse";

    ProgressDialog progressDialog;

    public DownloadTaskListener mListener;

//    public interface DownloadTaskListener
//    {
//        public void onDownloadFinish(String s);
//        public void onDownloadProgress(float progress);
//    }

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
//            /** Done Process */
//            /** Use Gson to parse Json to Class*/
//            Gson gson = new Gson();
//            Type listType = new TypeToken<List<Dustbin>>(){}.getType();
//            dustbins = gson.fromJson(s,listType);
//            CustomAdapter adapter = new CustomAdapter(mContext.getApplicationContext(),dustbins);
//            listView.setAdapter(adapter);
//            progressDialog.dismiss();
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
