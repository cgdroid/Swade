package com.tmhnry.swade.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.tmhnry.swade.Cashout;
import com.tmhnry.swade.Home;
import com.tmhnry.swade.R;
import com.tmhnry.swade.database.Firebase;
import com.tmhnry.swade.databinding.FragmentOrdersBinding;
import com.tmhnry.swade.databinding.TableRowOrderBinding;
import com.tmhnry.swade.model.Asset;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.model.Model;
import com.tmhnry.swade.model.Stock;
import com.tmhnry.swade.model.Transaction;
import com.tmhnry.swade.singleton.Company;
import com.tmhnry.swade.singleton.User;
import com.tmhnry.swade.viewhandler.OrderRowHandler.QuantityListener;
import com.tmhnry.swade.viewhandler.OrderRowHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OrdersFragment extends Fragment {
    public static final String FRAGMENT_ID = "tmhnry.employeeproductivity.ordersfragment";
    FragmentOrdersBinding binding;
    AppCompatButton proceed;
    TableLayout table;
    Dialog payment;
    AppCompatEditText $cash;
    TextView $amount;
    TextView $change;
    Calendar calendar;

    public OrdersFragment() {
    }

    public static OrdersFragment Builder() {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    private Transaction createTransaction() {
        Context context = getContext();
        Map<String, Object> data = new HashMap<>();
        data.put(Transaction.DATE, calendar.getTime());
        data.put(Model.ID, Transaction.getRandomPublicId(context));
        data.put(Transaction.COMPANY_ONE, null);
        data.put(Transaction.COMPANY_ONE_TYPE, Transaction.BUYER);
        data.put(Transaction.COMPANY_ONE_ASSETS, "Cash");
        data.put(Transaction.COMPANY_TWO, Company.getKey(context));
        data.put(Transaction.COMPANY_TWO_TYPE, Transaction.SELLER);
        data.put(Transaction.COMPANY_TWO_ASSETS, Stock.getOrders().size() + " Unique Item(s)");
        data.put(Transaction.ENTITY_ONE, null);
        data.put(Transaction.ENTITY_ONE_NAME, null);

        Entity entity = null;
        for (Entity _entity : Entity.getModels().values()) {
            if (_entity.userKey.equals(User.getKey(getContext()))) {
                entity = _entity;
            }
        }
        data.put(Transaction.ENTITY_TWO, entity.key);
        data.put(Transaction.COST_VALUE, 0f);
//        How to put nickname or display name here?
        data.put(Transaction.ENTITY_TWO_NAME, User.getFullName(context));
        data.put(Transaction.VALUE, 0f);
        Transaction transaction = Transaction.Model(data);
//        Can we put key in Map<String, Object > data instead ?
        transaction.key = Firebase
                .getChildReference(Transaction.TABLE_NAME)
                .push()
                .getKey();
        return transaction;
    }

    private List<Asset> createAssets(Transaction transaction) {
        List<Asset> assets = new ArrayList<>();
        for (Stock stock : Stock.getOrders().values()) {
            float value = stock.price * stock.quantityOrdered;
            float costValue = stock.cost * stock.quantityOrdered;
            transaction.value += value;
            transaction.costValue += costValue;
            Map<String, Object> data = new HashMap<>();
            data.put(Model.ID, Asset.getRandomPublicId(getContext()));
            data.put(Asset.ASSET_NAME, stock.name);
            data.put(Asset.ASSET_UNIT, stock.unit);
            data.put(Asset.ASSET_QUANTITY, stock.confirmOrder());
            data.put(Asset.ASSET_CATEGORY, stock.group);
            data.put(Asset.TRANSACTION, transaction);
            data.put(Asset.ASSET_VALUE, value);
            assets.add(Asset.Model(data));
        }
        return assets;
    }

    private void updateCloudDatabase(List<Transaction> transactions,
                                     List<Asset> assets,
                                     List<Stock> stocks) {
        Transaction.append(transactions);
        Asset.append(assets);
        Stock.update(stocks);
    }

    private void onConfirmOrders() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = createTransaction();
        transactions.add(transaction);
        List<Asset> assets = createAssets(transaction);
        List<Stock> stocks = new ArrayList<>(Stock.getOrders().values());
        Stock.clearOrders();
        updateCloudDatabase(transactions, assets, stocks);
    }

    private void createPaymentDialog() {
        TextView $cancel, $confirm;
        payment = new Dialog(getContext());
        payment.setContentView(R.layout.dialog_payment);
        payment.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        $amount = payment.findViewById(R.id.amount_to_pay);
        $change = payment.findViewById(R.id.change);
        $cash = payment.findViewById(R.id.cash_amount);
        $cancel = payment.findViewById(R.id.cancel);
        $confirm = payment.findViewById(R.id.confirm);
        $cancel.setOnClickListener(v -> {
            payment.cancel();
        });
        $confirm.setOnClickListener(v -> {
            if (Stock.getOrders().size() == 0) {
                return;
            }
            onConfirmOrders();
            payment.cancel();
            startActivity(new Intent(getActivity(), Home.class));
            getActivity().finish();
        });
        $cash.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Float amount = 0f;
                for (Stock stock : Stock.getOrders().values()) {
                    amount += stock.price * stock.quantityOrdered;
                }
                Currency currency = Currency.getInstance("PHP");
                if (s.length() == 0) {
                    $change.setText(currency.getSymbol() + "0.00");
                    return;
                }
                Float cash = Float.parseFloat(s.toString());
                $change.setText(currency.getSymbol() + String.format("%.2f", cash - amount));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        table = binding.tableLayout;
        proceed = binding.proceed;
        binding.clearOrders.setOnClickListener(v -> {
            Stock.clearOrders();
            startActivity(new Intent(getActivity(), Home.class));
        });
        createPaymentDialog();
        calendar = Calendar.getInstance(Locale.getDefault());
        Date date = calendar.getTime();
        binding.orderDate.setText(date.toLocaleString());
        binding.orderSize.setText("No. of Items: " + Stock.getOrders().size());
        for (Stock stock : Stock.getOrders().values()) {
            TableRowOrderBinding tableRow = TableRowOrderBinding.inflate(inflater, table, true);
            tableRow.setStock(stock);
            tableRow.setHandler(new OrderRowHandler((TableRow) tableRow.getRoot(), (QuantityListener) getContext()));
            tableRow.executePendingBindings();
        }
        payment.setOnShowListener(dialog -> {
            Float amount = 0f;
            for (Stock stock : Stock.getOrders().values()) {
                amount += stock.price * stock.quantityOrdered;
            }
            Currency currency = Currency.getInstance("PHP");
            $amount.setText(currency.getSymbol() + String.format("%.2f", amount));
            $change.setText(currency.getSymbol() + "0.00");
        });
        proceed.setOnClickListener(v -> {
            payment.show();
        });
        return binding.getRoot();
    }


    private void proceedToPayments() {
        Activity activity = getActivity();
        if (activity != null) {
            ((Cashout) activity).loadFragment(PaymentsFragment.FRAGMENT_ID);
        }
    }

    public void updateOrdersTable(TableRow row) {
        table.removeView(row);
    }
}