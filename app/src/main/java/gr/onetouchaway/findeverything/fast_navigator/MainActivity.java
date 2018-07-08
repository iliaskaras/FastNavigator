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
import Model.Dustbin;
import AsyncTaskControllers.GetData;

public class MainActivity extends AppCompatActivity implements DownloadTaskListener {

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

            }
        });
    }

    @Override
    public void onDownloadProgress(float progress) {
        MainActivity.this.runOnUiThread(new Runnable()
        {
            public void run()
            {

            }
        });
    }

}
