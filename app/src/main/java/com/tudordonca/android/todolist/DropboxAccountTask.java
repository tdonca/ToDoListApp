package com.tudordonca.android.todolist;

import android.os.AsyncTask;

import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

import java.util.ArrayList;

public class DropboxAccountTask extends AsyncTask<Void, Void, FullAccount> {

    private final DbxClientV2 dbxClient;
    private final Callback callback;
    private Exception exception;

    DropboxAccountTask(DbxClientV2 dbxClient, Callback callback){

        this.dbxClient = dbxClient;
        this.callback = callback;
        this.exception = null;
    }

    public interface Callback {
        void onComplete(FullAccount result);
        void onError(Exception e);
    }

    @Override
    protected FullAccount doInBackground(Void... voids) {

        try{
            return dbxClient.users().getCurrentAccount();
        }
        catch(Exception e){
            exception = e;
        }

        return null;
    }

    protected void onPostExecute(FullAccount account){
        if(account != null){
            callback.onComplete(account);
        }
        else{
            callback.onError(exception);
        }
    }
}
