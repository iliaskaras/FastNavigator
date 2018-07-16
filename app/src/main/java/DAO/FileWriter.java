package DAO;

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

public class FileWriter {
    private Context mContext;
    final static String fileName = "data.txt";
    final static String userTxtFile = "userAddedLocations.txt";
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/DAO/";

    final static String TAG = FileWriter.class.getName();

    public FileWriter() {}

    public FileWriter(Context mContext) {
        this.mContext = mContext;
    }

    public boolean saveTxtFile(List<Dustbin> dustbinsList, String fileName){
        boolean result = false;
        FileOutputStream fileOutputStream = null;

        try{
            File file = openFile(fileName);

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


    private File openFile(String fileName){
        File file = null;
        File path = this.mContext.getExternalFilesDir(null);
        String filePath = null;

        try {
            file = new File(path , fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            filePath = file.getAbsolutePath();
        } catch (Exception ex){
            Log.d(TAG, ex.getMessage());
        }

        return file;
    }

    //TODO better refactor
//    private void writeCollectionToFile(FileOutputStream fileOutputStream, List<Dustbin>  dustbinsList) throws IOException {
//        for (Dustbin dustbin: dustbinsList){
//            fileOutputStream.write((dustbin.getLocation() + System.getProperty("line.separator")).getBytes());
//            fileOutputStream.flush();
//        }
//    }


}
