package com.tudordonca.android.todolist.dropboxutils;

import android.os.AsyncTask;

import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;



public class DropboxAccountTask extends AsyncTask<Void, Void, FullAccount> {

    private final DbxClientV2 client;
    private final Callback callback;


    DropboxAccountTask(DbxClientV2 client, Callback callback){

        this.client = client;
        this.callback = callback;
    }

    public interface Callback {
        void onComplete(FullAccount result);
        void onError();
    }

    @Override
    protected FullAccount doInBackground(Void... voids) {
        return DropboxUtils.getDropboxAccount(client);
    }

    protected void onPostExecute(FullAccount account){
        if(account != null){
            callback.onComplete(account);
        }
        else{
            callback.onError();
        }
    }
}