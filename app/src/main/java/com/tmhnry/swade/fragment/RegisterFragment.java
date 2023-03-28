package com.tmhnry.swade.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tmhnry.swade.LoginRegister;
import com.tmhnry.swade.R;
import com.tmhnry.swade.singleton.User;
import com.tmhnry.swade.databinding.FragmentRegisterBinding;
import com.tmhnry.swade.databinding.TextfieldBinding;

import java.util.HashMap;
import java.util.Map;

import kotlin.text.Regex;

public class RegisterFragment extends Fragment {
    public static final String FRAGMENT_ID = "com.example.dailyfitness.REGISTER";
    FragmentRegisterBinding binding;
    private Dialog dAlert;
    private TextView vPos;
    private TextView vNeg;
    private TextView vTit;
    private TextView vMes;

    public RegisterFragment() {
    }

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        dAlert = new Dialog(getActivity());

        dAlert.setContentView(R.layout.dialog_alert_notification);

        dAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vPos = dAlert.findViewById(R.id.txt_positive);
        vNeg = dAlert.findViewById(R.id.txt_negative);
        vMes = dAlert.findViewById(R.id.txt_alert_message);
        vTit = dAlert.findViewById(R.id.dialog_title);

        vNeg.setVisibility(View.INVISIBLE);

        validateEmail();
        validateFirstName();
        validateLastName();
        validatePassword();

        binding.btnRegister.setOnClickListener(v -> {
            submitForm();
        });

        binding.wrapperLogin.setOnClickListener(v -> {
            ((LoginRegister) getActivity()).loadFragment(LoginFragment.FRAGMENT_ID);
        });

        return binding.getRoot();
    }

    private void submitForm() {
        TextfieldBinding email = binding.txtFieldEmail;
        TextfieldBinding password = binding.txtFieldPassword;
        TextfieldBinding firstName = binding.txtFieldFirstName;
        TextfieldBinding lastName = binding.txtFieldLastName;

        email.inputLayout.setHelperText(validate(R.string.email));
        password.inputLayout.setHelperText(validate(R.string.password));
        firstName.inputLayout.setHelperText(validate(R.string.first_name));
        lastName.inputLayout.setHelperText(validate(R.string.last_name));

        boolean validEm = email.inputLayout.getHelperText() == null || email.inputLayout.getHelperText().toString().isEmpty();
        boolean validPw = password.inputLayout.getHelperText() == null || password.inputLayout.getHelperText().toString().isEmpty();
        boolean validFn = firstName.inputLayout.getHelperText() == null || firstName.inputLayout.getHelperText().toString().isEmpty();
        boolean validLn = lastName.inputLayout.getHelperText() == null || lastName.inputLayout.getHelperText().toString().isEmpty();

        if (validEm && validPw && validFn && validLn) {
            String em = email.inputEditText.getText().toString();
            String pw = password.inputEditText.getText().toString();
            String fn = firstName.inputEditText.getText().toString();
            String ln = lastName.inputEditText.getText().toString();

            Map<String, Object> userCredentials = new HashMap<>();

            userCredentials.put(User.EMAIL, em);
            userCredentials.put(User.PASSWORD, pw);
            userCredentials.put(User.FIRST_NAME, fn);
            userCredentials.put(User.LAST_NAME, ln);
            User.register(userCredentials, ((LoginRegister) getActivity()).getRegisterListener());
//            resetForm();
        } else
            invalidForm();
    }

    private void resetForm() {
        TextfieldBinding email = binding.txtFieldEmail;
        TextfieldBinding password = binding.txtFieldPassword;

        vTit.setText("Form submitted");
        vMes.setText("Processing");
        vPos.setText("Continue");
        vPos.setOnClickListener(view -> {
            email.inputLayout.setHelperText("Required");
            password.inputLayout.setHelperText("Required");
            dAlert.dismiss();
        });

    }

    private void invalidForm() {
        vTit.setText("Invalid Format");
        vMes.setText("You have provided an invalid format. Please try again.");
        vPos.setText("Okay");
        vPos.setOnClickListener(view -> {
            dAlert.dismiss();
        });
        dAlert.show();
    }

    private String validate(int id) {
        String helperText = "";
        if (id == R.string.email) {
            String emailText = binding.txtFieldEmail
                    .inputEditText
                    .getText()
                    .toString()
                    .trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                return "Please provide a valid email";
            }
        }
        if (id == R.string.password) {
            String passwordText = binding.txtFieldPassword
                    .inputEditText
                    .getText()
                    .toString()
                    .trim();
            if (passwordText.length() < 8) {
                return "Password must be at least 8-characters long";
            }
            if (!passwordText.matches(String.valueOf(new Regex(".*[A-Z].*")))) {
                return "Password must contain at least 1 upper-case character";
            }
            if (!passwordText.matches(String.valueOf(new Regex(".*[a-z].*")))) {
                return "Password must contain 1 lower-case character";
            }
            if (!passwordText.matches(String.valueOf(new Regex(".*[@#\\$%^&+=].*")))) {
                return "Password must contain 1 special character (@#\\$%^&+=)";
            }
        }
        if (id == R.string.first_name) {
            String firstNameText = binding.txtFieldFirstName
                    .inputEditText
                    .getText()
                    .toString()
                    .trim();
            if (firstNameText.length() < 3) {
                return "First name is too short";
            }
            if (!firstNameText.matches(String.valueOf(new Regex(".*[A-Z].*")))) {
                return "Please provide at least 1 upper-case character";
            }
            if (!firstNameText.matches(String.valueOf(new Regex(".*[a-z].*")))) {
                return "Please provide at least 1 lower-case character";
            }
            if (firstNameText.matches(String.valueOf(new Regex(".*[@#\\$%^&+=].*")))) {
                return "We don't allow special characters (@#\\$%^&+=)";
            }
        }
        if (id == R.string.last_name) {
            String lastNameText = binding.txtFieldLastName
                    .inputEditText
                    .getText()
                    .toString()
                    .trim();
            if (lastNameText.length() < 3) {
                return "Last name is too short";
            }
            if (!lastNameText.matches(String.valueOf(new Regex(".*[A-Z].*")))) {
                return "Please provide at least 1 upper-case character";
            }
            if (!lastNameText.matches(String.valueOf(new Regex(".*[a-z].*")))) {
                return "Please provide at least 1 lower-case character";
            }
            if (lastNameText.matches(String.valueOf(new Regex(".*[@#\\$%^&+=].*")))) {
                return "We don't allow special characters (@#\\$%^&+=)";
            }
        }
        return helperText;
    }

    private void validateEmail() {
        TextfieldBinding _b = binding.txtFieldEmail;
        _b.inputLayout.setStartIconDrawable(R.drawable.icons8_envelope_24);
        _b.inputLayout.setEndIconVisible(false);
        _b.inputLayout.setHint("Email Address");
        _b.inputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        _b.inputEditText.setOnFocusChangeListener((v, focused) -> {
            if (!focused) {
                _b.inputLayout.setHelperText(validate(R.string.email));
            }
        });
    }

    private void validatePassword() {
        TextfieldBinding _b = binding.txtFieldPassword;
        _b.inputLayout.setHint("Password");
        _b.inputLayout.setStartIconDrawable(R.drawable.icons8_lock_24);
//        _b.inputLayout.setHelperText("must be a valid email");
        _b.inputEditText.setOnFocusChangeListener((v, focused) -> {
            if (!focused) {
                _b.inputLayout.setHelperText(validate(R.string.password));
            }
        });
    }

    private void validateFirstName() {
        TextfieldBinding _b = binding.txtFieldFirstName;
        _b.inputLayout.setHint("First Name");
        _b.inputLayout.setEndIconVisible(false);
        _b.inputLayout.setStartIconDrawable(R.drawable.icons8_contacts_24);
        _b.inputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        _b.inputEditText.setOnFocusChangeListener((v, focused) -> {
            if (!focused) {
                _b.inputLayout.setHelperText(validate(R.string.first_name));
            }
        });
    }

    private void validateLastName() {
        TextfieldBinding _b = binding.txtFieldLastName;
        _b.inputLayout.setHint("Last Name");
        _b.inputLayout.setEndIconVisible(false);
        _b.inputLayout.setStartIconDrawable(R.drawable.icons8_contacts_24);
        _b.inputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        _b.inputEditText.setOnFocusChangeListener((v, focused) -> {
            if (!focused) {
                _b.inputLayout.setHelperText(validate(R.string.last_name));
            }
        });
    }
}