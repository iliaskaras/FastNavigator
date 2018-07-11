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
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath();
//            + "/instinctcoder/readwrite/" ;

    public FileWriterController() {
    }

    public FileWriterController(Context mContext) {
        this.mContext = mContext;
    }

    public void writeTxtToResources(List<Dustbin> dustbinsList){


        try{
            new File(path  ).mkdir();
            File file = new File(path+ fileName);

//            File file = this.mContext.getFileStreamPath("test.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream writer = this.mContext.openFileOutput(file.getName(), Context.MODE_PRIVATE);

            for (Dustbin dustbin: dustbinsList){
                writer.write(dustbin.getLocation().getBytes());
                writer.flush();
            }

            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
