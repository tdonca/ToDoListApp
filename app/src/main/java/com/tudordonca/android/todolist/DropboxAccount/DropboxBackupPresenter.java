package com.tudordonca.android.todolist.DropboxAccount;

import android.util.Log;

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
    private String filedir, filename;
    private ArrayList<String> tasks;


    DropboxBackupPresenter(DropboxBackupContract.View view, String filedir, String filename){
        UIView = view;
        this.filedir = filedir;
        this.filename = filename;
        tasks = new ArrayList<>();
    }

    @Override
    public void resume(String accessToken) {
        // check access token

        // get a new one if necessary

        // get account data

        // tell view to display
    }

    public void initAndLoadData(String accessToken){
        DropboxClientFactory.init(accessToken);
        loadAccount();
    }


    // This needs to be cleaned up somehow...
    private void loadAccount(){

        fileCallback = new DropboxDownloadFileTask.Callback() {
            @Override
            public void onComplete(File result) {
                //TODO: read db-file and display tasks on the screen
                try{
                    tasks = new ArrayList<>(FileUtils.readLines(result, "UTF-8"));
                    UIView.showExistingTasks(tasks);
                }
                catch(Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Exception e) {

            }
        };

        accountCallback = new DropboxAccountTask.Callback() {
            @Override
            public void onComplete(FullAccount result) {
                UIView.showAccountData(result.getName().getDisplayName(), result.getEmail(), result.getAccountType().name());

                // Download the saved tasks to a file
                new DropboxDownloadFileTask(DropboxClientFactory.getClient(), fileCallback, filedir, filename).execute();

                Log.d(getClass().getName(),"loadData worked onComplete");
            }

            @Override
            public void onError(Exception e) {
                //UIView.showAccountData("N/A", "N/A", "N/A");
                Log.e(getClass().getName(), "Failed to get account details.", e);
            }
        };

        new DropboxAccountTask(DropboxClientFactory.getClient(), accountCallback).execute();

    }


    public void syncData(){
        UIView.deliverIntentResult(true, tasks);
    }


}
