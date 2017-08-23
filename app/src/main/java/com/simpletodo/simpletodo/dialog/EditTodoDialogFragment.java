package com.simpletodo.simpletodo.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.simpletodo.simpletodo.R;
import com.simpletodo.simpletodo.customAdapter.Todo;
import com.simpletodo.simpletodo.db.DbTodoDataProvider;

public class EditTodoDialogFragment extends DialogFragment {

    private static EditText mEditTodoItem;
    private static EditText mEditPriority;
    private static DatePicker mDatePicker;
    private static DbTodoDataProvider mDataProvider;
    String mValue;
    private boolean isNewTodoItem = false;

    public EditTodoDialogFragment() {
    }

    public static EditTodoDialogFragment newInstance(String name, String priority, String date) {
        EditTodoDialogFragment frag = new EditTodoDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("value", name);
        bundle.putString("priority", priority);
        bundle.putString("date", date);
        frag.setArguments(bundle);

//        final Button button = (Button) frag.(R.id.save_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.d("@@@", "OnClickListener");
//                String content = mEditTodoItem.getText().toString();
//                Integer priority = Integer.parseInt(mEditPriority.getText().toString());
//                String date = mDatePicker.getYear() + "/" + mDatePicker.getMonth() + "/" + mDatePicker.getDayOfMonth();
//                Todo todoItem = new Todo(content, priority, date);
//                if (!content.equals("")) {
//                    if (isNewTodoItem) {
//                        mDataProvider.storeTodoItemInDB(todoItem);
//                    } else {
//                        mDataProvider.updateTodoItemInDb(mValue, todoItem);
//                    }
//                }
////                getActivity().getFragmentManager().beginTransaction().remove(this).commit();
//            }
//        });

        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_edit_item, container);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditTodoItem = (EditText) view.findViewById(R.id.edit_todo_item);
        mEditPriority = (EditText) view.findViewById(R.id.item_priority);
        mDatePicker = (DatePicker) view.findViewById(R.id.item_due_date);
        // Fetch arguments from bundle and set title
        mValue = getArguments().getString("value", "");
        getDialog().setTitle(mValue);
        String priority = getArguments().getString("priority", "1");
        String date = getArguments().getString("date", "2017/11/12");

        mEditTodoItem.setText(mValue);
        mEditPriority.setText(priority);
        // Show soft keyboard automatically and request focus to field
        mEditTodoItem.requestFocus();
        getDialog().getWindow().setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mDataProvider = new DbTodoDataProvider(this.getContext());
    }

}
