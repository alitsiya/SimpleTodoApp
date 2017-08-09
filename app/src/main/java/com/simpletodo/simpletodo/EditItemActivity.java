package com.simpletodo.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.simpletodo.simpletodo.customAdapter.Todo;
import com.simpletodo.simpletodo.db.DbTodoDataProvider;

public class EditItemActivity extends AppCompatActivity {

    private Integer mPriority;
    private String mValue;
    private String mDate;

    private EditText mEditTodoItem;
    private EditText mEditPriority;

    private DbTodoDataProvider mDataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        setTitle(R.string.edit_app_name);
        mEditTodoItem = (EditText) findViewById(R.id.edit_todo_item);
        mEditTodoItem.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        mEditPriority = (EditText) findViewById(R.id.item_priority);

        Bundle bundle = getIntent().getExtras();
        mPriority = bundle.getInt("priority");
        mValue = bundle.getString("value");
        mDate = bundle.getString("date");

        mEditTodoItem.setText(mValue);
        mEditTodoItem.setSelection(mEditTodoItem.getText().toString().length());
        mEditPriority.setText(mPriority.toString());

        mDataProvider = new DbTodoDataProvider(getApplicationContext());

        final Button button = (Button) findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String content = mEditTodoItem.getText().toString();
                Integer priority = Integer.parseInt(mEditPriority.getText().toString());
                Todo todoItem = new Todo(content, priority, mDate);
                if (content != mValue && !content.equals("")) {
                    if (mDataProvider.isTodoItemInDB(todoItem)) {
                        mDataProvider.updateTodoItemInDb(mValue, content);
                    } else {
                        mDataProvider.storeTodoItemInDB(todoItem);
                    }
                }
                finish();
            }
        });
    }

}
