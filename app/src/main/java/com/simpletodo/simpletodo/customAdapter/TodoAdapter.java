package com.simpletodo.simpletodo.customAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simpletodo.simpletodo.db.DbTodoDataProvider;

import java.util.Collections;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoItemHolder> implements ItemTouchHelperAdapter {
    private Context context;
    private final List<Todo> todoItems;
    private int itemResource;
    private DbTodoDataProvider mDataProvider;

    public TodoAdapter(Context context, int itemResource, List<Todo> todoItems) {
        this.todoItems = todoItems;
        this.context = context;
        this.itemResource = itemResource;

        mDataProvider = new DbTodoDataProvider(this.context);
    }

    @Override
    public TodoItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(this.itemResource, parent, false);
        return new TodoItemHolder(this.context, view);
    }

    @Override
    public void onBindViewHolder(TodoItemHolder holder, int position) {
        Todo todoItem = this.todoItems.get(position);
        holder.bindTodo(todoItem);
    }

    @Override
    public int getItemCount() {
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
        mDataProvider.removeTodoItemFromDb(this.todoItems.get(position));
        this.todoItems.remove(position);
        notifyItemRemoved(position);
    }

}

