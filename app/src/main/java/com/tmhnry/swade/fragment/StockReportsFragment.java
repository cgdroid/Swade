package com.tmhnry.swade.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tmhnry.swade.databinding.FragmentStockReportsBinding;
import com.tmhnry.swade.databinding.TableRowStockBinding;
import com.tmhnry.swade.model.Stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StockReportsFragment extends Fragment {
    public static final String FRAGMENT_ID = "tmhnry.employeeproductivity.allstocksreportfragment";
    FragmentStockReportsBinding binding;
    TableLayout table;
    WindowManager windowManager;
    DisplayMetrics metrics;
    PieChart pieChart;

    public StockReportsFragment() {
    }

    public static StockReportsFragment Builder() {
        StockReportsFragment fragment = new StockReportsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        windowManager = ((Activity) context).getWindowManager();
        metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentStockReportsBinding.inflate(inflater, container, false);
        table = binding.tableLayout;
        pieChart = binding.pieChart;
        setupPieChart();
        loadPieChartData();

        float density = metrics.density;
        // 15dp and 10dp for stock bar parent width and stock bar height respectively,
        // must correspond with actual dimensions from layout
        int height = (int) Math.ceil(10 * density);
        int width = 0;

        for (Stock stock : Stock.getUserSoldStocks().values()) {
            TableRowStockBinding tableRow = TableRowStockBinding.inflate(inflater, table, true);
            tableRow.setStock(stock);
            tableRow.executePendingBindings();
//            width = (int) Math.ceil(15 * density * 0);
            width = (int) (Math.ceil(15 * density) * stock.getRelativeRevenue());
            LayoutParams params = new LayoutParams(width, height);
            tableRow.stockBar.setLayoutParams(params);
        }

        return binding.getRoot();
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelColor(Color.TRANSPARENT);
        pieChart.setCenterText("Top Categories (Revenue)");
        pieChart.setCenterTextColor(Color.parseColor("#b1b1b1"));
//        Description description = new Description();
//        description.setText("Top Categories (Revenue)");
//        description.setTextSize(14f);
//        description.setTextColor(Color.parseColor("#b1b1b1"));
//        description.setPosition(500f, 100f);
//        pieChart.setDescription(description);
//        pieChart.setCenterTextSize(14f);
        pieChart.getDescription().setEnabled(false);
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setEnabled(true);
        legend.setTextColor(Color.parseColor("#bfbfbf"));
    }

    private void loadPieChartData() {
        Map<String, Float> groupRevenue = new HashMap<>();
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Stock stock : Stock.getUserSoldStocks().values()) {
            if (groupRevenue.containsKey(stock.group)) {
                continue;
            }
            groupRevenue.put(stock.group, 0f);
        }
        for (String group : groupRevenue.keySet()) {
            Float revenue = groupRevenue.get(group);
            for (Stock stock : Stock.getUserSoldStocks().values()) {
                if (stock.group.equals(group)) {
                    revenue += stock.netIncome;
                }
            }
            groupRevenue.put(group, revenue);
        }
        for (Map.Entry<String, Float> entry : groupRevenue.entrySet()) {
            if (entry.getValue() == 0f) {
                continue;
            }
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Product Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter((pieChart)));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }
}