package com.tudordonca.android.todolist;


    /* Contract between Model, View, and Presenter for the Main Task List Activity
    *
    * */

import android.content.Intent;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface MainListContract {


    interface TaskListView {

        void showTasks( ArrayList<String> tasks );

        void showCreateTaskUI();

    }



    interface TaskListPresenter {

        void start();

        void createTask();

        void addTask( String task );

        void removeTask( int position );

        void onResult(int requestCode, int resultCode);

    }



    interface TaskListModel {

        void loadTasks();

        void saveTasks();

        void addTask( String task );

        void removeTask( int position );

        ArrayList<String> getTasks();

    }





}
