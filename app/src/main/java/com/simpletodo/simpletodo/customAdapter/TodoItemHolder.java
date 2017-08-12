package com.simpletodo.simpletodo.customAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.simpletodo.simpletodo.EditItemActivity;
import com.simpletodo.simpletodo.R;

//example from http://traversoft.com/blog/2016/01/31/replace-listview-with-recyclerview/
public class TodoItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private final TextView itemName;
    private final TextView itemPriority;
    private final TextView itemDate;

    private Todo todo;
    private Context context;

    public TodoItemHolder(Context context, View itemView) {

        super(itemView);
        this.context = context;
        this.itemName = itemView.findViewById(R.id.item_name);
        this.itemPriority = itemView.findViewById(R.id.item_priority);
        this.itemDate = itemView.findViewById(R.id.item_date);

        itemView.setOnClickListener(this);
    }

    public void bindTodo(Todo todo) {
        this.todo = todo;
        this.itemName.setText(this.todo.name);
        this.itemPriority.setText(this.todo.priority.toString());
        this.itemDate.setText(this.todo.date);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this.context, "Clicked on " + this.todo.name, Toast.LENGTH_SHORT ).show();

        Intent intent = new Intent(view.getContext(), EditItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("value", this.todo.name);
        bundle.putString("priority", this.todo.priority.toString());
        bundle.putString("date", this.todo.date);
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);
    }

    @Override
    public boolean onLongClick(View view) {
        Toast.makeText(view.getContext(), "Item Deleted", Toast.LENGTH_LONG).show();
        return false;
    }
}

