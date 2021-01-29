package com.example.morina.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.morina.R;
import com.example.morina.api.App;

import org.jetbrains.annotations.NotNull;

public abstract class PermissionTools {
    public static boolean isPermissionGranted(
        String[] grantPermissions,
        int[] grantResults,
        String permission
    ) {
        for (int i = 0; i < grantPermissions.length; i++) {
            if (permission.equals(grantPermissions[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }

    public static class PermissionDeniedDialog extends DialogFragment {
        private static final String ARGUMENT_FINISH_ACTIVITY = "finish";
        private boolean finishActivity = false;

        public static PermissionDeniedDialog newInstance(boolean finishActivity) {
            Bundle arguments = new Bundle();
            arguments.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity);

            PermissionDeniedDialog dialog = new PermissionDeniedDialog();
            dialog.setArguments(arguments);
            return dialog;
        }

        @NotNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            finishActivity = getArguments().getBoolean(ARGUMENT_FINISH_ACTIVITY);

            return new AlertDialog.Builder(getActivity())
                .setMessage(R.string.location_permission_denied)
                .setPositiveButton(android.R.string.ok, null)
                .create();
        }

        @Override
        public void onDismiss(@NotNull DialogInterface dialog) {
            super.onDismiss(dialog);
            if (finishActivity) {
                Toast.makeText(App.getContext(), R.string.permission_required_toast, Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    }
}