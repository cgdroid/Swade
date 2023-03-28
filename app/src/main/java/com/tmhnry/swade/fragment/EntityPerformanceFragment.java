package com.tmhnry.swade.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmhnry.swade.databinding.FragmentEntityPerformanceBinding;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.model.Stock;
import com.tmhnry.swade.model.Transaction;
import com.tmhnry.swade.recyclerview.CalendarDaysViewAdapter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EntityPerformanceFragment extends Fragment {
    FragmentEntityPerformanceBinding binding;
    CalendarDaysViewAdapter adapter;
    RecyclerView recyclerView;
    TextView monthYearText;
    LocalDate selectedDate;
    Entity entity;

    public EntityPerformanceFragment() {
        // Required empty public constructor
    }

    public static EntityPerformanceFragment Builder(String key) {
        EntityPerformanceFragment fragment = new EntityPerformanceFragment();
        Bundle args = new Bundle();
        args.putString("key", key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectedDate = LocalDate.now();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String key = (String) getArguments().get("key");
        for (Entity entity : Entity.getModels().values()) {
            if (entity.key.equals(key)) {
                this.entity = entity;
                break;
            }
        }
    }

    public void updateSoldStocks() {
        int stocks = 0;
//        Remember that the Asset objects in Asset.getModels() are retrieved as an asset from selling
        for (Stock stock : Stock.getUserSoldStocks().values()) {
            stocks += stock.quantitySold;
        }

        binding.summary.stocksSold.setText(String.valueOf(stocks));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEntityPerformanceBinding.inflate(inflater, container, false);

        float revenue = 0f;
        float profit = 0f;
        int customers = 0;

        List<Transaction> transactions = Transaction.getTransactions(Transaction.EMPLOYEE, entity.key, Transaction.SELLER);

        for (Transaction transaction : transactions) {
            revenue += transaction.value;
            profit += transaction.value - transaction.costValue;
            customers += 1;
        }

        updateSoldStocks();

        binding.summary.customersServed.setText(String.valueOf(customers));
        binding.summary.revenue.setText("\u20b1" + String.format("%.0f", revenue));
        binding.summary.profit.setText("\u20b1" + String.format("%.0f", profit));

        initWidgets();
        setMonthView();

        updateAttendanceViews(false);

        return binding.getRoot();
    }


    private void initWidgets() {
        binding.nextMonth.setOnClickListener(this::nextMonthAction);
        binding.prevMonth.setOnClickListener(this::previousMonthAction);
        recyclerView = binding.recyclerView;
        monthYearText = binding.monthYear;
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        ArrayList<LocalDate> datesInMonth = datesInMonthArray(selectedDate);
        CalendarDaysViewAdapter calendarAdapter = new CalendarDaysViewAdapter(daysInMonth, datesInMonth, (CalendarDaysViewAdapter.OnItemListener) getContext());
        recyclerView.setAdapter(calendarAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
    }


    public void updateAttendanceViews(boolean notify) {
        if (notify) {
            recyclerView.getAdapter().notifyDataSetChanged();
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
}