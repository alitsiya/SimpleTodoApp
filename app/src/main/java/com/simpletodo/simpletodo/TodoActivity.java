package com.simpletodo.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.simpletodo.simpletodo.customAdapter.SimpleItemTouchHelperCallback;
import com.simpletodo.simpletodo.customAdapter.Todo;
import com.simpletodo.simpletodo.customAdapter.TodoAdapter;
import com.simpletodo.simpletodo.db.DbTodoDataProvider;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private List<String> mTodoItems = new ArrayList<>(); //use as local cache
    private RecyclerView mListView;
    private DbTodoDataProvider mDataProvider;
    private String mTodoItemToEdit;
    private EditText mEditText;
    private TodoAdapter mAdapter;

    final Integer EDIT_ITEM_ACTIVITY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        mListView = (RecyclerView) findViewById(R.id.list_item);
        mListView.setHasFixedSize(true);

        mDataProvider = new DbTodoDataProvider(getApplicationContext());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        mEditText = (EditText) findViewById(R.id.edit_text);
        mEditText.requestFocus();

        mTodoItems = mDataProvider.getTodoDataFromDb();
        updateItemList();

        final Button button = (Button) findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String content = mEditText.getText().toString();
                if (!content.equals("") && !mTodoItems.contains(content)) {
                    mTodoItems.add(content);
                    mDataProvider.storeTodoItemInDB(content);
                    updateItemList();
                } else if (mTodoItems.contains(content)) {
                    Toast.makeText(TodoActivity.this, "Item already in the list", Toast.LENGTH_LONG).show();
                }
                mEditText.setText(null);
            }
        });

        ItemTouchHelper.Callback itemTouchCallback = new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }
            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                RecyclerView.ViewHolder target)
            {
                Log.w("SOME_TAG", "onMove");
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

        ItemTouchHelper.Callback callback =
            new SimpleItemTouchHelperCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mListView);

//        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3)
//            {
//                Todo obj = (Todo) mAdapter.getItem(position);
//                Intent intent = new Intent(TodoActivity.this, EditItemActivity.class);
//                Bundle bundle = new Bundle();
//                mTodoItemToEdit = obj.name;
//                bundle.putInt("index", position);
//                bundle.putString("value", obj.name);
//                intent.putExtras(bundle);
//
//                startActivityForResult(intent, EDIT_ITEM_ACTIVITY);
//            }
//        });
//
//        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
//            @Override
//            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id)
//            {
//                Toast.makeText(TodoActivity.this, "Item Deleted", Toast.LENGTH_LONG).show();
//                String toRemove = mTodoItems.get(pos);
//                mTodoItems.remove(pos);
//                mDataProvider.removeTodoItemFromDb(toRemove);
//                updateItemList();
//                return true;
//            }
//        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_ITEM_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                Integer index = data.getIntExtra("index", -1);
                String newValue =data.getStringExtra("value");
                mTodoItems.set(index, newValue);
                mDataProvider.updateTodoItemInDb(mTodoItemToEdit, newValue);
                updateItemList();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mDataProvider.close();
        super.onDestroy();
    }

    private void updateItemList() {
        // Construct the data source
        ArrayList<Todo> arrayOfTodos = new ArrayList<>();
        for (String item : mTodoItems) {
            Todo newTodo = new Todo(item);
            arrayOfTodos.add(newTodo);
        }

        // Create the adapter to convert the array to views
        mAdapter = new TodoAdapter(this, R.layout.item_todo, arrayOfTodos);
        // Attach the adapter to a ListView
        mListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mListView.setAdapter(mAdapter);
    }

}
