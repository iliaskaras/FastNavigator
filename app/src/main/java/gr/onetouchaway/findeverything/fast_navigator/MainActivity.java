package gr.onetouchaway.findeverything.fast_navigator;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import Adapters.CustomAdapter;
import Common.Common;
import Common.HTTPDataHandler;
import Model.Dustbin;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button btnAdd,btnDelete,btnEdit;
    EditText editUser;
    Dustbin dustbinSelected = null;
    List<Dustbin> dustbins = new ArrayList<Dustbin>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        editUser = (EditText) findViewById(R.id.editUsername);

        // Data loading
        new GetData().execute(Common.getAddressAPI());

    }


    class GetData extends AsyncTask<String,Void,String>{

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

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

            /** Done Process */
            /** Use Gson to parse Json to Class*/
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Dustbin>>(){}.getType();
            dustbins = gson.fromJson(s,listType);
            CustomAdapter adapter = new CustomAdapter(getApplicationContext(),dustbins);
            listView.setAdapter(adapter);
            progressDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... params) {

            String stream = null;
            String urlString = params[0];

            HTTPDataHandler http = new HTTPDataHandler();
            stream = http.GetHTTPData(urlString);

            return stream;
        }
    }

}
