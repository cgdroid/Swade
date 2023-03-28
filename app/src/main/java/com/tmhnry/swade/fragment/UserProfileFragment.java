package com.tmhnry.swade.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmhnry.swade.MainActivity;
import com.tmhnry.swade.R;
import com.tmhnry.swade.databinding.FragmentUserProfileBinding;
import com.tmhnry.swade.model.Department;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.model.Notification;
import com.tmhnry.swade.model.Stock;
import com.tmhnry.swade.model.Transaction;
import com.tmhnry.swade.singleton.Company;
import com.tmhnry.swade.singleton.User;

public class UserProfileFragment extends Fragment {
    public static final String FRAGMENT_ID = "tmhnry.employeeproductivity.userprofilefragment";

    FragmentUserProfileBinding binding;
    Dialog logout;

    public UserProfileFragment() {
    }

    public static UserProfileFragment Builder() {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    private void setLogoutDialog() {
        TextView title, mes, posBtn, negBtn;

        title = logout.findViewById(R.id.dialog_title);
        mes = logout.findViewById(R.id.txt_alert_message);
        posBtn = logout.findViewById(R.id.txt_positive);
        negBtn = logout.findViewById(R.id.txt_negative);

        title.setText("Logout Prompt");
        mes.setText("This may cause data loss for unsaved processes. Continue?");
        posBtn.setText("Yes");
        negBtn.setText("No");

        posBtn.setOnClickListener(v -> {
            logout.dismiss();
            Department.reset();
            Entity.reset();
            Stock.reset();
            Notification.reset();
            Transaction.reset();
            User.logout();
            User.delete(getContext());
            Company.delete(getContext());
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        });

        negBtn.setOnClickListener(v -> {
            logout.dismiss();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        binding.email.setText((String) User.retrieve(getContext(), User.EMAIL));
        String name = User.retrieve(getContext(), User.FIRST_NAME) + " " + User.retrieve(getContext(), User.LAST_NAME);
        binding.displayName.setText(name);
        binding.companyName.setText((String) Company.retrieve(getContext(), Company.NAME));
        String position = (String) User.retrieve(getContext(), User.POSITION);
        binding.position.setText(position);
        logout = new Dialog(getContext());
        logout.getWindow()
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logout.setContentView(R.layout.dialog_alert_notification);
        binding.logout.setOnClickListener(v -> {
            logout.show();
        });
        setLogoutDialog();
        return binding.getRoot();
    }
}