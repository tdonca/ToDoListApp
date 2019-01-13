package com.tudordonca.android.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
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


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);



        // Find edit text
        addTaskText = findViewById(R.id.new_task_edit);

        // Setup Recycler View
        taskRecyclerView = findViewById(R.id.task_list_recycler_view);
        taskRecyclerView.setHasFixedSize(true);
        taskLayoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(taskLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(taskRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        taskRecyclerView.addItemDecoration(dividerItemDecoration);

        // create presenter
        tasksPresenter = new MainListPresenter(this, getFilesDir().toString() );

        // create adapter for task list
        tasksList = new ArrayList<>();
        taskAdapter = new TaskAdapter(tasksList, tasksPresenter);
        taskRecyclerView.setAdapter(taskAdapter);


        // display any saved tasks
        tasksPresenter.start();

        // display help toast
        Toast.makeText(this, "Tap and hold to remove tasks", Toast.LENGTH_SHORT).show();
    }


    public void showTasks( ArrayList<String> tasks ){

        Log.i("View", "show updated tasks");
        tasksList.clear();
        tasksList.addAll(tasks);
        taskAdapter.notifyDataSetChanged();
        Log.i("View", "Num tasks: " + tasksList.size());
    }


    public void showCreateTaskUI(){

        // create intent and launch CreateTaskActivity with result
        Log.i("View", "create Intent and launch CreateTaskActivity");
        Intent create_task_intent = new Intent( this, AddTaskActivity.class );
        startActivityForResult(create_task_intent, NEW_TASK_REQUEST);
    }

    public void showDropboxBackupUI(){
        Log.i("View", "create Intent and launch DropboxBackupActivity");
        Intent dropboxBackupIntent = new Intent( this, DropboxBackupActivity.class );
        startActivityForResult(dropboxBackupIntent, DROPBOX_BACKUP_REQUEST);
    }

    public void showEditTaskUI(){
        Log.i("View", "create Intent and launch EditTaskActivity");

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

                // Load tasks obtained from Dropbox
            }
        }

    }

    public void createTask(View view) {

        // call presenter createTask function
        Log.i("View", "call presenter createTask()");
        tasksPresenter.createTask();

    }
}
