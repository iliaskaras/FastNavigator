package Controllers.AsyncTaskControllers;

/**
 * Created by ILIAS on 6/7/2018.
 */

public interface DownloadTaskListener
{
    public void onDownloadFinish(String s);
    public void onDownloadProgress(float progress);
}
