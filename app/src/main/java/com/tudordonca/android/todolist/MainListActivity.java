package com.tudordonca.android.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class MainListActivity extends AppCompatActivity {

    private String filename = "MyTasks.txt";
    private ArrayList<String> tasks;
    private ArrayAdapter<String> tasks_adapter;
    private ListView task_list_view;
    private EditText new_task_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        task_list_view = findViewById(R.id.task_list);
        new_task_text = findViewById(R.id.new_task_edit);
        tasks = new ArrayList<>();

        // load saved tasks from memory
        readTasksFromFile();

        // create adapter for task list
        tasks_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        // assign adapter to a UI viewgroup
        task_list_view.setAdapter(tasks_adapter);


        // setup click listener for listView
        // having the click handler on the listView
        setupListViewListener();

        // display help toast
        Toast.makeText(this, "Tap and hold to remove tasks", Toast.LENGTH_SHORT).show();
    }

    private void setupListViewListener() {
        task_list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // remove directly from data set
                tasks.remove(position);
                // update display through adapter
                tasks_adapter.notifyDataSetChanged();
                // save change to file
                writeTasksToFile();
                return true;
            }
        });

    }

    public void createTask(View view) {

        String task_text = new_task_text.getText().toString();
        // only add non-empty tasks
        if (!task_text.equals("")) {
            tasks_adapter.add(task_text);
            new_task_text.setText("");
            writeTasksToFile();
        }

    }


    private void readTasksFromFile() {

        File filesdir = getFilesDir();
        File tasks_file = new File(filesdir, filename);

        try {
            tasks = new ArrayList<>(FileUtils.readLines(tasks_file, "UTF-8"));
        } catch (Exception e) {
            // no file found
            e.printStackTrace();
            tasks = new ArrayList<>();
        }

    }

    private void writeTasksToFile() {

        File filesdir = getFilesDir();
        File tasks_file = new File(filesdir, filename);

        try {
            FileUtils.writeLines(tasks_file, tasks);
        } catch (Exception e) {

            e.printStackTrace();
        }

    }


}
