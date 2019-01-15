package com.tudordonca.android.todolist;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dropbox.core.android.Auth;

import java.util.ArrayList;

public class DropboxBackupActivity extends AppCompatActivity implements DropboxBackupContract.View {

    private DropboxBackupContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox_backup);

        // Setup Dropbox login button
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.startOAuth2Authentication(DropboxBackupActivity.this, getString(R.string.dropbox_app_key));
            }
        });

        // Setup presenter
        presenter = new DropboxBackupPresenter(this, getFilesDir().toString(), getString(R.string.dropbox_local_file));


    }

    protected void onResume(){
        super.onResume();
        //TODO: change name
        SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
        String accessToken = prefs.getString("access-token", null);
        if (accessToken == null) {
            accessToken = Auth.getOAuth2Token();
            if (accessToken != null) {
                prefs.edit().putString("access-token", accessToken).apply();
                presenter.initAndLoadData(accessToken);
            }
        } else {
            presenter.initAndLoadData(accessToken);
        }

        if (hasToken()) {
            findViewById(R.id.login_button).setVisibility(View.GONE);
            findViewById(R.id.email_text).setVisibility(View.VISIBLE);
            findViewById(R.id.name_text).setVisibility(View.VISIBLE);
            findViewById(R.id.type_text).setVisibility(View.VISIBLE);
            findViewById(R.id.saved_tasks_text).setVisibility(View.VISIBLE);
            findViewById(R.id.sync_button).setEnabled(true);
            Log.d("DropboxBackupActivity","On Resume Has Token");
        } else {
            findViewById(R.id.login_button).setVisibility(View.VISIBLE);
            findViewById(R.id.email_text).setVisibility(View.GONE);
            findViewById(R.id.name_text).setVisibility(View.GONE);
            findViewById(R.id.type_text).setVisibility(View.GONE);
            findViewById(R.id.saved_tasks_text).setVisibility(View.GONE);
            findViewById(R.id.sync_button).setEnabled(false);
            Log.d("DropboxBackupActivity","On Resume Does Not Have Token");
        }
    }



    public void showAccountData(String name, String email, String type){
        ((TextView)findViewById(R.id.email_text)).setText(email);
        ((TextView)findViewById(R.id.name_text)).setText(name);
        ((TextView)findViewById(R.id.type_text)).setText(type);
    }

    public void showExistingTasks(ArrayList<String> tasks){
        // display formatted tasks
        ((TextView)findViewById(R.id.saved_tasks_text)).setText(formatSavedTasks(tasks));
    }

    public String formatSavedTasks(ArrayList<String> tasks){
        // put all tasks on separate lines
        // or display "No saved tasks"
        String result = "DB Saved Tasks: \n";
        for(String task : tasks){
            result += task + "\n";
        }
        return result;
    }


    protected boolean hasToken() {
        //TODO: change name
        SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
        String accessToken = prefs.getString("access-token", null);
        return accessToken != null;
    }


}
