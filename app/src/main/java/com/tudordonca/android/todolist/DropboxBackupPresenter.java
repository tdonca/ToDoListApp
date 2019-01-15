package com.tudordonca.android.todolist;

import android.content.SharedPreferences;

import com.dropbox.core.android.Auth;
import com.tudordonca.android.todolist.DropboxBackupContract.Presenter;

public class DropboxBackupPresenter implements DropboxBackupContract.Presenter {

    private DropboxBackupContract.View UIView;
    private DropboxAccountTask dropboxAccountTask;

    DropboxBackupPresenter(DropboxBackupContract.View view){
        UIView = view;
    }

    @Override
    public void start() {
        // might not need this anymore
    }

    public void initAndLoadData(String accessToken){
        DropboxClientFactory.init(accessToken);
        loadData();
    }



    private void loadData(){
        //TODO: launch AsyncTask to get current account info and saved tasks for display

    }


}
