package com.tmhnry.swade.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tmhnry.swade.CompanySetup;
import com.tmhnry.swade.MainActivity;
import com.tmhnry.swade.R;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.singleton.Company;
import com.tmhnry.swade.singleton.User;
import com.tmhnry.swade.databinding.FragmentDepartmentSetupBinding;
import com.tmhnry.swade.gridview.DepartmentsViewAdapter;
import com.tmhnry.swade.model.Department;
import com.tmhnry.swade.model.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pl.droidsonroids.gif.GifImageButton;


public class DepartmentSetupFragment extends Fragment {
    public static final String FRAGMENT_ID = "com.example.dailyfitness.departmentsetupfragment";
    FragmentDepartmentSetupBinding binding;
    Model.FirebaseQueryListener queryListener;
    DepartmentsViewAdapter.CheckboxClickListener listener;

    Dialog addDepartmentD;
    Dialog addEmployeeD;
    Dialog schDepartmentD;

    List<Department> _departments;
    List<Entity> _entities;

    List<Department> _departmentsCopy;
    List<Entity> _employeesCopy;
    List<Integer> temp;

    DepartmentsViewAdapter adapter;

    Mode mode;


    DepartmentsViewAdapter.CheckboxClickListener deleteL = (position, isChecked) -> {
        if (isChecked) {
            temp.add(position);
            return;
        }
        temp.remove(Integer.valueOf(position));
    };

    public DepartmentSetupFragment() {
    }

    public static DepartmentSetupFragment Builder() {
        DepartmentSetupFragment fragment = new DepartmentSetupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        queryListener = (Model.FirebaseQueryListener) context;
        _departments = new ArrayList<>();
        _departmentsCopy = new ArrayList<>();
        _entities = new ArrayList<>();
        _employeesCopy = new ArrayList<>();
        temp = new ArrayList<>();
        mode = Mode.NORMAL;
    }

    private int getStepperCount() {
        return ((CompanySetup) getActivity()).getStepperCount();
    }

    public void setPageLoaderVisibility(int visibility) {
//        binding.pageLoader.setVisibility(visibility);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    private void updateDeptsGV(boolean init) {
        if (init) {
            adapter = new DepartmentsViewAdapter(getContext(), deleteL, _departmentsCopy, mode);
            binding.gridView.setAdapter(adapter);
            updateDepartments("");
            return;
        }
        updateDepartments("");
    }

    public void setAdapter() {
        Context context = getContext();
        setAddDepartmentDialog(context);
        setAddEmployeeDialog(context);
        setSearchDepartmentDialog(context);
        for (Department department : Department.getModels().values()) {
            _departments.add(department);
        }
        updateDeptsGV(true);
        updateGuider();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDepartmentSetupBinding.inflate(inflater, container, false);
        Department.retrieve();
//        Entity.retrieveDBData(fbQueryListener);
        binding.wrapperConfirm.setVisibility(View.GONE);

        binding.wrapperConfirm.setOnClickListener(v -> {
            v.setVisibility(View.GONE);
            List<Department> _departmentsToDelete = new ArrayList<>();
            for (int pos : temp) {
                _departmentsToDelete.add(_departments.get(pos));
            }
            _departments.removeAll(_departmentsToDelete);
            temp.clear();
            clearInputs();
            mode = Mode.NORMAL;
            updateDeptsGV(true);
            updateGuider();
        });

        binding.wrapperGuider.setOnClickListener(v -> {
            updateGuider();
        });

        binding.imgAdd
                .setOnClickListener(v -> {
                    if (mode == Mode.DELETE) {
                        Toast.makeText(getContext(), "Invalid: Deletion is in Progress", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    addDepartmentD.show();
                });

        binding.imgSearch
                .setOnClickListener(v -> {
                    schDepartmentD.show();
                });

        binding.imgDelete
                .setOnClickListener(v -> {
                    if (Department.getModels().size() == 0) {
                        return;
                    }
                    if (mode == Mode.NORMAL) {
                        mode = Mode.DELETE;
                        binding.wrapperConfirm.setVisibility(View.VISIBLE);
                    } else {
                        mode = Mode.NORMAL;
                        binding.wrapperConfirm.setVisibility(View.GONE);
                    }
                    temp.clear();
                    updateDeptsGV(true);
                });

        return binding.getRoot();
    }

    boolean initEmpDialog = true;

    private void updateGuider() {
        int pos = getStepperCount();
        if (pos == 1 && _departments.size() < 3) {
            binding.wrapperGuider.setOnClickListener(v -> {
                Toast.makeText(getContext(), "You need at least 3 departments.", Toast.LENGTH_SHORT).show();
            });
            binding.txtGuider.setText(_departments.size() + "/3");
            return;
        }
        if (pos == 1) {
            binding.wrapperGuider.setOnClickListener(v -> {
                ((CompanySetup) getActivity()).incrementStepper();
//                Department.updateDB(getContext(), _departments);
                updateGuider();
            });
            binding.givGuider.setBackgroundResource(R.drawable.checkbox);
            binding.txtGuider.setText("Next");
            return;
        }
        if (pos == 2 && Entity.getModels().size() < 3) {
            binding.imgDelete.setVisibility(View.INVISIBLE);
            if (initEmpDialog) {
                setAddEmployeeDialog(getContext());
                initEmpDialog = false;
            }
            binding.imgAdd
                    .setOnClickListener(v -> {
                        addEmployeeD.show();
                    });

            binding.wrapperGuider.setOnClickListener(v -> {
                Toast.makeText(getContext(), "You need at least 3 entities.", Toast.LENGTH_SHORT).show();
            });

            binding.givGuider.setBackgroundResource(R.drawable.icons8_reddit);
            binding.txtGuider.setText(_entities.size() + "/3");
            return;
        }

        if (pos == 2) {
            binding.wrapperGuider.setOnClickListener(v -> {
                ((CompanySetup) getActivity()).incrementStepper();
                updateGuider();
            });
            binding.givGuider.setBackgroundResource(R.drawable.checkbox);
            binding.txtGuider.setText("Next");
            return;
        }

        if (pos == 3) {
            Company.update(getContext(), Company.SETUP_POSITION, 2);
            User.update(getContext(), User.SETUP_COMPLETE, true);
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }

    private void setSearchDepartmentDialog(Context context) {
        schDepartmentD = new Dialog(context);
        schDepartmentD.setContentView(R.layout.dialog_search_department);
        GifImageButton refGib = schDepartmentD.findViewById(R.id.gib_refresh);
        AppCompatButton posBtn = schDepartmentD.findViewById(R.id.btn_search);
        AppCompatButton negBtn = schDepartmentD.findViewById(R.id.btn_cancel);

        refGib.setOnClickListener(v -> {
            EditText nameInput = schDepartmentD.findViewById(R.id.input_name);
            nameInput.getText().clear();
            updateDepartments("");
            schDepartmentD.dismiss();
        });

        posBtn.setOnClickListener(v -> {
            EditText nameInput = schDepartmentD.findViewById(R.id.input_name);
            updateDepartments(nameInput.getText().toString().trim());
            // Use dismiss as no cancelListener is listening to the dialog
            schDepartmentD.dismiss();
        });

        negBtn.setOnClickListener(v -> {
            schDepartmentD.dismiss();
        });
    }


    private void setAddEmployeeDialog(Context context) {
        addEmployeeD = new Dialog(context);
        addEmployeeD.setContentView(R.layout.dialog_add_employee);

        AppCompatButton posBtn = addEmployeeD.findViewById(R.id.btn_add);
        AppCompatButton negBtn = addEmployeeD.findViewById(R.id.btn_cancel);

        addEmployeeD.setOnCancelListener(dialog -> {
            EditText fn = addEmployeeD.findViewById(R.id.edit_fn);
            EditText ln = addEmployeeD.findViewById(R.id.edit_ln);
            EditText dist = addEmployeeD.findViewById(R.id.edit_distance);
            EditText inc = addEmployeeD.findViewById(R.id.edit_monthly_income);
            EditText age = addEmployeeD.findViewById(R.id.edit_age);
            fn.getText().clear();
            ln.getText().clear();
            dist.getText().clear();
            inc.getText().clear();
            age.getText().clear();
        });

        posBtn.setOnClickListener(v -> {
            addEmployee();
            updateGuider();
            updateDepartments("");
            addEmployeeD.cancel();
        });

        RadioGroup departmentRG = (RadioGroup) addEmployeeD.findViewById(R.id.rg_department);
        for (Department department : Department.getModels().values()) {
            RadioButton radioB = new RadioButton(getContext());
            radioB.setId(department.getId());
//            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            radioB.setLayoutParams(params);
            radioB.setOnClickListener(v -> {
                departmentRG.clearCheck();
                departmentRG.check(department.getId());
            });

            radioB.setText(department.getName());
            departmentRG.addView(radioB);
        }

        negBtn.setOnClickListener(v -> {
            addEmployeeD.cancel();
        });
    }


    private void setAddDepartmentDialog(Context context) {
        addDepartmentD = new Dialog(context);
        addDepartmentD.setContentView(R.layout.dialog_add_department);
        AppCompatButton posBtn = addDepartmentD.findViewById(R.id.btn_add);
        AppCompatButton negBtn = addDepartmentD.findViewById(R.id.btn_cancel);
        addDepartmentD.setOnCancelListener(dialog -> {
            clearInputs();
        });
        posBtn.setOnClickListener(v -> {
            Map<String, Object> data = validInputs();
            if (data == null) {
                return;
            }
            _departments.add(Department.Model(data));
            updateDeptsGV(false);
            updateGuider();
            addDepartmentD.cancel();
        });

        negBtn.setOnClickListener(v -> {
            addDepartmentD.cancel();
        });
    }

    private void addEmployee() {
        EditText fn = addEmployeeD.findViewById(R.id.edit_fn);
        EditText ln = addEmployeeD.findViewById(R.id.edit_ln);
        EditText dist = addEmployeeD.findViewById(R.id.edit_distance);
        EditText inc = addEmployeeD.findViewById(R.id.edit_monthly_income);
        EditText ag = addEmployeeD.findViewById(R.id.edit_age);

        String name = fn.getText().toString() + " " + ln.getText().toString();
        String distS = dist.getText().toString();

        if (distS.isEmpty()) {
            Toast.makeText(getContext(), "Distance cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Integer distanceFromHome = Integer.parseInt(distS);

        String incS = inc.getText().toString();
        if (incS.isEmpty()) {
            Toast.makeText(getContext(), "Invalid income provided", Toast.LENGTH_SHORT).show();
            return;
        }
        Double income = Double.parseDouble(incS);

        String ageS = ag.getText().toString();
        if (ageS.isEmpty()) {
            Toast.makeText(getContext(), "No age given", Toast.LENGTH_SHORT).show();
            return;
        }
        Integer age = Integer.parseInt(ageS);

        RadioGroup departmentRG = (RadioGroup) addEmployeeD.findViewById(R.id.rg_department);
        int chosenDept = departmentRG.getCheckedRadioButtonId();
        Department dept = null;
        for (Department department : Department.getModels().values()) {
            if (department.getId() == chosenDept) {
                dept = department;
                break;
            }
        }

        dept.incrementSize();
        Entity.MaritalStatus marital = null;
        RadioGroup maritalRG = (RadioGroup) addEmployeeD.findViewById(R.id.rg_marital);
        int chosenMS = maritalRG.getCheckedRadioButtonId();
        if (chosenMS == R.id.rb_single) {
            marital = Entity.MaritalStatus.SINGLE;
        } else if (chosenMS == R.id.rb_married) {
            marital = Entity.MaritalStatus.MARRIED;
        } else {
            marital = Entity.MaritalStatus.DIVORCED;
        }
        Map<String, Object> employeeData = new HashMap<>();
        employeeData.put(Entity.NAME, name);
        employeeData.put(Entity.MONTHLY_INCOME, income);
        employeeData.put(Entity.AGE, age);
        employeeData.put(Entity.MONITOR, true);
        employeeData.put(Entity.DISTANCE_FROM_HOME, distanceFromHome);
        employeeData.put(Entity.DEPARTMENT, dept);
        employeeData.put(Entity.MARITAL_STATUS, marital);
        employeeData.putIfAbsent(Model.ID, Entity.getRandomPublicId(getContext()));
    }

    private void clearInputs() {
        EditText nameInput = addDepartmentD.findViewById(R.id.input_name);
        EditText sizeInput = addDepartmentD.findViewById(R.id.input_cap);
        EditText descInput = addDepartmentD.findViewById(R.id.input_desc);
        nameInput.getText().clear();
        sizeInput.getText().clear();
        descInput.getText().clear();
    }

    private Map<String, Object> validInputs() {
        Map<String, Object> data = null;
        EditText nameInput = addDepartmentD.findViewById(R.id.input_name);
        EditText capInput = addDepartmentD.findViewById(R.id.input_cap);
        EditText descInput = addDepartmentD.findViewById(R.id.input_desc);
        String capS = capInput.getText().toString();
        if (capS.isEmpty()) {
            Toast.makeText(getContext(), "Invalid capacity provided", Toast.LENGTH_SHORT).show();
            return data;
        }
        Integer cap = Integer.parseInt(capS);
        if (cap < 10 || cap > 200) {
            Toast.makeText(getContext(), "Invalid capacity provided", Toast.LENGTH_SHORT).show();
            return data;
        }
        String name = "";
        name = nameInput.getText().toString().trim();
        if (name.length() < 4 || name.length() > 32) {
            Toast.makeText(getContext(), "A succinct name is needed Consider using abbreviations", Toast.LENGTH_SHORT).show();
            return data;
        }
        String[] nameA = name.split(" ");
        StringBuilder sbName = new StringBuilder();
        for (String nameE : nameA) {
            sbName.append(nameE.substring(0, 1).toUpperCase(Locale.ROOT));
            sbName.append(nameE.substring(1).toLowerCase(Locale.ROOT));
            sbName.append(" ");
        }

        String desc = "";
        desc = descInput.getText().toString().trim();
        if (desc.length() < 10) {
            Toast.makeText(getContext(), "Please provide a more specific description", Toast.LENGTH_SHORT).show();
            return data;
        }
        data = new HashMap<>();
        data.putIfAbsent(Department.NAME, sbName.toString().trim());
        data.putIfAbsent(Department.DESCRIPTION, desc);
        data.putIfAbsent(Department.CAPACITY, cap);
        data.putIfAbsent(Department.SIZE, 0);
        data.putIfAbsent(Model.ID, Department.getRandomPublicId(getContext()));
        return data;
    }

    private void updateDepartments(String pattern) {
        _departmentsCopy.clear();
        for (Department department : _departments) {
            if (pattern.isEmpty()) {
                _departmentsCopy.add(department);
                continue;
            }
            if (department.getName().contains(pattern)) {
                _departmentsCopy.add(department);
            }
        }
        binding.gridView.invalidateViews();
    }


    public enum Mode {
        DELETE,
        EDIT,
        NORMAL
    }
}