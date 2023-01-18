package com.craft404.maximus.ui.chats;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.craft404.maximus.R;

public class ReplyFragment extends BottomSheetDialogFragment {

    TextInputEditText text;
    TextInputLayout text1;
    public static ReplyFragment newInstance() {
        return new ReplyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_reply, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text = view.findViewById(R.id.text);
        text1 = view.findViewById(R.id.text1);
        view.findViewById(R.id.send).setOnClickListener(v -> {
            if (text.getText().length() != 0) {
                text1.setError(null);
                Intent waIntent = new Intent(Intent.ACTION_SEND);
                waIntent.setType("text/plain");
                String text = "This is  a Test"; // Replace with your own message.
                waIntent.setPackage("com.whatsapp");
                waIntent.putExtra(Intent.EXTRA_TEXT, text);
                dismiss();
                startActivity(Intent.createChooser(waIntent, "Share with"));
            }else text1.setError("Cannot be empty!");
        });
    }
}