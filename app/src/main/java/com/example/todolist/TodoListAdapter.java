package com.example.todolist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoListViewHandler> {

    private Context context;
    private List<TodoList> todoLists;
    private Activity activity;

    public TodoListAdapter(Context context, List<TodoList> todoLists, Activity activity) {
        this.context = context;
        this.todoLists = todoLists;
        this.activity = activity;
    }

    @NonNull
    @Override
    public TodoListViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todolistview, parent, false);
        return new TodoListViewHandler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListViewHandler holder, final int position) {
        final TodoList todoList = todoLists.get(position);
        holder.textName.setText(todoList.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Clicking TodoList Id: " + todoList.getId(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, TaskActivity.class);
                intent.putExtra("todoListId", todoList.getId());
                intent.putExtra("todoListName", todoList.getName());
                context.startActivity(intent);
            }
        });

        holder.btnDeleteTodoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTodoList(todoList);
            }
        });

        holder.btnEditTodoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, UpdateActivity.class);
                intent.putExtra("updateName", todoList.getName());
                intent.putExtra("id", todoList.getId());
                intent.putExtra("updateEntityType", "todoList");
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoLists.size();
    }

    class TodoListViewHandler extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textName;
        ImageView btnDeleteTodoList;
        ImageView btnEditTodoList;

        public TodoListViewHandler(View item) {
            super(item);
            textName = item.findViewById(R.id.todoListName);
            btnDeleteTodoList = item.findViewById(R.id.deleteTodoList);
            btnEditTodoList = item.findViewById(R.id.btnEditTodoList);

        }
        @Override
        public void onClick(View view) {
            TodoList todoList = todoLists.get(getAdapterPosition());
        }
    }

    public void deleteTodoList(TodoList todoList) {

        final TodoList selectedTodoList = todoList;

        class DeleteTodoList extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(context).getTodoListAppDatabase().todoListDao().delete(selectedTodoList);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //activity.recreate();
                activity.startActivity(activity.getIntent());
                activity.finish();
                Toast.makeText(context, "List Deleted", Toast.LENGTH_LONG).show();
            }
        }

        DeleteTodoList delete = new DeleteTodoList();
        delete.execute();

    }
}
