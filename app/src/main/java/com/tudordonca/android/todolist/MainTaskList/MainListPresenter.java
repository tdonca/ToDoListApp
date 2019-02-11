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


    @Override
    public ArrayList<String> getTaskData(){
        return tasks;
    }

    @Override
    public void loadTasks() {
        // read tasks from local file
        // show tasks
        readTasks(tasksStorageFile);
        tasksView.showTasks(tasks);
    }



    private void readTasks( File tasksFile ){
        Log.i("Presenter", "Reading tasks from file.");
        try{
            tasks.clear();
            tasks.addAll( FileUtils.readLines( tasksFile, "UTF-8" ) );
        }
        catch( Exception e ){
            e.printStackTrace();
            Log.e("Presenter", "Loading Tasks Failed!");
        }
    }


    @Override
    public void addTask(String task ){
        tasks.add(task);
        Log.i("Presenter", "Added task, now there are num: " + tasks.size());
        tasksView.showTasks(tasks);
    }



    @Override
    public void removeTask( int position ){
        tasks.remove(position);
        Log.i("Presenter", "Removed task, now there are num: " + tasks.size());
        tasksView.showTasks(tasks);
    }


    @Override
    public void dropboxBackup() {
        Log.i("Presenter", "modify UI for DropboxBackupTask");
        tasksView.showDropboxBackupUI();
    }



    @Override
    public void saveTasks() {
        // save current tasks to the file
        // look at passed-in shared-prefs for dropbox sync
        // upload tasks file to dropbox
        writeTasks(tasksStorageFile);
    }



    private void writeTasks( File tasksFile ){
        Log.i("Presenter", "Writing tasks to file.");
        try{
            FileUtils.writeLines( tasksFile, tasks );
        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("Presenter", "Saving Tasks Failed!");
        }
    }


}
