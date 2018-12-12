package com.example.tudor.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateTaskActivity extends AppCompatActivity {

    private EditText new_task_text;
    public static final String EXTRA_CREATED_TASK = "com.example.tudor.todolist.CREATED_TASK";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        new_task_text = findViewById(R.id.edit_new_task);

    }


    public void finishCreateTask(View view) {

        // return stuff to calling activity
        String task_name = new_task_text.getText().toString();

        // check for valid string
        if( task_name.equals("") ){
            // Display error toast
        }
        else{
            Intent created_task_intent = new Intent();
            created_task_intent.putExtra(EXTRA_CREATED_TASK, task_name);
            setResult(RESULT_OK, created_task_intent);
            finish();
        }
    }
}
