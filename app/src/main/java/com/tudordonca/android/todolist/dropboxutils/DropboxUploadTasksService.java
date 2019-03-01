package com.tudordonca.android.todolist.dropboxutils;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.tudordonca.android.todolist.R;

public class DropboxUploadTasksService extends IntentService {

    public static final String PARAM_FILEDIR = "com.tudordonca.android.todolist.filedir";
    public static final String PARAM_FILENAME = "com.tudordonca.android.todolist.filename";
    DropboxUploadFileTask.Callback uploadCallback;

    public DropboxUploadTasksService(){
        super(DropboxUploadTasksService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String filedir = workIntent.getStringExtra(PARAM_FILEDIR);
        String filename = workIntent.getStringExtra(PARAM_FILENAME);
        // Initialize and call dropbox client function
        String accessToken = getSharedPreferences(getString(R.string.shared_prefs_file), MODE_PRIVATE).getString(getString(R.string.prefs_access_token), null);
        DropboxClientFactory.init(accessToken);
        uploadCallback = new DropboxUploadFileTask.Callback() {
            @Override
            public void onComplete() {
                Log.i("DropboxUploadService","Finished uploading file in the service.");
            }

            @Override
            public void onError() {
                Log.e("DropboxUploadService","Uploading file failed in the service!");
            }
        };
        new DropboxUploadFileTask(DropboxClientFactory.getClient(), uploadCallback, filedir, filename).execute();

    }
}