package com.tudordonca.android.todolist;


    /* Contract between Model, View, and Presenter for the Main Task List Activity
    *
    * */

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface MainListContract {


    interface TaskListView {

        void showTasks( ArrayList<String> tasks );

        // Future intent activity transitions

    }



    interface TaskListPresenter {

        void start();

        void addTask( String task );

        void removeTask( int position );

    }



    interface TaskListModel {

        void loadTasks();

        void saveTasks();

        void addTask( String task );

        void removeTask( int position );

        ArrayList<String> getTasks();

    }





}
