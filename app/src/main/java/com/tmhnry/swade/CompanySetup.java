package com.tmhnry.swade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.singleton.Company;
import com.tmhnry.swade.databinding.ActivityCompanySetupBinding;
import com.tmhnry.swade.fragment.CreateCompanyFragment;
import com.tmhnry.swade.fragment.DepartmentSetupFragment;
import com.tmhnry.swade.model.Model;
import com.tmhnry.swade.singleton.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CompanySetup extends AppCompatActivity implements
        Company.OnCreateCompanyListener,
        Company.OnJoinCompanyListener,
        Model.FirebaseQueryListener {
    public static final String DATA = "data";
    ActivityCompanySetupBinding binding;
    Map<String, Boolean> querySuccessful;
    Handler handler;
    Dialog alertDialog;
    Dialog loadingDialog;
    String fragmentId;
    String[] stepLabels;
    TextView title, msg, posBtn, negBtn;
    HashMap<String, Object> data;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        binding = ActivityCompanySetupBinding
                .inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        data = (HashMap<String, Object>) getIntent().getSerializableExtra(DATA);
        querySuccessful = new HashMap<>();

        loadingDialog = new Dialog(this);
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(R.layout.dialog_loading_indicator);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog = new Dialog(this);
        alertDialog.setContentView(R.layout.dialog_alert_notification);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        title = alertDialog.findViewById(R.id.dialog_title);
        msg = alertDialog.findViewById(R.id.txt_alert_message);
        posBtn = alertDialog.findViewById(R.id.txt_positive);
        negBtn = alertDialog.findViewById(R.id.txt_negative);

        pos = (int) Company.retrieve(this, Company.SETUP_POSITION);

        stepLabels = new String[4];
        stepLabels[0] = "Details";
        stepLabels[1] = "Departments";
        stepLabels[2] = "Entities";
        stepLabels[3] = "Finalize";

        binding.stepperIndicator.setLabels(stepLabels);
        binding.stepperIndicator.setIndicatorColor(Color.parseColor("#fb7632"));
        binding.stepperIndicator.setCurrentStep(pos);

        if (pos == 0) {
            loadFragment(CreateCompanyFragment.FRAGMENT_ID);
        } else if (pos == 1) {
            loadFragment(DepartmentSetupFragment.FRAGMENT_ID);
        }

        Entity.initModels(this);
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void loadFragment(String fragmentId) {
        this.fragmentId = fragmentId;
        Fragment fragment;
        switch (fragmentId) {
            case DepartmentSetupFragment.FRAGMENT_ID:
                fragment = DepartmentSetupFragment.Builder();
                break;
            default:
                fragment = CreateCompanyFragment.Builder();
        }
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .replace(binding.fragment.getId(), fragment, fragmentId)
                .commit();
    }

    @Override
    public void onCreateStart() {
        loadingDialog.show();
    }

    public void incrementStepper() {
        pos += 1;
        binding.stepperIndicator.setCurrentStep(pos);
        if (pos == 1) {
            loadFragment(DepartmentSetupFragment.FRAGMENT_ID);
        }
    }

    public int getStepperCount() {
        return pos;
    }

    @Override
    public void onCreateSuccess(Map<String, Object> _data) {
        title.setText("Creation Successful");
        msg.setText("Your company has been successfully added to the network.");
        posBtn.setText("Proceed");

        posBtn.setOnClickListener(view -> {
            alertDialog.cancel();
            Intent intent = new Intent(CompanySetup.this, MainActivity.class);
            intent.putExtra(MainActivity.TYPE, Company.DATA);
            intent.putExtra(MainActivity.DATA, (HashMap<String, Object>) data);
            startActivity(intent);
            finish();
        });

        negBtn.setVisibility(View.INVISIBLE);
        Map<String, Object> data = new HashMap<>();
        data.put(Model.ID, Entity.getRandomPublicId(this));
        data.put(Entity.NAME, User.getFullName(this));
        data.put(Entity.MONTHLY_INCOME, 0d);
        data.put(Entity.CONTACT_NUMBER, this.data.get(Company.NUMBER));
        data.put(Entity.MONITOR, true);
        data.put(Entity.MARITAL_STATUS, Entity.MaritalStatus.SINGLE);
        data.put(Entity.GENDER, Entity.Gender.MALE);
        data.put(Entity.HOURS_WORKED, 0);
        data.put(Entity.STOCKS_SOLD, 0);
        data.put(Entity.USER_KEY, User.getKey(this));
        data.put(Entity.ADDRESS, this.data.get(Company.ADDRESS));
        data.put(Entity.COMPANY_KEY, this.data.get(Company.KEY));
        data.put(Entity.POSITION, Entity.OWNER);
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        data.put(Entity.JOIN_DATE, calendar.getTime());
        List<Entity> entities = new ArrayList<>();
        entities.add(Entity.Model(data));
        Entity.append(entities);
    }

    @Override
    public void onCreateFailure() {
        loadingDialog.cancel();
        title.setText("Processing Failed");
        msg.setText("An error occurred during registration. Please try again.");
        posBtn.setText("Okay");
        posBtn.setOnClickListener(view -> {
            alertDialog.cancel();
        });
        negBtn.setVisibility(View.INVISIBLE);
        alertDialog.show();
    }

    @Override
    public void onCompanyExists() {
        loadingDialog.dismiss();
        title.setText("Company Already Exists");
        msg.setText("A company with that email has already existed. Please provide a different email address.");
        posBtn.setText("Okay");
        posBtn.setOnClickListener(view -> {
            alertDialog.cancel();
        });
        negBtn.setVisibility(View.INVISIBLE);
        alertDialog.show();
    }

    @Override
    public void onJoinStart() {
        loadingDialog.show();
    }

    @Override
    public void onJoinSuccess(Map<String, Object> companyData) {
        loadingDialog.cancel();
    }

    @Override
    public void onJoinFailure() {
        loadingDialog.cancel();
        title.setText("Processing Failed");
        msg.setText("An error occurred during registration. Please try again.");
        posBtn.setText("Okay");
        posBtn.setOnClickListener(view -> {
            alertDialog.cancel();
        });
        negBtn.setVisibility(View.INVISIBLE);
        alertDialog.show();
    }

    @Override
    public void onCompanyDoesntExist() {
        loadingDialog.dismiss();
        title.setText("Company not Found");
        msg.setText("We cannot find the company associated with the provided email. Please check the email provided.");
        posBtn.setText("Okay");
        posBtn.setOnClickListener(view -> {
            alertDialog.cancel();
        });
        negBtn.setVisibility(View.INVISIBLE);
        alertDialog.show();
    }

    private void updateDepartmentsGridView() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(DepartmentSetupFragment.FRAGMENT_ID);
        if (fragment == null) {
            return;
        }
        ((DepartmentSetupFragment) fragment).setAdapter();
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
//                    updateDepartmentsGridView();
//                    Is this bad design?
                    if (!isFinishing()) {
                        loadingDialog.cancel();
                        alertDialog.show();
                    }

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
        if (name.equals(Entity.TABLE_NAME)) {

        }
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