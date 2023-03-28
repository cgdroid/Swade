package com.tmhnry.swade.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tmhnry.swade.databinding.FragmentUserSetupMainBinding;
import com.tmhnry.swade.viewpager.UserSetupViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserSetupMainFragment extends Fragment {
    public static final String FRAGMENT_ID = "tmhnry.employeeproductivity.usersetupmainfragment";
    List<UserSetupViewPagerAdapter.Page> pages;
    FragmentUserSetupMainBinding binding;
    UserSetupViewPagerAdapter userSetupViewPagerAdapter;

    public UserSetupMainFragment() {
    }

    public static UserSetupMainFragment Builder() {
        UserSetupMainFragment fragment = new UserSetupMainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        pages = new ArrayList<>();
        pages.add(new UserSetupViewPagerAdapter.Page("Holy Shit", 5, new ArrayList<>()));
        pages.add(new UserSetupViewPagerAdapter.Page("Holy Shit", 5, new ArrayList<>()));
        pages.add(new UserSetupViewPagerAdapter.Page("Holy Shit", 5, new ArrayList<>()));
        pages.add(new UserSetupViewPagerAdapter.Page("Holy Shit", 5, new ArrayList<>()));
        pages.add(new UserSetupViewPagerAdapter.Page("Holy Shit", 5, new ArrayList<>()));
        pages.add(new UserSetupViewPagerAdapter.Page("Holy Shit", 5, new ArrayList<>()));
        userSetupViewPagerAdapter = new UserSetupViewPagerAdapter(context, pages);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserSetupMainBinding.inflate(inflater, container, false);
        binding.viewPager.setAdapter(userSetupViewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
