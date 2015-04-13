package com.nkollip.todoapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nkollip.todoapp.db.TodoSQLiteHelper;
import com.nkollip.todoapp.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Data access object to perform CRUD operations on database
 * Created by nkollip on 4/8/2015.
 */
public class ItemsDAO {

    private SQLiteDatabase database;
    private TodoSQLiteHelper dbHelper;
    private String[] ITEMS_TABLE_ALL_COLUMNS = {TodoSQLiteHelper.COLUMN_ID, TodoSQLiteHelper.COLUMN_ITEM};
    private static final String TAG = ItemsDAO.class.getName();

    public ItemsDAO(Context context) {
        dbHelper = TodoSQLiteHelper.getInstance(context);
    }

    /**
     * Method to get database instance (by creating or retrieving cached instance)
     *
     * @throws SQLException
     */
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * This method closes database instance, if it is open
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * This method creates an Item in the database
     * @param item
     * @return
     */
    public Item createItem(String item) {
        ContentValues values = new ContentValues();
        values.put(TodoSQLiteHelper.COLUMN_ITEM, item);

        long insertId = database.insert(TodoSQLiteHelper.ITEMS_TABLE_NAME, null,
                values);
        Cursor cursor = database.query(TodoSQLiteHelper.ITEMS_TABLE_NAME,
                ITEMS_TABLE_ALL_COLUMNS, TodoSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();

        //Convert the DB Cursor to model object
        Item newItem = cursorToItem(cursor);

        //Make sure to close the cursor
        cursor.close();

        Log.i(TAG, "Item \"" + item + "\"" + " is created successfully");
        return newItem;
    }

    /**
     * This method updates the item name in the database
     * @param item
     */
    public void updateItem(Item item) {
        long id = item.getId();

        ContentValues values = new ContentValues();
        values.put(TodoSQLiteHelper.COLUMN_ITEM, item.getItemName());

        if (!database.isOpen()) {
            open();
        }
        database.update(TodoSQLiteHelper.ITEMS_TABLE_NAME, values, TodoSQLiteHelper.COLUMN_ID+ " = " + item.getId(), null );
        Log.i(TAG, "Item \"" + item.getItemName() + "\"" + " is updated successfully");
    }

    /**
     * This method deletes an item from the database
     * @param item
     */
    public void deleteItem(Item item) {
        long id = item.getId();
        database.delete(TodoSQLiteHelper.ITEMS_TABLE_NAME, TodoSQLiteHelper.COLUMN_ID
                + " = " + id, null);
        Log.i(TAG, "Item \"" + item.getItemName() + "\"" + " is deleted successfully");
    }

    /**
     * This method retrieves all the items from database
     * @return
     */
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<Item>();

        Cursor cursor = database.query(TodoSQLiteHelper.ITEMS_TABLE_NAME,
                ITEMS_TABLE_ALL_COLUMNS, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Item item = cursorToItem(cursor);
                items.add(item);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        }
        Log.i(TAG, "Items are retrieved successfully");
        return items;
    }

    /**
     * Utility method to convert <code>Cursor</code> to <code>Item</code>
     * @param cursor
     * @return
     */
    private Item cursorToItem(Cursor cursor) {
        Item item = new Item();
        item.setId(cursor.getLong(0));
        item.setItemName(cursor.getString(1));
        return item;
    }
}