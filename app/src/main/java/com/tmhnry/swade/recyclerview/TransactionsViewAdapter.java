package com.tmhnry.swade.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmhnry.swade.R;
import com.tmhnry.swade.model.Transaction;
import com.tmhnry.swade.singleton.Company;

import java.util.List;
import java.util.Locale;

public class TransactionsViewAdapter extends RecyclerView.Adapter<TransactionsViewAdapter.ViewHolder> {
    List<Transaction> transactions;
    Context context;

    public TransactionsViewAdapter(Context context, List<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView amountGained, assetGained, assetLost, agentOne, agentTwo, date;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            amountGained = itemView.findViewById(R.id.amount_gained);
            assetGained = itemView.findViewById(R.id.asset_gained);
            assetLost = itemView.findViewById(R.id.asset_lost);
            agentOne = itemView.findViewById(R.id.agent_one);
            agentTwo = itemView.findViewById(R.id.agent_two);
            date = itemView.findViewById(R.id.transaction_date);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        view = inflater.inflate(R.layout.view_transaction, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        if (transaction.companyOne != null && transaction.companyOne.equals((String) Company.retrieve(context, Company.KEY))) {
            if (transaction.companyOneAssets.contains("Cash")) {
                String amount = "-\u20b1" + String.format(Locale.getDefault(), "%.2f", transaction.value);
                holder.amountGained.setText(amount);
                holder.assetGained.setText(transaction.companyTwoAssets);
                holder.assetLost.setText(transaction.companyOneAssets);
                holder.agentOne.setText(transaction.entityOneName);
                holder.agentTwo.setText(transaction.entityTwoName);
                holder.date.setText(transaction.date.toLocaleString());
            } else {
                String amount = "\u20b1" + String.format(Locale.getDefault(), "%.2f", transaction.value);
                holder.amountGained.setText(amount);
                holder.assetGained.setText(transaction.companyTwoAssets);
                holder.assetLost.setText(transaction.companyOneAssets);
                holder.agentOne.setText(transaction.entityOneName);
                holder.agentTwo.setText(transaction.entityTwoName);
                holder.date.setText(transaction.date.toLocaleString());
            }
        }
        if (transaction.companyTwo != null && transaction.companyTwo.equals((String) Company.retrieve(context, Company.KEY))) {
            if (transaction.companyTwoAssets.contains("Cash")) {
                String amount = "-\u20b1" + String.format(Locale.getDefault(), "%.2f", transaction.value);
                holder.amountGained.setText(amount);
                holder.assetGained.setText(transaction.companyTwoAssets);
                holder.assetLost.setText(transaction.companyOneAssets);
                holder.agentOne.setText(transaction.entityOneName);
                holder.agentTwo.setText(transaction.entityTwoName);
                holder.date.setText(transaction.date.toLocaleString());
            } else {
                String amount = "\u20b1" + String.format(Locale.getDefault(), "%.2f", transaction.value);
                holder.amountGained.setText(amount);
                holder.assetGained.setText(transaction.companyTwoAssets);
                holder.assetLost.setText(transaction.companyOneAssets);
                holder.agentOne.setText(transaction.entityOneName);
                holder.agentTwo.setText(transaction.entityTwoName);
                holder.date.setText(transaction.date.toLocaleString());
            }
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }
}
