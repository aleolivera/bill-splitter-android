package com.example.billsplitter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.billsplitter.adapters.CardExpenceAdapter;
import com.example.billsplitter.entities.Expence;
import com.example.billsplitter.entities.Friend;
import com.example.billsplitter.ui.home.SharedViewModel;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class FriendExpenceFragment extends Fragment{

    private int currentIndex;
    private TextView tvTotal;
    private RecyclerView rvExpences;
    private SharedViewModel model;

    public FriendExpenceFragment() {}

    public static FriendExpenceFragment newInstance(int index) {
        FriendExpenceFragment fragment = new FriendExpenceFragment();
        Bundle args = new Bundle();
        args.putInt("index",index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            currentIndex = getArguments().getInt("index");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        model.getFriends().observe(getViewLifecycleOwner(), this::updateUI);

        return inflater.inflate(R.layout.fragment_friend_expence, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(currentIndex >= Objects.requireNonNull(model.getFriends().getValue()).size())
            currentIndex = 0;

        startUI(view);

    }

    private void startUI(View view){
        Friend friend =
                Objects.requireNonNull(model.getFriends()
                        .getValue())
                        .get(currentIndex);
        TextView tvName = view.findViewById(R.id.tvExpencesName);
        tvTotal = view.findViewById(R.id.tvExpencesTotal);
        rvExpences = view.findViewById(R.id.rvExpences);

        tvName.setText(friend.getName());
    }

    private void updateUI(List<Friend> friends){
        showCards();
        String total = new DecimalFormat("#.##").format(calculateTotal(friends.get(currentIndex).getExpences()));
        tvTotal.setText(String.format("Total $" + total));
    }

    private void showCards(){
        CardExpenceAdapter adapter = new CardExpenceAdapter(requireContext(),model,currentIndex,R.layout.expence_card);
        rvExpences.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvExpences.setAdapter(adapter);
    }

    private double calculateTotal(List<Expence> expences){
        double total=0;

        for ( Expence item : expences ) {
            total += item.getPrice();
        }

        return total;
    }
}