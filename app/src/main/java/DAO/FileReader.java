package DAO;

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

public class FileReader {

    private Context mContext;
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/DAO/";
    final static String TAG = FileReader.class.getName();

    public FileReader() {}

    public FileReader(Context mContext) {
        this.mContext = mContext;
    }

    public String readTxtFile(String fileName){
        String line = null;
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;

        try {
//            File path = this.mContext.getFilesDir();
            File path = this.mContext.getExternalFilesDir(null);
            fileInputStream = new FileInputStream (new File(path , fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            line = stringBuilder.toString();
        }
        catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }
        catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                    bufferedReader.close();
                } catch (IOException ex) {
                    Log.d(TAG, ex.getMessage());
                }
            }
        }

        return line;
    }

    public String readUserDistancesFile() {
        String line = null;
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = this.mContext.getResources().openRawResource(R.raw.user_distances);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }

            line = stringBuilder.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    Log.d(TAG, ex.getMessage());
                }
            }
        }

        return line;
    }


}
