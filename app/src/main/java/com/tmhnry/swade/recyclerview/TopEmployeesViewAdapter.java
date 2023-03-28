
package com.tmhnry.swade.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.tmhnry.swade.R;
import com.tmhnry.swade.model.Attendance;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.model.Transaction;

import java.util.Date;
import java.util.List;

public class TopEmployeesViewAdapter extends RecyclerView.Adapter<TopEmployeesViewAdapter.ViewHolder> {
    private static final int HEADER = 0;
    private static final int BODY = 1;
    private static final int FOOTER = 2;
    int mode;
    Date start;
    Date end;
    Context context;
    List<Entity> entities;

    public TopEmployeesViewAdapter(Context context, List<Entity> entities, int mode, Date start, Date end) {
        this.start = start;
        this.end = end;
        this.entities = entities;
        this.mode = mode;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        view = inflater.inflate(R.layout.view_top_employee, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (holder.viewType) {
            case HEADER:
                break;
            case FOOTER:
                holder.separator.setVisibility(View.GONE);
                break;
            default:
                int absPos = position - 1;
                Entity entity = entities.get(absPos);
                if (entity == null) {
                    holder.name.setText("N/A");
                    holder.profile.setVisibility(View.INVISIBLE);
                    holder.served.setText("N/A");
                    holder.checkBox.setEnabled(false);
                    holder.profit.setVisibility(View.GONE);
                    holder.revenue.setVisibility(View.GONE);
                    holder.score.setVisibility(View.GONE);
                    holder.average.setVisibility(View.GONE);
                    holder.checkBox.setVisibility(View.VISIBLE);
                    return;
                }
                List<Transaction> transactions = Transaction.getTransactions(Transaction.EMPLOYEE, entity.key, Transaction.SELLER, start, end);
                List<Attendance> attendances = Attendance.getAttendances(Attendance.EMPLOYEE, entity.key, start, end, Attendance.PRESENT);
                float revenue = 0f;
                float profit = 0f;
                for (Transaction transaction : transactions) {
                    revenue += transaction.value;
                    profit += transaction.value - transaction.costValue;
                }

                if (mode == Entity.MODE_WORK_HOURS) {
                    holder.profile.setVisibility(View.VISIBLE);
                    holder.name.setText(entity.getName());
                    holder.score.setVisibility(View.GONE);
                    holder.profit.setVisibility(View.GONE);
                    holder.revenue.setVisibility(View.GONE);
                    holder.checkBox.setVisibility(View.GONE);
                    holder.served.setText("Work Hours: " + attendances.size() * 8);
                    holder.average.setText("Average this week: " + String.format("%.2f", attendances.size() * 8f / (7)) + " hour(s) / day");
                } else {
                    holder.revenue.setVisibility(View.VISIBLE);
                    holder.score.setVisibility(View.VISIBLE);
                    holder.average.setVisibility(View.VISIBLE);
                    holder.revenue.setText("Revenue: \u20b1" + String.format("%.2f", revenue));
                    holder.profit.setText("Profit: \u20b1" + String.format("%.2f", profit));
                    holder.score.setText("% Score: " + String.format("%.0f", entity.getEmployeeScore(mode, start, end) * 100f));
                    float average;
                    if (transactions.size() == 0) {
                        average = 0f;
                    } else {
                        average = revenue / transactions.size();
                    }
                    holder.average.setText("Average Per Customer: \u20b1" + String.format("%.2f", average));
                    holder.profile.setVisibility(View.VISIBLE);
                    holder.separator.setVisibility(View.VISIBLE);
                    holder.name.setText(entity.getName());
                    holder.served.setText("Customer: " + transactions.size());
                    holder.checkBox.setVisibility(View.GONE);
                }
//                if (entity.monitor) {
//                    holder.monitor.setText("TRACKED");
//                    holder.monitor.setTextColor(Color.GRAY);
//                }
                if (entity.gender == Entity.Gender.MALE) {
                    holder.profile.setBackgroundResource(R.drawable.icons8_male_user_100);
                } else {
                    holder.profile.setBackgroundResource(R.drawable.icons8_female_profile_100);
                }
        }
    }

    @Override
    public int getItemCount() {
//        Return entities.size() if no header and footer
        return entities.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        }
        if (position == getItemCount() - 1) {
            return FOOTER;
        }
        return BODY;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int viewType;
        ImageView profile;
        View itemView, separator;
        CheckBox checkBox;
        AppCompatImageButton addStock, addHour;
        TextView name, served, joinDate, monitor, revenue, profit, average, score;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            this.itemView = itemView;
            checkBox = itemView.findViewById(R.id.checkbox_delete);
            name = itemView.findViewById(R.id.txt_name);
            served = itemView.findViewById(R.id.customers_served);
            monitor = itemView.findViewById(R.id.txt_monitor);
            profile = itemView.findViewById(R.id.img_profile);
            separator = itemView.findViewById(R.id.separator);
            addStock = itemView.findViewById(R.id.btn_add_stock);
            addHour = itemView.findViewById(R.id.btn_add_hour);
            revenue = itemView.findViewById(R.id.revenue_generated);
            profit = itemView.findViewById(R.id.profit_generated);
            average = itemView.findViewById(R.id.revenue_per_customer);
            score = itemView.findViewById(R.id.score);
            switch (viewType) {
                case HEADER:
                    itemView.findViewById(R.id.header_emp).setVisibility(View.VISIBLE);
                    itemView.findViewById(R.id.body_emp).setVisibility(View.GONE);
                    itemView.findViewById(R.id.footer_emp).setVisibility(View.GONE);
                    break;
                case FOOTER:
                    itemView.findViewById(R.id.footer_emp).setVisibility(View.VISIBLE);
                    itemView.findViewById(R.id.body_emp).setVisibility(View.GONE);
                    itemView.findViewById(R.id.header_emp).setVisibility(View.GONE);
                    break;
                default:
                    itemView.findViewById(R.id.body_emp).setVisibility(View.VISIBLE);
                    itemView.findViewById(R.id.header_emp).setVisibility(View.GONE);
                    itemView.findViewById(R.id.footer_emp).setVisibility(View.GONE);
            }
        }
    }
}

