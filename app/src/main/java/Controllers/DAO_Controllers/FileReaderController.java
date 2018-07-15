package Controllers.DAO_Controllers;

import android.content.Context;
import DAO.FileReader;

/**
 * Created by ILIAS on 11/7/2018.
 */

public class FileReaderController {
    private Context mContext;

    public FileReaderController() {
    }

    public FileReaderController(Context mContext) {
        this.mContext = mContext;
    }

    public String readTxtFile(String fileName){
        FileReader fileReader = new FileReader();
        String line = null;

        line = fileReader.readTxtFile(fileName);

        return line;
    }

    public String readUserDistancesFile() {
        FileReader fileReader = new FileReader();
        String line = null;

        line = fileReader.readUserDistancesFile();

        return line;
    }


}
