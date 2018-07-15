package Controllers.DAO_Controllers;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import DAO.FileWriter;
import Model.Dustbin;

/**
 * Created by ILIAS on 11/7/2018.
 */

public class FileWriterController {
    private Context mContext;
    final static String fileName = "data.txt";
    final static String userTxtFile = "userAddedLocations.txt";
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/Controllers/";
    final static String TAG = FileWriterController.class.getName();

    public FileWriterController() {
    }

    public FileWriterController(Context mContext) {
        this.mContext = mContext;
    }

    public boolean saveTxtFile(List<Dustbin> dustbinsList, String fileName){
        FileWriter fileWriter = new FileWriter();
        boolean result = false;

        result = fileWriter.saveTxtFile(dustbinsList,fileName);

        return result;
    }


}
