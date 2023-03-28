package com.tmhnry.swade.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tmhnry.swade.R;
import com.tmhnry.swade.databinding.FragmentAllEmployeesReportBinding;

public class AllEmployeesReportFragment extends Fragment {
    FragmentAllEmployeesReportBinding binding;

    public AllEmployeesReportFragment() {
        // Required empty public constructor
    }

    public static AllEmployeesReportFragment Builder() {
        AllEmployeesReportFragment fragment = new AllEmployeesReportFragment();
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
        binding = FragmentAllEmployeesReportBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}