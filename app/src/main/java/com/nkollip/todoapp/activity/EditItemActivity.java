package com.nkollip.todoapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.nkollip.todoapp.R;
import com.nkollip.todoapp.common.TodoConstants;

/**
 * Edit activity class -- receives user selected item and returns user edited item;
 * validates and alerts if user blanks out the item
 *
 *
 * Created by nkollip on 4/02/2015.
 */
public class EditItemActivity extends ActionBarActivity {

    //Declaration of views
    private EditText etItemEdit;
    String itemBeforeEdit;

    //Initialization of Log TAG
    private static final String TAG = EditItemActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //Populate the Edit Text field with value passed form Main Activity
        itemBeforeEdit = getIntent().getStringExtra(TodoConstants.ITEM_BEFORE_EDIT);
        etItemEdit = (EditText) findViewById(R.id.etItemEdit);
        etItemEdit.setText(itemBeforeEdit);
        etItemEdit.setSelection(itemBeforeEdit.length());
    }

    /**
     * This method is registered in the layout xml file
     * Shows alert dialog if user blanks out the field; Otherwise, returns user edited item
     *
     * @param view
     */
    public void onUpdateItemClick(View view) {
        String editedItem = etItemEdit.getText().toString();
        if (editedItem != null) {
            editedItem = editedItem.trim();
        }
        if (TodoConstants.EMPTY_STRING.equals(editedItem)) {
            // Clicked Add button without entering any item, display and an error
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(EditItemActivity.this);

            dlgAlert.setMessage(TodoConstants.ERROR_MSG_ENTER_ITEM_TO_EDIT);
            dlgAlert.setTitle(TodoConstants.ERROR_MSG);
            dlgAlert.setPositiveButton(TodoConstants.OK, null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            dlgAlert.setPositiveButton(TodoConstants.OK,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            etItemEdit.setText(itemBeforeEdit);
                            etItemEdit.setSelection(itemBeforeEdit.length());

                        }
                    });

        } else {
            int itemPosition = getIntent().getExtras().getInt(TodoConstants.ITEM_POSITION);
            Intent editedDataIntent = new Intent();
            editedDataIntent.putExtra(TodoConstants.EDITED_ITEM, editedItem);
            editedDataIntent.putExtra(TodoConstants.ITEM_POSITION, itemPosition);
            setResult(RESULT_OK, editedDataIntent);
            this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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
