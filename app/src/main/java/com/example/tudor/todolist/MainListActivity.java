package com.example.tudor.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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


        // create clickListener
        View.OnClickListener remove_task_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task_layout.removeView(v);
            }
        };


        // set up TextViews and their parent parameters
        final TextView task1_view = new TextView(this);
        task1_view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                    LinearLayout.LayoutParams.WRAP_CONTENT));
        task1_view.setText(task1);
        task1_view.setTextSize(48);
        task1_view.setOnClickListener(remove_task_listener);

        TextView task2_view = new TextView(this);
        task2_view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                    LinearLayout.LayoutParams.WRAP_CONTENT));
        task2_view.setText(task2);
        task2_view.setTextSize(48);
        task2_view.setOnClickListener(remove_task_listener);

        TextView task3_view = new TextView(this);
        task3_view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                                                    LinearLayout.LayoutParams.WRAP_CONTENT));
        task3_view.setText(task3);
        task3_view.setTextSize(48);
        task3_view.setOnClickListener(remove_task_listener);

        // add the tasks to the view
        task_layout.addView(task1_view);
        task_layout.addView(task2_view);
        task_layout.addView(task3_view);
    }
}
