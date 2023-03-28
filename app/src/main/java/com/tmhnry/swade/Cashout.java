package com.tmhnry.swade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.tmhnry.swade.databinding.ActivityCashoutBinding;
import com.tmhnry.swade.viewhandler.OrderRowHandler.QuantityListener;
import com.tmhnry.swade.fragment.OrdersFragment;
import com.tmhnry.swade.fragment.PaymentsFragment;
import com.tmhnry.swade.fragment.PaymentsFragment.PaymentListener;

public class Cashout extends AppCompatActivity implements QuantityListener, PaymentListener {
    ActivityCashoutBinding binding;
    String fragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        binding = ActivityCashoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadFragment(OrdersFragment.FRAGMENT_ID);
    }

    public void loadFragment(String fragmentId) {
        this.fragmentId = fragmentId;

        Fragment fragment;
        switch (fragmentId) {
            case PaymentsFragment.FRAGMENT_ID:
                fragment = PaymentsFragment.Builder();
                break;
            default:
                fragment = OrdersFragment.Builder();
        }

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .replace(binding.fragment.getId(), fragment, fragmentId)
                .commit();
    }

    private void setViewColumn(View view, int column) {
        view.setLayoutParams(new TableRow.LayoutParams(column));
    }

    private void setWeight(View view, float weight) {
        int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
        view.setLayoutParams(new LinearLayout.LayoutParams(matchParent, matchParent, weight));
    }

    @Override
    public void onEmptyOrder(TableRow row) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(OrdersFragment.FRAGMENT_ID);
        if (fragment == null) {
            return;
        }
        ((OrdersFragment) fragment).updateOrdersTable(row);
    }

    @Override
    public void onPayment() {
        Toast.makeText(this, "Transaction Added", Toast.LENGTH_SHORT).show();
    }
}