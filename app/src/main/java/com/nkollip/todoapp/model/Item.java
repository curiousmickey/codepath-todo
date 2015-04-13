package com.nkollip.todoapp.model;

/**
 * Model object. <code>itemName</code> is displayed to user; CRUD operations are performed using <code>id</code>
 * Created by nkollip on 4/8/2015.
 */
public class Item {

    private long id;
    private String itemName;

    /**
     * Default constructor
     */
    public Item() {
        super();
    }

    public Item(String itemName, long id) {
        this.itemName = itemName;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return itemName;
    }
}
