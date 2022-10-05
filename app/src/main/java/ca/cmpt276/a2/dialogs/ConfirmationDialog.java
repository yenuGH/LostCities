package ca.cmpt276.a2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ConfirmationDialog extends AppCompatDialogFragment {

    ConfirmationDialogListener listener;

    private static final String DIALOG_KEY = "Dialog Key";

    private String key;
    private String title;
    private String message;
    private String positiveButton;
    private String negativeButton;

    // https://www.linkedin.com/learning/android-development-tips/create-a-reusable-dialog-class
    public static ConfirmationDialog makeInstance(String dialogKey){
        Bundle args = new Bundle();
        args.putString(DIALOG_KEY, dialogKey);

        ConfirmationDialog dialogInstance = new ConfirmationDialog();
        dialogInstance.setArguments(args);
        return dialogInstance;
    }
    private void extractBundleData(){
        key = getArguments().getString(DIALOG_KEY);
        if (key.equals("cancel")){
            title = "Are you sure you want to exit?";
            message = "If you exit, nothing will be saved.";
            positiveButton = "Yes, exit.";
            negativeButton = "No, continue.";
        }
        if (key.equals("delete")){
            title = "Are you sure you want to delete this game?";
            message = "All information will be lost!";
            positiveButton = "Yes, delete.";
            negativeButton = "No, don't delete.";
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        extractBundleData();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButton, (dialogInterface, i) -> listener.userConfirm(key))
                .setNegativeButton(negativeButton, null);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ConfirmationDialogListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context + "must implement CancelDialogListener");
        }
    }

    public interface ConfirmationDialogListener{
        void userConfirm(String key);
    }
}
