package com.tudordonca.android.todolist.dropboxaccount;

import android.util.Log;

import com.dropbox.core.v2.users.FullAccount;
import com.tudordonca.android.todolist.dropboxutils.DropboxClientFactory;
import com.tudordonca.android.todolist.dropboxutils.DropboxUtils;

import java.util.ArrayList;

public class DropboxBackupPresenter implements DropboxBackupContract.Presenter {

    private DropboxBackupContract.View UIView;
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
        FullAccount account = DropboxUtils.getDropboxAccount(DropboxClientFactory.getClient());
        if(account != null){
            Log.i("DropboxBackupPresenter","Showing account data...");
            UIView.showAccountData(account.getName().toString(), account.getEmail(), account.getAccountType().toString());
        }
        else{
            Log.e("DropboxBackupPresenter","Could not get account data from Dropbox to display!");
            UIView.hideAccountData();
        }
    }



    public void syncData(){
        if(DropboxUtils.downloadDropboxFile(DropboxClientFactory.getClient(), filepath, filename)) {
            Log.i("DropboxBackupPresenter","Starting sync: Found remote file, replaced local file.");
            UIView.deliverIntentResult(true, tasks);
        }
        else{
            Log.i("DropboxBackupPresenter","Starting sync: No remote file.");
            UIView.deliverIntentResult(false, tasks);
        }
    }


}
