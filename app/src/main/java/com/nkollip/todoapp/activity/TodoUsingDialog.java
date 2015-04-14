package com.nkollip.todoapp.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import com.nkollip.todoapp.adapter.ListItemObjCustomAdapter;
import com.nkollip.todoapp.common.TodoConstants;
import com.nkollip.todoapp.dao.ItemsDAO;
import com.nkollip.todoapp.dialogfragment.EditItemDialog;
import com.nkollip.todoapp.model.Item;

import java.util.List;

/**
 * Main activity class -- used SQLite database to persist the data; launches Dialog/overlay to edit item
 * Please check <code>TodoActivity</code> for File based persistence
 *
 * Created by nkollip on 4/5/2015.
 */
public class TodoUsingDialog  extends ActionBarActivity implements EditItemDialog.EditItemDialogListener{

    //Declaration of views and list view adapter
    private EditText etAddItem;
    private ListView lvItems;
    private List<Item> items;
    private ArrayAdapter<Item> aItems;

    //Request code to exchange in Intent
    private int REQUEST_CODE_EDIT_ITEM = 30;

    //Declaration of Data source
    private ItemsDAO itemsDAO;

    //Initialization of Log TAG
    private static final String TAG = TodoUsingDialog.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_using_dialog);

        //Initialize views
        lvItems = (ListView) findViewById(android.R.id.list);
        etAddItem = (EditText) findViewById(R.id.etAddItem);

        //set listeners for 'on item click' and 'on item long click':  list view items
        setupLVItemsOnItemLongClickListener();
        setupLVItemsOnItemClickListener();

        //Read list of items from SQLite database
        //readItems();
        itemsDAO = new ItemsDAO(this);
        itemsDAO.open();
        items = itemsDAO.getAllItems();

        //Playing with different layouts for list view
        // (1) android provided simple_list_item_1 : android.R.layout.simple_list_item_1
        // (2) using simple customization of text : R.id.customTextView
        // (3) using custom adapter - to display star image and text : ListItemObjCustomAdapter
        //aItems = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        //aItems = new ArrayAdapter<>(this, R.layout.layout_list_item, R.id.customTextView, items);
        aItems = new ListItemObjCustomAdapter(this, items);

        lvItems.setAdapter(aItems);

        Toast.makeText(this, "Increase the device volume to enjoy crowd cheer..!!", Toast.LENGTH_LONG).show();
        //Audio plays at app launch.. enjoy the crowd cheer..!! :)
        MediaPlayer crowdCheer = MediaPlayer.create(this, R.raw.crowd_cheer);
        crowdCheer.start();
    }

    /**
     * This Method adds the user entered item to items list and writes to SQLite database table
     *
     * Registered this method in the layout xml file
     * Functionality on clicking 'Add' button of main screen
     * @param view
     */
    public void onAddItemBtnClick(View view) {
        String itemName = etAddItem.getText().toString();
        Item itemNew;

        if (itemName != null) {
            itemName = itemName.trim();
        }
        if (TodoConstants.EMPTY_STRING.equals(itemName)) {
            // Show alert if Add button clicked without entering any item
            Toast.makeText(this, TodoConstants.ERROR_MSG_ENTER_ITEM_TO_ADD, Toast.LENGTH_SHORT).show();
        } else {
            itemNew = itemsDAO.createItem(itemName);
            aItems.add(itemNew);

            // Show alert notifying item name which is added
            Toast.makeText(getBaseContext(), "You added \"" + itemName + "\"", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "item added");
        }
        etAddItem.setText(TodoConstants.EMPTY_STRING);
    }

    /**
     * This method removes item (event: long click) from UI and database
     *
     * Setting up listener for on long click of any list view item
     */
    private void setupLVItemsOnItemLongClickListener() {

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> itemsAdapter, View item, int position, long id) {

                Item removedItem = items.get(position);
                itemsDAO.deleteItem(removedItem);
                aItems.remove(removedItem);

                Log.d(TAG, "item removed");

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
                Item itemTobeEdited = aItems.getItem(position);
                String itemBeforeEdit = itemTobeEdited.getItemName();
                showEditItemDialog(itemBeforeEdit, position);
                Log.d(TAG, "Edit Item dialog initiated");
            }
        });
    }

    private void showEditItemDialog(String itemBeforeEdit, int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        EditItemDialog editItemDialog = EditItemDialog.newInstance("Edit Your item", itemBeforeEdit, position);
        editItemDialog.show(fragmentManager, "Edit Item Dialog Id");
    }

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
    @Override
    protected void onResume() {
        itemsDAO.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        itemsDAO.close();
        super.onPause();
    }

    @Override
    public void onFinishEditDialog(String dataFromEditForm, int position) {
        Item itemUpdated = (Item) items.get(position);
        if (dataFromEditForm != null) {
            dataFromEditForm = dataFromEditForm.trim();
        }
        itemUpdated.setItemName(dataFromEditForm);
        items.set(position, itemUpdated);
        itemsDAO.updateItem(itemUpdated);
        aItems.notifyDataSetChanged();
    }
}