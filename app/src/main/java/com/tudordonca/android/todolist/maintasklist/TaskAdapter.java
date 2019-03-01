package com.tudordonca.android.todolist.maintasklist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tudordonca.android.todolist.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private MainListContract.TaskListPresenter presenter;

    TaskAdapter(MainListContract.TaskListPresenter presenter){
        this.presenter = presenter;
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView taskText;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            taskText = itemView.findViewById(R.id.task_textView);
            itemView.setOnLongClickListener(this);
        }


        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                presenter.removeTask(position);
                notifyItemRemoved(position);


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

        String item = presenter.getTaskData().get(i);
        TextView taskText = taskViewHolder.taskText;
        taskText.setText(item);
    }

    public int getItemCount(){
        return presenter.getTaskData().size();
    }
}