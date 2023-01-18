package com.craft404.maximus.ui.pinned;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.craft404.maximus.R;
import com.craft404.maximus.adapter.MessageAdapter;
import com.craft404.maximus.model.MessageQuery;
import com.craft404.maximus.util.ClickListener;
import com.craft404.maximus.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class PinnedFragment extends Fragment {
    private static final String TAG = "PinnedFragment";
    RecyclerView recyclerView;
    List<MessageQuery> list;
    MessageAdapter messageAdapter;
    private PinnedViewModel mViewModel;

    public static PinnedFragment newInstance() {
        return new PinnedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PinnedViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        messageAdapter = new MessageAdapter(getContext(), list, true);
        recyclerView.setAdapter(messageAdapter);
        mViewModel.getList().observe(getViewLifecycleOwner(), messages -> {
            list = messages;
            if (list.size() == 0) {
                view.findViewById(R.id.no_results).setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                view.findViewById(R.id.no_results).setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                messageAdapter.setList(list);
                recyclerView.setAdapter(messageAdapter);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                long messageId = list.get(position).getId();
                mViewModel.deletePinned(messageId);
                Snackbar.make(view, "Message Unpinned", BaseTransientBottomBar.LENGTH_SHORT).show();
            }
        }));
    }

}