package com.example.tudor.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

public class MainListActivity extends AppCompatActivity {

    private String task1 = "Wake up";
    private String task2 = "Eat";
    private String task3 = "Go to library";

    private ViewGroup task_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        // get parent view
        task_layout = findViewById(R.id.main_task_layout);

        // set up TextViews and their parent parameters
        

        // add the tasks to the view
    }
}
