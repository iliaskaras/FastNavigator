package Controllers;

import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
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

    public FileWriterController() {
    }

    public FileWriterController(Context mContext) {
        this.mContext = mContext;
    }

    public boolean saveTxtFile(List<Dustbin> dustbinsList){
        boolean result = false;

        try{
            new File(path  ).mkdir();
            File file = new File(path+ fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file,true);

            for (Dustbin dustbin: dustbinsList){
                fileOutputStream.write((dustbin.getLocation() + System.getProperty("line.separator")).getBytes());
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            result = true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }


}
