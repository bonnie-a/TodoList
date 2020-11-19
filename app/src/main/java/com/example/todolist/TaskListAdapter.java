package com.example.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHandler> {

    private Context context;
    private List<Task> taskList;
    private Activity activity;

    public TaskListAdapter(Context context, List<Task> taskList, Activity activity) {
        this.context = context;
        this.taskList = taskList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TaskListViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.task, parent, false);
        return new TaskListViewHandler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHandler holder, final int position) {
        final Task task = this.taskList.get(position);
        final TaskListViewHandler updateHolder = holder;

        holder.textName.setText(task.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTask(task);
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.setIsChecked(updateHolder.checkBox.isChecked());
                updateTask(task);
            }
        });

        holder.checkBox.setChecked(task.isChecked());

        holder.btnEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, UpdateActivity.class);
                intent.putExtra("updateName", task.getName());
                intent.putExtra("id", task.getId());
                intent.putExtra("updateEntityType", "task");
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TaskListViewHandler extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textName;
        ImageView btnDeleteTask;
        CheckBox checkBox;
        ImageView btnEditTask;

        public TaskListViewHandler(View item) {
            super(item);
            textName = item.findViewById(R.id.taskName);
            btnDeleteTask = item.findViewById(R.id.deleteTask);
            checkBox = item.findViewById(R.id.taskCheckbox);
            btnEditTask = item.findViewById(R.id.editTask);

        }
        @Override
        public void onClick(View view) {

        }
    }

    public void deleteTask(final Task task) {

        final Task selectedTask = task;


        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(context).getTodoListAppDatabase().taskDao().delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //activity.recreate();
                activity.startActivity(activity.getIntent());
                activity.finish();
                Toast.makeText(context, "Task Deleted", Toast.LENGTH_LONG).show();
            }
        }

        DeleteTask delete = new DeleteTask();
        delete.execute();

    }

    public void updateTask(final Task task) {

        final Task selectedTask = task;

        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(context).getTodoListAppDatabase().taskDao().update(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

            }
        }

        UpdateTask update = new UpdateTask();
        update.execute();

    }
}
