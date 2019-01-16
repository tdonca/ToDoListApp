package com.tudordonca.android.todolist.MainTaskList;

import android.util.Log;

import com.tudordonca.android.todolist.MainTaskList.MainListContract;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;

public class MainListPresenter implements MainListContract.TaskListPresenter {

    private MainListContract.TaskListView tasksView;
    private ArrayList<String> tasks;
    private File tasksStorageFile;


    public MainListPresenter( MainListContract.TaskListView tasksListView, String fileFolder ){

        tasksView = tasksListView;
        tasks = new ArrayList<>();
        tasksStorageFile = new File(fileFolder, "MyTasks.txt");
    }


    public void start(){

        readTasks(tasksStorageFile);
        tasksView.showTasks(tasks);
    }

    @Override
    public void resume() {

    }


    public void dropboxBackup() {
        Log.i("Presenter", "modify UI for DropboxBackupTask");
        tasksView.showDropboxBackupUI();
    }


    public void addTask(String task ){

        tasks.add(task);
        writeTasks(tasksStorageFile);
        Log.i("Presenter", "Num tasks: " + tasks.size());
        tasksView.showTasks(tasks);
    }

    public void removeTask( int position ){

        tasks.remove(position);
        writeTasks(tasksStorageFile);
        Log.i("Presenter", "Num tasks: " + tasks.size());
        tasksView.showTasks(tasks);
    }

    @Override
    public void loadTasks() {

    }

    @Override
    public void saveTasks() {

    }


    private void readTasks( File tasksFile ){

        try{
            tasks.addAll( FileUtils.readLines( tasksFile, "UTF-8" ) );
        }
        catch( Exception e ){
            e.printStackTrace();
            Log.e("Presenter", "Loading Tasks Failed!");
        }
    }

    private void writeTasks( File tasksFile ){

        try{
            FileUtils.writeLines( tasksFile, tasks );
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("Presenter", "Saving Tasks Failed!");
        }
    }


}
