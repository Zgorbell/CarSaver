package com.example.aprosoft.ui.car.delete;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.example.aprosoft.R;
import com.example.aprosoft.ui.car.CarActivity;

public class DeleteDialogFragment extends MvpAppCompatDialogFragment implements
        DialogInterface.OnClickListener{
    public static final String TAG = DeleteDialogFragment.class.getSimpleName();
    public static final int BUTTON_NO = 0;
    public static final int BUTTON_OK = 1;
    private DialogListener dialogListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogListener = (CarActivity)getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.e(TAG, "onCreateDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle(R.string.dialog_deleteCar_message)
                .setPositiveButton("Yes", this)
                .setNegativeButton("Cancel", this).create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                Log.e(TAG, "Pressed button yes");
                dialogListener.buttonPressed(BUTTON_OK);
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                dialogListener.buttonPressed(BUTTON_NO);
                break;
        }
    }

    public interface DialogListener{
        void buttonPressed(int buttonId);
    }
}
