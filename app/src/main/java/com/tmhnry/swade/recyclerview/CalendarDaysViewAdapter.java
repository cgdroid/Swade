package com.tmhnry.swade.recyclerview;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmhnry.swade.R;
import com.tmhnry.swade.model.Attendance;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarDaysViewAdapter extends RecyclerView.Adapter<CalendarDaysViewAdapter.ViewHolder> {
    private final ArrayList<String> daysInMonth;
    private final ArrayList<LocalDate> datesInMonth;
    private final OnItemListener onItemListener;

    public CalendarDaysViewAdapter(ArrayList<String> daysInMonth,
                                   ArrayList<LocalDate> datesInMonth,
                                   OnItemListener onItemListener) {
        this.datesInMonth = datesInMonth;
        this.daysInMonth = daysInMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarDaysViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_calendar_day, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarDaysViewAdapter.ViewHolder holder, int position) {
        String day = daysInMonth.get(position);
        if (day.isEmpty()) {
            holder.status.setVisibility(View.INVISIBLE);
            holder.background.setVisibility(View.INVISIBLE);
        }

        holder.day.setText(daysInMonth.get(position));
        LocalDate date = datesInMonth.get(position);

        if (date == null) {
            return;
        }


        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.YEAR, date.getYear());
//        Subtracted by 1 because January in LocalDate has value of 1 while in Calendar, the value is 0
        calendar.set(Calendar.MONTH, date.getMonthValue() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, date.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date previous = calendar.getTime();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date next = calendar.getTime();
        Attendance _attendance = null;
        for (Attendance attendance : Attendance.getModels().values()) {
            if (attendance.date.after(previous) && attendance.date.before(next)) {
                _attendance = attendance;
                break;
            }
        }
        if (_attendance == null) {
            holder.background.setVisibility(View.INVISIBLE);
            holder.status.setVisibility(View.INVISIBLE);
            return;
        }
        if (_attendance.status.equals(Attendance.PRESENT)) {
            holder.status.setText("P");
            holder.background.getBackground().setTint(Color.parseColor("#d9f4df"));
            holder.status.setTextColor(Color.parseColor("#3db33d"));
        } else if (_attendance.status.equals(Attendance.EXCUSED)) {
            holder.status.setText("E");
            holder.background.getBackground().setTint(Color.parseColor("#e2edfe"));
            holder.status.setTextColor(Color.parseColor("#3786fb"));
        } else {
            holder.status.setText("A");
            holder.background.getBackground().setTint(Color.parseColor("#fed3d3"));
            holder.status.setTextColor(Color.parseColor("#fb373f"));
        }

    }

    @Override
    public int getItemCount() {
        return daysInMonth.size();
    }

    public interface OnItemListener {
        void onItemClick(int position, String dayText);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, status;
        View view, background;

        public ViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            status = view.findViewById(R.id.status_text);
            background = view.findViewById(R.id.status_background);
            day = view.findViewById(R.id.day);
        }
    }
}