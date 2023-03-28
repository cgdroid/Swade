package com.tmhnry.swade;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.chaquo.python.PyObject;
//import com.chaquo.python.Python;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.tmhnry.swade.databinding.ActivityHomeBinding;
import com.tmhnry.swade.databinding.DialogAttendanceBinding;
import com.tmhnry.swade.fragment.GeneralReportsFragment;
import com.tmhnry.swade.fragment.WorkplaceFragment;
import com.tmhnry.swade.fragment.HomeFragment;
import com.tmhnry.swade.fragment.ReportsFragment;
import com.tmhnry.swade.fragment.UserProfileFragment;
import com.tmhnry.swade.model.Asset;
import com.tmhnry.swade.model.Attendance;
import com.tmhnry.swade.model.Department;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.model.Model;
import com.tmhnry.swade.model.Stock;
import com.tmhnry.swade.model.Transaction;
import com.tmhnry.swade.recyclerview.CalendarDaysViewAdapter;
import com.tmhnry.swade.recyclerview.EmployeesViewAdapter;
import com.tmhnry.swade.model.Department.Departments;
import com.tmhnry.swade.model.Entity.Entities;
import com.tmhnry.swade.model.Stock.Stocks;
import com.tmhnry.swade.recyclerview.StocksViewAdapter;
import com.tmhnry.swade.singleton.Company;
import com.tmhnry.swade.singleton.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class Home extends AppCompatActivity implements
        EmployeesViewAdapter.EmployeeClickListener,
        EmployeesViewAdapter.AddHourClickListener,
        EmployeesViewAdapter.AddStockClickListener,
        HomeFragment.DepartmentQueryListener,
        WorkplaceFragment.EmployeeQueryListener,
        StocksViewAdapter.OnCartIconClicked,
        Model.FirebaseQueryListener,
        CalendarDaysViewAdapter.OnItemListener {
    private String fragmentId;
    private ActivityHomeBinding binding;
    public static final String TAG = "home";
    RadioGroup stockRG;
    String code;
    TextView addStock, addHour, cancelStock, cancelHour;
    Map<String, Boolean> querySuccessful;
    android.os.Handler handler;
    View $progress;
    Dialog stockDialog;
    Dialog hourDialog;
//    PyObject graphData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        $progress = binding.btmProgressIndicator;
        querySuccessful = new HashMap<>();

        code = getIntent().getStringExtra("barcode");

        Transaction.initModels(this);
        Asset.initModels(this);
        Stock.initModels(this);
        Stock.retrieve(this);
        Department.initModels(this);
        Department.retrieve();
        Entity.initModels(this);
        Entity.retrieve(this);
        Attendance.initModels(this);

        loadFragment(HomeFragment.FRAGMENT_ID);

        stockDialog = new Dialog(this);
        stockDialog.setContentView(R.layout.dialog_add);
        stockDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        hourDialog = new Dialog(this);
        hourDialog.setContentView(R.layout.dialog_add);
        hourDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addStock = stockDialog.findViewById(R.id.txt_positive);
        addHour = hourDialog.findViewById(R.id.txt_positive);
        cancelStock = stockDialog.findViewById(R.id.txt_negative);
        cancelHour = hourDialog.findViewById(R.id.txt_negative);
        stockRG = stockDialog.findViewById(R.id.rg_stock);

        TextView hourTitle, stockTitle;
        hourTitle = hourDialog.findViewById(R.id.dialog_title);
        stockTitle = stockDialog.findViewById(R.id.dialog_title);
        hourTitle.setText("Add Work Hours");
        stockTitle.setText("Add Stocks Sold");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            switch (itemId) {
                case R.id.nav_employees:
                    loadFragment(WorkplaceFragment.FRAGMENT_ID);
                    break;
                case R.id.nav_reports:
                    loadFragment(ReportsFragment.FRAGMENT_ID);
                    break;
                case R.id.nav_account:
                    loadFragment(UserProfileFragment.FRAGMENT_ID);
                    break;
                default:
                    loadFragment(HomeFragment.FRAGMENT_ID);
            }
            return true;
        });

        binding.bottomNavigation.setOnItemReselectedListener(item -> {
            return;
        });
//
//        new Thread(runnable).start();
//
//        new OrderRowHandler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                GraphManager.updateGraph(graphData.toString());
//            }
//        }, 5000);
    }


    private void startLoading() {
        $progress.setVisibility(View.VISIBLE);
    }

    private void stopLoading() {
        $progress.setVisibility(View.GONE);
    }

    public void goToCashout(View view) {
        startActivity(new Intent(this, Cashout.class));
    }

    public void goToStocksSupply(View view) {
        startActivity(new Intent(this, StocksSupply.class));
//        finish();
    }

//    private void initReportsGraphs(Python py) {
//    }

    private void retrieveTransactions() {
        Transaction.retrieve(this);
    }

    public void loadFragment(String fragmentId) {
        this.fragmentId = fragmentId;

        Fragment fragment;
        switch (fragmentId) {
            case WorkplaceFragment.FRAGMENT_ID:
                fragment = WorkplaceFragment.Builder();
                break;
            case ReportsFragment.FRAGMENT_ID:
                fragment = ReportsFragment.Builder();
                break;
            case UserProfileFragment.FRAGMENT_ID:
                fragment = UserProfileFragment.Builder();
                break;
            default:
                fragment = HomeFragment.Builder();
        }

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                .replace(binding.fragment.getId(), fragment, fragmentId)
                .commit();
    }

    @Override
    public void onEmployeeClicked(Entity entity) {
        Intent intent = new Intent(Home.this, EntityProfile.class);
        intent.putExtra(Model.KEY, entity.key);
        startActivity(intent);
    }

    @Override
    public void onAddDepartment(Map<String, Object> data) {
        Toast.makeText(this, "Adding in progress", Toast.LENGTH_SHORT).show();
        List<Department> departments = new ArrayList<>();
        departments.add(Department.Model(data));
        Departments.getInstance().appendToCloudDatabase(departments);
    }

    @Override
    public void onDeleteDepartment(List<Integer> temp) {
        List<Department> departments = new ArrayList<>();
        for (int pos : temp) {
            Department department = Department.getModels().get(pos);
            for (Entity entity : Entity.getModels().values()) {
                if (entity.department.equals(department)) {
                    Toast.makeText(this, "Invalid: Cannot delete unempty departments", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            departments.add(department);
        }
        Departments.getInstance().removeFromCloudDatabase(departments);
    }

    @Override
    public void onAddEmployee(Map<String, Object> data) {

    }

    @Override
    public void onDeleteEmployee(List<Integer> temp) {
        List<Department> departments = new ArrayList<>();
        List<Entity> entities = new ArrayList<>();
        for (int pos : temp) {
            Entity entity = Entity.getModels().get(pos);
            departments.add(entity.department);
            entity.department.decrementSize();
            entities.add(entity);
        }
        Departments.getInstance().updateCloudDatabase(departments);
        Entities.getInstance().removeFromCloudDatabase(entities);
    }

    @Override
    public void onAddStockBtnClicked(Entity entity) {
        TextInputLayout layout = stockDialog.findViewById(R.id.input_layout);
        layout.setHint("Quantity of stocks sold");
        layout.setStartIconDrawable(R.drawable.ic_baseline_add_shopping_cart_24);
        layout.setEndIconVisible(false);
        TextInputEditText edit = stockDialog.findViewById(R.id.input_edit_text);
        stockDialog.show();

        addStock.setOnClickListener(v -> {
            int chosenStock = stockRG.getCheckedRadioButtonId();
            Stock _stock = null;

            for (Stock stock : Stock.getModels().values()) {
                if (stock.getId() == chosenStock) {
                    _stock = stock;
                    break;
                }
            }
            String _quantity = edit.getText().toString();
            int quantity = Integer.parseInt(_quantity);
            if (quantity < 0 || quantity > _stock.quantityRemaining) {
                Toast.makeText(this, "Invalid quantity", Toast.LENGTH_SHORT).show();
                return;
            }
            entity.addStocks(quantity);
//            _stock.sell(quantity);
            edit.getText().clear();
            List<Entity> entities = new ArrayList<>();
            List<Stock> stocks = new ArrayList<>();
            stocks.add(_stock);
            entities.add(entity);
            stockDialog.cancel();
            Entities.getInstance().updateCloudDatabase(entities);
            Stocks.getInstance().updateCloudDatabase(stocks);
        });

        cancelStock.setOnClickListener(v -> {
            edit.getText().clear();
            stockDialog.cancel();
        });
    }

    @Override
    public void onAddHourBtnClicked(Entity entity) {
        TextInputLayout layout = hourDialog.findViewById(R.id.input_layout);
        layout.setStartIconDrawable(R.drawable.ic_baseline_add_alarm_24);
        layout.setHint("Number of hours to add");
        layout.setEndIconVisible(false);
        TextInputEditText edit = hourDialog.findViewById(R.id.input_edit_text);
        hourDialog.show();
        addHour.setOnClickListener(v -> {
            String _hours = edit.getText().toString();
            int hours = Integer.parseInt(_hours);
            if (hours < 0 || hours > 12) {
                Toast.makeText(this, "Invalid work hours", Toast.LENGTH_SHORT).show();
                return;
            }
            entity.addHours(hours);
            edit.getText().clear();
            hourDialog.cancel();
            List<Entity> entities = new ArrayList<>();
            entities.add(entity);
            Entities.getInstance().updateCloudDatabase(entities);
        });

        cancelHour.setOnClickListener(v -> {
            edit.getText().clear();
            hourDialog.cancel();
        });
    }

//    private boolean initStockRG = true;
//    private int currStockSize = 0;
//    private int prevStockSize = -1;

    private void updateEmployeesFragment() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(WorkplaceFragment.FRAGMENT_ID);
        if (fragment == null) {
            return;
        }
        ((WorkplaceFragment) fragment).updateEmployeesList();
    }

    private void updateStocksRadioGroup() {
        stockRG.setVisibility(View.VISIBLE);
        stockRG.removeAllViews();
        int wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT;
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(wrapContent, wrapContent);
        params.setMargins(0, 25, 0, 25);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/segoe_ui_bold_italic.ttf");
        for (Stock stock : Stock.getModels().values()) {
            RadioButton radioB = new RadioButton(Home.this);
            radioB.setId(stock.getId());
            radioB.setLayoutParams(params);
            radioB.setTypeface(face);
            radioB.setOnClickListener(v -> {
                stockRG.clearCheck();
                stockRG.check(stock.getId());
            });
            radioB.setText(stock.name);
            stockRG.addView(radioB);
        }
    }

    private void updateHomeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(HomeFragment.FRAGMENT_ID);
        if (fragment == null) {
            return;
        }
        ((HomeFragment) fragment).updateGridView(true);
    }

    private void updateStocks() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(HomeFragment.FRAGMENT_ID);
        if (fragment == null) {
            return;
        }
        if (code != null) {
            ((HomeFragment) fragment).updateStocks(code);
        } else {
            ((HomeFragment) fragment).updateStocks("");
        }
    }

    private boolean allQueriesCompleted() {
        boolean complete = true;
        for (Boolean queryComplete : querySuccessful.values()) {
            if (!queryComplete) {
                complete = false;
                break;
            }
        }
        return complete;
    }


    private void setState() {
        // checks query status every 1 second
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (allQueriesCompleted()) {
                    updateStocks();
                    updateStocksRadioGroup();
                    updateHomeFragment();
                    updateEmployeesFragment();
                    stopLoading();
                    handler.removeCallbacks(this);
                    handler = null;
                    return;
                }
                setState();
            }
        }, 1000);
    }

    @Override
    public void onStartQuery(String name) {
        querySuccessful.put(name, false);
        startLoading();
    }

    @Override
    public void onSuccessQuery(String name) {
        querySuccessful.put(name, true);
//        It is important to call this before setState else setState all queries prior to this call
//        may be finished causing the dialog to be cancelled
        if (name.equals(Transaction.TABLE_NAME)) {
//        Although Stock doesn't depend on Transaction, this is still desirable for dynamic user experience.
            Stock.retrieve(this);
            String accountType = User.getAccountType(this);
            String userKey = User.getKey(this);
            Entity entity = null;
            for (Entity _entity : Entity.getModels().values()) {
                if (_entity.userKey.equals(userKey)) {
                    entity = _entity;
                }
            }
            if (entity == null) {
                return;
            }
            if (accountType.equals(Entity.EMPLOYEE)) {
                List<Transaction> transactions = Transaction.getTransactions(Transaction.EMPLOYEE, entity.key, Transaction.SELLER);
                Asset.retrieve(transactions);
            } else {
                List<Transaction> transactions = Transaction.getTransactions(Transaction.SELLER);
                Asset.retrieve(transactions);
            }
        }
        if (name.equals(Attendance.TABLE_NAME)) {
            FragmentManager manager = getSupportFragmentManager();
            Fragment fragment = manager.findFragmentByTag(ReportsFragment.FRAGMENT_ID);
            if (fragment == null) {
                return;
            }
            ((ReportsFragment) fragment).updateFragment(GeneralReportsFragment.FRAGMENT_ID);
        }
        if (name.equals(Stock.TABLE_NAME)) {
            FragmentManager manager = getSupportFragmentManager();
            Fragment fragment = manager.findFragmentByTag(HomeFragment.FRAGMENT_ID);
            if (fragment == null) {
                return;
            }
            ((HomeFragment) fragment).updateStocks("");
        }
        if (name.equals(Asset.TABLE_NAME)) {
            String accountType = User.getAccountType(this);
            if (accountType.equals(Entity.OWNER)) {
//                clear user sold stocks to free memory space
                Stock.getUserSoldStocks().clear();
                Stock.getCompanySoldStocks().clear();
                for (Asset asset : Asset.getModels().values()) {
                    List<Stock> stocks = Stock.getCompanySoldStocks().values().stream()
                            .filter(stock -> stock.name.equals(asset.assetName))
                            .collect(Collectors.toList());
                    if (!stocks.isEmpty()) {
                        Stock _stock = stocks.get(0);
                        _stock.customers += 1;
                        _stock.quantitySold += asset.assetQuantity;
                        _stock.netIncome += asset.assetValue;
//                    Log.v("quantity-sold", String.valueOf(_stock.quantitySold));
                        continue;
                    }
                    Map<String, Object> data = new HashMap<>();
                    data.put(Model.ID, Stock.getRandomPublicId(this));
                    data.put(Stock.NAME, asset.assetName);
                    data.put(Stock.GROUP, asset.assetCategory);
                    data.put(Stock.PRICE, 0f);
                    data.put(Stock.COST, 0f);
                    data.put(Stock.UNIT, asset.assetUnit);
                    data.put(Stock.NET_INCOME, asset.assetValue);
                    data.put(Stock.COMPANY_KEY, Company.getKey(this));
                    data.put(Stock.QUANTITY_ORDERED, 0);
                    data.put(Stock.CODE, "");
                    data.put(Stock.QUANTITY_SOLD, asset.assetQuantity);
                    data.put(Stock.QUANTITY_REMAINING, 0);
                    data.put(Stock.NET_COST, 0);
                    Stock stock = Stock.Model(data);
                    stock.customers += 1;
                    Stock.getCompanySoldStocks().put(stock.getId(), stock);
                }
            } else {
                Stock.getUserSoldStocks().clear();
                for (Asset asset : Asset.getModels().values()) {
                    List<Stock> stocks = Stock.getUserSoldStocks().values().stream()
                            .filter(stock -> stock.name.equals(asset.assetName))
                            .collect(Collectors.toList());
                    if (!stocks.isEmpty()) {
                        Stock _stock = stocks.get(0);
                        _stock.customers += 1;
                        _stock.quantitySold += asset.assetQuantity;
                        _stock.netIncome += asset.assetValue;
//                    Log.v("quantity-sold", String.valueOf(_stock.quantitySold));
                        continue;
                    }
                    Map<String, Object> data = new HashMap<>();
                    data.put(Model.ID, Stock.getRandomPublicId(this));
                    data.put(Stock.NAME, asset.assetName);
                    data.put(Stock.GROUP, asset.assetCategory);
                    data.put(Stock.PRICE, 0f);
                    data.put(Stock.COST, 0f);
                    data.put(Stock.UNIT, asset.assetUnit);
                    data.put(Stock.NET_INCOME, asset.assetValue);
                    data.put(Stock.COMPANY_KEY, Company.getKey(this));
                    data.put(Stock.QUANTITY_ORDERED, 0);
                    data.put(Stock.CODE, "");
                    data.put(Stock.QUANTITY_SOLD, asset.assetQuantity);
                    data.put(Stock.QUANTITY_REMAINING, 0);
                    data.put(Stock.NET_COST, 0);
                    Stock stock = Stock.Model(data);
                    stock.customers += 1;
                    Stock.getUserSoldStocks().put(stock.getId(), stock);
                }
            }
        }
        if (name.equals(Entity.TABLE_NAME)) {
            Entity entity = null;
            for (Entity _entity : Entity.getModels().values()) {
                if (_entity.userKey.equals(User.getKey(this))) {
                    entity = _entity;
                }
            }
            Transaction.retrieve(this);
            if(entity.position.equals(Entity.OWNER)){
                Attendance.retrieve(this, null);
            }
            else {
                Attendance.retrieve(this, entity.key);
            }
        }
        if (handler != null) {
            return;
        }
        handler = new android.os.Handler();
        setState();
    }

    @Override
    public void onFailQuery() {
    }

    @Override
    public void onCartIconClicked() {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(HomeFragment.FRAGMENT_ID);
        if (fragment == null) {
            return;
        }
        ((HomeFragment) fragment).updateCartIcon();
    }

    public void scanProduct(View view) {
        Intent intent = new Intent(this, LivePreviewActivity.class);
        intent.putExtra(LivePreviewActivity.SOURCE_TAG, Home.TAG);
        startActivity(intent);
    }

    public void onEditClicked(Date date, Entity entity) {
        ConstraintLayout.LayoutParams params = new ConstraintLayout
                .LayoutParams(720, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        Dialog dialog = new Dialog(this);
        DialogAttendanceBinding binding = DialogAttendanceBinding.inflate(dialog.getLayoutInflater());
        RadioGroup radioGroup = binding.radioGroup;
        binding.date.setText(date.toLocaleString());
        binding.confirm.setOnClickListener(v -> {
            int id = radioGroup.getCheckedRadioButtonId();
            if (id == -1) {
                Toast.makeText(Home.this, "Please provide a valid choice.", Toast.LENGTH_SHORT).show();
                return;
            }
            String status = Attendance.PRESENT;
            if (id == binding.excused.getId()) {
                status = Attendance.EXCUSED;
            } else if (id == binding.absent.getId()) {
                status = Attendance.ABSENT;
            }
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            Map<String, Object> data = new HashMap<>();
            data.put(Attendance.COMPANY_KEY, Company.getKey(Home.this));
            data.put(Attendance.EMPLOYEE_KEY, entity.key);
            data.put(Attendance.DATE, calendar.getTime());
            data.put(Attendance.TYPE, Attendance.ENTER);
            data.put(Attendance.STATUS, status);
            data.put(Model.ID, Attendance.getRandomPublicId(this));
            Attendance attendance = Attendance.Model(data);
            List<Attendance> attendances = new ArrayList<>();
            attendances.add(attendance);
            Attendance.append(attendances);
            dialog.cancel();
        });
        binding.cancel.setOnClickListener(v -> {
            dialog.cancel();
        });
        dialog.setContentView(binding.getRoot(), params);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        Toast.makeText(this, "Working", Toast.LENGTH_SHORT).show();
    }
}