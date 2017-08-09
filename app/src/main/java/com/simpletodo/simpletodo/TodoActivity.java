package com.simpletodo.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.simpletodo.simpletodo.customAdapter.SimpleItemTouchHelperCallback;
import com.simpletodo.simpletodo.customAdapter.Todo;
import com.simpletodo.simpletodo.customAdapter.TodoAdapter;
import com.simpletodo.simpletodo.db.DbTodoDataProvider;

import java.util.ArrayList;
import java.util.List;

public class TodoActivity extends AppCompatActivity {

    private List<Todo> mTodoItems = new ArrayList<>(); //use as local cache
    private RecyclerView mListView;
    private DbTodoDataProvider mDataProvider;
    private EditText mEditText;
    private TodoAdapter mAdapter;

    private final String VALUE = "value";
    private final String PRIORITY = "priority";
    private final String DATE = "date";

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
                Intent intent = new Intent(mListView.getContext(), EditItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(VALUE, content);
                bundle.putString(DATE, "07/07/2107");
                bundle.putInt(PRIORITY, 1);
                intent.putExtras(bundle);
                mListView.getContext().startActivity(intent);
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

    }

    @Override
    public void onResume() {
        super.onResume();
        updateItemList();
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
        mTodoItems = mDataProvider.getTodoDataFromDb();
        // Create the adapter to convert the array to views
        mAdapter = new TodoAdapter(this, R.layout.item_todo, mTodoItems);
        // Attach the adapter to a ListView
        mListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mListView.setAdapter(mAdapter);
    }

}
