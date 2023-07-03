package com.example.billsplitter.ui.splitbill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billsplitter.R;
import com.example.billsplitter.adapters.CardSplitBillAdapter;
import com.example.billsplitter.entities.Expence;
import com.example.billsplitter.entities.Friend;
import com.example.billsplitter.ui.home.SharedViewModel;

import java.text.DecimalFormat;
import java.util.List;

public class SplitBillFragment extends Fragment {
    private RecyclerView recyclerView;
    private TextView tvTotal;
    private TextView tvTotalSplit;
    private SharedViewModel model;

    public SplitBillFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        model.getFriends().observe(getViewLifecycleOwner(), this::updateUI);

        return inflater.inflate(R.layout.fragment_split_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rvSplitBill);
        tvTotal = view.findViewById(R.id.tvSplitTotalSpent);
        tvTotalSplit = view.findViewById(R.id.tvSplitTotalSplit);

    }

    private void updateUI(List<Friend> friends) {
        double total = calculateTotal(friends);
        double totalSplit = total / countPayingFriends(friends);
        String tvTotalText = "Total Spent $" + new DecimalFormat("#.##").format(total);
        String tvTotalSplitText = "Each´s cut $" + new DecimalFormat("#.##").format(totalSplit);

        tvTotal.setText(tvTotalText);
        tvTotalSplit.setText(tvTotalSplitText);

        CardSplitBillAdapter adapter =
                new CardSplitBillAdapter(
                        requireContext(),
                        model,
                        R.layout.split_bill_card,
                        totalSplit);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager( new LinearLayoutManager(requireContext()));
    }

    private int countPayingFriends(List<Friend> friends){
        int i = 0;
        for(Friend item : friends){
            if(item.isPaying())
                i++;
        }
        return i;
    }

    private double calculateTotal(List<Friend> friends) {
        double total = 0;
        for (Friend itemFriend : friends) {
            for(Expence itemExpence : itemFriend.getExpences() ){
                total += itemExpence.getPrice();
            }
        }
        return total;
    }
}