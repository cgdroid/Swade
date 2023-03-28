package com.tmhnry.swade.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tmhnry.swade.ApplicationSetup;
import com.tmhnry.swade.R;
import com.tmhnry.swade.databinding.FragmentApplyCompanyBinding;
import com.tmhnry.swade.databinding.TextfieldBinding;
import com.tmhnry.swade.model.Notification;
import com.tmhnry.swade.singleton.Company;
import com.tmhnry.swade.singleton.User;

import java.util.HashMap;

import kotlin.text.Regex;

public class ApplyCompanyFragment extends Fragment {
    public static final String FRAGMENT_ID = "tmhnry.employeeproductivity.applycompanyfragment";
    FragmentApplyCompanyBinding binding;
    AppCompatButton $apply;
    Dialog alert;
    Company.OnJoinCompanyListener listener;
    RadioGroup $marital;
    RadioGroup $gender;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (Company.OnJoinCompanyListener) context;
    }

    public ApplyCompanyFragment() {
    }

    public static ApplyCompanyFragment Builder() {
        ApplyCompanyFragment fragment = new ApplyCompanyFragment();
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
        binding = FragmentApplyCompanyBinding.inflate(inflater, container, false);
        setContactInput();
        setEmailInput();
        setNameInput();
        setSalaryInput();
        setAddressInput();
        $apply = binding.apply;
        $apply.setOnClickListener(v -> {
            submitForm();
        });
        $marital = binding.rgMarital;
        $gender = binding.rgGender;
        return binding.getRoot();
    }

    private void setEmailInput() {
        TextfieldBinding inputField = binding.companyEmail;
        inputField.inputLayout.setHint("Company Email");
        inputField.inputLayout.setEndIconVisible(false);
        inputField.inputLayout.setStartIconDrawable(R.drawable.icons8_envelope_24);
        inputField.inputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        inputField.inputEditText.setOnFocusChangeListener((v, focused) -> {
            if (!focused) {
                inputField.inputLayout.setHelperText(validate(R.string.company_email));
            }
        });
    }

    private void setAddressInput() {
        TextfieldBinding inputField = binding.address;
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

    private void setNameInput() {
        TextfieldBinding inputField = binding.displayName;
        inputField.inputLayout.setHint("Display Name");
        inputField.inputEditText.setText(User.getFullName(getContext()));
        inputField.inputLayout.setEndIconVisible(false);
        inputField.inputLayout.setStartIconDrawable(R.drawable.icons8_contacts_24);
        inputField.inputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        inputField.inputEditText.setOnFocusChangeListener((v, focused) -> {
            if (!focused) {
                inputField.inputLayout.setHelperText(validate(R.string.company_name));
            }
        });
    }

    private void setContactInput() {
        TextfieldBinding inputField = binding.contactNumber;
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

    private void setSalaryInput() {
        TextfieldBinding inputField = binding.salary;
        inputField.inputLayout.setHint("Desired Monthly Salary (PHP)");
        inputField.inputLayout.setEndIconVisible(false);
        inputField.inputLayout.setStartIconDrawable(R.drawable.icons8_ruble_24);
        inputField.inputEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputField.inputEditText.setOnFocusChangeListener((v, focused) -> {
            if (!focused) {
                inputField.inputLayout.setHelperText(validate(R.string.salary));
            }
        });
    }

    private String validate(int id) {
        String helperText = "";
        if (id == R.string.company_email) {
            String emailText = binding.companyEmail
                    .inputEditText
                    .getText()
                    .toString()
                    .trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                return "Please provide a valid email";
            }

        }
        if (id == R.string.company_contact) {
            String contactText = binding.contactNumber
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
            String addressText = binding.displayName
                    .inputEditText
                    .getText()
                    .toString();
            if (addressText.length() < 3) {
                return "Display name is too short";
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
            String addressText = binding.address
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
        if (id == R.string.salary) {
            String _salary = binding.salary
                    .inputEditText
                    .getText()
                    .toString().trim();
            if (_salary.isEmpty()) {
                return "Please provide a desired salary";
            }
            Integer salary = Integer.parseInt(_salary);
            if (salary < 0) {
                return "Invalid salary provided";
            }
        }
        return helperText;
    }

    private void submitForm() {
        TextfieldBinding email = binding.companyEmail;
        TextfieldBinding name = binding.displayName;
        TextfieldBinding contact = binding.contactNumber;
        TextfieldBinding address = binding.address;
        TextfieldBinding salary = binding.salary;
        String msg = binding.msg.getText().toString();

        email.inputLayout.setHelperText(validate(R.string.company_email));

        boolean validEmail = email.inputLayout.getHelperText() == null || email.inputLayout.getHelperText().toString().isEmpty();
        boolean validName = name.inputLayout.getHelperText() == null || name.inputLayout.getHelperText().toString().isEmpty();
        boolean validContact = contact.inputLayout.getHelperText() == null || contact.inputLayout.getHelperText().toString().isEmpty();
        boolean validAddress = address.inputLayout.getHelperText() == null || address.inputLayout.getHelperText().toString().isEmpty();
        boolean validSalary = salary.inputLayout.getHelperText() == null || salary.inputLayout.getHelperText().toString().isEmpty();

        if ($marital.getCheckedRadioButtonId() == -1 || $gender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getContext(), "Form is incomplete", Toast.LENGTH_SHORT).show();
            return;
        }

        if (validEmail && validName && validContact && validAddress && validSalary) {
            String _email = getInputText(email);
            String _name = getInputText(name);
            String _contact = getInputText(contact);
            String _address = getInputText(address);
            Integer _salary = Integer.parseInt(getInputText(salary));

            String marital;
            int chosenMarital = $marital.getCheckedRadioButtonId();

            if (chosenMarital == R.id.rb_single) {
                marital = "Single";
            } else if (chosenMarital == R.id.rb_married) {
                marital = "Married";
            } else {
                marital = "Divorced";
            }

            String gender;
            int chosenGender = $gender.getCheckedRadioButtonId();
            if (chosenGender == R.id.rb_male) {
                gender = "Male";
            } else {
                gender = "Female";
            }

            HashMap<String, Object> data = ((ApplicationSetup) getActivity()).getData();
            data.put(Notification.MESSAGE, msg);
            data.put(Company.EMAIL, _email);
            data.put(Notification.MARITAL_STATUS, marital);
            data.put(Notification.GENDER, gender);
            data.put(Notification.CONTACT_NUMBER, _contact);
            data.put(Notification.ADDRESS, _address);
            data.put(Notification.DESIRED_SALARY, _salary);
            data.put(Notification.SENDER_NAME, _name);
            Company.join(data, listener);
//            resetForm();
        } else
            invalidForm();
    }

    private String getInputText(TextfieldBinding binding) {
        return binding.inputEditText.getText().toString().trim();
    }

    private void invalidForm() {
        alert = new Dialog(getContext());
        alert.setContentView(R.layout.dialog_alert_notification);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView title = alert.findViewById(R.id.dialog_title);
        TextView msg = alert.findViewById(R.id.txt_alert_message);
        TextView posBtn = alert.findViewById(R.id.txt_positive);
        TextView negBtn = alert.findViewById(R.id.txt_negative);

        title.setText("Invalid Format");
        msg.setText("You have provided an invalid format. Please try again.");
        posBtn.setText("Okay");
        posBtn.setOnClickListener(v -> {
            alert.cancel();
        });
        negBtn.setVisibility(View.GONE);
        negBtn.setOnClickListener(view -> {
            alert.dismiss();
        });
        alert.show();
    }
}