package com.example.tudor.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

public class MainListActivity extends AppCompatActivity {

    private String task1 = "Wake up";
    private String task2 = "Eat";
    private String task3 = "Go to library";

    private Vector<String> tasks;
    private Vector<TextView> task_views;
    private ViewGroup task_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        // get parent view
        task_layout = findViewById(R.id.main_task_layout);


        // simulate loading the tasks from memory
        tasks = new Vector<>();
        tasks.add("Wake up");
        tasks.add("Eat");
        tasks.add("Go to library");


        // create clickListener
        View.OnClickListener remove_task_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task_layout.removeView(v);
            }
        };


        // set up TextViews and their parent parameters
        task_views = new Vector<>();
        for(int i = 0; i < tasks.size(); ++i){

            task_views.add( new TextView(this) );
            task_views.get(i).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                        LinearLayout.LayoutParams.WRAP_CONTENT));
            task_views.get(i).setText(tasks.get(i));
            task_views.get(i).setTextSize(48);
            task_views.get(i).setOnClickListener(remove_task_listener);

            task_layout.addView(task_views.get(i));
        }


    }
}
