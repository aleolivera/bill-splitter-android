package com.example.billsplitter.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billsplitter.R;
import com.example.billsplitter.entities.Expence;
import com.example.billsplitter.ui.home.SharedViewModel;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class CardExpenceAdapter extends RecyclerView.Adapter<CardExpenceAdapter.CardViewHolder> {

    private final Context context;
    private final SharedViewModel model;
    private final int resource;
    private final int friendIndex;

    public CardExpenceAdapter(Context context, SharedViewModel model, int friendIndex ,int resource) {
        this.context = context;
        this.model = model;
        this.resource = resource;
        this.friendIndex = (friendIndex > -1) ? friendIndex : 0;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resource,parent,false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(model.getFriends().getValue()).get(friendIndex).getExpences().size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvItem;
        private final Button btnDelete;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tvCardExpenceDescription);
            btnDelete = itemView.findViewById(R.id.btnExpenceDelete);
        }

        public void onBind(int position) {
            List<Expence> expences =
                    Objects.requireNonNull(model.getFriends()
                                    .getValue())
                            .get(friendIndex)
                            .getExpences();

            String itemDescription = expences.get(position).getDescription();
            String itemPrice = new DecimalFormat("#.##").format(expences.get(position).getPrice());
            tvItem.setText(
                     String.format(itemDescription + " $" + itemPrice)
                    );

            btnDelete.setOnClickListener(v -> {
                expences.remove(position);
                model.setExpences(friendIndex,expences);
            });
        }
    }
}
