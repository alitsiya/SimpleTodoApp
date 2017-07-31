package com.simpletodo.simpletodo.customAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;

public class TodoAdapter extends RecyclerView.Adapter<TodoItemHolder> implements ItemTouchHelperAdapter {
    private Context context;
    private final ArrayList<Todo> todoItems;
    private int itemResource;

    public TodoAdapter(Context context, int itemResource, ArrayList<Todo> todoItems) {
        this.todoItems = todoItems;
        this.context = context;
        this.itemResource = itemResource;
    }

    @Override
    public TodoItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("@@@", "TodoAdapter onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext())
            .inflate(this.itemResource, parent, false);
        return new TodoItemHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(TodoItemHolder holder, int position) {
        Todo todoItem = this.todoItems.get(position);
        Log.d("@@@", "TodoAdapter onBindViewHolder");
        holder.bindTodo(todoItem);
    }

    @Override
    public int getItemCount() {
        Log.d("@@@", "TodoAdapter getItemCount " + this.todoItems.size());
        return this.todoItems.size();
    }
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(this.todoItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(this.todoItems, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }
    @Override
    public void onItemDismiss(int position) {
        this.todoItems.remove(position);
        notifyItemRemoved(position);
    }

}

