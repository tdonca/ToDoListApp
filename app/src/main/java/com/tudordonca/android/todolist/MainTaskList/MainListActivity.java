package com.tudordonca.android.todolist.MainTaskList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tudordonca.android.todolist.AddTaskActivity;
import com.tudordonca.android.todolist.DropboxAccount.DropboxBackupActivity;
import com.tudordonca.android.todolist.R;

import java.util.ArrayList;

public class MainListActivity extends AppCompatActivity implements MainListContract.TaskListView {

    static final int DROPBOX_BACKUP_REQUEST = 2;

    private MainListContract.TaskListPresenter tasksPresenter;
    private EditText addTaskText;
    private RecyclerView taskRecyclerView;
    private RecyclerView.Adapter taskAdapter;
    private RecyclerView.LayoutManager taskLayoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        // create presenter
        tasksPresenter = new MainListPresenter(this, getFilesDir().toString() );

        addTaskText = findViewById(R.id.new_task_edit);

        // Tasks Recycler View
        taskRecyclerView = findViewById(R.id.task_list_recycler_view);
        taskRecyclerView.setHasFixedSize(true);
        taskLayoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(taskLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(taskRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        taskRecyclerView.addItemDecoration(dividerItemDecoration);

        // Tasks Adapter for Recycler View
        taskAdapter = new TaskAdapter(tasksPresenter);
        taskRecyclerView.setAdapter(taskAdapter);


        // display help toast
        Toast.makeText(this, "Tap and hold to remove tasks", Toast.LENGTH_SHORT).show();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void onResume(){
        super.onResume();
        tasksPresenter.loadTasks();
    }


    public void showTasks( ArrayList<String> tasks ){
        Log.i("View", "show updated tasks");
        taskAdapter.notifyDataSetChanged();
        //Log.i("View", "Num tasks: " + tasksList.size());
    }


    public void showDropboxBackupUI(){
        Log.i("View", "create Intent and launch DropboxBackupActivity");
        Intent dropboxBackupIntent = new Intent( this, DropboxBackupActivity.class );
        startActivityForResult(dropboxBackupIntent, DROPBOX_BACKUP_REQUEST);
    }



    public void addTask(View view){
        Log.i("View", "call presenter addTask()");
        String taskText = addTaskText.getText().toString();
        if(!taskText.equals("")){
            tasksPresenter.addTask(taskText);

            // clear input and keyboard
            addTaskText.setText("");
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if(inputManager != null){
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void dropboxBackup(MenuItem mi){
        tasksPresenter.dropboxBackup();

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data){
       if( requestCode == DROPBOX_BACKUP_REQUEST ){
            if( resultCode == RESULT_OK ){
                if(data.getBooleanExtra(DropboxBackupActivity.EXTRA_REPLACE_TASKS, false)){
                    if(getSharedPreferences(getString(R.string.shared_prefs_file), MODE_PRIVATE).getBoolean(getString(R.string.prefs_dropbox_sync), false)){
                        Log.d("MainListActivity", "Started sync, there is an existing Dropbox file, overwriting local tasks file.");
                        tasksPresenter.loadTasks();
                    }
                }
                else{
                    Log.d("MainListActivity", "No existing Dropbox file.");
                }
            }
        }
    }



    // Save tasks and sync
    protected void onPause(){
        super.onPause();
        tasksPresenter.saveTasks(getSharedPreferences(getString(R.string.shared_prefs_file), MODE_PRIVATE).getBoolean(getString(R.string.prefs_dropbox_sync), false));
    }
}
