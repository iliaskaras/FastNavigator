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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import Adapters.CustomAdapter;
import Controllers.AsyncTaskControllers.DeleteDataTaskController;
import Controllers.AsyncTaskControllers.DownloadTaskListener;
import Controllers.AsyncTaskControllers.PutDataTaskController;
import Common.Common;
import Controllers.AsyncTaskControllers.TaskStrategyContext;
import Controllers.DAO_Controllers.FileReaderController;
import Controllers.DAO_Controllers.JsonController;
import Controllers.DAO_Controllers.FileWriterController;
import Model.Dustbin;
import Controllers.AsyncTaskControllers.GetDataTaskController;

public class MainActivity extends AppCompatActivity implements DownloadTaskListener, View.OnClickListener {

    private ListView listView;
    private EditText editUser;
    private Dustbin dustbinSelected = null;
    private List<Dustbin> dustbins = new ArrayList<Dustbin>();
    private GetDataTaskController getData;
    private ProgressDialog progressDialog;
    private TaskStrategyContext taskStrategyContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnEdit = (Button) findViewById(R.id.btnEdit);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        Button btnGoogleMap = (Button) findViewById(R.id.btnGoogleMap);
        Button btnShowUserLocations = (Button) findViewById(R.id.btnLoadYourFile);

        taskStrategyContext = new TaskStrategyContext();
        listView = (ListView)findViewById(R.id.listView);
        editUser = (EditText) findViewById(R.id.editUsername);
        progressDialog = new ProgressDialog(MainActivity.this);

        getData = new GetDataTaskController(progressDialog, MainActivity.this);
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
        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnGoogleMap.setOnClickListener(this);
//        btnAdd.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                startAddDustbinActivity();
//            }
//        });
//        btnGoogleMap.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                startGoogleMapsActivity();
//            }
//        });
        btnShowUserLocations.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadUserLocations();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnEdit || view.getId() == R.id.btnDelete){
            asyncTaskFactory(view);
        } else if (view.getId() == R.id.btnAdd || view.getId() == R.id.btnGoogleMap ){
            openActivityFactory(view);
        }

    }

    private void startGoogleMapsActivity(){
        Intent myIntent = new Intent(MainActivity.this, MapsMarkerActivity.class);

        myIntent.putExtra("dustbins", (Serializable) this.dustbins);

        MainActivity.this.startActivity(myIntent);
    }

    private void startAddDustbinActivity(){
        Intent myIntent = new Intent(MainActivity.this, AddNewDustbinActivity.class);
        MainActivity.this.startActivity(myIntent);
    }

    private void loadUserLocations(){
        String result = null;
        FileReaderController fileReaderController = new FileReaderController(getApplicationContext());
        result = fileReaderController.readUserDistancesFile();

    }

    private void openActivityFactory(View view){

        if (view.getId() == R.id.btnAdd){
            startAddDustbinActivity();
        }
        if (view.getId() == R.id.btnGoogleMap){
            startGoogleMapsActivity();
        }

        resetList();
    }

    private void asyncTaskFactory(View view){

        if (view.getId() == R.id.btnEdit){
            taskStrategyContext.setTaskStrategy(new PutDataTaskController(progressDialog, MainActivity.this, dustbinSelected));
        }
        if (view.getId() == R.id.btnDelete){
            taskStrategyContext.setTaskStrategy(new DeleteDataTaskController(progressDialog, MainActivity.this, dustbinSelected));
        }

        taskStrategyContext.executeStrategy(Common.getAddressSingle(dustbinSelected));
        resetList();
    }

    private void resetList(){
        getData = new GetDataTaskController(progressDialog, MainActivity.this);
        taskStrategyContext.setTaskStrategy(getData);
        taskStrategyContext.executeStrategy(Common.getAddressAPI());
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
                fileWriterController.saveTxtFile(dustbins, "data.txt");
                String result = fileReaderController.readTxtFile("data.txt");


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

//    private void asyncTaskFactory(View view){
//        switch (view.getId()){
//            case R.id.btnAdd:
//                /** commented to test json dao and controller */
//                startAddDustbinActivity();
//                break;
//            case R.id.btnEdit:
////                PutDataTaskController putDataTask = new PutDataTaskController(progressDialog, MainActivity.this, dustbinSelected);
////                putDataTask.mListener = MainActivity.this;
////                putDataTask.execute(Common.getAddressSingle(dustbinSelected));
//                TaskStrategyContext taskStrategyContext = new TaskStrategyContext();
//                taskStrategyContext.setOpenNewActivityStrategy(new PutDataTaskController(progressDialog, MainActivity.this, dustbinSelected));
//                taskStrategyContext.executeStrategy(Common.getAddressSingle(dustbinSelected));
//
//                getData = new GetDataTaskController(progressDialog, MainActivity.this);
//                getData.mListener = MainActivity.this;
//                getData.execute(Common.getAddressAPI());
//                break;
//            case R.id.btnDelete:
//                DeleteDataTaskController deleteDataTask = new DeleteDataTaskController(progressDialog, MainActivity.this, dustbinSelected);
//                deleteDataTask.mListener = MainActivity.this;
//                deleteDataTask.execute(Common.getAddressSingle(dustbinSelected));
//
//
//                getData = new GetDataTaskController(progressDialog, MainActivity.this);
//                getData.mListener = MainActivity.this;
//                getData.execute(Common.getAddressAPI());
//                break;
//            case R.id.btnLoadYourFile:
//                loadUserLocations();
//                break;
//            default:
//                break;
//        }
//    }
}
