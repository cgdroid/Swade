package com.tmhnry.swade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tmhnry.swade.databinding.ActivityLoginRegisterBinding;
import com.tmhnry.swade.fragment.LoginFragment;
import com.tmhnry.swade.fragment.RegisterFragment;
import com.tmhnry.swade.singleton.User;

import java.util.HashMap;
import java.util.Map;

public class LoginRegister extends AppCompatActivity {
    private ActivityLoginRegisterBinding binding;
    private Dialog dLoading;
    private Dialog dAlert;
    private TextView vPos;
    private TextView vNeg;
    private TextView vMes;
    private TextView vTit;


    private final User.OnLoginListener loginListener = new User.OnLoginListener() {
        @Override
        public void onStart() {
            onShowDialog("Logging In...");
        }

        @Override
        public void onSuccess(Map<String, Object> userCredentials) {
            startNextActivity(userCredentials);
            dLoading.dismiss();
        }

        @Override
        public void onFailed() {
            dLoading.dismiss();
            vTit.setText("Invalid Credentials");
            vMes.setText("Your email address or password is incorrect.");
            vPos.setText("Okay");
            vPos.setOnClickListener(view -> {
                dAlert.dismiss();
            });
            vNeg.setVisibility(View.INVISIBLE);
            dAlert.show();
        }
    };


    private final User.OnRegisterListener registerListener = new User.OnRegisterListener() {
        @Override
        public void onStart() {
            onShowDialog("Please Wait...");
        }

        @Override
        public void onSuccess(Map<String, Object> userCredentials) {
            dLoading.dismiss();
            vTit.setText("Registration Successful");
            vMes.setText("Do you want to log in with these credentials?");
            vPos.setText("Yes");
            vPos.setOnClickListener(view -> {
                dAlert.dismiss();
                User.login(userCredentials, loginListener);
            });
            vNeg.setVisibility(View.VISIBLE);
            vNeg.setText("No");
            vNeg.setOnClickListener(view -> {
                dAlert.dismiss();
            });
            dAlert.show();
        }

        @Override
        public void onUserExists() {
            dLoading.dismiss();
            vTit.setText("Email Already Exists");
            vMes.setText("A user with that email has already existed. Please provide a different email address.");
            vPos.setText("Okay");
            vPos.setOnClickListener(view -> {
                dAlert.dismiss();
            });
            vNeg.setVisibility(View.INVISIBLE);
            dAlert.show();
        }

        @Override
        public void onFailed() {
            dLoading.dismiss();
            vTit.setText("Processing Failed");
            vMes.setText("An error occurred during registration. Please try again.");
            vPos.setText("Okay");
            vPos.setOnClickListener(view -> {
                dAlert.dismiss();
            });
            vNeg.setVisibility(View.INVISIBLE);
            dAlert.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        binding = ActivityLoginRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dLoading = new Dialog(this);

        dLoading.setCancelable(false);

        dLoading.setContentView(R.layout.dialog_loading_indicator);

        dLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dAlert = new Dialog(this);

        dAlert.setContentView(R.layout.dialog_alert_notification);

        dAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vPos = dAlert.findViewById(R.id.txt_positive);
        vNeg = dAlert.findViewById(R.id.txt_negative);
        vMes = dAlert.findViewById(R.id.txt_alert_message);
        vTit = dAlert.findViewById(R.id.dialog_title);

        vNeg.setVisibility(View.INVISIBLE);

        loadFragment(LoginFragment.FRAGMENT_ID);
    }

    private void startNextActivity(Map<String, Object> userCredentials) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.TYPE, User.DATA);
        intent.putExtra(MainActivity.DATA, (HashMap<String, Object>) userCredentials);
        startActivity(intent);
        finish();
    }

    public void onShowDialog(String message) {
        ((TextView) dLoading.findViewById(R.id.txt_loading_message)).setText(message);
        dLoading.show();
    }

    public void loadFragment(String fragmentId) {
        Fragment fragment;
        if (fragmentId == LoginFragment.FRAGMENT_ID) {
            fragment = LoginFragment.newInstance();
        } else {
            fragment = RegisterFragment.newInstance("", "");
        }
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .replace(binding.fragment.getId(), fragment)
                .commit();
    }

    public User.OnRegisterListener getRegisterListener() {
        return registerListener;
    }

    public User.OnLoginListener getLoginListener() {
        return loginListener;
    }

    @Override
    public void onBackPressed() {

    }
}