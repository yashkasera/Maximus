package com.craft404.maximus.ui.group;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.craft404.maximus.R;
import com.craft404.maximus.adapter.GroupAdapter;
import com.craft404.maximus.model.Group;
import com.craft404.maximus.util.ClickListener;
import com.craft404.maximus.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class GroupFragment extends Fragment {

    List<Group> list;
    GroupAdapter groupAdapter;
    RecyclerView recyclerView;
    private GroupViewModel mViewModel;

    public static GroupFragment newInstance() {
        return new GroupFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        groupAdapter = new GroupAdapter(getActivity(), list);
        mViewModel.getGroups().observe(getViewLifecycleOwner(), groups -> {
            list = groups;
            if (list.size() == 0) {
                view.findViewById(R.id.no_results).setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                view.findViewById(R.id.no_results).setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                groupAdapter.setList(list);
                recyclerView.setAdapter(groupAdapter);
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putLong("groupId", list.get(position).getId());
                NavHostFragment.findNavController(GroupFragment.this)
                        .navigate(R.id.action_navigation_groups_to_chatFragment, bundle);
            }

            @Override
            public void onLongClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putLong("groupId", list.get(position).getId());
                NavHostFragment.findNavController(GroupFragment.this)
                        .navigate(R.id.action_navigation_groups_to_groupInfoFragment, bundle);
            }
        }));
    }
}