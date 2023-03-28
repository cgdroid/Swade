package com.tmhnry.swade.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmhnry.swade.CompanySetup;
import com.tmhnry.swade.R;
import com.tmhnry.swade.databinding.FragmentCreateCompanyBinding;
import com.tmhnry.swade.singleton.Company;
import com.tmhnry.swade.databinding.TextfieldBinding;

import java.util.HashMap;

import kotlin.text.Regex;


public class CreateCompanyFragment extends Fragment {
    public static final String FRAGMENT_ID = "com.example.dailyfitness.companydetailsfragment";
    FragmentCreateCompanyBinding binding;
    Dialog alertDialog;
    Company.OnCreateCompanyListener listener;

    public CreateCompanyFragment() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (Company.OnCreateCompanyListener) context;
    }

    public static CreateCompanyFragment Builder() {
        CreateCompanyFragment fragment = new CreateCompanyFragment();
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
        binding = FragmentCreateCompanyBinding.inflate(inflater, container, false);

        setNameInput();
        setEmailInput();
        setContactInput();
        setAddressInput();
        binding.gtvAddDepartments.setOnClickListener(v -> {
            ((CompanySetup) getActivity()).incrementStepper();
        });
        binding.btnCreate.setOnClickListener(v -> {
            submitForm();
        });
        binding.gtvAddDepartments.setVisibility(View.GONE);
        return binding.getRoot();
    }

    private void setNameInput() {
        TextfieldBinding inputField = binding.inputName;
        inputField.inputLayout.setHint("Name");
        inputField.inputLayout.setEndIconVisible(false);
        inputField.inputLayout.setStartIconDrawable(R.drawable.icons8_box_24);
        inputField.inputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        inputField.inputEditText.setOnFocusChangeListener((v, focused) -> {
            if (!focused) {
                inputField.inputLayout.setHelperText(validate(R.string.company_name));
            }
        });
    }

    private void setAddressInput() {
        TextfieldBinding inputField = binding.inputAddress;
        inputField.inputLayout.setHint("Address");
        inputField.inputLayout.setEndIconVisible(false);
        inputField.inputLayout.setStartIconDrawable(R.drawable.icons8_home_24);
        inputField.inputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
        inputField.inputEditText.setOnFocusChangeListener((v, focused) -> {
            if (!focused) {
                inputField.inputLayout.setHelperText(validate(R.string.company_address));
            }
        });
    }

    private void setContactInput() {
        TextfieldBinding inputField = binding.inputContactNumber;
        inputField.inputLayout.setHint("Contact Number");
        inputField.inputLayout.setEndIconVisible(false);
        inputField.inputLayout.setStartIconDrawable(R.drawable.icons8_news_24);
        inputField.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputField.inputEditText.setOnFocusChangeListener((v, focused) -> {
            if (!focused) {
                inputField.inputLayout.setHelperText(validate(R.string.company_contact));
            }
        });
    }

    private void setEmailInput() {
        TextfieldBinding inputField = binding.inputEmail;
        inputField.inputLayout.setHint("Email Address");
        inputField.inputLayout.setEndIconVisible(false);
        inputField.inputLayout.setStartIconDrawable(R.drawable.icons8_envelope_24);
        inputField.inputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        inputField.inputEditText.setOnFocusChangeListener((v, focused) -> {
            if (!focused) {
                inputField.inputLayout.setHelperText(validate(R.string.company_email));
            }
        });
    }

    private void submitForm() {
        TextfieldBinding address = binding.inputAddress;
        TextfieldBinding name = binding.inputName;
        TextfieldBinding email = binding.inputEmail;
        TextfieldBinding number = binding.inputContactNumber;

        address.inputLayout
                .setHelperText(validate(R.string.company_address));
        name.inputLayout
                .setHelperText(validate(R.string.company_name));
        email.inputLayout
                .setHelperText(validate(R.string.company_email));
        number.inputLayout
                .setHelperText(validate(R.string.company_contact));

        boolean validAddr = address.inputLayout.getHelperText() == null || address.inputLayout.getHelperText().toString().isEmpty();
        boolean validName = name.inputLayout.getHelperText() == null || name.inputLayout.getHelperText().toString().isEmpty();
        boolean validEmail = email.inputLayout.getHelperText() == null || email.inputLayout.getHelperText().toString().isEmpty();
        boolean validNum = number.inputLayout.getHelperText() == null || number.inputLayout.getHelperText().toString().isEmpty();

        if (validAddr && validName && validEmail && validNum) {
            String caddr = address.inputEditText.getText().toString();
            String cname = name.inputEditText.getText().toString();
            String cem = email.inputEditText.getText().toString();
            String cnum = number.inputEditText.getText().toString();

            HashMap<String, Object> companyData = ((CompanySetup) getActivity()).getData();
            companyData.put(Company.EMAIL, cem);
            companyData.put(Company.ADDRESS, caddr);
            companyData.put(Company.NAME, cname);
            companyData.put(Company.NUMBER, cnum);
            Company.create(companyData, listener);
//            resetForm();
        } else
            invalidForm();
    }

    private void invalidForm() {
        alertDialog = new Dialog(getContext());
        alertDialog.setContentView(R.layout.dialog_alert_notification);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView title = alertDialog.findViewById(R.id.dialog_title);
        TextView msg = alertDialog.findViewById(R.id.txt_alert_message);
        TextView posBtn = alertDialog.findViewById(R.id.txt_positive);
        TextView negBtn = alertDialog.findViewById(R.id.txt_negative);

        title.setText("Invalid Format");
        msg.setText("You have provided an invalid format. Please try again.");
        posBtn.setText("Okay");
        posBtn.setOnClickListener(v -> {
            alertDialog.cancel();
        });
        negBtn.setVisibility(View.GONE);
        negBtn.setOnClickListener(view -> {
            alertDialog.dismiss();
        });
        alertDialog.show();
    }

    private String validate(int id) {
        String helperText = "";
        if (id == R.string.company_email) {
            String emailText = binding.inputEmail
                    .inputEditText
                    .getText()
                    .toString()
                    .trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                return "Please provide a valid email";
            }

        }
        if (id == R.string.company_contact) {
            String contactText = binding.inputContactNumber
                    .inputEditText
                    .getText()
                    .toString()
                    .trim();
            if (contactText.length() < 11) {
                return "Contact number is 11-characters long";
            }
            if (contactText.matches(String.valueOf(new Regex(".*[A-Z].*")))) {
                return "Unfortunately, we don't allow letters";
            }
            if (contactText.matches(String.valueOf(new Regex(".*[a-z].*")))) {
                return "Unfortunately, we don't allow letters";
            }
            if (contactText.matches(String.valueOf(new Regex(".*[@#\\$%^&+=].*")))) {
                return "Sorry, we don't allow special characters (@#\\$%^&+=)";
            }
        }
        if (id == R.string.company_name) {
            String addressText = binding.inputName
                    .inputEditText
                    .getText()
                    .toString();
            if (addressText.length() < 3) {
                return "Company name is too short";
            }
            if (!addressText.matches(String.valueOf(new Regex(".*[A-Z].*")))) {
                return "Please provide at least 1 upper-case character";
            }
            if (!addressText.matches(String.valueOf(new Regex(".*[a-z].*")))) {
                return "Please provide at least 1 lower-case character";
            }
            if (addressText.matches(String.valueOf(new Regex(".*[@#\\$%^&+=].*")))) {
                return "We don't allow special characters (@#\\$%^&+=)";
            }
        }
        if (id == R.string.company_address) {
            String addressText = binding.inputAddress
                    .inputEditText
                    .getText()
                    .toString();
            if (addressText.length() < 8) {
                return "Please provide a more specific address";
            }
            if (addressText.matches(String.valueOf(new Regex(".*[@#\\$%^&+=].*")))) {
                return "Addresses can't contain special characters (@#\\$%^&+=)";
            }
        }
        return helperText;
    }

    public void showAddDepartmentsButton() {
        binding.gtvAddDepartments.setVisibility(View.VISIBLE);
    }
}