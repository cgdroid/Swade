package com.tmhnry.swade.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmhnry.swade.MainActivity;
import com.tmhnry.swade.R;
import com.tmhnry.swade.databinding.FragmentPendingApplicationBinding;

public class PendingApplicationFragment extends Fragment {
    public static final String FRAGMENT_ID = "tmhnry.employeeproductivity.pendingapplicationfragment";
    FragmentPendingApplicationBinding binding;
    Dialog loading;

    public PendingApplicationFragment() {
    }

    public static PendingApplicationFragment Builder() {
        PendingApplicationFragment fragment = new PendingApplicationFragment();
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
        binding = FragmentPendingApplicationBinding.inflate(inflater, container, false);
        createLoadingDialog();
        binding.refresh.setOnClickListener(v -> {
            refresh();
        });
        return binding.getRoot();
    }

    private void refresh() {
        loading.show();
        new Handler().postDelayed(() -> {
            loading.cancel();
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }, 2500);
    }

    private void createLoadingDialog() {
        loading = new Dialog(getContext());
        loading.setContentView(R.layout.dialog_loading_indicator);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}