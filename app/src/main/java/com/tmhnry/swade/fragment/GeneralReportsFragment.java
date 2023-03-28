package com.tmhnry.swade.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.tmhnry.swade.Home;
import com.tmhnry.swade.databinding.FragmentGeneralReportsBinding;
import com.tmhnry.swade.databinding.TableRowStockBinding;
import com.tmhnry.swade.databinding.TableRowTransactionBinding;
import com.tmhnry.swade.model.Attendance;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.model.Stock;
import com.tmhnry.swade.model.Transaction;
import com.tmhnry.swade.recyclerview.AttendancesViewAdapter;
import com.tmhnry.swade.recyclerview.CalendarDaysViewAdapter;
import com.tmhnry.swade.singleton.User;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GeneralReportsFragment extends Fragment {
    public static final String FRAGMENT_ID = "tmhnry.employeeproductivity.generalreportfragment";
    FragmentGeneralReportsBinding binding;
    RecyclerView recyclerView;
    RecyclerView calendarRecyclerView;
    TableLayout table;
    AttendancesViewAdapter adapter;
    List<Transaction> transactions;
    LocalDate selectedDate;
    TextView monthYearText;
    Entity entity;
    BarChart barChart;
    Spinner spinner;
    String[] options = {"Attrition", "Hours", "Stocks", "Combined"};

    public GeneralReportsFragment() {
    }

    public static GeneralReportsFragment Builder() {
        GeneralReportsFragment fragment = new GeneralReportsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        adapter = new AttendancesViewAdapter(context);
        for (Entity entity : Entity.getModels().values()) {
            if (entity.userKey.equals(User.getKey(context))) {
                this.entity = entity;
                break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGeneralReportsBinding.inflate(inflater, container, false);
        recyclerView = binding.recyclerView;
        recyclerView.setAdapter(adapter);

        FlexboxLayoutManager manager = new FlexboxLayoutManager(getContext());
        manager.setJustifyContent(JustifyContent.SPACE_AROUND);
        manager.setFlexDirection(FlexDirection.ROW);
        manager.setFlexWrap(FlexWrap.WRAP);

        recyclerView.setLayoutManager(manager);

        selectedDate = LocalDate.now();
        initWidgets();
        setMonthView();

        updateAttendanceViews(false);

//        table = binding.table.getRoot();
        transactions = new ArrayList<>();
        transactions.addAll(Transaction.getModels().values());


        for (Transaction transaction : Transaction.getModels().values()) {
            TableRowTransactionBinding tableRow = TableRowTransactionBinding.inflate(inflater, table, true);
            tableRow.setTransaction(transaction);
            tableRow.executePendingBindings();
//            width = (int) Math.ceil(15 * density * 0);
        }

        int weeklyTransactions = 0;
        float revenue = 0f;
        int customers = 0;
        int stocks = 0;
        float profit = 0f;
        float weeklyRevenue = 0f;

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        Date dateToday = calendar.getTime();
        int dayFromMonday = (calendar.get(Calendar.DAY_OF_WEEK) + 7 - Calendar.MONDAY) % 7;
        calendar.add(Calendar.DATE, -dayFromMonday - 1);
        Date lastWeek = calendar.getTime();

        Context context = getContext();
        String userKey = User.getKey(context);
//        We are querying all transactions records belonging to the user and the company the user is
//        associated to.
//        Remember that transactions are first queried according to company key.
        List<Transaction> transactions = Transaction.getTransactions(Transaction.EMPLOYEE, entity.key, Transaction.SELLER);
        for (Transaction transaction : transactions) {
            revenue += transaction.value;
            profit += transaction.value - transaction.costValue;
            customers += 1;
        }

//        Remember that the Aseet objects in Asset.getModels() are retrieved as an asset from selling
        for (Stock stock : Stock.getUserSoldStocks().values()) {
            stocks += stock.quantitySold;
        }

        binding.summary.stocksSold.setText(String.valueOf(stocks));
        binding.summary.customersServed.setText(String.valueOf(customers));
        binding.summary.revenue.setText("\u20b1" + String.format("%.0f", revenue));
        binding.summary.profit.setText("\u20b1" + String.format("%.0f", profit));

        binding.editAttendance.setOnClickListener(v -> {
            ((Home) getActivity()).onEditClicked(dateToday, entity);
        });

        barChart = binding.barChart;
        barChart.setDescription(null);

        spinner = (Spinner) binding.spinner;

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String flag = "attrition";

                switch (position) {
                    case 0:
                        return;
                    case 1:
                        showHoursWorkedChart();
                        return;
                    case 2:
                        showStocksSoldChart();
                        return;
                    default:
                        showGroupedBarChart();
                        return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter optionsAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, options);
        optionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(optionsAdapter);
        return binding.getRoot();
    }

    private void initWidgets() {
        binding.nextMonth.setOnClickListener(this::nextMonthAction);
        binding.prevMonth.setOnClickListener(this::previousMonthAction);
        calendarRecyclerView = binding.calendarRecyclerView;
        monthYearText = binding.monthYear;
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        ArrayList<LocalDate> datesInMonth = datesInMonthArray(selectedDate);
        CalendarDaysViewAdapter calendarAdapter = new CalendarDaysViewAdapter(daysInMonth, datesInMonth, (CalendarDaysViewAdapter.OnItemListener) getContext());
        calendarRecyclerView.setAdapter(calendarAdapter);
        calendarRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
    }


    public void updateAttendanceViews(boolean notify) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date start = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date end = calendar.getTime();

        boolean hasAttendance = false;
        for (Attendance attendance : Attendance.getModels().values()) {
            if (attendance.date.after(start) && attendance.date.before(end)) {
                hasAttendance = true;
                break;
            }
        }

        if (hasAttendance) {
            binding.editAttendance.setVisibility(View.GONE);
        } else {
            binding.editAttendance.setVisibility(View.VISIBLE);
        }
        if (notify) {
            calendarRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }


    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    private ArrayList<LocalDate> datesInMonthArray(LocalDate date) {
        ArrayList<LocalDate> datesInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = date.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                datesInMonthArray.add(null);
            } else {
                datesInMonthArray.add(date.withDayOfMonth(i - dayOfWeek));
            }
        }
        return datesInMonthArray;
    }

    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }


    private void showStocksSoldChart() {
        List<BarEntry> stocksSold = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setDrawGridLines(false);

        int i = 0;
        for (Entity entity : Entity.getModels().values()) {
            labels.add(entity.getName());
            stocksSold.add(new BarEntry(i + 0.5f, (float) (entity.stocksSold)));
            i += 1;
        }

        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        BarDataSet _stocks = new BarDataSet(stocksSold, "Stocks");
        _stocks.setValueTextSize(12f);

        _stocks.setColor(Color.parseColor("#dde8ff"));

        BarData barData = new BarData(_stocks);

        barChart.setData(barData);
        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);

        barData.setBarWidth(0.15f);
        xAxis.setAxisMinimum(0);
        barChart.invalidate();
        barChart.animateXY(2000, 3000);
    }

    private void showHoursWorkedChart() {
        List<BarEntry> hoursWorked = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setDrawGridLines(false);

        int i = 0;
        for (Entity entity : Entity.getModels().values()) {
            labels.add(entity.getName());
            hoursWorked.add(new BarEntry(i + 0.5f, (float) (entity.hoursWorked)));
            i += 1;
        }

        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        BarDataSet _hours = new BarDataSet(hoursWorked, "Hours");
        _hours.setValueTextSize(12f);

        _hours.setColor(Color.parseColor("#ffd4d3"));

        BarData barData = new BarData(_hours);

        barChart.setData(barData);
        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);

        barData.setBarWidth(0.15f);
        xAxis.setAxisMinimum(0);
        barChart.invalidate();
        barChart.animateXY(2000, 3000);
    }

    private void showGroupedBarChart() {
        List<BarEntry> hoursWorked = new ArrayList<>();
        List<BarEntry> stocksSold = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setDrawGridLines(false);

        int i = 0;
        for (Entity entity : Entity.getModels().values()) {
            labels.add(entity.getName());
            hoursWorked.add(new BarEntry(i + 1, (float) (entity.hoursWorked)));
            stocksSold.add(new BarEntry(i + 1, (float) (entity.stocksSold)));
            i += 1;
        }

        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        BarDataSet _stocks = new BarDataSet(stocksSold, "Stocks");
        BarDataSet _hours = new BarDataSet(hoursWorked, "Hours");
        _stocks.setValueTextSize(12f);
        _hours.setValueTextSize(12f);

        _hours.setColor(Color.parseColor("#ffd4d3"));
        _stocks.setColor(Color.parseColor("#dde8ff"));

        BarData barData = new BarData(_hours, _stocks);

        barChart.setData(barData);
        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);

        float barSpace = 0.1f;
        float groupSpace = 0.5f;

        barData.setBarWidth(0.15f);
        xAxis.setAxisMinimum(0);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.invalidate();
        barChart.animateXY(2000, 3000);
    }
}