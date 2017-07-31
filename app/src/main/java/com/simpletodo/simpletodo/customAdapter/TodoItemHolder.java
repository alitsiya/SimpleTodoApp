package com.simpletodo.simpletodo.customAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.simpletodo.simpletodo.EditItemActivity;
import com.simpletodo.simpletodo.R;
import com.simpletodo.simpletodo.TodoActivity;

import static android.support.v7.widget.helper.ItemTouchHelper.Callback.makeMovementFlags;

//example from http://traversoft.com/blog/2016/01/31/replace-listview-with-recyclerview/
public class TodoItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private final TextView itemName;

    private Todo todo;
    private Context context;

    public TodoItemHolder(Context context, View itemView) {

        super(itemView);
        this.context = context;
        this.itemName = itemView.findViewById(R.id.item_name);

        // 3. Set the "onClick" listener of the holder
        itemView.setOnClickListener(this);
    }

    public void bindTodo(Todo todo) {
        Log.d("@@@", "TodoItemHolder bindTodo " + todo.name);
        // 4. Bind the data to the ViewHolder
        this.todo = todo;
        this.itemName.setText(this.todo.name);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this.context, "Clicked on " + this.todo.name, Toast.LENGTH_SHORT ).show();

        Intent intent = new Intent(view.getContext(), EditItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("value", this.todo.name);
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);
    }

    @Override
    public boolean onLongClick(View view) {
        Toast.makeText(view.getContext(), "Item Deleted", Toast.LENGTH_LONG).show();
//        String toRemove = mTodoItems.get(pos);
//        mTodoItems.remove(pos);
//        mDataProvider.removeTodoItemFromDb(toRemove);
//        updateItemList();
//        return true;
        return false;
    }
}

