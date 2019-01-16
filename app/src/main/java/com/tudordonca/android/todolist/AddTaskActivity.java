package com.tudordonca.android.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddTaskActivity extends AppCompatActivity {

    public static final String EXTRA_NEW_TASK = "com.tudordonca.android.todolist.NEW_TASK";

    private EditText m_edit_new_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        m_edit_new_task = findViewById(R.id.new_task_editText);
    }


    public void returnCreateTask(View view) {

        // return intent with the text
        Intent return_intent = new Intent();
        return_intent.putExtra(EXTRA_NEW_TASK, m_edit_new_task.getText().toString());
        setResult(RESULT_OK, return_intent);
        finish();
    }
}
