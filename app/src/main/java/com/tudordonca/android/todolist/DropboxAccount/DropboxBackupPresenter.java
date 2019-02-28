package com.tudordonca.android.todolist.DropboxAccount;

import android.util.Log;

import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.users.FullAccount;
import com.tudordonca.android.todolist.DropboxUtils.DropboxClientFactory;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class DropboxBackupPresenter implements DropboxBackupContract.Presenter {

    private DropboxBackupContract.View UIView;
    private DropboxAccountTask dropboxAccountTask;
    private DropboxAccountTask.Callback accountCallback;
    private DropboxDownloadFileTask.Callback fileCallback;
    private String filename;
    private ArrayList<String> tasks;


    DropboxBackupPresenter(DropboxBackupContract.View view, String filename) {
        UIView = view;
        this.filename = filename;
        tasks = new ArrayList<>();
    }


    public void loadAccount(String accessToken){
        DropboxClientFactory.init(accessToken);
       //TODO: load account network util


        UIView.showAccountData("test", "test", "test");
    }


    //TODO: Sync button pressed, start syncing with Dropbox
    // try to download existing file
    // if remote file exists, overwrite existing local tasks file, send info in Intent result
    // if no remote, do nothing, send back replace=false
    public void syncData(){
        //TODO: download file network util
        UIView.deliverIntentResult(true, tasks);

        // else
        //  UIView.deliverIntentResult(false, tasks);
    }


}
