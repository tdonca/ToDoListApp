package com.tudordonca.android.todolist;

import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class MainListModel implements MainListContract.TaskListModel {

    private ArrayList<String> m_tasks;
    private String m_file_dir;
    private String m_file_name;

    public MainListModel( String filedir, String filename ){

        m_file_dir = filedir;
        m_file_name = filename;
        m_tasks = new ArrayList<>();
    }


    public void loadTasks(){

        // read tasks from file
        Log.i("Model", "loading tasks from file");
        File tasks_file = new File(m_file_dir, m_file_name);
        try{
            m_tasks = new ArrayList<>( FileUtils.readLines( tasks_file, "UTF-8" ) );
        }
        catch( Exception e ){
            e.printStackTrace();
            Log.i("Model", "loading tasks failed!!");
            m_tasks = new ArrayList<>();
        }
    }

    public void saveTasks(){

        // write tasks to file
        Log.i("Model", "writing tasks to file");
        File tasks_file = new File(m_file_dir, m_file_name);
        try{
            FileUtils.writeLines(tasks_file, m_tasks);
        }
        catch( Exception e){
            e.printStackTrace();
            Log.i("Model", "writing tasks failed");
        }
    }

    public void addTask( String task ){

        m_tasks.add(task);

    }

    public void removeTask( int position ){

        try{
            m_tasks.remove(position);
        }
        catch( Exception e ){
            e.printStackTrace();
        }
    }

    public ArrayList<String> getTasks(){

        return new ArrayList<>(m_tasks);
    }



}
