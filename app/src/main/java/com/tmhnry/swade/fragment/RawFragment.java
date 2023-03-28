package com.tmhnry.swade.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tmhnry.swade.MainActivity;
import com.tmhnry.swade.R;
import com.tmhnry.swade.singleton.User;
import com.tmhnry.swade.databinding.FragmentRawBinding;

public class RawFragment extends Fragment {
    public static final String FRAGMENT_ID = "tmhnry.employeeproductivity.reportsfragment";
    private FragmentRawBinding binding;
    Dialog accountD;
    Dialog logoutD;
    ImageView menu;

    public RawFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        accountD = new Dialog(getContext());
        accountD.getWindow()
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        accountD.setContentView(R.layout.dialog_account);
        logoutD = new Dialog(getContext());
        logoutD.getWindow()
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logoutD.setContentView(R.layout.dialog_alert_notification);
        setAccountDialog();
        setLogoutDialog();
    }

    public static RawFragment Builder() {
        RawFragment fragment = new RawFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRawBinding.inflate(inflater, container, false);
//        binding.graph1.imgGraph.setImageBitmap(GraphManager.getGraph1());
//        binding.graph2.imgGraph.setImageBitmap(GraphManager.getGraph2());
//        binding.graph3.imgGraph.setImageBitmap(GraphManager.getGraph3());
//        binding.graph4.imgGraph.setImageBitmap(GraphManager.getGraph4());
        binding.imgAccount.setOnClickListener(v -> {
            accountD.show();
        });

        menu = binding.imgMenu;
        menu.setOnClickListener(v -> {

        });
        return binding.getRoot();
    }

    private void setLogoutDialog() {
        TextView title, mes, posBtn, negBtn;

        posBtn = logoutD.findViewById(R.id.txt_positive);
        negBtn = logoutD.findViewById(R.id.txt_negative);
        title = logoutD.findViewById(R.id.dialog_title);
        mes = logoutD.findViewById(R.id.txt_alert_message);

        title.setText("Logout Prompt");
        mes.setText("This may cause data loss for unsaved processes. Continue?");
        posBtn.setText("Yes");
        negBtn.setText("No");

        posBtn.setOnClickListener(v -> {
            logoutD.dismiss();
            User.logout();
            startActivity(new Intent(getActivity(), MainActivity.class));
        });

        negBtn.setOnClickListener(v -> {
            logoutD.dismiss();
        });
    }

    private void setAccountDialog() {
        accountD.findViewById(R.id.txt_logout).setOnClickListener(v -> {
            accountD.dismiss();
            logoutD.show();
        });
        accountD.findViewById(R.id.txt_okay).setOnClickListener(v -> {
            accountD.dismiss();
        });
    }
}