package com.tudordonca.android.todolist;

import android.util.Log;

public class MainListPresenter implements MainListContract.TaskListPresenter {

    private MainListContract.TaskListView m_tasks_view;
    private MainListContract.TaskListModel m_tasks_model;


    public MainListPresenter( MainListContract.TaskListView tasks_view, MainListContract.TaskListModel tasks_model ){

        m_tasks_view = tasks_view;
        m_tasks_model = tasks_model;

        m_tasks_model.loadTasks();
    }


    public void start(){

        m_tasks_view.showTasks( m_tasks_model.getTasks());
    }


    public void addTask( String task ){

        m_tasks_model.addTask(task);
        m_tasks_model.saveTasks();
        m_tasks_view.showTasks( m_tasks_model.getTasks() );
    }

    public void removeTask( int position ){

        m_tasks_model.removeTask( position );
        m_tasks_model.saveTasks();
        m_tasks_view.showTasks( m_tasks_model.getTasks() );
    }
}
