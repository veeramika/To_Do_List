package com.rashmiappd.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditDataActivity extends AppCompatActivity {
    private static final String TAG = "EditDataActivity";

    private Button btnSave,btnDelete;
    private EditText editable_item;

    DatabaseHelper mDatabaseHelper;

    private String selectedName;
    private int selectedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        btnSave =  findViewById(R.id.btnSave);
        btnDelete =  findViewById(R.id.btnDelete);
        editable_item = findViewById(R.id.editable_item);
        mDatabaseHelper = new DatabaseHelper(this);

        //get the intent extra from the ListDataActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");

        //set the text to show the current selected name
        editable_item.setText(selectedName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = editable_item.getText().toString();
                if(!item.equals("")){
                    mDatabaseHelper.updateName(item,selectedID,selectedName);
                }else{
                    makeToast("Enter a task!");
                }

                Intent editScreenIntent = new Intent(EditDataActivity.this, ListDataActivity.class);
                startActivity(editScreenIntent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteName(selectedID,selectedName);
                editable_item.setText("");
                makeToast("Successfully removed from database.");
                Intent editScreenIntent = new Intent(EditDataActivity.this, ListDataActivity.class);
                startActivity(editScreenIntent);
            }
        });

    }

    void makeToast(String s)
    {
        Toast.makeText(this , s,Toast.LENGTH_SHORT).show();
    }
}