package com.nkollip.todoapp.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nkollip.todoapp.R;
import com.nkollip.todoapp.adapter.ListItemCustomAdapter;
import com.nkollip.todoapp.common.TodoConstants;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main activity class -- used File to persist the data
 * Please check <code>TodoUsingSQLiteActivity</code> for File based persistence
 *
 * Created by nkollip on 3/30/2015.
 */
public class TodoActivity extends ActionBarActivity {

    //Declaration of views and list view adapter
    private EditText etAddItem;
    private ListView lvItems;
    private List<String> items;
    private ArrayAdapter<String> aItems;

    //Request code to exchange in Intent
    private int REQUEST_CODE_EDIT_ITEM = 30;

    //Initialization of Log TAG
    private static final String TAG = TodoActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        //Initialize views
        lvItems = (ListView) findViewById(R.id.lvItems);
        etAddItem = (EditText) findViewById(R.id.etAddItem);

        //set listeners for 'on item click' and 'on item long click':  list view items
        setupLVItemsOnItemLongClickListener();
        setupLVItemsOnItemClickListener();

        //Read list of items from file
        readItems();

        //Playing with different layouts for list view
            // (1) android provided simple_list_item_1 : android.R.layout.simple_list_item_1
            // (2) using simple customization of text : R.id.customTextView
            // (3) using custom adapter - to display star image and text : ListItemCustomAdapter
        //aItems = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        //aItems = new ArrayAdapter<>(this, R.layout.layout_list_item, R.id.customTextView, items);
        aItems = new ListItemCustomAdapter(this, items);
        lvItems.setAdapter(aItems);

        Toast.makeText(this, "Increase the device volume to enjoy crowd cheer..!!", Toast.LENGTH_LONG).show();
        //Audio plays at app launch.. enjoy the crowd cheer..!! :)
        MediaPlayer crowdCheer = MediaPlayer.create(this, R.raw.crowd_cheer);
        crowdCheer.start();
    }

    /**
     * This Method adds the user entered item to items list and writes to file
     *
     * Registered this method in the layout xml file
     * Functionality on clicking 'Add' button of main screen
     * @param view
     */
    public void onAddItemBtnClick(View view) {
        String itemName = etAddItem.getText().toString();
        if (itemName != null) {
            itemName = itemName.trim();
        }
        if (TodoConstants.EMPTY_STRING.equals(itemName)) {
            // Show alert if Add button clicked without entering any item
            Toast.makeText(this, TodoConstants.ERROR_MSG_ENTER_ITEM_TO_ADD, Toast.LENGTH_SHORT).show();
        } else {
            aItems.add(itemName);
            writeItems();

            // Show alert notifying item name which is added
            Toast.makeText(getBaseContext(), "You added \"" + itemName + "\"", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "item added");
        }
        etAddItem.setText(TodoConstants.EMPTY_STRING);
    }

    /**
     * This method removes item (event: long click) from UI and file
     *
     * Setting up listener for on long click of any list view item
     */
    private void setupLVItemsOnItemLongClickListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> itemsAdapter, View item, int position, long id) {
                String removedItem = items.get(position);
                items.remove(position);
                aItems.notifyDataSetChanged();
                writeItems();

                Log.d(TAG, "Item removed");

                // Show alert notifying item name which is removed
                Toast.makeText(getBaseContext(), "You removed \"" + removedItem + "\"", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    /**
     * This method launches Edit Item screen to edit the clicked item
     *
     * Listener for on click of any list view item
     */
    private void setupLVItemsOnItemClickListener() {
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> itemsAdapter, View item, int position, long id) {
                String itemBeforeEdit = aItems.getItem(position);

                //Using Intent to start new activity (screen) and pass data
                Intent iItemEdit = new Intent(TodoActivity.this, EditItemActivity.class);
                iItemEdit.putExtra(TodoConstants.ITEM_POSITION, position);
                iItemEdit.putExtra(TodoConstants.ITEM_BEFORE_EDIT, itemBeforeEdit);
                startActivityForResult(iItemEdit, REQUEST_CODE_EDIT_ITEM);
                Log.d(TAG, "Intent for Edit Item Activity initiated");
            }
        });
    }

    /**
     * Callback method upon return from 'Edit Item' screen -- to update with edited item name
     *
     * @param requestCode
     * @param resultCode
     * @param editedItemData
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent editedItemData) {
        if (REQUEST_CODE_EDIT_ITEM == requestCode && RESULT_OK == resultCode) {
            String dataFromEditForm = editedItemData.getExtras().getString(TodoConstants.EDITED_ITEM);
            int position = editedItemData.getExtras().getInt(TodoConstants.ITEM_POSITION);
            if (dataFromEditForm != null) {
                dataFromEditForm = dataFromEditForm.trim();
            }
            items.set(position, dataFromEditForm);
            aItems.notifyDataSetChanged();
            writeItems();
        }
        Log.d(TAG, "Done processing activity result from Edit Activity");
    }

    /**
     * Method to read items from file
     */
    private void readItems() {
        File filesDirectory = getFilesDir();
        File todoFile = new File(filesDirectory, TodoConstants.TODO_DATA_FILE_NAME);
        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<>();
            e.printStackTrace();
        }
        Log.d(TAG, "Done reading items from File todo.txt");
    }

    /**
     * Method to write items into file
     */
    private void writeItems() {
        File filesDirectory = getFilesDir();
        File todoFile = new File(filesDirectory, TodoConstants.TODO_DATA_FILE_NAME);
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Done writing items to File todo.txt");
    }
    //Those good old days.. :) foundation of this app
   /* private void populateItems() {
        items = new ArrayList<>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 4");
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}