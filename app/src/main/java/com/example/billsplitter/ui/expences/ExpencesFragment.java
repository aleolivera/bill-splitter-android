package com.example.billsplitter.ui.expences;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.billsplitter.FriendExpenceFragment;
import com.example.billsplitter.R;
import com.example.billsplitter.dialogs.AddExpenceDialog;
import com.example.billsplitter.ui.home.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class ExpencesFragment extends Fragment{

    private ViewPager2 viewPager;
    private SharedViewModel model;

    private int friendsQuantity;
    public ExpencesFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return inflater.inflate(R.layout.fragment_expences, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        friendsQuantity = Objects.requireNonNull(model.getFriends().getValue()).size();

        startUI(view);
    }

    private void startUI(View view){
        viewPager = view.findViewById(R.id.vpExpences);
        startViewPager();

        FloatingActionButton fab = view.findViewById(R.id.fabExpences);
        fab.setOnClickListener(v -> onClickFab());
    }

    private void onClickFab() {
        int friendIndex = viewPager.getCurrentItem();
        AddExpenceDialog dialog = new AddExpenceDialog(friendIndex);
        dialog.show(getChildFragmentManager(),null);
    }

    private void startViewPager() {
        viewPager.setVisibility(View.VISIBLE);
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(),getLifecycle()));
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
        viewPager.setCurrentItem(0);
    }

    class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return FriendExpenceFragment.newInstance(position);
        }

        @Override
        public int getItemCount() {
            return friendsQuantity;
        }
    }
}