package com.tmhnry.swade.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmhnry.swade.R;
import com.tmhnry.swade.model.Stock;

import java.util.List;

public class SupplyStocksViewAdapter extends RecyclerView.Adapter<SupplyStocksViewAdapter.ViewHolder> {
    List<Stock> stocks;
    Context context;

    public SupplyStocksViewAdapter(Context context, List<Stock> stocks) {
        this.context = context;
        this.stocks = stocks;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, cost, sub, category;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            name = itemView.findViewById(R.id.supply_name);
            quantity = itemView.findViewById(R.id.supply_qty);
            cost = itemView.findViewById(R.id.supply_cost);
            sub = itemView.findViewById(R.id.supply_sub);
            category = itemView.findViewById(R.id.supply_category);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        view = inflater.inflate(R.layout.view_supply, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Stock stock = stocks.get(position);
        holder.name.setText(stock.name);
        holder.quantity.setText("Quantity: " + stock.quantityRemaining + " " + stock.unit);
        holder.category.setText("Category: " + stock.group);
        holder.cost.setText("Cost: " + "\u20b1" + String.format("%.2f", stock.cost));
        holder.sub.setText("Sub Total: " + "\u20b1" + String.format("%.2f", stock.cost * stock.quantityRemaining));
    }


    @Override
    public int getItemCount() {
        return stocks.size();
    }
}
