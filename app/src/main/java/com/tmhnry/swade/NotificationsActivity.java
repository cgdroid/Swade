package com.tmhnry.swade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.tmhnry.swade.database.Firebase;
import com.tmhnry.swade.databinding.ActivityNotificationsBinding;
import com.tmhnry.swade.model.Model;
import com.tmhnry.swade.model.Notification;
import com.tmhnry.swade.recyclerview.NotificationsViewAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsActivity extends AppCompatActivity implements Model.FirebaseQueryListener, NotificationsViewAdapter.OnNotificationClickListener {
    ActivityNotificationsBinding binding;
    Map<String, Boolean> querySuccessful;
    Handler handler;
    Dialog loading;
    RecyclerView recyclerView;
    NotificationsViewAdapter adapter;
    List<Notification> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        binding.imgBack.setOnClickListener(v -> {
            onBackPressed();
        });
        setContentView(binding.getRoot());
        recyclerView = binding.recyclerView;
        createLoadingDialog();
        querySuccessful = new HashMap<>();
        Firebase.Create();
        Notification.initModels(this);
        Notification.retrieve(this);
        notifications = new ArrayList<>();
        adapter = new NotificationsViewAdapter(this, notifications);
        recyclerView = binding.recyclerView;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void createLoadingDialog() {
        loading = new Dialog(this);
        loading.setContentView(R.layout.dialog_loading_indicator);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                    loading.cancel();
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
        loading.show();
    }

    @Override
    public void onSuccessQuery(String name) {
        querySuccessful.put(name, true);
//        It is important to call this before setState else setState all queries prior to this call
//        may be finished causing the dialog to be cancelled
        if (name.equals(Notification.TABLE_NAME)) {
            notifications.addAll(Notification.getModels().values());
            adapter.notifyDataSetChanged();
        }
        if (handler != null) {
            return;
        }
        handler = new android.os.Handler();
        setState();
    }

    @Override
    public void onFailQuery() {
        loading.cancel();
    }

    @Override
    public void onNotificationClick(Notification notification, int notificationId) {
        Intent intent = new Intent(this, ApplicantProfile.class);
        intent.putExtra(Notification.SENDER_NAME, notification.senderName);
        intent.putExtra(Notification.CONTACT_NUMBER, notification.contactNumber);
        intent.putExtra(Notification.MESSAGE, notification.message);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String date = dateFormat.format(notification.date);
        intent.putExtra(Notification.DATE, date);
        intent.putExtra(Notification.GENDER, notification.gender);
        intent.putExtra(Notification.MARITAL_STATUS, notification.maritalStatus);
        intent.putExtra(Notification.ADDRESS, notification.address);
        intent.putExtra(Notification.DESIRED_SALARY, notification.desiredSalary);
        intent.putExtra(Notification.SENDER_KEY, notification.senderKey);
        intent.putExtra(Notification.COMPANY_KEY, notification.companyKey);
        intent.putExtra(Notification.ID, notificationId);
        startActivity(intent);
        finish();
    }
}