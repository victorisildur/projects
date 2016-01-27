package me.isildur.tomato2.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import me.isildur.tomato2.R;

/**
 * Created by isi on 16/1/26.
 */
public class TomatoContentDialog extends DialogFragment {
    private OnDialogConfirm mListener;
    public static String CONTEXT = "CONTEXT";

    public void setListener(OnDialogConfirm listener) {
        mListener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(null == mListener)
            return null;
        /* build the dialog via inflating layout */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.tomato_content_dialog, null);
        builder.setView(view)
                .setPositiveButton(R.string.ack, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String activity = ((EditText) view.findViewById(R.id.activity)).getText().toString();
                        mListener.onDialogConfirm(true, activity);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        return builder.create();
    }
}
