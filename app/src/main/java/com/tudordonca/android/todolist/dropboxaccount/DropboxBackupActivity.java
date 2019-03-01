package com.tudordonca.android.todolist.dropboxaccount;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dropbox.core.android.Auth;
import com.tudordonca.android.todolist.R;

import java.util.ArrayList;

public class DropboxBackupActivity extends AppCompatActivity implements DropboxBackupContract.View {

    public static final String EXTRA_REPLACE_TASKS = "com.tudordonca.android.todolist.REPLACE_TASKS";
    private SharedPreferences preferences;
    private DropboxBackupContract.Presenter presenter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox_backup);

        presenter = new DropboxBackupPresenter(this, getFilesDir().toString(), getString(R.string.local_tasks_file));

        preferences = getSharedPreferences(getString(R.string.shared_prefs_file), MODE_PRIVATE);

        // Setup Dropbox login button
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.startOAuth2Authentication(DropboxBackupActivity.this, getString(R.string.dropbox_app_key));
            }
        });




    }

    protected void onResume(){
        super.onResume();

        String accessToken = preferences.getString(getString(R.string.prefs_access_token), null);

        if (accessToken == null) {
            accessToken = Auth.getOAuth2Token();
            if (accessToken != null) {
                preferences.edit().putString(getString(R.string.prefs_access_token), accessToken).apply();
                presenter.loadAccount(accessToken);
            }
            else{
                hideAccountData();
                Log.e("DropboxBackupActivity","On Resume Does Not Have Token");
            }
        } else {
            presenter.loadAccount(accessToken);
        }

    }



    public void showAccountData(String name, String email, String type){
        findViewById(R.id.login_button).setVisibility(View.GONE);
        findViewById(R.id.email_text).setVisibility(View.VISIBLE);
        findViewById(R.id.name_text).setVisibility(View.VISIBLE);
        findViewById(R.id.type_text).setVisibility(View.VISIBLE);
        findViewById(R.id.sync_button).setEnabled(true);

        ((TextView)findViewById(R.id.email_text)).setText(email);
        ((TextView)findViewById(R.id.name_text)).setText(name);
        ((TextView)findViewById(R.id.type_text)).setText(type);
    }

    public void hideAccountData(){
        findViewById(R.id.login_button).setVisibility(View.VISIBLE);
        findViewById(R.id.email_text).setVisibility(View.GONE);
        findViewById(R.id.name_text).setVisibility(View.GONE);
        findViewById(R.id.type_text).setVisibility(View.GONE);
        findViewById(R.id.sync_button).setEnabled(false);
    }



    // start sync
    public void onSyncData(View view){
        preferences.edit().putBoolean(getString(R.string.prefs_dropbox_sync), true).apply();
        presenter.syncData();

        // TODO: add unsync option
    }



    public void deliverIntentResult(Boolean replaceLocal, ArrayList<String> tasks){
        Intent return_intent = new Intent();
        return_intent.putExtra(EXTRA_REPLACE_TASKS, replaceLocal);
        setResult(RESULT_OK, return_intent);
        finish();
    }

}
