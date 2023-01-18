package com.craft404.maximus.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.craft404.maximus.R;

public class InfoDialog extends BottomSheetDialogFragment {
    static String title1;
    static String message1;
    static String positiveButtonText;
    Button positiveButton;
    TextView title, message;

    public static InfoDialog newInstance(Bundle bundle) {
        title1 = bundle.getString("title");
        message1 = bundle.getString("message");
        positiveButtonText = bundle.getString("button");
        return new InfoDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        positiveButton = view.findViewById(R.id.positive);
        title = view.findViewById(R.id.title);
        message = view.findViewById(R.id.message);
        title.setText(title1);
        message.setText(message1);
        if (positiveButtonText != null) {
            positiveButton.setText(positiveButtonText);
        }
        positiveButton.setOnClickListener(v -> dismiss());
    }


}
