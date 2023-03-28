package com.tmhnry.swade.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tmhnry.swade.R;
import com.tmhnry.swade.model.Attendance;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AttendancesViewAdapter extends RecyclerView.Adapter<AttendancesViewAdapter.ViewHolder> {

    private enum Day {
        SUNDAY(1),
        MONDAY(2),
        TUESDAY(3),
        WEDNESDAY(4),
        THURSDAY(5),
        FRIDAY(6),
        SATURDAY(7);

        int code;

        Day(int code) {
            this.code = code;
        }

        public String getAbbreviation() {
            return this.name().substring(0, 1);
        }
    }

    private static boolean isLeap;
    private final Context context;
    private Calendar calendar;
    private final Day[] units;
    private final Attendance[] attendances;
    private final Date[] dates;
    private int dayOfWeek;
    private final int dayOfMonth;
    private final int month;

    public AttendancesViewAdapter(Context context) {
        this.context = context;
        units = new Day[7];
        attendances = new Attendance[7];
        dates = new Date[7];
        for (int i = 0; i < 7; i++) {
            calendar = Calendar.getInstance(TimeZone.getDefault());
            dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            calendar.add(Calendar.DAY_OF_WEEK, i - dayOfWeek);
            Date date = calendar.getTime();
            dates[i] = date;
            switch (i) {
                case 0:
                    units[0] = Day.SUNDAY;
                    break;
                case 1:
                    units[1] = Day.MONDAY;
                    break;
                case 2:
                    units[2] = Day.TUESDAY;
                    break;
                case 3:
                    units[3] = Day.WEDNESDAY;
                    break;
                case 4:
                    units[4] = Day.THURSDAY;
                    break;
                case 5:
                    units[5] = Day.FRIDAY;
                    break;
                default:
                    units[6] = Day.SATURDAY;
            }
        }

        isLeap = false;
        calendar = Calendar.getInstance(TimeZone.getDefault());
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(context);
        View view = inflate.inflate(R.layout.view_attendance, parent, false);
        return new ViewHolder(view, viewType);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.abbr.setText(units[position].getAbbreviation());
        if (units[position].code == dayOfWeek) {
            Date date = calendar.getTime();
            holder.date.setText(date.toLocaleString());
            holder.edit.setVisibility(View.VISIBLE);
            holder.edit.setOnClickListener(v -> {
            });
//            holder.mDay.setImageResource(R.drawable.ic_baseline_check_circle_24);
//            holder.mDay.setColorFilter(Color.argb(0XFF, 0X00, 0X6a, 0XFF));
//            holder.mDay.setVisibility(View.VISIBLE);
        } else {
            Calendar _calendar = Calendar.getInstance(TimeZone.getDefault());
            _calendar.add(Calendar.DAY_OF_WEEK, units[position].code - dayOfWeek);
            Date date = _calendar.getTime();
            holder.date.setText(date.toLocaleString());
            holder.edit.setVisibility(View.INVISIBLE);
            int d = units[position].code - dayOfWeek;
            int day = dayOfMonth + d;
            if (day < 1) {
                day = maxDays(month - 1) + day;
            }
            if (day > maxDays(month)) {
                day = day - maxDays(month);
            }
//            holder.mNum.setText(String.format("%d", day));
        }
    }

    @Override
    public int getItemCount() {
        return units.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView abbr, date;
        ImageView edit;

        public ViewHolder(@NonNull View view, int viewType) {
            super(view);
            abbr = view.findViewById(R.id.day_abbreviation);
            date = view.findViewById(R.id.attendance_date);
            edit = view.findViewById(R.id.edit);
//            mAbv = view.findViewById(R.id.day_abbreviation);
//            mNum = view.findViewById(R.id.day_number);
//            mSel = view.findViewById(R.id.day_selector);
//            mDay = view.findViewById(R.id.check_day);
        }
    }

    private int maxDays(int month) {
        switch (month) {
            // -1 included because the compiler computes -1 % 12 as -1, case 12 is optional if arg
            // month is modded
            case -1:
            case 12:
            case Calendar.JANUARY:
            case Calendar.MARCH:
            case Calendar.MAY:
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.OCTOBER:
            case Calendar.DECEMBER:
                return 31;
            case Calendar.APRIL:
            case Calendar.JUNE:
            case Calendar.SEPTEMBER:
            case Calendar.NOVEMBER:
                return 30;
            default:
                return isLeap ? 28 : 27;
        }
    }
}
