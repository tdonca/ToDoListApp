package com.example.tudor.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

public class MainListActivity extends AppCompatActivity {

    private String task1 = "Wake up";
    private String task2 = "Eat";
    private String task3 = "Go to library";
    private String filename = "MyTasksFile";
    private BufferedReader in;
    private BufferedWriter out;
    private Vector<String> tasks;
    private Vector<TextView> task_views;
    private ViewGroup task_layout;


    
    public static final int TEXT_REQUEST = 1;

    // create clickListener
    View.OnClickListener remove_task_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            task_layout.removeView(v);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        task_layout = findViewById(R.id.main_task_layout);
        tasks = new Vector<>();
        task_views = new Vector<>();


        try{
            // load the tasks from memory
            File file = new File(this.getFilesDir(), filename);
            if(file.exists()){
                in = new BufferedReader(new FileReader(file));
                String line;
                while( (line = in.readLine()) != null ){
                    tasks.add(line);
                }

                // delete file
                file.delete();
            }
        }
        catch (Exception e){
            Log.e("MainListActivity", "READING FROM FILE FAILED");
            e.printStackTrace();
        }
        finally{
            try{
                in.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }



        // set up TextViews and their parent parameters
        Log.d("MainListActivity", "Number of tasks read: " + tasks.size());
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

    public void createTask(View view) {

        // launch new activity to create task
        Intent new_task_intent = new Intent(this, CreateTaskActivity.class);
        startActivityForResult(new_task_intent, TEXT_REQUEST);

    }

    public void onActivityResult( int request_code, int result_code, Intent data ){

        super.onActivityResult(request_code, result_code, data);

        if( request_code == TEXT_REQUEST ){
            if( result_code == RESULT_OK ){

                String created_task = data.getStringExtra(CreateTaskActivity.EXTRA_CREATED_TASK);
                if( !created_task.equals("") ){

                    // add new task code
                    tasks.add(created_task);

                    task_views.add( new TextView(this) );
                    task_views.lastElement().setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    task_views.lastElement().setText(created_task);
                    task_views.lastElement().setTextSize(48);
                    task_views.lastElement().setOnClickListener(remove_task_listener);

                    task_layout.addView(task_views.lastElement());
                }
            }
        }

    }


    public void onStop(){
        super.onStop();

        // save remaining tasks to memory
        Log.d("MainListActivity", "Trying to write to file");
        try{
            File file = new File(this.getFilesDir(), filename);
            out = new BufferedWriter( new FileWriter(file) );
            for( int i = 0; i < tasks.size(); ++i ){
                out.write(tasks.get(i));
                out.newLine();
            }
            Log.d("MainListActivity", "Number of Tasks written to file: " + tasks.size());
        }
        catch(Exception e){
            Log.e("MainListActivity", "WRITING TO FILE FAILED");
            e.printStackTrace();
        }
        finally{
            try{
                out.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

    }
}
