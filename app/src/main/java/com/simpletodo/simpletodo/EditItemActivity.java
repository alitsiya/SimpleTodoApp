package com.simpletodo.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.simpletodo.simpletodo.customAdapter.Todo;
import com.simpletodo.simpletodo.db.DbTodoDataProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditItemActivity extends AppCompatActivity {

    private String mPriority;
    private String mValue;
    private String mDate;

    private EditText mEditTodoItem;
    private EditText mEditPriority;
    private DatePicker mDatePicker;
    private boolean isNewTodoItem = false;

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
        mDatePicker = (DatePicker) findViewById(R.id.item_due_date);

        updateUI();

        mDataProvider = new DbTodoDataProvider(getApplicationContext());

        final Button button = (Button) findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String content = mEditTodoItem.getText().toString();
                Integer priority = Integer.parseInt(mEditPriority.getText().toString());
                String date = mDatePicker.getYear() + "/" + mDatePicker.getMonth() + "/" + mDatePicker.getDayOfMonth();
                Todo todoItem = new Todo(content, priority, date);
                if (!content.equals("")) {
                    if (isNewTodoItem) {
                        mDataProvider.storeTodoItemInDB(todoItem);
                    } else {
                        mDataProvider.updateTodoItemInDb(mValue, todoItem);
                    }
                }
                finish();
            }
        });
    }
    private void updateUI() {
        Bundle bundle = getIntent().getExtras();
        mPriority = bundle.getString("priority", "1");
        mValue = bundle.getString("value", "");
        mDate = bundle.getString("date", null);
        if (mValue == "" || mDate == null) {
            isNewTodoItem = true;
        }
        mDate = formatDate(mDate);
        mEditTodoItem.setText(mValue);
        mEditTodoItem.setSelection(mEditTodoItem.getText().toString().length());
        mEditPriority.setText(mPriority.toString());
        mDatePicker.updateDate(Integer.parseInt(mDate.split("/")[0]),
            Integer.parseInt(mDate.split("/")[1]),
            Integer.parseInt(mDate.split("/")[2]));
    }

    private String formatDate(String dateToFormat) {
        if (dateToFormat == null || dateToFormat.isEmpty()) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/dd/MM");
            return sdf.format(date);
        }
        return dateToFormat;
    }

}
