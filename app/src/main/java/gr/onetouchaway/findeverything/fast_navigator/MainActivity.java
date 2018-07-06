package gr.onetouchaway.findeverything.fast_navigator;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Adapters.CustomAdapter;
import AsyncTaskControllers.DeleteDataTask;
import AsyncTaskControllers.DownloadTaskListener;
import AsyncTaskControllers.PostDataTask;
import AsyncTaskControllers.PutDataTask;
import Common.Common;
import Common.HTTPDataHandler;
import Model.Dustbin;
import Services.DeleteService;
import Services.GetService;
import Services.PostService;
import Services.PutService;
import AsyncTaskControllers.GetData;

public class MainActivity extends AppCompatActivity implements DownloadTaskListener {
    private static final String ACTION_FOR_INTENT_CALLBACK = "THIS_IS_A_UNIQUE_KEY_WE_USE_TO_COMMUNICATE";

    ListView listView;
    Button btnAdd,btnDelete,btnEdit;
    EditText editUser;
    Dustbin dustbinSelected = null;
    List<Dustbin> dustbins = new ArrayList<Dustbin>();
    GetData getData;
    PostDataTask postDataTask;
    PutDataTask putDataTask;
    DeleteDataTask deleteDataTask;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        editUser = (EditText) findViewById(R.id.editUsername);

        progressDialog = new ProgressDialog(MainActivity.this);

        getData = new GetData(progressDialog, MainActivity.this);
        getData.mListener = this;
        getData.execute(Common.getAddressAPI());

        // Data loading
//TODO        new GetData().execute(Common.getAddressAPI());

        /** Setup Dustbin Selections availability */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dustbinSelected = dustbins.get(position);

                /** set the selected dustbin location to edtUser textview */
                editUser.setText(dustbinSelected.getLocation());
            }
        });

        /** Button Events */

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                postDataTask = new PostDataTask(progressDialog, MainActivity.this,editUser.getText().toString());
                postDataTask.mListener = MainActivity.this;
                postDataTask.execute(Common.getAddressAPI());

                getData = new GetData(progressDialog, MainActivity.this);
                getData.mListener = MainActivity.this;
                getData.execute(Common.getAddressAPI());

//   TODO             new PostData(editUser.getText().toString()).execute(Common.getAddressAPI());
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                putDataTask = new PutDataTask(progressDialog, MainActivity.this, dustbinSelected);
                putDataTask.mListener = MainActivity.this;
                putDataTask.execute(Common.getAddressSingle(dustbinSelected));

                getData = new GetData(progressDialog, MainActivity.this);
                getData.mListener = MainActivity.this;
                getData.execute(Common.getAddressAPI());
//   TODO                  new PutData(dustbinSelected).execute(Common.getAddressSingle(dustbinSelected));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteDataTask = new DeleteDataTask(progressDialog, MainActivity.this, dustbinSelected);
                deleteDataTask.mListener = MainActivity.this;
                deleteDataTask.execute(Common.getAddressSingle(dustbinSelected));

                getData = new GetData(progressDialog, MainActivity.this);
                getData.mListener = MainActivity.this;
                getData.execute(Common.getAddressAPI());

//                new DeleteData(dustbinSelected).execute(Common.getAddressAPI());

            }
        });

    }

    @Override
    public void onDownloadFinish(final String s) {
        MainActivity.this.runOnUiThread(new Runnable()
        {
            public void run()
            {
                /** Done Process */
                /** Use Gson to parse Json to Class*/
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Dustbin>>(){}.getType();
                dustbins = gson.fromJson(s,listType);
                CustomAdapter adapter = new CustomAdapter(getApplicationContext(),dustbins);
                listView.setAdapter(adapter);
                progressDialog.dismiss();

                //updates to UI here.
            }
        });
    }

    @Override
    public void onDownloadProgress(float progress) {
        MainActivity.this.runOnUiThread(new Runnable()
        {
            public void run()
            {
                //updates to UI here.
            }
        });
    }


//    /** Process Data */
//    class GetData extends AsyncTask<String,Void,String>{
//
//        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            /** Pre Process */
//            progressDialog.setTitle("Please wait...");
//            progressDialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            /** Done Process */
//            /** Use Gson to parse Json to Class*/
//            Gson gson = new Gson();
//            Type listType = new TypeToken<List<Dustbin>>(){}.getType();
//            dustbins = gson.fromJson(s,listType);
//            CustomAdapter adapter = new CustomAdapter(getApplicationContext(),dustbins);
//            listView.setAdapter(adapter);
//            progressDialog.dismiss();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            String stream = null;
//            String urlString = params[0];
//
//            GetService getService = new GetService();
//            stream = getService.GetHTTPData(urlString);
//
//            return stream;
//        }
//    }

//    /** Process Data */
//    class PostData extends AsyncTask<String,String,String>{
//
//        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
//        String userName;
//
//        public PostData(String userName) {
//            this.userName = userName;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            /** Pre Process */
//            progressDialog.setTitle("Please wait...");
//            progressDialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            /** Refresh Data */
////            new GetData().execute(Common.getAddressAPI());
//            getData.execute(Common.getAddressAPI());
//
//            progressDialog.dismiss();
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        @Override
//        protected String doInBackground(String... params) {
//            String urlString = params[0];
//
//            PostService postService = new PostService();
//            String json = "{\"user\":\""+this.userName+"\"}";
//
//            postService.PostHTTPData(urlString,json);
//
//            return "";
//        }
//    }

//    /** Edit Data */
//    class PutData extends AsyncTask<String,String,String>{
//
//        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
//        Dustbin dustbinSelected;
//
//        public PutData(Dustbin dustbinSelected) {
//            this.dustbinSelected = dustbinSelected;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            /** Pre Process */
//            progressDialog.setTitle("Please wait...");
//            progressDialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            /** Refresh Data */
////            new GetData().execute(Common.getAddressAPI());
//            getData.execute(Common.getAddressAPI());
//
//            progressDialog.dismiss();
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        @Override
//        protected String doInBackground(String... params) {
//            String urlString = params[0];
//
//            PutService putService = new PutService();
//            String json = "{\"location\":\""+this.dustbinSelected.getLocation()+"\"," +
//                    "\"coordinates\":{" +
//                    "\"lat\":"+this.dustbinSelected.getCoordinates().getLat()+"," +
//                    "\"longitude\":"+this.dustbinSelected.getCoordinates().getLongitude()+"}}";
//
//            putService.PutHTTPData(urlString,json);
//
//            return "";
//        }
//    }

//    /** Delete Data */
//    class DeleteData extends AsyncTask<String,String,String>{
//
//        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
//        Dustbin dustbin;
//
//        public DeleteData(Dustbin dustbin) {
//            this.dustbin = dustbin;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            /** Pre Process */
//            progressDialog.setTitle("Please wait...");
//            progressDialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            /** Refresh Data */
////            new GetData(progressDialog,MainActivity.this).execute(Common.getAddressAPI());
//            progressDialog.dismiss();
//            getData = new GetData(progressDialog, MainActivity.this);
//            getData.mListener = MainActivity.this;
//            getData.execute(Common.getAddressAPI());
////            getData.execute(Common.getAddressAPI());
//
////            progressDialog.dismiss();
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//        @Override
//        protected String doInBackground(String... params) {
//            String urlString = params[0];
//
//            DeleteService deleteService = new DeleteService();
//            String json = "{\"user\":\""+this.dustbin.getLocation()+"\"}";
//
//            deleteService.DeleteHTTPData(urlString,json);
//
//            return "";
//        }
//    }
}
