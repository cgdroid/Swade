package com.tmhnry.swade.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmhnry.swade.NotificationsActivity;
import com.tmhnry.swade.R;
import com.tmhnry.swade.databinding.FragmentWorkplaceBinding;
import com.tmhnry.swade.databinding.SearchTextfieldBinding;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.recyclerview.EmployeesViewAdapter;
import com.tmhnry.swade.singleton.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class WorkplaceFragment extends Fragment {
    public static final String FRAGMENT_ID = "com.example.dailyfitness.employeesfragment";

    FragmentWorkplaceBinding binding;
    Mode mode;
    List<Entity> entities;
    List<Integer> temp;
    EmployeesViewAdapter adapter;
    SearchTextfieldBinding textField;
    View confirmDelete;
    ImageView trashBtn;
    RecyclerView recyclerView;
    EmployeesViewAdapter.CheckboxClickListener listener;
    Entity.FirebaseQueryListener fbQueryListener;
    EmployeeQueryListener employeeQueryListener;
    Dialog alertDialog;
    TextView title;
    TextView message;
    TextView okayBtn;
    TextView cancelBtn;
    ImageView $notif;

    public interface EmployeeQueryListener {
        void onAddEmployee(Map<String, Object> data);

        void onDeleteEmployee(List<Integer> temp);
    }

    public static WorkplaceFragment Builder() {
        WorkplaceFragment fragment = new WorkplaceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        entities = new ArrayList<>();
        for (Entity entity : Entity.getModels().values()) {
            if (entity.userKey.equals(User.getKey(context))) {
                continue;
            }
            entities.add(entity);
        }
        fbQueryListener = (Entity.FirebaseQueryListener) context;
        employeeQueryListener = (EmployeeQueryListener) context;
        temp = new ArrayList<>();
        mode = Mode.NORMAL;
        initAlertDialog(context);
        listener = new EmployeesViewAdapter.CheckboxClickListener() {
            @Override
            public void onCheckboxChanged(int position, boolean isChecked) {
                if (isChecked) {
                    temp.add(position);
                    return;
                }
                temp.remove(Integer.valueOf(position));
            }
        };
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
        // Inflate the layout for this fragment
        binding = FragmentWorkplaceBinding.inflate(inflater, container, false);
        trashBtn = binding.imgDelete;
        confirmDelete = binding.wrapperConfirm;
        recyclerView = binding.recyclerView;
        textField = binding.searchTextField;
        $notif = binding.notif;

        String position = (String) User.retrieve(getContext(), User.POSITION);
        if (position.equals(Entity.EMPLOYEE)) {
            $notif.setVisibility(View.GONE);
        }

        $notif.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), NotificationsActivity.class));
        });
//
//        if (entities.isEmpty()) {
//            binding.empty.setVisibility(View.VISIBLE);
//        } else {
//            binding.empty.setVisibility(View.GONE);
//        }

        textField.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                entities.clear();
                for (Entity entity : Entity.getModels().values()) {
                    if(entity.userKey.equals(User.getKey(getContext()))){
                        continue;
                    }
                    if (s.length() == 0) {
                        entities.add(entity);
                        continue;
                    }
                    if (entity.getName().contains(s)) {
                        entities.add(entity);
                    }
                }
                updateRecyclerView(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmDelete.setVisibility(View.GONE);
        adapter = new EmployeesViewAdapter(getContext(), listener, entities, mode);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        confirmDelete.setOnClickListener(v -> {
            showDialog();
        });

        trashBtn.setOnClickListener(v -> {
            if (Entity.getModels().size() == 0) {
                return;
            }
            if (mode == Mode.NORMAL) {
                mode = Mode.DELETE;
                confirmDelete.setVisibility(View.VISIBLE);
            } else {
                mode = Mode.NORMAL;
                confirmDelete.setVisibility(View.GONE);
            }
            updateRecyclerView(true);
            temp.clear();
        });

        return binding.getRoot();
    }

    private void initAlertDialog(Context context) {
        alertDialog = new Dialog(context);
        alertDialog.setContentView(R.layout.dialog_alert_notification);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        title = alertDialog.findViewById(R.id.dialog_title);
        message = alertDialog.findViewById(R.id.txt_alert_message);
        okayBtn = alertDialog.findViewById(R.id.txt_positive);
        cancelBtn = alertDialog.findViewById(R.id.txt_negative);
    }

    private void showDialog() {
        if (temp.size() == 0) {
            return;
        }
        title.setText("Confirm Delete");
        message.setText("Deleting will remove the employee from the database. Do you wish to continue?");
        okayBtn.setText("Okay");
        alertDialog.setOnCancelListener(dialog -> {
            confirmDelete.setVisibility(View.GONE);
            temp.clear();
            mode = Mode.NORMAL;
            updateRecyclerView(true);
        });
        okayBtn.setOnClickListener(v -> {
            employeeQueryListener.onDeleteEmployee(temp);
            alertDialog.cancel();
        });
        cancelBtn.setText("Cancel");
        cancelBtn.setOnClickListener(v -> {
            alertDialog.cancel();
        });
        alertDialog.show();
    }

    private void updateRecyclerView(boolean init) {
        if (init) {
            adapter = new EmployeesViewAdapter(getContext(), listener, entities, mode);
            recyclerView.setAdapter(adapter);
            return;
        }
        adapter.notifyDataSetChanged();
    }

    public void updateEmployeesList() {
        entities.clear();
        for(Entity entity : Entity.getModels().values()){
            if(entity.userKey.equals(User.getKey(getContext()))){
                continue;
            }
            entities.add(entity);
        }
        if (entities.isEmpty()) {
            binding.empty.setVisibility(View.VISIBLE);
        } else {
            binding.empty.setVisibility(View.GONE);
        }
        updateRecyclerView(false);
    }

    public enum Mode {
        DELETE,
        EDIT,
        NORMAL
    }
}