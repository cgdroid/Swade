package com.tmhnry.swade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tmhnry.swade.database.Firebase;
import com.tmhnry.swade.databinding.ActivityStocksSupplyBinding;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.model.Model;
import com.tmhnry.swade.model.Stock;
import com.tmhnry.swade.model.Transaction;
import com.tmhnry.swade.model.Asset;
import com.tmhnry.swade.recyclerview.SupplyStocksViewAdapter;
import com.tmhnry.swade.singleton.Company;
import com.tmhnry.swade.singleton.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class StocksSupply extends AppCompatActivity implements Model.FirebaseQueryListener {
    ActivityStocksSupplyBinding binding;
    RecyclerView recyclerView;
    SupplyStocksViewAdapter adapter;
    Map<String, Boolean> querySuccessful;
    List<Stock> stocks;
    Handler handler;
    Dialog dialog;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStocksSupplyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        calendar = Calendar.getInstance(Locale.getDefault());

        Transaction.initModels(this);
        Asset.initModels(this);

        createLoadingDialog();
        querySuccessful = new HashMap<>();
        stocks = new ArrayList<>();
        stocks.addAll(Stock.getSupply().values());
        float total = 0f;
        for (Stock stock : stocks) {
            total += stock.cost * stock.quantityRemaining;
        }
        binding.supplyTotal.setText("Total: " + "\u20b1" + String.format("%.2f", total));
        adapter = new SupplyStocksViewAdapter(this, stocks);
        recyclerView = binding.recyclerView;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void createLoadingDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_loading_indicator);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void addSupplyToStorage(View view) {
        if (stocks.isEmpty()) {
            return;
        }

        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction = createTransaction();
        transactions.add(transaction);
        List<Asset> assets = createAssets(transaction);
        List<Stock> stocks = new ArrayList<>(Stock.getSupply().values());
        updateCloudDatabase(transactions, assets, stocks);
        // A call to Stock.retrieve() is unnecessary since Home already implements one
        // We can invoke that in this activity for example to show the available stocks in the storage
    }

    private void updateCloudDatabase(List<Transaction> transactions,
                                     List<Asset> assets,
                                     List<Stock> stocks) {
        Transaction.append(transactions);
        Asset.append(assets);
        Stock.append(stocks);
    }

    private Transaction createTransaction() {
        Map<String, Object> data = new HashMap<>();
        data.put(Transaction.DATE, calendar.getTime());
        data.put(Transaction.COMPANY_ONE, Company.getKey(this));
        data.put(Transaction.COMPANY_ONE_TYPE, Transaction.BUYER);
        data.put(Transaction.COMPANY_ONE_ASSETS, "Cash");
        data.put(Transaction.COMPANY_TWO, null);
        data.put(Transaction.COMPANY_TWO_TYPE, Transaction.SELLER);
        data.put(Transaction.COMPANY_TWO_ASSETS, stocks.size() + " Unique Item(s)");
        data.put(Model.ID, Transaction.getRandomPublicId(this));
        data.put(Transaction.VALUE, 0f);

        Entity entity = null;
        for (Entity _entity : Entity.getModels().values()) {
            if (_entity.userKey.equals(User.getKey(this))) {
                entity = _entity;
            }
        }
//       What makes Entity.key preferable over User.key?
        data.put(Transaction.ENTITY_ONE, entity.key);
//        How to put display name here?
        data.put(Transaction.ENTITY_ONE_NAME, User.getFullName(this));
        data.put(Transaction.ENTITY_TWO, null);
        data.put(Transaction.ENTITY_TWO_NAME, null);
        data.put(Transaction.COST_VALUE, 0f);

        Transaction transaction = Transaction.Model(data);
//        We need to initialize transaction key before adding assets

        transaction.key = Firebase
                .getChildReference(Transaction.TABLE_NAME)
                .push()
                .getKey();
        return transaction;
    }

    private List<Asset> createAssets(Transaction transaction) {
        List<Asset> assets = new ArrayList<>();
        for (Stock stock : Stock.getSupply().values()) {
            float cost = stock.cost * stock.quantityRemaining;
            transaction.value += cost;
            Map<String, Object> data = new HashMap<>();
            data.put(Model.ID, Asset.getRandomPublicId(this));
            data.put(Asset.ASSET_NAME, stock.name);
            data.put(Asset.ASSET_UNIT, stock.unit);
            data.put(Asset.ASSET_QUANTITY, stock.quantityRemaining);
            data.put(Asset.TRANSACTION, transaction);
            data.put(Asset.ASSET_CATEGORY, stock.group);
            data.put(Asset.ASSET_VALUE, cost);
            assets.add(Asset.Model(data));
        }
        return assets;
    }

    public void addNewStock(View view) {
        startActivity(new Intent(this, StockSetup.class));
        finish();
    }


    private boolean allQueriesCompleted() {
        boolean complete = true;
        for (Boolean queryComplete : querySuccessful.values()) {
            if (!queryComplete) {
                complete = false;
                break;
            }
        }
        return complete;
    }

    private void goToHome() {
        startActivity(new Intent(this, Home.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        goToHome();
    }

    private void setState() {
        // checks query status every 1 second
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (allQueriesCompleted()) {
                    dialog.cancel();
                    handler.removeCallbacks(this);
                    handler = null;
                    Stock.getSupply().clear();
                    onBackPressed();
                    return;
                }
                setState();
            }
        }, 1000);
    }

    @Override
    public void onStartQuery(String name) {
        querySuccessful.put(name, false);
        dialog.show();
    }

    @Override
    public void onSuccessQuery(String name) {
        querySuccessful.put(name, true);
        if (handler != null) {
            return;
        }
        handler = new android.os.Handler();
        setState();
    }

    @Override
    public void onFailQuery() {
    }
}