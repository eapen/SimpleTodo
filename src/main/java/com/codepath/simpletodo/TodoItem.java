package com.codepath.simpletodo;

/**
 * Created by geapen on 11/19/15.
 */

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

@Table(name = "Items", id = "_id")
public class TodoItem extends Model {
    // If name is omitted, then the field name is used.
    @Column(name = "Name")
    public String name;

    @Column(name = "DisplayOrder", index = true)
    public int displayOrder;

    @Column(name = "Priority", index = true)
    public String priority;

    public TodoItem() {
        super();
    }

    public TodoItem(String name, String priority, int displayOrder) {
        super();
        this.name = name;
        this.priority = priority;
        this.displayOrder = displayOrder;
    }

    public static List<TodoItem> getAll() {
        return new Select().from(TodoItem.class).orderBy("displayOrder").execute();
    }
}
