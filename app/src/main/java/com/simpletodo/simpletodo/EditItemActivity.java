package com.simpletodo.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private Integer mIndex;
    private String mValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        setTitle(R.string.edit_app_name);
        EditText editText = (EditText) findViewById(R.id.edit_todo_item);
        editText.requestFocus();


        Bundle bundle = getIntent().getExtras();
        mIndex = bundle.getInt("index");
        mValue = bundle.getString("value");

        editText.setText(mValue);
        editText.setSelection(editText.getText().toString().length());

        final Button button = (Button) findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText editEditText = (EditText) findViewById(R.id.edit_todo_item);
                String content = editEditText.getText().toString();
                if (!content.equals(mValue) && !content.equals("")) {
                    mValue = content;
                }
                Intent returnIntent = new Intent();
                returnIntent.putExtra("index" ,mIndex);
                returnIntent.putExtra("value", mValue);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
