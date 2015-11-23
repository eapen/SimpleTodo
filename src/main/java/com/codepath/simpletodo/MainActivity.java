package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    ArrayList<TodoItem> todoItems;
    CustomArrayAdapter todoItemsAdapter;
    ListView lvTodoItems;
    int displayOrder = 0;

    private final int REQUEST_CODE = 40;
    private static final String SIMPLE_TODO = "SimpleTodo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvTodoItems = (ListView)findViewById(R.id.lvItems);
        todoItems = new ArrayList<>();
        todoItemsAdapter = new CustomArrayAdapter(this, todoItems);
        readItems();
        lvTodoItems.setAdapter(todoItemsAdapter);
        setupListViewListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        if (!etNewItem.getText().toString().trim().equals("")) {
            String itemText = etNewItem.getText().toString().trim();
            TodoItem todoItem = new TodoItem(itemText, "MED", ++displayOrder);
            Logger.getLogger(SIMPLE_TODO).log(Level.INFO, "Saving " + itemText);
            todoItem.save();
            todoItems.add(todoItem);
            todoItemsAdapter.notifyDataSetChanged();
        }
        etNewItem.setText("");
    }

    private void setupListViewListener() {
        lvTodoItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        TodoItem deleteItem = todoItems.remove(pos);
                        todoItemsAdapter.notifyDataSetChanged();
                        Logger.getLogger(SIMPLE_TODO).log(Level.WARNING, Long.toString(id));
                        if (deleteItem != null) {
                            deleteItem.delete();
                        }
                        return true;
                    }
                }
        );
        lvTodoItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapter,
                                            View item, int pos, long id) {
                        TextView et = (TextView) item;
                        String content = et.getText().toString();
                        int displayOrder = pos;
                        launchEditItemActivity(displayOrder, content);
                    }
                }
        );
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String content = data.getExtras().getString("content");
            int displayOrder = data.getExtras().getInt("displayOrder", 0);
            String priority = data.getExtras().getString("priority");
            TodoItem item = todoItems.get(displayOrder);
            item.name = content;
            item.save();
            todoItemsAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Updated with: " + content, Toast.LENGTH_SHORT).show();
        }
    }

    private void launchEditItemActivity(int displayOrder, String content) {
        Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
        intent.putExtra("content", content);
        intent.putExtra("displayOrder", displayOrder);
        intent.putExtra("priority", "MED");

        startActivityForResult(intent, REQUEST_CODE);
    }

    private void readItems() {
        List<TodoItem> itemList = TodoItem.getAll();
        todoItemsAdapter.addAll(itemList);
        displayOrder = itemList.size();
        Logger.getLogger(SIMPLE_TODO).log(Level.INFO, "Read items from SQLite");
    }
}
