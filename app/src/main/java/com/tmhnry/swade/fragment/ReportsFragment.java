package com.tmhnry.swade.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.chaquo.python.PyObject;
//import com.chaquo.python.Python;
//import com.chaquo.python.android.AndroidPlatform;
import com.google.android.material.tabs.TabLayout;
import com.tmhnry.swade.R;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.singleton.User;
import com.tmhnry.swade.databinding.FragmentReportsBinding;
import com.tmhnry.swade.viewpager.ReportsViewPagerAdapter;

public class ReportsFragment extends Fragment {
    public static final String FRAGMENT_ID = "tmhnry.employeeproductivity.reportsfragment";
    private FragmentReportsBinding binding;
    Entity entity;
    ReportsViewPagerAdapter adapter;
    //    PyObject graphData;
    ViewPager viewPager;
    TabLayout tabLayout;

    public ReportsFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        String userKey = User.getKey(context);
        for (Entity entity : Entity.getModels().values()) {
            if (entity.userKey.equals(userKey)) {
                this.entity = entity;
                break;
            }
        }
    }

    public static ReportsFragment Builder() {
        ReportsFragment fragment = new ReportsFragment();
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
        binding = FragmentReportsBinding
                .inflate(inflater, container, false);

        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;

        FragmentManager fragmentManager = getChildFragmentManager();
        adapter = new ReportsViewPagerAdapter(fragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        addAdapterFragments();
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
//        binding.tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
//        binding.tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
//        binding.tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));

        return binding.getRoot();
    }

    private void addAdapterFragments() {
        if (entity.position.equals(Entity.OWNER)) {
            addFragmentsForOwner();
            return;
        }
        addFragmentsForEmployee();
    }

    private void addFragmentsForEmployee() {
        adapter.addFragment(GeneralReportsFragment.Builder(), "GENERAL");
        adapter.addFragment(TransactionReportsFragment.Builder(), "TRANSACTIONS");
        adapter.addFragment(StockReportsFragment.Builder(), "STOCKS");
    }

    private void addFragmentsForOwner() {
        adapter.addFragment(CompanyReportsFragment.Builder(), "GENERAL");
        adapter.addFragment(EmployeeReportsFragment.Builder(), "EMPLOYEES");
        adapter.addFragment(TransactionReportsFragment.Builder(), "TRANSACTIONS");
    }

    private void loadGraph() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (graphData != null) {
////                    binding.subGraph.imgGraph.setImageBitmap(base64Decode(graphData.toString()));
//                } else {
//                    Toast.makeText(getContext(),
//                            "Cannot Load Graph. Please Try Again",
//                            Toast.LENGTH_SHORT).show();
//                }
            }
        }, 10000);
    }

    public void updateFragment(String fragmentId) {
        if (fragmentId.equals(GeneralReportsFragment.FRAGMENT_ID)) {
            Fragment fragment = adapter.getItem(0);
            if (!(fragment instanceof GeneralReportsFragment)) {
                return;
            }
            ((GeneralReportsFragment) fragment).updateAttendanceViews(true);
        }
    }

    private Bitmap base64Decode(String imgString) {
        byte[] bytes = android.util.Base64.decode(imgString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private void sendData(int type, String flag) {
//        if (!Python.isStarted()) {
//            Python.start(new AndroidPlatform(getContext()));
//        }
//
//        Python py = Python.getInstance();

        StringBuilder sbData = new StringBuilder();
        for (Entity entity : Entity.getModels().values()) {
            sbData.append(entity.getAttrition());
            sbData.append(",");
            sbData.append(entity.getName());
            sbData.append(",");
            sbData.append(entity.gender.getName());
            sbData.append(",");
            sbData.append(entity.getAge());
            sbData.append(",");
            sbData.append(entity.getDepartment().getName());
            sbData.append(",");
            sbData.append(entity.maritalStatus.getName());
            sbData.append(",");
            sbData.append(entity.getMonthlyIncome());
            sbData.append(",");
            sbData.append(entity.getStocksSold());
            sbData.append("\n");
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                PyObject graph = py.getModule("graph");
//                if (type == 0) {
//                    if (flag.equals("heatmap")) {
//                        graphData = graph.callAttr("get_raw_heatmap");
//                        return;
//                    }
//                    if (flag.equals("attrition")) {
//                        graphData = graph.callAttr("get_raw_attrition");
//                        return;
//                    }
//                    graphData = graph.callAttr("get_raw_count_plot", flag);
//
////                    graphData = graph.callAttr("get_raw_heatmap", flag);
//                }
                if (type == 1) {

                }
            }
        };

        new Thread(runnable).start();
    }


}