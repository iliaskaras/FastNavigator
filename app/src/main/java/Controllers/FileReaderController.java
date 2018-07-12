package Controllers;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import gr.onetouchaway.findeverything.fast_navigator.R;

/**
 * Created by ILIAS on 11/7/2018.
 */

public class FileReaderController {
    private Context mContext;
    final static String fileName = "data.txt";
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Controllers/";
    final static String TAG = FileReaderController.class.getName();

    public FileReaderController() {
    }

    public FileReaderController(Context mContext) {
        this.mContext = mContext;
    }

    public String readTxtFile(String fileName){
        String line = null;

        try {
            FileInputStream fileInputStream = new FileInputStream (new File(path + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }
        catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return line;
    }

    public String readUserDistancesFile() {
        String line = null;

        InputStream inputStream = this.mContext.getResources().openRawResource(R.raw.user_distances);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        try {
            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }

            line = stringBuilder.toString();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return line;
    }


}
