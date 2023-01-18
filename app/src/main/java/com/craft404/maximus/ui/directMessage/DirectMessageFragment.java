package com.craft404.maximus.ui.directMessage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.craft404.maximus.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static android.Manifest.permission.READ_CALL_LOG;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.craft404.maximus.util.Functions.openWhatsApp;

public class DirectMessageFragment extends Fragment {
    private static final String TAG = "DirectMessageFragment";
    Context context;
    TextInputEditText mobNo, message;
    TextInputLayout mobNo1;
    public static final int REQUEST_READ_CALL_LOG = 1003;

    public DirectMessageFragment() {
        context = getContext();
    }

    public static DirectMessageFragment newInstance() {
        return new DirectMessageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_direct_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mobNo = view.findViewById(R.id.mobNo);
        mobNo1 = view.findViewById(R.id.mobNo1);
        message = view.findViewById(R.id.message);
        view.findViewById(R.id.save).setOnClickListener(v -> {
            String phone = "+91"+mobNo.getText();
            Log.d(TAG, "onClick() returned: " + phone);
            if (mobNo.getText().length() >= 10) {
                mobNo1.setError(null);
                openWhatsApp(getContext(), phone, (Objects.requireNonNull(message.getText()).toString().length() != 0) ? message.getText().toString() : null);
            } else {
                mobNo1.setError("Invalid Phone Number!");
            }
        });
        view.findViewById(R.id.call_log).setOnClickListener(v -> {
            if (hasCallLogPermission()) readCallLogs();
            else
                requestPermissions(new String[]{READ_CALL_LOG}, REQUEST_READ_CALL_LOG);
        });
    }
    private boolean hasCallLogPermission(){
        return (ContextCompat.checkSelfPermission(requireContext(), READ_CALL_LOG) == PERMISSION_GRANTED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1005) {
            if (resultCode == Activity.RESULT_OK) {
                String number = data.getStringExtra("phone");
                Log.d(TAG, "onActivityResult() returned: " + number);
                if (number.startsWith("+"))
                    if (number.length() == 13) mobNo.setText(number.substring(3));
                    else if (number.length() == 14) mobNo.setText(number.substring(4));
                    else mobNo.setText(number);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        Log.e(TAG, "onRequestPermissionsResult: " + requestCode + " " + grantResults.toString());
        if (requestCode == REQUEST_READ_CALL_LOG) {
            Log.d(TAG, "onRequestPermissionsResult: " + grantResults.toString() + " " + grantResults[0]);
            if (grantResults[0] == PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult: permission granted");
                readCallLogs();
            }
            else
                Toast.makeText(context, "You need to allow call log access to pick a number from call logs", Toast.LENGTH_SHORT).show();
        }
    }

    private void readCallLogs() {
        CallLogFragment callLogFragment = new CallLogFragment();
        callLogFragment.setTargetFragment(DirectMessageFragment.this, 1005);
        callLogFragment.show(getFragmentManager().beginTransaction(), "dialog_call_log");
    }



}