package com.nkollip.todoapp.dialogfragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nkollip.todoapp.R;
import com.nkollip.todoapp.common.TodoConstants;

/**
 * Edit Item Dialog
 * Created by m631222 on 4/13/2015.
 */
public class EditItemDialog extends DialogFragment{

    private EditText mEditText;
    private Button btnFragmentSave;

    public interface EditItemDialogListener {
        void onFinishEditDialog(String dataFromEditForm, int position);
    }

    public EditItemDialog() {
        // Empty constructor required for DialogFragment
    }

    public static EditItemDialog newInstance(String title, String itemBeforeEdit, int position) {
        EditItemDialog frag = new EditItemDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString(TodoConstants.ITEM_BEFORE_EDIT, itemBeforeEdit);
        args.putInt(TodoConstants.ITEM_POSITION, position);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_item, container);
        mEditText = (EditText) view.findViewById(R.id.etItemEdit);
        String title = getArguments().getString("title");
        getDialog().setTitle(title);
        // Show soft keyboard automatically
        mEditText.setText(getArguments().getString(TodoConstants.ITEM_BEFORE_EDIT));
        mEditText.setSelection(getArguments().getString(TodoConstants.ITEM_BEFORE_EDIT).length());
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        btnFragmentSave = (Button) view.findViewById(R.id.btnFragmentSave);
        setOnSaveClickEventHandler();
        return  view;
    }

    private void setOnSaveClickEventHandler() {
        btnFragmentSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditItemDialogListener listener = (EditItemDialogListener) getActivity();
                String editedItem = mEditText.getText().toString();
                if (editedItem != null) {
                    editedItem = editedItem.trim();
                }
                if (TodoConstants.EMPTY_STRING.equals(editedItem)) {
                    Toast.makeText(getDialog().getContext(), TodoConstants.ERROR_MSG_ENTER_ITEM_TO_EDIT, Toast.LENGTH_LONG).show();
                } else {
                    listener.onFinishEditDialog(editedItem, getArguments().getInt(TodoConstants.ITEM_POSITION));
                    dismiss();
                }
            }
        });
    }
}
