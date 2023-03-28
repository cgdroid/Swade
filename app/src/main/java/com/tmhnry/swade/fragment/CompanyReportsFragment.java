package com.tmhnry.swade.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tmhnry.swade.R;
import com.tmhnry.swade.databinding.FragmentCompanyReportsBinding;
import com.tmhnry.swade.model.Stock;
import com.tmhnry.swade.model.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CompanyReportsFragment extends Fragment {
    FragmentCompanyReportsBinding binding;
    RadarChart radarChart;
    LineChart lineChart;
    LocalDateTime start;
    LocalDateTime end;
    float totalProfit = 0f;
    float totalRevenue = 0f;

    public CompanyReportsFragment() {
    }

    public static CompanyReportsFragment Builder() {
        CompanyReportsFragment fragment = new CompanyReportsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        LocalDateTime now = LocalDate.now().atStartOfDay();
        start = now.minusDays(now.getDayOfWeek().getValue());
        end = start.plusDays(7).minusNanos(1);
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
        binding = FragmentCompanyReportsBinding.inflate(inflater, container, false);
        radarChart = binding.radarChart;
        lineChart = binding.lineChart;
        binding.prevWeek.setOnClickListener(v -> {
            getPreviousWeek();
        });
        binding.nextWeek.setOnClickListener(v -> {
            getNextWeek();
        });

        updateRange();
        return binding.getRoot();
    }

    private void getPreviousWeek() {
        start = start.minusDays(7);
        end = end.minusDays(7);
        updateRange();
    }

    private void getNextWeek() {
        start = start.plusDays(7);
        end = end.plusDays(7);
        updateRange();
    }

    private Date getStartDate() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, start.getYear());
        calendar.set(Calendar.MONTH, start.getMonthValue() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, start.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getEndDate() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, end.getYear());
        calendar.set(Calendar.MONTH, end.getMonthValue() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, end.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    private void updateRange() {
        binding.range.setText(start.toLocalDate() + " - " + end.toLocalDate());
        loadLineChart();
        loadRadarChartByStock();
        setTotalProfit();
        setTotalRevenue();
    }

    private Date getDateFromDateTime(LocalDateTime dateTime) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, dateTime.getYear());
        calendar.set(Calendar.MONTH, dateTime.getMonthValue() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, dateTime.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, dateTime.getHour());
        calendar.set(Calendar.MINUTE, dateTime.getMinute());
        calendar.set(Calendar.SECOND, dateTime.getSecond());
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private void loadLineChart() {
        totalProfit = 0f;
        totalRevenue = 0f;
        ArrayList<Entry> sales = new ArrayList<>();
        ArrayList<Entry> profits = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Date _start = getDateFromDateTime(start.plusDays(i));
            Date _end = getDateFromDateTime(start.plusDays(i + 1));
            float revenue = 0f;
            float profit = 0f;
            List<Transaction> transactions = Transaction.getTransactions(Transaction.SELLER, _start, _end);
            for (Transaction transaction : transactions) {
                revenue += transaction.value;
                profit += transaction.value - transaction.costValue;
            }
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            calendar.setTime(_start);
            sales.add(new Entry(0 + (float) i, revenue));
            profits.add(new Entry(0 + (float) i, profit));
            switch (i) {
                case 0:
                    binding.day1.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                    break;
                case 1:
                    binding.day2.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                    break;
                case 2:
                    binding.day3.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                    break;
                case 3:
                    binding.day4.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                    break;
                case 4:
                    binding.day5.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                    break;
                case 5:
                    binding.day6.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                    break;
                default:
                    binding.day7.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            }
            totalProfit += profit;
            totalRevenue += revenue;

        }

        LineDataSet dataSet = new LineDataSet(sales, "Revenue");
        LineDataSet dataSetProfit = new LineDataSet(profits, "Profits");
        dataSet.setColor(Color.WHITE);
        dataSet.setValueTextColor(Color.parseColor("#4b4b4b"));
        dataSet.setDrawValues(false);
        dataSet.setLineWidth(2f);
        dataSet.setHighlightEnabled(false);
        dataSet.setDrawCircles(false);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setDrawFilled(true);
        dataSet.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_chart));

        dataSetProfit.setColor(Color.parseColor("#56cda8"));
        dataSetProfit.setValueTextColor(Color.parseColor("#4b4b4b"));
        dataSetProfit.setDrawValues(false);
        dataSetProfit.setLineWidth(2f);
        dataSetProfit.setHighlightEnabled(false);
        dataSetProfit.setDrawCircles(false);
        dataSetProfit.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSetProfit.setDrawFilled(true);
        dataSetProfit.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_chart));
        LineData data = new LineData();

        data.addDataSet(dataSet);
        data.addDataSet(dataSetProfit);
        lineChart.setData(data);
        lineChart.setDrawGridBackground(false);
        lineChart.getXAxis().setAxisMinimum(0f);
//        lineChart.getXAxis().setAxisMaximum(24f);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularity(4f);
        lineChart.getXAxis().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getXAxis().setDrawAxisLine(false);

        lineChart.getAxisRight().setEnabled(false);

        lineChart.getAxisLeft().setEnabled(false);
        lineChart.getAxisLeft().setAxisMinimum(-200f);
        lineChart.setDragEnabled(true);
        lineChart.setTouchEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);

        lineChart.getDescription().setEnabled(false);
        lineChart.getLegend().setEnabled(true);
        lineChart.invalidate();
    }


    private void loadRadarChartByStock() {
//        top performing stocks
        Stock[] stocks;
        int size = Stock.getCompanySoldStocks().size();
        float minIncome = 0f;
        int currentIndex = 0;
        int minIndex = 0;
        if (size > 8) {
            stocks = new Stock[8];
        } else {
            stocks = new Stock[size];
        }
        Arrays.fill(stocks, null);
        for (Stock stock : Stock.getCompanySoldStocks().values()) {
            if (stocks[0] == null) {
                stocks[0] = stock;
                minIncome = stock.netIncome;
                continue;
            }
            if (currentIndex < 7 && stock.netIncome < minIncome) {
                currentIndex += 1;
                stocks[currentIndex] = stock;
                minIncome = stock.netIncome;
                minIndex = currentIndex;
                continue;
            }
            if (currentIndex < 7) {
                currentIndex += 1;
                stocks[currentIndex] = stock;
                continue;
            }
            if (stock.netIncome > minIncome) {
                stocks[minIndex] = stock;
                minIncome = stock.netIncome;
                for (int i = 0; i < stocks.length; i++) {
                    if (stocks[i].netIncome < minIncome) {
                        minIncome = stocks[i].netIncome;
                        minIndex = i;
                    }
                }
            }
        }

        ArrayList<RadarEntry> revenues = new ArrayList<>();
        String[] names = new String[8];
        for (int i = 0; i < stocks.length; i++) {
            Stock stock = stocks[i];
            revenues.add(new RadarEntry(stock.netIncome));
            names[i] = stock.name;
        }

        for (int i = 0; i < 8 - stocks.length; i++) {
            revenues.add(new RadarEntry(0f));
            names[i + stocks.length] = "N/A";
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color : ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        RadarDataSet dataSet = new RadarDataSet(revenues, "Top Stocks by Revenue");
        dataSet.setColors(Color.parseColor("#9b8aec"));
        dataSet.setLineWidth(2f);
        dataSet.setFillColor(Color.parseColor("#9b8aec"));
        dataSet.setFillAlpha(160);
        dataSet.setDrawFilled(true);
        dataSet.setValueTextColor(Color.TRANSPARENT);
        dataSet.setDrawHighlightCircleEnabled(true);
        dataSet.setDrawHighlightCircleEnabled(false);
        dataSet.setValueTextSize(10f);
        dataSet.setLabel(null);

        RadarData data = new RadarData();
        data.addDataSet(dataSet);

        XAxis xAxis = radarChart.getXAxis();
        YAxis yAxis = radarChart.getYAxis();

        xAxis.setAxisLineColor(Color.WHITE);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTextSize(9f);
        yAxis.setAxisLineColor(Color.WHITE);
        yAxis.setGranularityEnabled(true);
        yAxis.setGranularity(40f);
        yAxis.setDrawTopYLabelEntry(true);
        yAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yAxis.setGridColor(Color.WHITE);
        xAxis.setGridColor(Color.WHITE);

        radarChart.setWebColorInner(Color.parseColor("#c9d2d7"));
        radarChart.setWebLineWidthInner(1f);
        radarChart.setWebColor(Color.parseColor("#c9d2d7"));
        radarChart.setWebLineWidth(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(names));
        radarChart.getDescription().setEnabled(false);
        radarChart.setData(data);
        radarChart.setDrawMarkers(true);
        radarChart.getLegend().setEnabled(false);
        radarChart.invalidate();
    }

    private void setTotalRevenue() {
        binding.totalRevenue.setText("\u20b1" + String.format("%.0f", totalRevenue));
    }

    private void setTotalProfit() {
        binding.totalProfit.setText("\u20b1" + String.format("%.0f", totalProfit));
    }

    private void loadRadarChartByCategory() {
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
//        data.setValueFormatter(new PercentFormatter((pieChart)));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.WHITE);
//
//        pieChart.setData(data);
//        pieChart.invalidate();
//
//        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }
}