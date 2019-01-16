package com.tudordonca.android.todolist.DropboxAccount;

import android.os.AsyncTask;

import com.dropbox.core.v2.DbxClientV2;
import com.tudordonca.android.todolist.DropboxUtils.DropboxUtils;

import java.io.File;

public class DropboxDownloadFileTask extends AsyncTask<Void, Void, File> {

    private final DbxClientV2 client;
    private final Callback callback;
    private Exception exception;
    private String directory;
    private String filename;

    public interface Callback {
        void onComplete(File result);
        void onError(Exception e);
    }

    DropboxDownloadFileTask(DbxClientV2 client, Callback callback, String directory, String filename){
        this.client = client;
        this.callback = callback;
        exception = null;
        this.directory = directory;
        this.filename = filename;

    }

    @Override
    protected File doInBackground(Void... voids) {
        Boolean result = DropboxUtils.getDropboxFile(client, directory, filename);
        if(result){
            return new File(directory, filename);
        }
        else{
            return null;
        }
    }

    protected void onPostExecute(File file){
        if(file != null){
            callback.onComplete(file);
        }
        else{
            // need to actually pass back the exception from NetworkUtils
            callback.onError(exception);
        }

    }
}
