package com.tmhnry.swade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.tmhnry.swade.databinding.ActivityEntityProfileBinding;
import com.tmhnry.swade.databinding.FragmentHomeBinding;
import com.tmhnry.swade.fragment.EntityPerformanceFragment;
import com.tmhnry.swade.fragment.EntityProfileFragment;
import com.tmhnry.swade.fragment.EntityStocksFragment;
import com.tmhnry.swade.fragment.EntityTransactionsFragment;
import com.tmhnry.swade.model.Asset;
import com.tmhnry.swade.model.Attendance;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.model.Model;
import com.tmhnry.swade.model.Stock;
import com.tmhnry.swade.model.Transaction;
import com.tmhnry.swade.recyclerview.CalendarDaysViewAdapter;
import com.tmhnry.swade.singleton.Company;
import com.tmhnry.swade.singleton.User;
import com.tmhnry.swade.viewpager.EntityProfileViewPagerAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityProfile extends AppCompatActivity implements Model.FirebaseQueryListener, CalendarDaysViewAdapter.OnItemListener {

    ActivityEntityProfileBinding binding;
    EntityProfileViewPagerAdapter adapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    Map<String, Boolean> querySuccessful;
    View $progress;
    Handler handler;
    Entity entity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEntityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        $progress = binding.btmProgressIndicator;
        querySuccessful = new HashMap<>();
        String key = getIntent().getStringExtra(Model.KEY);
        Attendance.getModels().clear();
        Transaction.initModels(this);
        Asset.initModels(this);
        Transaction.retrieve(this);
        Attendance.initModels(this);
        Attendance.retrieve(this, key);

        viewPager = binding.viewPager;
        tabLayout = binding.tabLayout;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        for (Entity entity : Entity.getModels().values()) {
            if (entity.key.equals(key)) {
                this.entity = entity;
            }
        }
        if (entity == null) {
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new EntityProfileViewPagerAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(EntityProfileFragment.Builder(key), "DETAILS");
        if (User.getAccountType(this).equals(Entity.OWNER)) {
            adapter.addFragment(EntityPerformanceFragment.Builder(key), "GENERAL");
            adapter.addFragment(EntityTransactionsFragment.Builder(entity.key), "TRANSACTIONS");
            adapter.addFragment(EntityStocksFragment.Builder(), "STOCKS");
        }
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        binding.name.setText(entity.getName());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Home.class));
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

    private void disableTabs() {
        binding.tabLayout.setEnabled(false);
        binding.tabLayout.setOnClickListener(v -> {
            Toast.makeText(this, "CLICKED", Toast.LENGTH_SHORT).show();
        });
    }

    private void stopLoading() {
        $progress.setVisibility(View.GONE);
    }

    private void startLoading() {
        $progress.setVisibility(View.VISIBLE);
    }

    private void setState() {
        // checks query status every 1 second
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (allQueriesCompleted()) {
                    stopLoading();
                    handler.removeCallbacks(this);
                    handler = null;
                    return;
                }
                setState();
            }
        }, 1000);
    }

    @Override
    public void onStartQuery(String name) {
        querySuccessful.put(name, false);
        startLoading();
        disableTabs();
    }

    @Override
    public void onSuccessQuery(String name) {
        querySuccessful.put(name, true);
//        It is important to call this before setState else setState all queries prior to this call
//        may be finished causing the dialog to be cancelled
        if (name.equals(Transaction.TABLE_NAME)) {
            List<Transaction> transactions = Transaction.getTransactions(Transaction.EMPLOYEE, entity.key, Transaction.SELLER);
            Asset.retrieve(transactions);
        }

        if (name.equals(Attendance.TABLE_NAME)) {
            if (User.getAccountType(this).equals(Entity.EMPLOYEE)) {
                return;
            }
            ((EntityPerformanceFragment) adapter.getItem(1))
                    .updateAttendanceViews(true);
        }

        if (name.equals(Asset.TABLE_NAME)) {
            Stock.getUserSoldStocks().clear();
            for (Asset asset : Asset.getModels().values()) {
                List<Stock> stocks = Stock.getUserSoldStocks().values().stream()
                        .filter(stock -> stock.name.equals(asset.assetName))
                        .collect(Collectors.toList());
                if (!stocks.isEmpty()) {
                    Stock _stock = stocks.get(0);
                    _stock.customers += 1;
                    _stock.quantitySold += asset.assetQuantity;
                    _stock.netIncome += asset.assetValue;
//                    Log.v("quantity-sold", String.valueOf(_stock.quantitySold));
                    continue;
                }
                Map<String, Object> data = new HashMap<>();
                data.put(Model.ID, Stock.getRandomPublicId(this));
                data.put(Stock.NAME, asset.assetName);
                data.put(Stock.GROUP, asset.assetCategory);
                data.put(Stock.PRICE, 0f);
                data.put(Stock.COST, 0f);
                data.put(Stock.UNIT, asset.assetUnit);
                data.put(Stock.NET_INCOME, asset.assetValue);
                data.put(Stock.COMPANY_KEY, Company.getKey(this));
                data.put(Stock.QUANTITY_ORDERED, 0);
                data.put(Stock.CODE, "");
                data.put(Stock.QUANTITY_SOLD, asset.assetQuantity);
                data.put(Stock.QUANTITY_REMAINING, 0);
                data.put(Stock.NET_COST, 0);
                Stock stock = Stock.Model(data);
                stock.customers += 1;

                Stock.getUserSoldStocks().put(stock.getId(), stock);
            }

            if (User.getAccountType(this).equals(Entity.EMPLOYEE)) {
                return;
            }

            ((EntityPerformanceFragment) adapter.getItem(1))
                    .updateSoldStocks();
        }

        if (handler != null) {
            return;
        }
        handler = new android.os.Handler();
        setState();
    }

    @Override
    public void onFailQuery() {
    }


    @Override
    public void onItemClick(int position, String dayText) {

    }
}