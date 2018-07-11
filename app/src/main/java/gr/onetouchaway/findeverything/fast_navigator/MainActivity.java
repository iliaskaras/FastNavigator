package gr.onetouchaway.findeverything.fast_navigator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import Adapters.CustomAdapter;
import AsyncTaskControllers.DeleteDataTask;
import AsyncTaskControllers.DownloadTaskListener;
import AsyncTaskControllers.PutDataTask;
import Common.Common;
import Controllers.FileReaderController;
import Controllers.JsonController;
import Controllers.FileWriterController;
import Model.Dustbin;
import AsyncTaskControllers.GetData;

public class MainActivity extends AppCompatActivity implements DownloadTaskListener, View.OnClickListener {

    private ListView listView;
    private EditText editUser;
    private Dustbin dustbinSelected = null;
    private List<Dustbin> dustbins = new ArrayList<Dustbin>();
    private GetData getData;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnEdit = (Button) findViewById(R.id.btnEdit);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        Button btnGoogleMap = (Button) findViewById(R.id.btnGoogleMap);
        listView = (ListView)findViewById(R.id.listView);
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

        /** Button Listeners initialize */
        btnAdd.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
//        btnAdd.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                postDataTask = new PostDataTask(progressDialog, MainActivity.this,editUser.getText().toString());
//                postDataTask.mListener = MainActivity.this;
//                postDataTask.execute(Common.getAddressAPI());
//
//                getData = new GetData(progressDialog, MainActivity.this);
//                getData.mListener = MainActivity.this;
//                getData.execute(Common.getAddressAPI());
//
//            }
//        });
//
//
//        btnEdit.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                putDataTask = new PutDataTask(progressDialog, MainActivity.this, dustbinSelected);
//                putDataTask.mListener = MainActivity.this;
//                putDataTask.execute(Common.getAddressSingle(dustbinSelected));
//
//                getData = new GetData(progressDialog, MainActivity.this);
//                getData.mListener = MainActivity.this;
//                getData.execute(Common.getAddressAPI());
//            }
//        });
//
//        btnDelete.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                deleteDataTask = new DeleteDataTask(progressDialog, MainActivity.this, dustbinSelected);
//                deleteDataTask.mListener = MainActivity.this;
//                deleteDataTask.execute(Common.getAddressSingle(dustbinSelected));
//
//                getData = new GetData(progressDialog, MainActivity.this);
//                getData.mListener = MainActivity.this;
//                getData.execute(Common.getAddressAPI());
//
//            }
//        });
        btnGoogleMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startGoogleMapsActivity();
            }
        });

    }

    @Override
    public void onClick(View v) {
        asyncTaskFactory(v);
    }

    private void startGoogleMapsActivity(){
        Intent myIntent = new Intent(MainActivity.this, MapsMarkerActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    private void asyncTaskFactory(View view){
        switch (view.getId()){
            case R.id.btnAdd:
                /** commented to test json dao and controller */
//                PostDataTask postDataTask = new PostDataTask(progressDialog, MainActivity.this, editUser.getText().toString());
//                postDataTask.mListener = MainActivity.this;
//                postDataTask.execute(Common.getAddressAPI());
//
//                getData = new GetData(progressDialog, MainActivity.this);
//                getData.mListener = MainActivity.this;
//                getData.execute(Common.getAddressAPI());
                JsonController jsonController = new JsonController(getAssets());
                List<Dustbin> dustbins = jsonController.getDustbinLists();
                for (int i = 0; i < dustbins.size(); i++) {
                    Dustbin dustbinTest = dustbins.get(i);
                }
                break;
            case R.id.btnEdit:
                PutDataTask putDataTask = new PutDataTask(progressDialog, MainActivity.this, dustbinSelected);
                putDataTask.mListener = MainActivity.this;
                putDataTask.execute(Common.getAddressSingle(dustbinSelected));

                getData = new GetData(progressDialog, MainActivity.this);
                getData.mListener = MainActivity.this;
                getData.execute(Common.getAddressAPI());
                break;
            case R.id.btnDelete:
                DeleteDataTask deleteDataTask = new DeleteDataTask(progressDialog, MainActivity.this, dustbinSelected);
                deleteDataTask.mListener = MainActivity.this;
                deleteDataTask.execute(Common.getAddressSingle(dustbinSelected));

                getData = new GetData(progressDialog, MainActivity.this);
                getData.mListener = MainActivity.this;
                getData.execute(Common.getAddressAPI());
                break;
            default:
                break;
        }
    }

    @Override
    public void onDownloadFinish(final String downloadedData) {
        MainActivity.this.runOnUiThread(new Runnable()
        {
            public void run()
            {
                /** Done Process */
                /** Use Gson to parse Json to Class*/
//                Gson gson = new Gson();
//                Type listType = new TypeToken<List<Dustbin>>(){}.getType();
//                dustbins = gson.fromJson(downloadedData,listType);
                JsonController jsonController = new JsonController();
                FileWriterController fileWriterController = new FileWriterController(getApplicationContext());
                FileReaderController fileReaderController = new FileReaderController(getApplicationContext());
                dustbins = jsonController.getDustbinLists(downloadedData);
                CustomAdapter adapter = new CustomAdapter(getApplicationContext(),dustbins);
                fileWriterController.writeTxtToResources(dustbins);
                String result = fileReaderController.readTxtToResources();


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
