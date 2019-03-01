package com.tudordonca.android.todolist.dropboxaccount;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.core.android.Auth;
import com.tudordonca.android.todolist.R;

import java.util.ArrayList;

public class DropboxBackupActivity extends AppCompatActivity implements DropboxBackupContract.View {

    public static final String EXTRA_REPLACE_TASKS = "com.tudordonca.android.todolist.REPLACE_TASKS";
    private SharedPreferences preferences;
    private DropboxBackupContract.Presenter presenter;
    Button loginButton;
    Button syncButton;
    TextView nameText;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox_backup);

        presenter = new DropboxBackupPresenter(this, getFilesDir().toString(), getString(R.string.local_tasks_file));
        preferences = getSharedPreferences(getString(R.string.shared_prefs_file), MODE_PRIVATE);

        // UI elements
        loginButton = findViewById(R.id.login_button);
        syncButton = findViewById(R.id.sync_button);
        nameText =  findViewById(R.id.name_text);


        // Setup Dropbox login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToggleAccountLogin();
            }
        });

        // Setup Sync button
        Boolean sync = preferences.getBoolean(getString(R.string.prefs_dropbox_sync), false);
        Log.e("DropboxBackupActivity","Sync Setting: " + sync);
        if(sync){
            syncButton.setText(getString(R.string.sync_button_on));
        }
        else{
            syncButton.setText(getString(R.string.sync_button_off));
        }




    }

    protected void onResume(){
        super.onResume();

        String accessToken = preferences.getString(getString(R.string.prefs_access_token), null);
        Log.e("DropboxBackupActivity","Access Token: " + ((accessToken != null) ? "has" : "none"));

        if (accessToken == null) {
            Log.i("DropboxBackupActivity","onResume Does Not Have Token");
            accessToken = Auth.getOAuth2Token();
            if (accessToken != null) {
                Log.i("DropboxBackupActivity","onResume Received a Token");
                preferences.edit().putString(getString(R.string.prefs_access_token), accessToken).apply();
                presenter.loadAccount(accessToken);
            }
            else{
                clearAccountData();
                Log.e("DropboxBackupActivity","Logged out: onResume cannot obtain a Token");
            }
        } else {
            Log.i("DropboxBackupActivity", "Account logged in, onResume has token");
            presenter.loadAccount(accessToken);
        }

        Boolean currentSyncOn = preferences.getBoolean(getString(R.string.prefs_dropbox_sync), false);
        if(!currentSyncOn && accessToken != null){
            showSyncOverwriteWarningToast();
        }
    }



    public void showAccountData(String name, String email, String type){
        loginButton.setText(getString(R.string.login_button_on));
        syncButton.setEnabled(true);
        String accountName = "Account: " + name;
        nameText.setText(accountName);
        nameText.setVisibility(View.VISIBLE);

    }

    public void clearAccountData(){
        preferences.edit().putString(getString(R.string.prefs_access_token), null).apply();
        loginButton.setText(getString(R.string.login_button_off));
        syncButton.setEnabled(false);
        nameText.setText(null);
        nameText.setVisibility(View.GONE);

    }



    // start sync
    public void onToggleSyncData(View view){
        Boolean currentSyncOn = preferences.getBoolean(getString(R.string.prefs_dropbox_sync), false);
        if(currentSyncOn){
            Log.i("DropboxBackupActivity","Turning Dropbox Sync OFF...");
            preferences.edit().putBoolean(getString(R.string.prefs_dropbox_sync), false).apply();
            ((Button)findViewById(R.id.sync_button)).setText(getString(R.string.sync_button_off));

        }
        else{
            Log.i("DropboxBackupActivity","Turning Dropbox Sync ON...");
            preferences.edit().putBoolean(getString(R.string.prefs_dropbox_sync), true).apply();
            ((Button)findViewById(R.id.sync_button)).setText(getString(R.string.sync_button_on));
            presenter.syncData();
        }


    }

    public void onToggleAccountLogin(){
        String accessToken = preferences.getString(getString(R.string.prefs_access_token), null);
        if(accessToken != null){
            // logout
            clearAccountData();
            Log.i("DropboxBackupActivity", "Just logged out...");

        }else{
            //login
            Log.i("DropboxBackupActivity", "Launching OAuth2 Authentication...");
            Auth.startOAuth2Authentication(DropboxBackupActivity.this, getString(R.string.dropbox_app_key));
//            accessToken = Auth.getOAuth2Token();
//            if (accessToken != null) {
//                Log.i("DropboxBackupActivity", "Logging in...");
//                preferences.edit().putString(getString(R.string.prefs_access_token), accessToken).apply();
//                presenter.loadAccount(accessToken);
//
//            }
//            else{
//                Log.e("DropboxBackupActivity","Failed to login... cannot get access token from OAuth!");
//                showDropboxAccountFailToast("No access token");
//            }
        }

    }

    public void showDropboxAccountFailToast(String error){
        Toast toast = Toast.makeText(this, "OAuth Authentication Failed: " + error, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();
    }

    public void showSyncOverwriteWarningToast(){
        Toast toast = Toast.makeText(this, "Turning Sync on will overwrite your local tasks!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
        toast.show();
    }

    public void deliverIntentResult(Boolean replaceLocal, ArrayList<String> tasks){
        Intent return_intent = new Intent();
        return_intent.putExtra(EXTRA_REPLACE_TASKS, replaceLocal);
        setResult(RESULT_OK, return_intent);
        finish();
    }

}
