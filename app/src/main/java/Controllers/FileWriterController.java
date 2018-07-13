package Controllers;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
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
        boolean result = false;
        FileOutputStream fileOutputStream = null;

        try{
            new File(path).mkdir();
            File file = new File(path + fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            fileOutputStream = new FileOutputStream(file,true);

            for (Dustbin dustbin: dustbinsList){
                fileOutputStream.write((dustbin.getLocation() + System.getProperty("line.separator")).getBytes());
                fileOutputStream.flush();
            }

            result = true;

        }catch (Exception ex){
            Log.d(TAG, ex.getMessage());
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException ex) {
                    Log.d(TAG, ex.getMessage());
                }
            }
        }

        return result;
    }


}
