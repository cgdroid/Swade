package com.tmhnry.swade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tmhnry.swade.databinding.ActivityEmployeeSetupBinding;
import com.tmhnry.swade.model.Department;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.model.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class EmployeeSetup extends AppCompatActivity
        implements Model.FirebaseQueryListener {
    ActivityEmployeeSetupBinding binding;
    Map<String, Boolean> querySuccessful;
    Handler handler;
    AppCompatButton addBtn;
    RadioGroup departmentRG;
    RadioGroup maritalRG;
    RadioGroup genderRG;
    EditText stocksSold;
    EditText firstName;
    EditText lastName;
    EditText age;
    EditText income;
    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        binding = ActivityEmployeeSetupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        departmentRG = binding.rgDepartment;
        maritalRG = binding.rgMarital;
        genderRG = binding.rgGender;
        addBtn = binding.btnAdd;
        firstName = binding.inputFn;
        lastName = binding.inputLn;
        age = binding.inputAge;
        income = binding.inputMonthlyInc;
        stocksSold = binding.inputStocksSold;
        querySuccessful = new HashMap<>();

        addBtn.setOnClickListener(v -> {
            addEmployee();
        });

        loadingDialog = new Dialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(R.layout.dialog_loading_indicator);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT;
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(wrapContent, wrapContent);
        params.setMargins(0, 25, 0, 25);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/segoe_ui_bold_italic.ttf");
        for (Department department : Department.getModels().values()) {
            RadioButton radioB = new RadioButton(this);
            radioB.setId(department.getId());
            radioB.setLayoutParams(params);
            radioB.setTypeface(face);
            radioB.setOnClickListener(v -> {
                departmentRG.clearCheck();
                departmentRG.check(department.getId());
            });

            radioB.setText(department.getName());
            departmentRG.addView(radioB);
        }
    }

    private void addEmployee() {
        String name = firstName.getText().toString().trim() +
                " " +
                lastName.getText().toString().trim();

        String _age = age.getText().toString().trim();
        String _income = income.getText().toString().trim();
        String _stocksSold = stocksSold.getText().toString().trim();

        if (_income.isEmpty()) {
            Toast.makeText(this, "Invalid income provided", Toast.LENGTH_SHORT).show();
            return;
        }
        Double income = Double.parseDouble(_income);
        if (income < 5000D || income > 200000D) {
            Toast.makeText(this, "Income is not in range", Toast.LENGTH_SHORT).show();
            return;
        }

        if (_age.isEmpty()) {
            Toast.makeText(this, "No age given", Toast.LENGTH_SHORT).show();
            return;
        }
        Integer age = Integer.parseInt(_age);
        if (age < 15 || age > 60) {
            Toast.makeText(this, "Age is not in range", Toast.LENGTH_SHORT).show();
            return;
        }

//        if (_stocksSold.isEmpty()) {
//            Toast.makeText(this, "You can provide a default of 0 stocks", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Integer stockSold = Integer.parseInt(_stocksSold);

        int chosenDept = departmentRG.getCheckedRadioButtonId();
        Department _department = null;

        for (Department department : Department.getModels().values()) {
            if (department.getId() == chosenDept) {
                _department = department;
                break;
            }
        }

        Entity.MaritalStatus marital = null;
        int chosenMS = maritalRG.getCheckedRadioButtonId();
        if (chosenMS == R.id.rb_single) {
            marital = Entity.MaritalStatus.SINGLE;
        } else if (chosenMS == R.id.rb_married) {
            marital = Entity.MaritalStatus.MARRIED;
        } else {
            marital = Entity.MaritalStatus.DIVORCED;
        }

        Entity.Gender gender = null;
        int chosenGender = genderRG.getCheckedRadioButtonId();
        if (chosenGender == R.id.rb_male) {
            gender = Entity.Gender.MALE;
        } else {
            gender = Entity.Gender.FEMALE;
        }

        Map<String, Object> employeeData = new HashMap<>();
        employeeData.put(Model.ID, Entity.getRandomPublicId(this));
        employeeData.put(Entity.NAME, name);
        employeeData.put(Entity.MONTHLY_INCOME, income);
        employeeData.put(Entity.AGE, age);
        employeeData.put(Entity.MONITOR, true);
        employeeData.put(Entity.DEPARTMENT, _department);
        employeeData.put(Entity.MARITAL_STATUS, marital);
        employeeData.put(Entity.GENDER, gender);
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        employeeData.put(Entity.JOIN_DATE, calendar.getTime());
        onAddEmployee(employeeData);
    }

    private void onAddEmployee(Map<String, Object> employeeData) {
        List<Entity> entities = new ArrayList<>();
        List<Department> departments = new ArrayList<>();
        Entity entity = Entity.Model(employeeData);
        entity.department.incrementSize();
        departments.add(entity.department);
        entities.add(entity);
        Department.Departments.getInstance().updateCloudDatabase(departments);
        Entity.Entities.getInstance().updateCloudDatabase(entities);
    }

    private boolean allQueriesCompleted() {
        boolean complete = true;
        for (Boolean queryComplete : querySuccessful.values()) {
            if (!queryComplete) {
                complete = false;
                break;
            }
        }
        return complete;
    }

    private void setState() {
        // checks query status every 1 second
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (allQueriesCompleted()) {
                    loadingDialog.cancel();
                    handler.removeCallbacks(this);
                    handler = null;
                    return;
                }
                setState();
            }
        }, 1000);
    }

    @Override
    public void onStartQuery(String name) {
        querySuccessful.put(name, false);
        loadingDialog.show();
    }

    @Override
    public void onSuccessQuery(String name) {
        querySuccessful.put(name, true);
        if (handler != null) {
            return;
        }
        handler = new Handler();
        setState();
    }

    @Override
    public void onFailQuery() {
    }
}