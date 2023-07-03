package com.example.billsplitter.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.billsplitter.R;
import com.example.billsplitter.databinding.FragmentHomeBinding;
import com.example.billsplitter.entities.Friend;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private SharedViewModel model;
    private TextInputEditText etAdd;
    private ChipGroup chipGroup;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etAdd = view.findViewById(R.id.etHomeAdd);
        chipGroup = view.findViewById(R.id.chipGroupHome);
        Button btnAdd = view.findViewById(R.id.btnHomeAdd);

        btnAdd.setOnClickListener(this::onClickBtnAdd);

    }
    private void onClickBtnAdd(View view){
        String name = Objects.requireNonNull(etAdd.getText()).toString().toLowerCase();
        if(isRepeated(name))
            Snackbar.make(view,name + " already in the list",Snackbar.LENGTH_SHORT).show();
        else if(name.equals(""))
            Snackbar.make(view,"field is empty",Snackbar.LENGTH_SHORT).show();
        else
            addFriend(name);

    }

    private boolean isRepeated(String name) {
        List<Friend> list =
                (model.getFriends().getValue() != null)
                        ? model.getFriends().getValue()
                        : new ArrayList<>();

        for (Friend item : list) {
            if(item.getName().equals(name))
                return true;
        }

        return false;
    }

    private void addFriend(String name){
        model.addFriend( new Friend(name) );
        addChip(name);

        etAdd.setText("");
    }

    private void addChip(String name) {
        Chip chip = new Chip(requireContext());
        chip.setText(name);
        chip.setTextSize(20);

        chip.setOnCloseIconClickListener(v -> {
            int index = indexOfFriend(name);
            if(index > -1){
                model.removeFriend(index);
                chipGroup.removeView(chip);
            }
            else
                Snackbar.make(v,"error removing chip", Snackbar.LENGTH_SHORT).show();
        });

        chipGroup.addView(chip);
    }

    private int indexOfFriend(String name) {
        List<Friend> list =
                (model.getFriends().getValue() != null)
                        ? model.getFriends().getValue()
                        : new ArrayList<>();

        for (int i = 0 ; i < list.size() ; i++) {
            if(list.get(i).getName().equals(name))
                return i;
        }

        return -1;
    }

    private void showChips() {
        List<Friend> list =
                (model.getFriends().getValue() != null)
                        ? model.getFriends().getValue()
                        : new ArrayList<>();

        for (Friend item : list) {
            addChip(item.getName());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        showChips();
    }
}