package ca.cmpt276.a2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ErrorDialog extends AppCompatDialogFragment {
    private static final String DIALOG_KEY = "Dialog Key";
    private final String POSITIVE_BUTTON = "I understand.";

    private String key;
    private String title;
    private String message;


    // https://www.linkedin.com/learning/android-development-tips/create-a-reusable-dialog-class
    public static ErrorDialog makeInstance(String dialogKey){
        Bundle args = new Bundle();
        args.putString(DIALOG_KEY, dialogKey);

        ErrorDialog dialogInstance = new ErrorDialog();
        dialogInstance.setArguments(args);
        return dialogInstance;
    }
    private void extractBundleData(){
        key = getArguments().getString(DIALOG_KEY);
        if (key.equals("empty")){
            title = "Missing inputs.";
            message = "There are empty fields. All fields must be filled in.";
        }
        if (key.equals("zero")){
            title = "Invalid inputs.";
            message = "Players with 0 cards cannot have a non-zero sum or non-zero amount of wagers.";
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        extractBundleData();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(POSITIVE_BUTTON, null);
        return builder.create();
    }
}
