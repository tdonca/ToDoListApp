package com.tudordonca.android.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class MainListActivity extends AppCompatActivity implements MainListContract.TaskListView {

    static final int NEW_TASK_REQUEST = 1;

    private MainListContract.TaskListPresenter tasksPresenter;
    private ArrayList<String> tasksList;
    private ArrayAdapter<String> tasksAdapter;
    private ListView taskListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        taskListView = findViewById(R.id.task_list);

        // create presenter
        tasksPresenter = new MainListPresenter(this, getFilesDir().toString() );

        // create adapter for task list
        tasksList = new ArrayList<>();
        tasksAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasksList);

        // assign adapter to a UI viewgroup
        taskListView.setAdapter(tasksAdapter);

        // setup click listener for listView
        setupListViewListener();

        // display any saved tasks
        tasksPresenter.start();

        // display help toast
        Toast.makeText(this, "Tap and hold to remove tasks", Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener() {
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                tasksPresenter.removeTask(position);
                return true;
            }
        });

    }


    public void showTasks( ArrayList<String> tasks ){

        Log.i("View", "show updated tasks");
        tasksList.clear();
        tasksList.addAll(tasks);
        tasksAdapter.notifyDataSetChanged();
        Log.i("View", "Num tasks: " + tasksList.size());
    }


    public void showCreateTaskUI(){

        // create intent and launch CreateTaskActivity with result
        Log.i("View", "create Intent and launch CreateTaskActivity");
        Intent create_task_intent = new Intent( this, AddTaskActivity.class );
        startActivityForResult(create_task_intent, NEW_TASK_REQUEST);
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

    }

    public void createTask(View view) {

        // call presenter createTask function
        Log.i("View", "call presenter createTask()");
        tasksPresenter.createTask();

    }
}
