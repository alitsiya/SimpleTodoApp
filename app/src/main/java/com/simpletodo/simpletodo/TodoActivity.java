package com.simpletodo.simpletodo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class TodoActivity extends AppCompatActivity {

    private List<String> mTodoItems = new ArrayList<>();
    private ListView mListView;
    private SharedPreferences mSharedPrefs;

    final Integer EDIT_ITEM_ACTIVITY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        mListView = (ListView) findViewById(R.id.list_item);

        mSharedPrefs = this.getPreferences(Context.MODE_PRIVATE);
        String mTodoItemsString = mSharedPrefs.getString("todoItemList", "");
        if (!mTodoItemsString.equals("")) {
            String[] list = mTodoItemsString.split(",");
            for (String item : list) {
                mTodoItems.add(item);
            }
            updateItemList();
        }

        final Button button = (Button) findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText editEditText = (EditText) findViewById(R.id.edit_text);
                String content = editEditText.getText().toString();
                if (!content.equals("")) {
                    mTodoItems.add(content);
                    updateItemList();
                }
                editEditText.setText(null);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3)
            {
                String selectedFromList = mListView.getItemAtPosition(position).toString();
                Toast.makeText(TodoActivity.this, "" + position + " " + selectedFromList, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(TodoActivity.this, EditItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("index", position);
                bundle.putString("value", selectedFromList);
                intent.putExtras(bundle);

                startActivityForResult(intent, EDIT_ITEM_ACTIVITY);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> av, View v, int pos, long id)
            {
                Toast.makeText(TodoActivity.this, "Item Deleted", Toast.LENGTH_LONG).show();
                mTodoItems.remove(pos);
                updateItemList();
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_ITEM_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                Integer index = data.getIntExtra("index", -1);
                String newValue =data.getStringExtra("value");
                mTodoItems.set(index, newValue);
                updateItemList();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        StringBuilder todoItemList = new StringBuilder();
        for(String s : mTodoItems){
            todoItemList.append(s);
            todoItemList.append(",");
        }
        mSharedPrefs.edit().putString("todoItemList", todoItemList.toString()).apply();
    }

    private void updateItemList() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
            this, android.R.layout.simple_list_item_1, mTodoItems);
        mListView.setAdapter(arrayAdapter);
    }

}
