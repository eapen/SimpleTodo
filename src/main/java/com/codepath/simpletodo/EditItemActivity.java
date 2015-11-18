package com.codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText etEditItem;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        position = getIntent().getIntExtra("position", 0);
        String content = getIntent().getStringExtra("content");

        etEditItem = (EditText) findViewById(R.id.eiMultilineText);
        etEditItem.setText(content);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etEditItem.requestFocus();
        etEditItem.setSelection(etEditItem.getText().length());
    }

    public void onSubmit(View v) {
        EditText etEditItem = (EditText) findViewById(R.id.eiMultilineText);

        if (!etEditItem.getText().toString().trim().equals("")) {
            Intent data = new Intent();
            String itemText = etEditItem.getText().toString().trim();
            data.putExtra("content", itemText);
            data.putExtra("position", position);
            setResult(RESULT_OK, data);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

}
