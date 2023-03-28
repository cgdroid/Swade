package com.tmhnry.swade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.tmhnry.swade.databinding.ActivityStockSetupBinding;
import com.tmhnry.swade.model.Model;
import com.tmhnry.swade.model.Stock;
import com.tmhnry.swade.singleton.Company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockSetup extends AppCompatActivity {
    ActivityStockSetupBinding binding;
    public static final String TAG = "stock-setup";
    AutoCompleteTextView group;
    AutoCompleteTextView unit;
    AppCompatButton save;
    EditText name;
    EditText code;
    EditText price;
    EditText cost;
    EditText quantity;
    List<String> groups;
    List<String> units;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        binding = ActivityStockSetupBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.imgBack.setOnClickListener(v -> {
            onBackPressed();
        });

        String barcode = getIntent().getStringExtra("barcode");
        String rawBarcode = getIntent().getStringExtra("barcode-raw");

        group = binding.autoCompGroup;
        unit = binding.autoCompUnit;
        name = binding.inputEditName;
        quantity = binding.inputEditQuantity;
        code = binding.inputEditCode;
        price = binding.inputEditPrice;
        cost = binding.inputEditCost;

        groups = new ArrayList<>();

        if (rawBarcode != null) {
            code.setText(rawBarcode);
        }

        for (Stock stock : Stock.getModels().values()) {
            if (stock.group == null) {
                continue;
            }
            if (groups.contains(stock.group)) {
                continue;
            }
            groups.add(stock.group);
        }

        units = new ArrayList<>();
        units.add("kilogram (kg)");
        units.add("gram (g)");
        units.add("piece (pc)");
        units.add("liter (l)");
        units.add("milliliter (ml)");

        binding.barcode.setOnClickListener(v -> {
            Intent intent = new Intent(this, LivePreviewActivity.class);
            intent.putExtra(LivePreviewActivity.SOURCE_TAG, TAG);
            startActivity(intent);
            finish();
        });

        save = binding.btnSave;
        save.setOnClickListener(v -> {
            Map<String, Object> data = new HashMap<>();
            String _group = group.getText().toString().trim();
            String _name = name.getText().toString().trim();
            String _code = code.getText().toString().trim();
            String _price = price.getText().toString().trim();
            String _cost = cost.getText().toString().trim();
            String _quantity = quantity.getText().toString().trim();
            String _unit = unit.getText().toString().trim();
            data.put(Stock.GROUP, _group);
            data.put(Stock.NAME, _name);
            data.put(Stock.CODE, _code);
            data.put(Stock.PRICE, Float.parseFloat(_price));
            data.put(Stock.COST, Float.parseFloat(_cost));
            data.put(Stock.QUANTITY_REMAINING, Integer.parseInt(_quantity));
            data.put(Stock.UNIT, _unit);
            data.put(Stock.QUANTITY_ORDERED, 0);
            data.put(Stock.QUANTITY_SOLD, 0);
            data.put(Stock.NET_COST, 0f);
            data.put(Stock.NET_INCOME, 0f);
            data.put(Stock.COMPANY_KEY, (String) Company.retrieve(this, Company.KEY));
            data.put(Model.ID, Stock.getRandomPublicId(this));
            clearInputs();
            onAddStock(data);
        });

        ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>(this, R.layout.drop_down_item, groups);
        unit.setLines(1);
        group.setAdapter(groupAdapter);
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this, R.layout.drop_down_item, units);
        unit.setAdapter(unitAdapter);
    }

    private void clearInputs() {
        group.getText().clear();
        unit.getText().clear();
        name.getText().clear();
        code.getText().clear();
        price.getText().clear();
        cost.getText().clear();
        quantity.getText().clear();
    }

//    public void onAddStock(Map<String, Object> data) {
//        List<Stock> stocks = new ArrayList<>();
//        stocks.add(Stock.Model(data));
//        Stock.Stocks.getInstance().append(stocks);
//    }

    public void onAddStock(Map<String, Object> data) {
        Stock.getSupply().put((Integer) data.get(Model.ID), Stock.Model(data));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StocksSupply.class);
        startActivity(intent);
    }

}