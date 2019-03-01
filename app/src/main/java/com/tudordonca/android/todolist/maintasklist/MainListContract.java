package com.tudordonca.android.todolist.maintasklist;


    /* Contract between Model, View, and Presenter for the Main Task List Activity
    *
    * */

import java.util.ArrayList;

public interface MainListContract {


    interface TaskListView {

        void showTasks( ArrayList<String> tasks );

        void showDropboxBackupUI();

        void launchDropboxUploadTasksService();
    }



    interface TaskListPresenter {

        ArrayList<String> getTaskData();

        void dropboxBackup();

        void addTask( String task );

        void removeTask( int position );

        void loadTasks();

        void saveTasks(boolean dropboxSync);


    }





}
