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

    static final int NEW_TASK_REQUEST = 1;
    static final int DROPBOX_BACKUP_REQUEST = 2;

    private MainListContract.TaskListPresenter tasksPresenter;
    private ArrayList<String> tasksList;
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

        // Find edit text
        addTaskText = findViewById(R.id.new_task_edit);

        // Setup Recycler View
        taskRecyclerView = findViewById(R.id.task_list_recycler_view);
        taskRecyclerView.setHasFixedSize(true);
        taskLayoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(taskLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(taskRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        taskRecyclerView.addItemDecoration(dividerItemDecoration);

        // create adapter for task list
        //TODO: use the ArrayList actually in Presenter, and use getTasks() to set the adapter
        tasksList = new ArrayList<>();
        taskAdapter = new TaskAdapter(tasksList, tasksPresenter);
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

        //TODO: remove the taskList array and instead just call notify() on the adapter
        Log.i("View", "show updated tasks");
        tasksList.clear();
        tasksList.addAll(tasks);
        taskAdapter.notifyDataSetChanged();
        Log.i("View", "Num tasks: " + tasksList.size());
    }



    public void showDropboxBackupUI(){
        Log.i("View", "create Intent and launch DropboxBackupActivity");
        Intent dropboxBackupIntent = new Intent( this, DropboxBackupActivity.class );
        startActivityForResult(dropboxBackupIntent, DROPBOX_BACKUP_REQUEST);
    }

    public void showEditTaskUI(){
        Log.i("View", "create Intent and launch EditTaskActivity");
    //TODO: remove
    }


    public void addTask(View view){
        Log.i("View", "call presenter addTask()");
        String taskText = addTaskText.getText().toString();
        if(!taskText.equals("")){
            //save task
            tasksPresenter.addTask(taskText);
            // clear textbox
            addTaskText.setText("");
            // minimize keyboard
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

        // get task name and call presenter to addtask()
        if( requestCode == NEW_TASK_REQUEST ){
            if( resultCode == RESULT_OK ){

                String new_task = data.getExtras().getString( AddTaskActivity.EXTRA_NEW_TASK );
                if(!new_task.equals("")){
                    tasksPresenter.addTask(new_task);
                }
            }
        }
        else if( requestCode == DROPBOX_BACKUP_REQUEST ){
            if( resultCode == RESULT_OK ){
                if(data.getBooleanExtra(DropboxBackupActivity.EXTRA_REPLACE_TASKS, false)){
                    // Load tasks obtained from Dropbox
                    Log.d("MainListActivity", "Synced tasks file from Dropbox: ");
                    Log.d("MainListActivity", data.getStringExtra(DropboxBackupActivity.EXTRA_SYNCED_TASKS_FILE));
                    //TODO: tell presenter to overwrite tasks if dropbox file was downloaded
                    // call presenter replace tasks function
                    // ONLY IF shared-prefs has SYNC ON
                }
                else{
                    Log.d("MainListActivity", "Dropbox Backup is turned OFF.");
                }

            }
        }

    }

    //TODO: onPause() function
    // Call presenter saveTasks() function
    // Pass in shared prefs of dropbox backup

}
