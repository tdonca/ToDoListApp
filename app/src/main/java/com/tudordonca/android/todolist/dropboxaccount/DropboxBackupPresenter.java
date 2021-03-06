package com.tudordonca.android.todolist.dropboxaccount;

import android.util.Log;

import com.dropbox.core.v2.users.FullAccount;
import com.tudordonca.android.todolist.dropboxutils.DropboxAccountTask;
import com.tudordonca.android.todolist.dropboxutils.DropboxClientFactory;
import com.tudordonca.android.todolist.dropboxutils.DropboxDownloadFileTask;
import com.tudordonca.android.todolist.dropboxutils.DropboxUtils;

import java.util.ArrayList;

public class DropboxBackupPresenter implements DropboxBackupContract.Presenter {

    private DropboxBackupContract.View UIView;
    private DropboxAccountTask.Callback accountCallback;
    private DropboxDownloadFileTask.Callback downloadCallback;
    private String filepath;
    private String filename;
    private ArrayList<String> tasks;


    DropboxBackupPresenter(DropboxBackupContract.View view, String filepath, String filename) {
        UIView = view;
        this.filepath = filepath;
        this.filename = filename;
        tasks = new ArrayList<>();
    }


    public void loadAccount(String accessToken){
        DropboxClientFactory.init(accessToken);
        // get Dropbox account asynchronously
        accountCallback = new DropboxAccountTask.Callback() {
            @Override
            public void onComplete(FullAccount result) {
                Log.i("DropboxBackupPresenter","Showing account data...");
                UIView.showAccountData(result.getName().getDisplayName(), result.getEmail(), result.getAccountType().toString());
            }

            @Override
            public void onError() {
                Log.e("DropboxBackupPresenter","Could not get account data from Dropbox to display!");
                UIView.clearAccountData();
                UIView.showDropboxAccountFailToast("Invalid access token");
            }
        };
        new DropboxAccountTask(DropboxClientFactory.getClient(), accountCallback).execute();

    }



    public void syncData(){
        // download Dropbox tasks file asynchronously
        downloadCallback = new DropboxDownloadFileTask.Callback() {
            @Override
            public void onComplete() {
                Log.i("DropboxBackupPresenter","Starting sync: Found remote file, replaced local file.");
                UIView.deliverIntentResult(true, tasks);
            }

            @Override
            public void onError() {
                Log.i("DropboxBackupPresenter","Starting sync: No remote file.");
                UIView.deliverIntentResult(false, tasks);
            }
        };
        new DropboxDownloadFileTask(DropboxClientFactory.getClient(), downloadCallback, filepath, filename).execute();

    }


}
