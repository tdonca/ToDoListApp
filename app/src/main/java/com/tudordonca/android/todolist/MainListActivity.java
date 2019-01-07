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

    private MainListContract.TaskListPresenter m_tasks_presenter;
    private MainListContract.TaskListModel m_tasks_model;
    private String m_filename = "MyTasks.txt";
    private ArrayList<String> m_tasks;
    private ArrayAdapter<String> m_tasks_adapter;
    private ListView m_task_list_view;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        m_task_list_view = findViewById(R.id.task_list);

        // create model
        m_tasks_model = new MainListModel( getFilesDir().toString(), m_filename );

        // create presenter
        m_tasks_presenter = new MainListPresenter(this, m_tasks_model);

        // create adapter for task list
        m_tasks = new ArrayList<>();
        m_tasks_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, m_tasks);

        // assign adapter to a UI viewgroup
        m_task_list_view.setAdapter(m_tasks_adapter);

        // setup click listener for listView
        setupListViewListener();

        // display any saved tasks
        m_tasks_presenter.start();

        // display help toast
        Toast.makeText(this, "Tap and hold to remove tasks", Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener() {
        m_task_list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                m_tasks_presenter.removeTask(position);
                return true;
            }
        });

    }


    public void showTasks( ArrayList<String> tasks ){

        Log.i("View", "show updated tasks");
        m_tasks.clear();
        m_tasks.addAll(tasks);
        m_tasks_adapter.notifyDataSetChanged();
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
                    m_tasks_presenter.addTask(new_task);
                }
            }
        }

    }

    public void createTask(View view) {

        // call presenter createTask function
        Log.i("View", "call presenter createTask()");
        m_tasks_presenter.createTask();

    }
}
