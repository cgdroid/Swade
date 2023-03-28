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
import com.tmhnry.swade.databinding.FragmentLoginBinding;
import com.tmhnry.swade.databinding.TextfieldBinding;

import java.util.HashMap;
import java.util.Map;

import kotlin.text.Regex;

public class LoginFragment extends Fragment {
    public static final String FRAGMENT_ID = "com.example.dailyfitness.LOGIN";
    private FragmentLoginBinding binding;
    private Dialog dAlert;
    private TextView vPos;
    private TextView vNeg;
    private TextView vTit;
    private TextView vMes;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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

        binding = FragmentLoginBinding.inflate(inflater, container, false);

        validateEmail();
        validatePassword();

        dAlert = new Dialog(getActivity());
        dAlert.setContentView(R.layout.dialog_alert_notification);

        dAlert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vPos = dAlert.findViewById(R.id.txt_positive);
        vNeg = dAlert.findViewById(R.id.txt_negative);
        vMes = dAlert.findViewById(R.id.txt_alert_message);
        vTit = dAlert.findViewById(R.id.dialog_title);

        vNeg.setVisibility(View.INVISIBLE);

//        inputLayout = inflate.findViewById(R.id.txt_field_container);
//        users.put("table-name", "users");
//        users.put("table-size", 0);

        binding.wrapperRegister.setOnClickListener(v -> {
            ((LoginRegister) getActivity()).loadFragment(RegisterFragment.FRAGMENT_ID);
        });

        binding.btnLogin.setOnClickListener(v -> {
            submitForm();
        });

//        vEmail.setPaintFlags(View.INVISIBLE);
//        vPass.setPaintFlags(View.INVISIBLE);

        return binding.getRoot();
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

    //
    private void submitForm() {
        TextfieldBinding email = binding.txtFieldEmail;
        TextfieldBinding password = binding.txtFieldPassword;
        email.inputLayout.setHelperText(validate(R.string.email));
        password.inputLayout.setHelperText(validate(R.string.password));

        boolean validEmail = email.inputLayout.getHelperText() == null || email.inputLayout.getHelperText().toString().isEmpty();
        boolean validPassword = password.inputLayout.getHelperText() == null || password.inputLayout.getHelperText().toString().isEmpty();

        if (validEmail && validPassword) {
            Map<String, Object> userCredentials = new HashMap<>();

            String em = email.inputEditText.getText().toString();
            userCredentials.put(User.EMAIL, em);

            String pw = password.inputEditText.getText().toString();
            userCredentials.put(User.PASSWORD, pw);

            User.login(userCredentials, ((LoginRegister) getActivity()).getLoginListener());
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

    //
    private void invalidForm() {
        vTit.setText("Invalid Format");
        vMes.setText("You have provided an invalid format. Please try again.");
        vPos.setText("Okay");
        vPos.setOnClickListener(view -> {
            dAlert.dismiss();
        });
        dAlert.show();
    }
    //
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
}