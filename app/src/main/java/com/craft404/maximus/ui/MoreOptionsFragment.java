package com.craft404.maximus.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.craft404.maximus.R;

public class MoreOptionsFragment extends Fragment {

    public static MoreOptionsFragment newInstance() {
        return new MoreOptionsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_more_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.faqs).setOnClickListener(v -> Toast.makeText(requireContext(), "Yet to implement!", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.privacy_policy).setOnClickListener(v -> Toast.makeText(requireContext(), "Yet to implement!", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.terms_of_use).setOnClickListener(v -> Toast.makeText(requireContext(), "Yet to implement!", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.contact_us).setOnClickListener(v -> Toast.makeText(requireContext(), "Yet to implement!", Toast.LENGTH_SHORT).show());
    }
}