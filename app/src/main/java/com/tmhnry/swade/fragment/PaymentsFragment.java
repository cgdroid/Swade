package com.tmhnry.swade.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmhnry.swade.databinding.FragmentPaymentsBinding;
import com.tmhnry.swade.model.Stock;

import java.util.Locale;

public class PaymentsFragment extends Fragment {
    public static final String FRAGMENT_ID = "tmhnry.employeeproductivity.paymentsfragment";
    FragmentPaymentsBinding binding;
    PaymentListener listener;


    public PaymentsFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (PaymentListener) context;
    }

    public static PaymentsFragment Builder() {
        PaymentsFragment fragment = new PaymentsFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentsBinding.inflate(inflater, container, false);
        binding.setListener(listener);
        TextView $amount = binding.amount;
        float amount = 0f;
        for (Stock stock : Stock.getOrders().values()) {
            amount += stock.price * stock.quantityRemaining;
        }
        $amount.setText(String.format(Locale.getDefault(), "%.2f", amount));
        return binding.getRoot();
    }

    public interface PaymentListener {
        void onPayment();
    }

}