package com.tmhnry.swade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tmhnry.swade.databinding.ActivityApplicationSetupBinding;
import com.tmhnry.swade.fragment.ApplyCompanyFragment;
import com.tmhnry.swade.fragment.PendingApplicationFragment;
import com.tmhnry.swade.model.Model;
import com.tmhnry.swade.model.Notification;
import com.tmhnry.swade.singleton.Company;
import com.tmhnry.swade.model.Notification.Notifications;
import com.tmhnry.swade.singleton.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class ApplicationSetup extends AppCompatActivity implements
        Company.OnJoinCompanyListener,
        Model.FirebaseQueryListener {
    public static final String DATA = "data";
    ActivityApplicationSetupBinding binding;
    Dialog loading;
    Dialog alert;
    String fragmentId;
    TextView title, msg, pos, neg;
    Map<String, Boolean> querySuccessful;
    Handler handler;
    HashMap<String, Object> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        binding = ActivityApplicationSetupBinding.inflate(getLayoutInflater());
        querySuccessful = new HashMap<>();
        Notifications.Create((Context) this);
        createLoadingDialog();
        createAlertDialog();
        data = (HashMap<String, Object>) getIntent().getSerializableExtra(DATA);
        setContentView(binding.getRoot());
        if ((Integer) User.retrieve(this, User.PENDING_STATUS) == -1) {
            loadFragment(ApplyCompanyFragment.FRAGMENT_ID);
        } else {
            loadFragment(PendingApplicationFragment.FRAGMENT_ID);
        }
    }

    public void loadFragment(String fragmentId) {
        this.fragmentId = fragmentId;
        Fragment fragment;
        if (fragmentId.equals(ApplyCompanyFragment.FRAGMENT_ID)) {
            fragment = ApplyCompanyFragment.Builder();
        } else {
            fragment = PendingApplicationFragment.Builder();
        }

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .replace(binding.fragment.getId(), fragment, fragmentId)
                .commit();
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    @Override
    public void onJoinStart() {
        loading.show();
    }

    @Override
    public void onJoinSuccess(Map<String, Object> data) {
        loading.cancel();
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        Log.v("", String.valueOf(data));
        data.put(Notification.OPENED, false);
        data.put(Notification.MESSAGE, data.get(Notification.MESSAGE));
        data.put(Notification.DATE, calendar.getTime());
        data.put(Notification.TITLE, "Application Pending");
        data.put(Notification.SENDER_KEY, User.retrieve(this, User.FIRESTORE_KEY));
        data.put(Model.ID, Notification.getRandomPublicId(this));
        List<Notification> notifications = new ArrayList<>();
        notifications.add(Notification.Model(data));
        Notifications.getInstance().appendToCloudDatabase(notifications);
    }

    @Override
    public void onJoinFailure() {
        title.setText("Error");
        msg.setText("An error has occurred. Please try again.");
        pos.setOnClickListener(v -> {
            alert.cancel();
        });
        new Handler().postDelayed(() -> {
            loading.cancel();
            alert.show();
        }, 2000);
    }

    @Override
    public void onCompanyDoesntExist() {
        title.setText("Company not found");
        msg.setText("The email provided cannot be found.");
        pos.setOnClickListener(v -> {
            alert.cancel();
        });
        new Handler().postDelayed(() -> {
            loading.cancel();
            alert.show();
        }, 2000);
    }

    private void createLoadingDialog() {
        loading = new Dialog(this);
        loading.setContentView(R.layout.dialog_loading_indicator);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void createAlertDialog() {
        alert = new Dialog(this);
        alert.setContentView(R.layout.dialog_alert_notification);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        title = alert.findViewById(R.id.dialog_title);
        msg = alert.findViewById(R.id.txt_alert_message);
        pos = alert.findViewById(R.id.txt_positive);
        pos.setText("Okay");
        neg = alert.findViewById(R.id.txt_negative);
        neg.setVisibility(View.GONE);
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
                    User.update(ApplicationSetup.this, User.PENDING_STATUS, 1);
                    startActivity(new Intent(ApplicationSetup.this, MainActivity.class));
                    finish();
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