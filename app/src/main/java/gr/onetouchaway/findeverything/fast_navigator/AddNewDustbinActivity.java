package gr.onetouchaway.findeverything.fast_navigator;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import Common.Common;
import Controllers.AsyncTaskControllers.DownloadTaskListener;
import Controllers.AsyncTaskControllers.PostDataTaskController;
import Controllers.UtilityControllers.UtilityController;
import Exceptions.MyCoordinateException;
import Exceptions.MyDustbinException;
import Model.Dustbin;

/**
 * Created by ILIAS on 12/7/2018.
 */

public class AddNewDustbinActivity extends AppCompatActivity implements DownloadTaskListener, View.OnClickListener  {
    final static String TAG = AddNewDustbinActivity.class.getName();

    private EditText dustbinLocation;
    private EditText dustbinLongitude;
    private EditText dustbinLatitude;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_dustbin);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        dustbinLocation = (EditText) findViewById(R.id.editLocation);
        dustbinLongitude = (EditText) findViewById(R.id.editLongitude);
        dustbinLatitude = (EditText) findViewById(R.id.editLatitude);
        progressDialog = new ProgressDialog(AddNewDustbinActivity.this);

        btnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                addDustbin();
            }
        });


    }

    private void addDustbin(){

        UtilityController utilityController = new UtilityController();
        Dustbin dustbin = null;
        try {
            dustbin = utilityController.createDustbin(
                    this.dustbinLocation.getText().toString(),
                    this.dustbinLatitude.getText().toString(),
                    this.dustbinLongitude.getText().toString());
        } catch (MyCoordinateException ex) {
            Log.d(TAG, ex.getMessage());
        } catch (MyDustbinException ex){
            Log.d(TAG, ex.getMessage());
        }

        PostDataTaskController postDataTask = new PostDataTaskController(progressDialog, dustbin);

        postDataTask.mListener = AddNewDustbinActivity.this;
        postDataTask.execute(Common.getAddressAPI());

    }

    @Override
    public void onDownloadFinish(String s) {
        AddNewDustbinActivity.this.runOnUiThread(new Runnable()
        {
            public void run()
            {
                /** Done Process */
                /** Use Gson to parse Json to Class*/
                progressDialog.dismiss();

            }
        });

    }

    @Override
    public void onDownloadProgress(float progress) {

    }

    @Override
    public void onClick(View v) {

    }
}
