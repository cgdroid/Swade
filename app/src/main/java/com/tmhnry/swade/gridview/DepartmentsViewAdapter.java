package com.tmhnry.swade.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tmhnry.swade.R;
import com.tmhnry.swade.fragment.DepartmentSetupFragment;
import com.tmhnry.swade.model.Department;

import java.util.List;

public class DepartmentsViewAdapter extends BaseAdapter {
    Context context;
    List<Department> departments;
    LayoutInflater inflater;
    DepartmentSetupFragment.Mode mode;
    CheckboxClickListener listener;

    public DepartmentsViewAdapter(Context context,
                                  CheckboxClickListener listener,
                                  List<Department> departments, DepartmentSetupFragment.Mode mode) {
        this.context = context;
        this.listener = listener;
        this.departments = departments;
        this.mode = mode;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return departments.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_department, null);
        }
        Department dept = departments.get(position);
        TextView deptName = convertView.findViewById(R.id.txt_dept_name);
        deptName.setText(dept.getName());
        TextView deptSize = convertView.findViewById(R.id.txt_dep_size);
        Integer size = dept.getSize();
        deptSize.setText(String.valueOf(size));
        TextView deptCap = convertView.findViewById(R.id.txt_dept_cap);
        Integer cap = dept.getCapacity();
        if (cap == null) {
            deptCap.setText("N/A");
        } else {
            deptCap.setText("/" + cap);
        }
        CheckBox checkBox = convertView.findViewById(R.id.checkbox_delete);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            listener.onCheckboxChanged(position, isChecked);
        });
        if (mode == DepartmentSetupFragment.Mode.DELETE) {
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    public interface CheckboxClickListener {
        void onCheckboxChanged(int position, boolean isChecked);
    }
}
