package com.example.billsplitter.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.billsplitter.R;
import com.example.billsplitter.entities.Expence;
import com.example.billsplitter.entities.Friend;
import com.example.billsplitter.ui.home.SharedViewModel;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class CardSplitBillAdapter extends RecyclerView.Adapter<CardSplitBillAdapter.CardViewHolder> {

    private final Context context;
    private final SharedViewModel model;
    private final int resource;
    private final double totalSplit;

    public CardSplitBillAdapter(Context context, SharedViewModel model, int resource, double totalSplit) {
        this.context = context;
        this.model = model;
        this.resource = resource;
        this.totalSplit = totalSplit;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resource,parent,false);
        return new CardSplitBillAdapter.CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Friend friend = Objects.requireNonNull(
                model.getFriends().getValue()).get(position);
        holder.onBind(friend,position);
    }

    @Override
    public int getItemCount() {
        return (model.getFriends().getValue() != null)
                ? model.getFriends().getValue().size()
                : 0;
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvName;
        private final TextView tvTotalSpent;
        private final TextView tvAction;
        private CheckBox ckIsPaying;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvSplitCardName);
            tvTotalSpent = itemView.findViewById(R.id.tvSplitCardTotal);
            tvAction = itemView.findViewById(R.id.tvSplitCardAction);
            ckIsPaying = itemView.findViewById(R.id.ckSplitCardIsPaying);
        }

        public void onBind(Friend friend,int position){
            double total = (friend.isPaying())? sumExpences(friend.getExpences()) : 0;
            double balance = (friend.isPaying())? total - totalSplit : 0;

            String actionText;
            if(balance == 0)
                actionText = "Setted up";
            else if(balance > 0) {
                actionText = "Takes $" + new DecimalFormat("#.##").format(balance);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvTotalSpent.setTextAppearance(R.style.TextCardSplitBillActionPositive);
                    tvAction.setTextAppearance(R.style.TextCardSplitBillActionPositive);
                }
            }
            else {
                actionText = "Owes $" + new DecimalFormat("#.##").format(balance * -1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tvTotalSpent.setTextAppearance(R.style.TextCardSplitBillActionNegative);
                    tvAction.setTextAppearance(R.style.TextCardSplitBillActionNegative);
                }
            }

            tvName.setText(friend.getName());
            tvTotalSpent.setText(String.format("Spent $" + total));
            tvAction.setText(actionText);
            ckIsPaying.setChecked(friend.isPaying());
            ckIsPaying.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    model.setPaying(position,isChecked);
                }
            });
        }

        public double sumExpences(List<Expence> list){
            double total = 0;

            for(Expence item : list){
                total += item.getPrice();
            }

            return total;
        }
    }
}
