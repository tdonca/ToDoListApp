package com.tudordonca.android.todolist.MainTaskList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tudordonca.android.todolist.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private ArrayList<String> dataset;
    private MainListContract.TaskListPresenter presenter;

    public TaskAdapter(ArrayList<String> data, MainListContract.TaskListPresenter presenter){
        dataset = data;
        this.presenter = presenter;
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        public TextView taskText;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskText = (TextView) itemView.findViewById(R.id.task_textView);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                dataset.remove(position);
                notifyItemRemoved(position);
                presenter.removeTask(position);

            }
            return true;
        }
    }


    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View taskView = inflater.inflate(R.layout.task_item, viewGroup, false);
        TaskViewHolder holder = new TaskViewHolder(taskView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder taskViewHolder, int i) {

        String item = dataset.get(i);
        TextView taskText = taskViewHolder.taskText;
        taskText.setText(item);
    }

    public int getItemCount(){
        return dataset.size();
    }
}