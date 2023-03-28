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

import com.tmhnry.swade.fragment.WorkplaceFragment;
import com.tmhnry.swade.R;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.model.Transaction;

import java.util.List;

public class EmployeesViewAdapter extends RecyclerView.Adapter<EmployeesViewAdapter.ViewHolder> {
    private static final int HEADER = 0;
    private static final int BODY = 1;
    private static final int FOOTER = 2;
    EmployeeClickListener listener;
    AddStockClickListener addStockListener;
    AddHourClickListener addHourListener;
    CheckboxClickListener deleteListener;
    WorkplaceFragment.Mode mode;
    Context context;
    List<Entity> entities;

    public EmployeesViewAdapter(Context context,
                                CheckboxClickListener deleteListener,
                                List<Entity> entities, WorkplaceFragment.Mode mode) {
        try {
            listener = (EmployeeClickListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        try {
            addStockListener = (AddStockClickListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        try {
            addHourListener = (AddHourClickListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        this.deleteListener = deleteListener;
        this.mode = mode;
        this.entities = entities;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        view = inflater.inflate(R.layout.view_employee, parent, false);
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
                List<Transaction> transactions = Transaction.getTransactions(Transaction.EMPLOYEE, entity.key, Transaction.SELLER);
                holder.separator.setVisibility(View.VISIBLE);
                holder.name.setText(entity.getName());
                holder.served.setText(transactions.size() + " served customers");
//                if (entity.monitor) {
//                    holder.monitor.setText("TRACKED");
//                    holder.monitor.setTextColor(Color.GRAY);
//                }
                if (entity.gender == Entity.Gender.MALE) {
                    holder.profile.setBackgroundResource(R.drawable.icons8_male_user_100);
                } else {
                    holder.profile.setBackgroundResource(R.drawable.icons8_female_profile_100);
                }
                switch (mode) {
                    case DELETE:
                        holder.checkBox.setVisibility(View.VISIBLE);
                        break;
                    default:
                        holder.checkBox.setVisibility(View.INVISIBLE);
                }
                holder.itemView.setOnClickListener(v -> {
                    listener.onEmployeeClicked(entity);
                });
                holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    deleteListener.onCheckboxChanged(absPos, isChecked);
                });
                holder.addHour.setOnClickListener(v -> {
                    addHourListener.onAddHourBtnClicked(entity);
                });
                holder.addStock.setOnClickListener(v -> {
                    addStockListener.onAddStockBtnClicked(entity);
                });
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
        TextView name, served, joinDate, monitor;

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

    public interface EmployeeClickListener {
        void onEmployeeClicked(Entity entity);
    }

    public interface CheckboxClickListener {
        void onCheckboxChanged(int position, boolean isChecked);
    }

    public interface AddStockClickListener {
        void onAddStockBtnClicked(Entity entity);
    }

    public interface AddHourClickListener {
        void onAddHourBtnClicked(Entity entity);
    }
}
