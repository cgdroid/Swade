package com.tmhnry.swade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
import android.widget.Toast;

import com.tmhnry.swade.databinding.ActivityApplicantProfileBinding;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.model.Model;
import com.tmhnry.swade.model.Notification;
import com.tmhnry.swade.singleton.Company;
import com.tmhnry.swade.singleton.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class ApplicantProfile extends AppCompatActivity implements Model.FirebaseQueryListener {
    ActivityApplicantProfileBinding binding;
    TextView department;
    TextView status;
    TextView joinDate;
    TextView maritalStatus;
    CardView cardName, cardDepartment, cardMarital, cardStatus;
    TextView editTitle, radioTitle, editPosBtn, editNegBtn, radioPosBtn, radioNegBtn;
    Dialog editDialog;
    Dialog radioDialog;
    View $progress;
    Map<String, Boolean> querySuccessful;
    Handler handler;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, NotificationsActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        binding = ActivityApplicantProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        $progress = binding.btmProgressIndicator;
        querySuccessful = new HashMap<>();

        String senderName = getIntent().getStringExtra(Notification.SENDER_NAME);
        String contactNumber = getIntent().getStringExtra(Notification.CONTACT_NUMBER);
        String message = getIntent().getStringExtra(Notification.MESSAGE);
        String date = getIntent().getStringExtra(Notification.DATE);
        String gender = getIntent().getStringExtra(Notification.GENDER);
        String maritalStatus = getIntent().getStringExtra(Notification.MARITAL_STATUS);
        String address = getIntent().getStringExtra(Notification.ADDRESS);
        String senderKey = getIntent().getStringExtra(Notification.SENDER_KEY);
        Integer desiredSalary = getIntent().getIntExtra(Notification.DESIRED_SALARY, 0);
        String companyKey = getIntent().getStringExtra(Notification.COMPANY_KEY);
        Integer notifId = getIntent().getIntExtra(Notification.ID, -1);

        Notification.initModels(this);
        Entity.initModels(this);

        binding.name.setText(senderName);
        binding.number.setText(contactNumber);
        binding.msg.setText(message);
        binding.gender.setText(gender);
        binding.marital.setText(maritalStatus);
        binding.address.setText(address);
        binding.desiredSalary.setText("PHP " + String.format("%.2f", desiredSalary.floatValue()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date _date = null;
        try {
            _date = format.parse((String) date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.applicationDate.setText(_date.toLocaleString());

        binding.employ.setOnClickListener(v -> {
            Map<String, Object> data = new HashMap<>();
            data.put(Model.ID, Entity.getRandomPublicId(this));
            data.put(Entity.NAME, senderName);
            String monthlyIncome = binding.salary.getText().toString().trim();
            if (monthlyIncome.isEmpty()) {
                Toast.makeText(ApplicantProfile.this, "Please provide a valid monthly salary", Toast.LENGTH_SHORT).show();
                return;
            }
            data.put(Entity.MONTHLY_INCOME, Double.parseDouble(monthlyIncome));
            data.put(Entity.MONITOR, true);
            if (maritalStatus.equals("Single")) {
                data.put(Entity.MARITAL_STATUS, Entity.MaritalStatus.SINGLE);
            } else if (maritalStatus.equals("Married")) {
                data.put(Entity.MARITAL_STATUS, Entity.MaritalStatus.MARRIED);
            } else {
                data.put(Entity.MARITAL_STATUS, Entity.MaritalStatus.DIVORCED);
            }
            if (gender.equals("Male")) {
                data.put(Entity.GENDER, Entity.Gender.MALE);
            } else {
                data.put(Entity.GENDER, Entity.Gender.FEMALE);
            }
            data.put(Entity.HOURS_WORKED, 0);
            data.put(Entity.STOCKS_SOLD, 0);
            data.put(Entity.USER_KEY, senderKey);
            data.put(Entity.ADDRESS, address);
            data.put(Entity.CONTACT_NUMBER, contactNumber);
            data.put(Entity.COMPANY_KEY, Company.getKey(this));
            data.put(Entity.COMPANY_NAME, Company.getCompanyName(this));
            data.put(Entity.POSITION, Entity.EMPLOYEE);
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            data.put(Entity.JOIN_DATE, calendar.getTime());
            List<Entity> entities = new ArrayList<>();
            entities.add(Entity.Model(data));
            Entity.append(entities);
            Map<String, Object> _data = new HashMap<>();
            _data.put(Notification.COMPANY_KEY, companyKey);
            _data.put(Notification.SENDER_KEY, senderKey);
            _data.put(User.POSITION, Entity.EMPLOYEE);
            Notification notification = Notification.getModels().values().stream()
                    .filter(notification1 -> notification1.getId().equals(notifId))
                    .collect(Collectors.toList()).get(0);
            notification.opened = true;
            List<Notification> notifications = new ArrayList<>();
            notifications.add(notification);
            Notification.update(notifications);
            Company.addUser(_data);
        });

        editDialog = new Dialog(this);
        editDialog.setContentView(R.layout.dialg_edit);
        editTitle = editDialog.findViewById(R.id.dialog_title);
        editPosBtn = editDialog.findViewById(R.id.txt_positive);
        editNegBtn = editDialog.findViewById(R.id.txt_negative);
        editDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        radioDialog = new Dialog(this);
        radioDialog.setContentView(R.layout.dialog_change_option);
        radioTitle = radioDialog.findViewById(R.id.dialog_title);
        radioPosBtn = radioDialog.findViewById(R.id.txt_positive);
        radioNegBtn = radioDialog.findViewById(R.id.txt_negative);
        radioDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//        if (entity.gender == Entity.Gender.MALE) {
//            binding.imgProfile.setImageResource(R.drawable.icons8_male_user_100);
//        } else {
//            binding.imgProfile.setImageResource(R.drawable.icons8_female_profile_100);
//        }
//
//        name = binding.txtName;
//        name.setText(entity.getName());
//
//        department = binding.txtDept;
//        department.setText(entity.getDepartment().getName());
//
//        status = binding.txtStat;
//        if (entity.monitor) {
//            status.setText("Tracked");
//        } else {
//            status.setText("Untracked");
//        }
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//        String dateTime = dateFormat.format(entity.joinDate);
//        joinDate = binding.txtJoinDate;
//        joinDate.setText(dateTime);
//
//        maritalStatus = binding.txtMarital;
//        maritalStatus.setText(entity.maritalStatus.getName());
//
//        joinDate = binding.txtJoinDate;

        binding.imgBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }


    private void startLoading() {
        $progress.setVisibility(View.VISIBLE);
    }

    private void stopLoading() {
        $progress.setVisibility(View.GONE);
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
                    stopLoading();
                    onBackPressed();
                    Toast.makeText(ApplicantProfile.this, "Employee has successfully joined the company.", Toast.LENGTH_SHORT).show();
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
        startLoading();
    }

    @Override
    public void onSuccessQuery(String name) {
        querySuccessful.put(name, true);

        if (handler != null) {
            return;
        }
        handler = new android.os.Handler();
        setState();
    }

    @Override
    public void onFailQuery() {
    }


}