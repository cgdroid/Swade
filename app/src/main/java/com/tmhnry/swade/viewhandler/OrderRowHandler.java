package com.tmhnry.swade.viewhandler;

import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;

import com.tmhnry.swade.model.Stock;

public class OrderRowHandler {
    QuantityListener listener;
    TableRow row;

    public OrderRowHandler(TableRow row, QuantityListener listener) {
        this.listener = listener;
        this.row = row;
    }

    public void onStockIncrement(
            Context context, Stock stock,
            TextView $quantity, TextView $sub
    ) {
        stock.addToCart();
        $quantity.setText(String.valueOf(stock.quantityOrdered));
        $sub.setText(String.format("%.2f", stock.quantityOrdered * stock.price));
    }

    public void onStockDecrement(
            Context context, Stock stock,
            TextView $quantity, TextView $sub
    ) {
        stock.addToCart(-1);
        $quantity.setText(String.valueOf(stock.quantityOrdered));
        $sub.setText(String.format("%.2f", stock.quantityOrdered * stock.price));
        if (stock.quantityOrdered == 0) {
            listener.onEmptyOrder(row);
            Stock.getOrders().remove(stock.getId());
//            Intent intent = new Intent(context, Cashout.class);
//            ((Activity) context).finish();
//            ((Activity) context).overridePendingTransition(0, 0);
//            context.startActivity(intent);
//            ((Activity) context).overridePendingTransition(0, 0);
        }
    }

    public interface QuantityListener {
        void onEmptyOrder(TableRow row);
    }
}
