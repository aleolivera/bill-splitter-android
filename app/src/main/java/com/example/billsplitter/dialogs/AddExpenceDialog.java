package com.example.billsplitter.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.billsplitter.R;
import com.example.billsplitter.entities.Expence;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AddExpenceDialog extends DialogFragment {
    private TextInputEditText etDescription;
    private TextInputEditText etPrice;

    private Expence expence;
    private NewAddExpenceDialogListener listener;
    private final int friendIndex;

    public AddExpenceDialog(int friendIndex){
        this.friendIndex = friendIndex;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_expence_dialog , null);

        builder.setView(view)
                .setTitle("New Expence")
                .setNegativeButton("Cancel", (dialog, which) -> { })
                .setPositiveButton("Add", (dialog, which) -> {
                    String description = Objects.requireNonNull(etDescription.getText()).toString();
                    String price = Objects.requireNonNull(etPrice.getText()).toString();

                    expence = (description.equals("") || price.equals("")) ?
                                    null : new Expence( 0, description, Double.parseDouble(price));

                    listener.onNewDialogPositiveClick(expence,friendIndex);
                });

        etDescription = view.findViewById(R.id.etDialogDescription);
        etPrice = view.findViewById(R.id.etDialogPrice);

        return builder.create();
    }

    public interface NewAddExpenceDialogListener{
        void onNewDialogPositiveClick(Expence expence, int friendIndex);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (NewAddExpenceDialogListener) context;
        }
        catch(ClassCastException e){
            throw new ClassCastException(
                    requireActivity() +
                            " must implement NewAddExpenceDialogListener"
            );
        }
    }
}