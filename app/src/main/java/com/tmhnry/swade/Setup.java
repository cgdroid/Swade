package com.tmhnry.swade;

import androidx.appcompat.app.AppCompatActivity;

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

import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.singleton.Company;
import com.tmhnry.swade.singleton.User;
import com.tmhnry.swade.databinding.ActivitySetupBinding;

import java.util.HashMap;
import java.util.Map;

public class Setup extends AppCompatActivity implements Company.OnJoinCompanyListener {
    ActivitySetupBinding binding;
    Dialog setupDialog;
    Dialog applyCompanyDialog;
    Company.OnJoinCompanyListener onJoinCompanyListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetupBinding.inflate(getLayoutInflater());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setContentView(binding.getRoot());
        onJoinCompanyListener = this;
        setupDialog = new Dialog(this);
        setupDialog.getWindow()
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        applyCompanyDialog = new Dialog(this);
        applyCompanyDialog
                .setContentView(R.layout.dialog_apply_company);
        binding.gtvOwn.setOnClickListener(v -> {
            User.update(this, User.OPTION, 0);
            Intent intent = new Intent(Setup.this, CompanySetup.class);
            HashMap<String, Object> data = new HashMap<>();
            data.put(User.POSITION, Entity.OWNER);
            data.put(User.FIRESTORE_KEY, User.retrieve(this, User.FIRESTORE_KEY));
            intent.putExtra(CompanySetup.DATA, data);
            startActivity(intent);
            finish();
        });

        binding.gtvApply.setOnClickListener(v -> {
            User.update(this, User.OPTION, 0);
            Intent intent = new Intent(Setup.this, ApplicationSetup.class);
            HashMap<String, Object> data = new HashMap<>();
            data.put(User.POSITION, Entity.EMPLOYEE);
            data.put(User.FIRESTORE_KEY, User.retrieve(this, User.FIRESTORE_KEY));
            intent.putExtra(ApplicationSetup.DATA, data);
            startActivity(intent);
            finish();
        });

//        binding.gtvApply.setOnClickListener(v -> {
//            User.update(this, User.OPTION, 1);
//            AppCompatButton joinBtn = applyCompanyDialog.findViewById(R.id.btn_join);
//            AppCompatButton cancelBtn = applyCompanyDialog.findViewById(R.id.btn_cancel);
//            EditText codeEdit = applyCompanyDialog.findViewById(R.id.edit_code);
//            applyCompanyDialog.setOnCancelListener(dialog -> {
//                codeEdit.setText("");
//            });
//            joinBtn.setOnClickListener(v1 -> {
//                applyCompanyDialog.cancel();
////                Company.checkIfCompanyExists(codeEdit.getText().toString(), onJoinCompanyListener);
//            });
//            cancelBtn.setOnClickListener(v1 -> {
//                applyCompanyDialog.cancel();
//            });
//            applyCompanyDialog.show();
//        });
    }

    @Override
    public void onJoinStart() {
        setupDialog.setCancelable(false);
        setupDialog.setContentView(R.layout.dialog_loading_indicator);
        setupDialog.show();
    }

    @Override
    public void onJoinSuccess(Map<String, Object> companyData) {
        setupDialog.dismiss();
        startActivity(new Intent(Setup.this, UserSetup.class));
        finish();
    }

    @Override
    public void onCompanyDoesntExist() {
        setupDialog.dismiss();
        setupDialog.setCancelable(true);
        setupDialog
                .setContentView(R.layout.dialog_alert_notification);
        TextView title = setupDialog.findViewById(R.id.dialog_title);
        TextView message = setupDialog.findViewById(R.id.txt_alert_message);
        TextView posBtn = setupDialog.findViewById(R.id.txt_positive);
        TextView negBtn = setupDialog.findViewById(R.id.txt_negative);

        title.setText("Company Not Found");
        message.setText("The unique identifier provided cannot be found. Please check the code before submitting.");
        posBtn.setText("Okay");
        posBtn.setOnClickListener(view -> {
            setupDialog.dismiss();
        });
        negBtn.setVisibility(View.INVISIBLE);
        setupDialog.show();
    }

    @Override
    public void onJoinFailure() {
        setupDialog.dismiss();
        setupDialog.setCancelable(true);
        setupDialog
                .setContentView(R.layout.dialog_alert_notification);
        TextView title = setupDialog.findViewById(R.id.dialog_title);
        TextView message = setupDialog.findViewById(R.id.txt_alert_message);
        TextView posBtn = setupDialog.findViewById(R.id.txt_positive);
        TextView negBtn = setupDialog.findViewById(R.id.txt_negative);

        title.setText("Processing Failed");
        message.setText("An error has occurred during the process. Please try again.");
        posBtn.setText("Okay");
        posBtn.setOnClickListener(view -> {
            setupDialog.dismiss();
        });
        negBtn.setVisibility(View.INVISIBLE);
        setupDialog.show();
    }
}