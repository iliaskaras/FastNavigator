package AsyncTaskControllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import Services.PostService;

/**
 * Created by ILIAS on 6/7/2018.
 */

public class PostDataTask extends AsyncTask<String,String,String> {

    ProgressDialog progressDialog;
    String userName;
    private Context mContext;
    private GetData mClient;
    private String mAction;

    public DownloadTaskListener mListener;

    public PostDataTask(ProgressDialog progressDialog, Context mContext, String userName) {
        this.progressDialog = progressDialog;
        this.userName = userName;
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

        PostService postService = new PostService();
        String json = "{\"user\":\""+this.userName+"\"}";

        postService.PostHTTPData(urlString,json);

        return "";
    }

}
