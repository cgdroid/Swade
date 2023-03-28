package com.tmhnry.swade.fragment;

import android.content.Context;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.tmhnry.swade.model.Attendance;
import com.tmhnry.swade.model.Entity;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tmhnry.swade.R;
import com.tmhnry.swade.databinding.FragmentEmployeeReportsBinding;
import com.tmhnry.swade.recyclerview.TopEmployeesViewAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.List;

public class EmployeeReportsFragment extends Fragment {
    FragmentEmployeeReportsBinding binding;
    RecyclerView topEmployees;
    TopEmployeesViewAdapter adapter;
    BubbleChart bubbleChart;
    BarChart barChart;
    LocalDateTime start;
    LocalDateTime end;
    List<Entity> entities;
    int mode;
    private static final int MODE_ATTRITION = 0;
    private static final int MODE_WORK_HOURS = 1;

    public EmployeeReportsFragment() {
    }

    public static EmployeeReportsFragment Builder() {
        EmployeeReportsFragment fragment = new EmployeeReportsFragment();
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
        LocalDateTime now = LocalDate.now().atStartOfDay();
        start = now.minusDays(now.getDayOfWeek().getValue());
        end = start.plusDays(7).minusNanos(1);
        entities = new ArrayList<>();
        mode = MODE_ATTRITION;
        adapter = new TopEmployeesViewAdapter(context, entities, mode, getStartDate(), getEndDate());
    }

    private void getPreviousWeek() {
        end = start.minusNanos(1);
        start = end.minusDays(7).plusNanos(1);
        updateRange();
    }

    private void getNextWeek() {
        start = end.plusNanos(1);
        end = start.plusDays(7).minusNanos(1);
        updateRange();
    }

    private void updateRange() {
        refresh();
        binding.range.setText(start.toLocalDate() + " - " + end.toLocalDate());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmployeeReportsBinding.inflate(inflater, container, false);
        bubbleChart = binding.bubbleChart;
        topEmployees = binding.topEmployees;
        barChart = binding.barChart;
        topEmployees.setAdapter(adapter);
        topEmployees.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.nextWeek.setOnClickListener(v -> getNextWeek());
        binding.prevWeek.setOnClickListener(v -> getPreviousWeek());
        binding.attrition.setOnClickListener(v -> {
            mode = MODE_ATTRITION;
            updateRange();
        });
        binding.hours.setOnClickListener(v -> {
            mode = MODE_WORK_HOURS;
            updateRange();
        });

        updateRange();
        return binding.getRoot();
    }

    private void refresh() {
        getTopEmployees();
        loadBarChart();
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
        calendar.set(Calendar.HOUR_OF_DAY, end.getHour());
        calendar.set(Calendar.MINUTE, end.getMinute());
        calendar.set(Calendar.SECOND, end.getSecond());
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private void getTopEmployees() {
        Entity[] entities;

        int size = Entity.getEmployees().size();

        if (size > 3) {
            entities = new Entity[3];
        } else {
            entities = new Entity[size];
        }
        Arrays.fill(entities, null);

        float minScore = 0f;
        int currentIndex = 0;
        int minIndex = 0;

        for (Entity entity : Entity.getEmployees()) {
            float score = entity.getEmployeeScore(mode, getStartDate(), getEndDate());

            if (score == 0f) {
                continue;
            }

            if (entities[0] == null) {
                entities[0] = entity;
                minScore = score;
                continue;
            }

            if (currentIndex < 2 && score < minScore) {
                currentIndex += 1;
                entities[currentIndex] = entity;
                minScore = score;
                minIndex = currentIndex;
                continue;
            }
            if (currentIndex < 2) {
                currentIndex += 1;
                entities[currentIndex] = entity;
                continue;
            }
            if (score > minScore) {
                entities[minIndex] = entity;
                minScore = score;
                for (int i = 0; i < entities.length; i++) {
                    score = entities[i].getEmployeeScore(mode, getStartDate(), getEndDate());
                    if (score < minScore) {
                        minScore = score;
                        minIndex = i;
                    }
                }
            }
        }

        this.entities.clear();
        Collections.addAll(this.entities, entities);

        for (int i = 0; i < 3 - entities.length; i++) {
            this.entities.add(null);
        }

        adapter = new TopEmployeesViewAdapter(getContext(), this.entities, mode, getStartDate(), getEndDate());
        topEmployees.setAdapter(adapter);
    }

    private void loadBarChart() {
        List<BarEntry> attrition = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setDrawGridLines(false);

        int i = 0;

        if (mode == MODE_ATTRITION) {
            for (Entity entity : Entity.getEmployees()) {
                labels.add(entity.getName());
                float score = (float) entity.getEmployeeScore(mode, getStartDate(), getEndDate());
                if (score == 0f) {
                    score = 0.5f;
                }
                attrition.add(new BarEntry(i + 0.5f, score * 100f));
                i += 1;
            }
        } else {
            for (Entity entity : Entity.getEmployees()) {
                labels.add(entity.getName());
                List<Attendance> attendances = Attendance.getAttendances(Attendance.EMPLOYEE, entity.key, getStartDate(), getEndDate(), Attendance.PRESENT);
                attrition.add(new BarEntry(i + 0.5f, attendances.size()));
                i += 1;
            }
        }

//        for (int i = 0; i < Entity.getModels().size(); i++) {
//            Entity entity = Entity.getModels().get(i);
//            labels.add(entity.getName());
//            attrition.add(new BarEntry(i + 0.5f, (float) (entity.getAttritionScore() * 100)));
//        }
//
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        BarDataSet _attrition;

        if (mode == MODE_ATTRITION) {
            _attrition = new BarDataSet(attrition, "Attrition");
        } else {
            _attrition = new BarDataSet(attrition, "Work Hours");
        }

        _attrition.setValueTextSize(12f);
        _attrition.setColors(ColorTemplate.MATERIAL_COLORS);

        BarData barData = new BarData(_attrition);

        barChart.setData(barData);
        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);

        barData.setBarWidth(0.15f);
        xAxis.setAxisMinimum(0);
        barChart.getAxisLeft().setAxisMaximum(100f);
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.invalidate();
        barChart.animateXY(2000, 3000);

    }


    private void loadBubbleChart() {
        ArrayList<BubbleEntry> bubbleEntries = new ArrayList<>();
        bubbleEntries.add(new BubbleEntry(0, 1, 0.21f));
        bubbleEntries.add(new BubbleEntry(1, 2, 0.12f));
        bubbleEntries.add(new BubbleEntry(2, 3, 0.20f));
        bubbleEntries.add(new BubbleEntry(2, 4, 0.52f));
        bubbleEntries.add(new BubbleEntry(3, 5, 0.29f));
        bubbleEntries.add(new BubbleEntry(4, 6, 0.62f));
        BubbleDataSet bubbleDataSet = new BubbleDataSet(bubbleEntries, "");
        BubbleData bubbleData = new BubbleData(bubbleDataSet);
        bubbleChart.setData(bubbleData);
        bubbleChart.getXAxis().setDrawGridLines(false);
        bubbleDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        bubbleDataSet.setValueTextColor(Color.BLACK);
        bubbleDataSet.setValueTextSize(18f);
    }
}