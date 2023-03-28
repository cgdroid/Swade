package com.tmhnry.swade.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.tmhnry.swade.Home;
import com.tmhnry.swade.R;
import com.tmhnry.swade.databinding.FragmentHomeBinding;
import com.tmhnry.swade.gridview.DepartmentsViewAdapter;
import com.tmhnry.swade.model.Department;
import com.tmhnry.swade.model.Entity;
import com.tmhnry.swade.model.Model;
import com.tmhnry.swade.model.Stock;
import com.tmhnry.swade.recyclerview.StocksViewAdapter;
import com.tmhnry.swade.singleton.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pl.droidsonroids.gif.GifImageButton;

public class HomeFragment extends Fragment {
    public static final String FRAGMENT_ID = "tmhnry.employeeproductivity.homefragment";
    private FragmentHomeBinding binding;
    Department.FirebaseQueryListener fbQueryListener;
    Dialog searchDialog;
    Dialog addDepartment;
    ImageView searchBtn;
    ImageView deleteBtn;
    View confirmBtn;
    GridView gridView;
    RecyclerView recyclerView;
    StocksViewAdapter stocksAdapter;
    DepartmentSetupFragment.Mode mode;
    List<Department> departments;
    List<Stock> stocks;
    DepartmentsViewAdapter departmentsAdapter;
    DepartmentQueryListener departmentQueryListener;
    DepartmentsViewAdapter.CheckboxClickListener listener;
    List<Integer> temp;

    public interface DepartmentQueryListener {
        void onAddDepartment(Map<String, Object> data);

        void onDeleteDepartment(List<Integer> temp);
    }

    public HomeFragment() {
    }

    public static HomeFragment Builder() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        stocks = new ArrayList<>();
        departments = new ArrayList<>();
        departments.addAll(Department.getModels().values());
        stocks.clear();
        stocks.addAll(Stock.getModels().values());
        stocksAdapter = new StocksViewAdapter(getContext(), stocks);
        fbQueryListener = (Department.FirebaseQueryListener) context;
        temp = new ArrayList<>();
        mode = DepartmentSetupFragment.Mode.NORMAL;
        listener = new DepartmentsViewAdapter.CheckboxClickListener() {
            @Override
            public void onCheckboxChanged(int position, boolean isChecked) {
                if (isChecked) {
                    temp.add(position);
                    return;
                }
                temp.remove(Integer.valueOf(position));
            }
        };

        setAddDepartmentDialog(context);
        setSearchDepartmentDialog(context);

//        FileInputStream fileInputStream = null;
//        InputStreamReader inputStreamReader = null;
//        BufferedReader reader = null;
//        StringBuilder stringBuilder = new StringBuilder();

//        try {
//            fileInputStream = context.openFileInput("count_plot.png");
//            inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
//            reader = new BufferedReader(inputStreamReader);
//            String line = reader.readLine();
//            while (line != null) {
//                stringBuilder.append(line).append('\n');
//                line = reader.readLine();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            byte imageData[] = android.util.Base64.decode(stringBuilder.toString(), Base64.DEFAULT);
//            bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
//        }
//        try {
//            fileInputStream = context.openFileInput("count_plot.png");
//            bitmap = BitmapFactory.decodeStream(fileInputStream);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        binding.companyName.setText((String) Company.retrieve(getContext(), Company.NAME));
        gridView = binding.gridView;
        recyclerView = binding.recyclerView;
        ((Home) getActivity()).onCartIconClicked();
        recyclerView.setAdapter(stocksAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        updateStocks("");
        confirmBtn = binding.wrapperConfirm;
        confirmBtn.setVisibility(View.GONE);
        searchBtn = binding.imgSearch;
        deleteBtn = binding.imgDelete;
        confirmBtn.setOnClickListener(v -> {
            v.setVisibility(View.GONE);
            departmentQueryListener.onDeleteDepartment(temp);
            mode = DepartmentSetupFragment.Mode.NORMAL;
            updateGridView(true);
            temp.clear();
            clearInputs();
        });

//        binding.imgAddDepartment.setOnClickListener(v -> {
//            if (mode == DepartmentSetupFragment.Mode.DELETE) {
//                Toast.makeText(getContext(), "Invalid: Deletion is in Progress", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            addDepartment.show();
//        });

        searchBtn.setOnClickListener(v -> {
            searchDialog.show();
        });

        deleteBtn.setOnClickListener(v -> {
            if (Department.getModels().size() == 0) {
                return;
            }
            if (mode == DepartmentSetupFragment.Mode.NORMAL) {
                mode = DepartmentSetupFragment.Mode.DELETE;
                binding.wrapperConfirm.setVisibility(View.VISIBLE);
            } else {
                mode = DepartmentSetupFragment.Mode.NORMAL;
                binding.wrapperConfirm.setVisibility(View.GONE);
            }
            temp.clear();
            updateGridView(true);
        });

        binding.searchTextField.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateStocks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        if (User.getAccountType(getContext()).equals(Entity.OWNER)) {
            binding.cashoutIcon.setVisibility(View.GONE);
        }

        return binding.getRoot();
    }

    public void updateCartIcon() {
        if (Stock.getOrders().size() == 0) {
            binding.orderSize.setVisibility(View.GONE);
            return;
        }
        binding.orderSize.setVisibility(View.VISIBLE);
        binding.orderSize.setText(String.valueOf(Stock.getOrders().size()));
    }


    private void updateDepartments(String pattern) {
        departments.clear();
        for (Department department : Department.getModels().values()) {
            if (pattern.isEmpty()) {
                departments.add(department);
                continue;
            }
            if (department.getName().contains(pattern)) {
                departments.add(department);
            }
        }

//        gridView.invalidate();
        gridView.invalidateViews();
    }

    public void updateStocksRecyclerView(boolean init) {
        if (init) {
            stocksAdapter = new StocksViewAdapter(getContext(), stocks);
            recyclerView.setAdapter(stocksAdapter);
            updateStocks("");
            return;
        }
        updateStocks("");
    }

    public void updateStocks(String pattern) {
        stocks.clear();
        for (Stock stock : Stock.getModels().values()) {
            if (pattern.isEmpty()) {
                stocks.add(stock);
                continue;
            }
            if (stock.name.contains(pattern)) {
                stocks.add(stock);
            }
            if(stock.barcode.contains(pattern)){
                stocks.add(stock);
            }
        }
        stocksAdapter.notifyDataSetChanged();
    }

    public void updateGridView(boolean init) {
        if (init) {
            departmentsAdapter = new DepartmentsViewAdapter(getContext(),
                    listener,
                    departments,
                    mode);
            binding.gridView.setAdapter(departmentsAdapter);
            updateDepartments("");
            return;
        }

        updateDepartments("");
    }

    private void setSearchDepartmentDialog(Context context) {
        searchDialog = new Dialog(context);
        searchDialog.setContentView(R.layout.dialog_search_department);
        GifImageButton refGib = searchDialog.findViewById(R.id.gib_refresh);
        AppCompatButton posBtn = searchDialog.findViewById(R.id.btn_search);
        AppCompatButton negBtn = searchDialog.findViewById(R.id.btn_cancel);

        refGib.setOnClickListener(v -> {
            EditText nameInput = searchDialog.findViewById(R.id.input_name);
            nameInput.getText().clear();
            updateDepartments("");
            searchDialog.dismiss();
        });

        posBtn.setOnClickListener(v -> {
            EditText nameInput = searchDialog.findViewById(R.id.input_name);
            updateDepartments(nameInput.getText().toString().trim());
            // Use dismiss as no cancelListener is listening to the dialog
            searchDialog.dismiss();
        });

        negBtn.setOnClickListener(v -> {
            searchDialog.dismiss();
        });
    }

    private void clearInputs() {
        EditText nameInput = addDepartment.findViewById(R.id.input_name);
        EditText sizeInput = addDepartment.findViewById(R.id.input_cap);
        EditText descInput = addDepartment.findViewById(R.id.input_desc);
        nameInput.getText().clear();
        sizeInput.getText().clear();
        descInput.getText().clear();
    }

    private void setAddDepartmentDialog(Context context) {
        addDepartment = new Dialog(context);
        addDepartment.setContentView(R.layout.dialog_add_department);
        AppCompatButton posBtn = addDepartment.findViewById(R.id.btn_add);
        AppCompatButton negBtn = addDepartment.findViewById(R.id.btn_cancel);
        addDepartment.setOnCancelListener(dialog -> {
            clearInputs();
        });
        posBtn.setOnClickListener(v -> {
            Map<String, Object> data = validInputs();
            if (data == null) {
                return;
            }
            departmentQueryListener.onAddDepartment(data);
            addDepartment.cancel();
        });

        negBtn.setOnClickListener(v -> {
            addDepartment.cancel();
        });
    }

    private Map<String, Object> validInputs() {
        Map<String, Object> data = null;
        EditText nameInput = addDepartment.findViewById(R.id.input_name);
        EditText capInput = addDepartment.findViewById(R.id.input_cap);
        EditText descInput = addDepartment.findViewById(R.id.input_desc);
        String capS = capInput.getText().toString();
        if (capS.isEmpty()) {
            Toast.makeText(getContext(), "Invalid capacity provided", Toast.LENGTH_SHORT).show();
            return data;
        }
        Integer cap = Integer.parseInt(capS);
        if (cap < 10 || cap > 200) {
            Toast.makeText(getContext(), "Invalid capacity provided", Toast.LENGTH_SHORT).show();
            return data;
        }
        String name = "";
        name = nameInput.getText().toString().trim();
        if (name.length() < 4 || name.length() > 32) {
            Toast.makeText(getContext(), "A succinct name is needed Consider using abbreviations", Toast.LENGTH_SHORT).show();
            return data;
        }
        String[] nameA = name.split(" ");
        StringBuilder sbName = new StringBuilder();
        for (String nameE : nameA) {
            sbName.append(nameE.substring(0, 1).toUpperCase(Locale.ROOT));
            sbName.append(nameE.substring(1).toLowerCase(Locale.ROOT));
            sbName.append(" ");
        }

        String desc = "";
        desc = descInput.getText().toString().trim();
        if (desc.length() < 10) {
            Toast.makeText(getContext(), "Please provide a more specific description", Toast.LENGTH_SHORT).show();
            return data;
        }
        data = new HashMap<>();
        data.putIfAbsent(Model.ID, Department.getRandomPublicId(getContext()));
        data.putIfAbsent(Department.NAME, sbName.toString().trim());
        data.putIfAbsent(Department.DESCRIPTION, desc);
        data.putIfAbsent(Department.CAPACITY, cap);
        data.putIfAbsent(Department.SIZE, 0);
        return data;
    }

}