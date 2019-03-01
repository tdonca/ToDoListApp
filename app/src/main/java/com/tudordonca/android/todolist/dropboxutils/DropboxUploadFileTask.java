package com.tudordonca.android.todolist.dropboxutils;

import android.os.AsyncTask;

import com.dropbox.core.v2.DbxClientV2;


public class DropboxUploadFileTask extends AsyncTask<Void, Void, Boolean> {

    private final DbxClientV2 client;
    private final Callback callback;
    private String directory;
    private String filename;

    public interface Callback {
        void onComplete();
        void onError();
    }

    public DropboxUploadFileTask(DbxClientV2 client, Callback callback, String directory, String filename){
        this.client = client;
        this.callback = callback;
        this.directory = directory;
        this.filename = filename;

    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return DropboxUtils.uploadDropboxFile(client, directory, filename);

    }

    protected void onPostExecute(Boolean uploaded){
        if(uploaded){
            callback.onComplete();
        }
        else{
            callback.onError();
        }

    }
}
