package com.craft404.maximus.ui.important;

import android.os.Bundle;
import android.util.Log;
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
import com.craft404.maximus.Repository;
import com.craft404.maximus.adapter.MessageAdapter;
import com.craft404.maximus.model.MessageQuery;
import com.craft404.maximus.model.PinnedMessage;
import com.craft404.maximus.util.ClickListener;
import com.craft404.maximus.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ImportantMessagesFragment extends Fragment {
    private static final String TAG = "ImportantMessagesFragme";
    RecyclerView recyclerView;
    Repository repository;
    private ImportantMessagesViewModel mViewModel;
    private List<MessageQuery> list;
    private MessageAdapter messageAdapter;

    public static ImportantMessagesFragment newInstance() {
        return new ImportantMessagesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ImportantMessagesViewModel.class);
        repository = mViewModel.getRepository();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        messageAdapter = new MessageAdapter(getActivity(), list, true);
        recyclerView.setAdapter(messageAdapter);
        view.findViewById(R.id.scrollToBottom).setOnClickListener(v ->
                recyclerView.scrollToPosition(list.size() - 1)
        );
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null && linearLayoutManager.findLastVisibleItemPosition() != list.size() - 1) {
                    view.findViewById(R.id.scrollToBottom).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.scrollToBottom).setVisibility(View.GONE);
                }
            }
        });
        mViewModel.getImportantMessages().observe(requireActivity(), importantMessages -> {
            list = importantMessages;
            if (list.size() == 0) {
                view.findViewById(R.id.no_results).setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                view.findViewById(R.id.no_results).setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                messageAdapter.setList(list);
                messageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(list.size() - 1);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Snackbar.make(requireContext(), view, "Long press on a message to pin it", BaseTransientBottomBar.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onLongClick(View view, int position) {
                long messageId = list.get(position).getId();
                Log.d(TAG, "onLongClick() returned: " + messageId);
                Executors.newSingleThreadExecutor().execute(() -> {
                    if (repository.databaseDao.isPinned(messageId) != null) {
                        Snackbar.make(view, "Message already pinned", BaseTransientBottomBar.LENGTH_SHORT).show();
                        return;
                    }
                    PinnedMessage pinnedMessage = new PinnedMessage(messageId);
                    long id = repository.databaseDao.insertPinned(pinnedMessage);
                    if (id != -1) {
                        Snackbar.make(view, "Message Pinned", BaseTransientBottomBar.LENGTH_SHORT)
                                .show();
                    }
                });
            }
        }));

    }
}