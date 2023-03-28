package com.tmhnry.swade.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmhnry.swade.R;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.model.Stock;
import com.tmhnry.swade.singleton.User;

import java.util.List;

public class StocksViewAdapter extends RecyclerView.Adapter<StocksViewAdapter.ViewHolder> {
    List<Stock> stocks;
    Context context;
    OnCartIconClicked listener;

    public StocksViewAdapter(Context context, List<Stock> stocks) {
        this.context = context;
        this.stocks = stocks;
        this.listener = (OnCartIconClicked) context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, quantity, price, sold;
        ImageView cart;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            quantity = itemView.findViewById(R.id.txt_quantity);
            sold = itemView.findViewById(R.id.txt_stock_sold);
            name = itemView.findViewById(R.id.txt_stock_name);
            price = itemView.findViewById(R.id.txt_stock_price);
            cart = itemView.findViewById(R.id.add_to_cart);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        view = inflater.inflate(R.layout.view_stock, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Stock stock = stocks.get(position);
        holder.name.setText(stock.name);
        holder.quantity.setText(String.valueOf(stock.quantityRemaining));
        holder.sold.setText(stock.quantitySold + " sold");
        holder.price.setText("\u20b1" + String.format("%.2f", stock.price));
        if(User.getAccountType(context).equals(Entity.OWNER)){
            holder.cart.setVisibility(View.INVISIBLE);
        }
        holder.cart.setOnClickListener(v -> {
            stock.addToCart();
            listener.onCartIconClicked();
        });
    }


    @Override
    public int getItemCount() {
        return stocks.size();
    }

    public interface OnCartIconClicked {
        void onCartIconClicked();
    }
}
