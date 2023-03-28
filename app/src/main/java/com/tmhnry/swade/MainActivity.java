package com.tmhnry.swade;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseUser;
import com.tmhnry.swade.database.Firebase;
import com.tmhnry.swade.singleton.Company;
import com.tmhnry.swade.singleton.User;
import com.tmhnry.swade.singleton.User.OnLoginListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String TYPE = "type";
    public static final String DATA = "data";
    HashMap<String, Object> data;
    Dialog loading;
    OnLoginListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setContentView(R.layout.activity_main);

//        Window window = getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.setStatusBarColor(Color.argb(0, 255, 0, 0));

//        User.delete(this);
//        User.logout();

        createLoadingDialog();

        listener = new OnLoginListener() {
            @Override
            public void onStart() {
                loading.show();
            }

            @Override
            public void onSuccess(Map<String, Object> data) {
                Boolean pending = (Boolean) data.get(User.PENDING_STATUS);
                String companyKey = (String) data.get(User.COMPANY_KEY);
                loading.cancel();
                if (pending != null && pending) {
                    User.update(MainActivity.this, User.PENDING_STATUS, 1);
                    goToApplicationSetup();
                    return;
                }
                if (companyKey == null) {
                    goToSetup();
                    return;
                }
                updateLocalUser((HashMap<String, Object>) data);
                goToHome();
            }

            @Override
            public void onFailed() {

            }
        };

        Firebase.Create();
        FirebaseUser user = Firebase.getUser();

        String type = getIntent().getStringExtra(TYPE);

        if (user == null) {
            goToLogin();
            return;
        }
        if (type == null) {
            Map<String, Object> _data = new HashMap<>();
            _data.put(User.EMAIL, user.getEmail());
            User.getCredentials(_data, listener);
            return;
        }

        data = (HashMap<String, Object>) getIntent().getSerializableExtra(DATA);

        String companyKey = (String) data.get(User.COMPANY_KEY);
        Boolean pending = (Boolean) data.get(User.PENDING_STATUS);

        if(User.retrieve(this, User.FIRST_NAME) == null){
            User.update(this, User.FIRST_NAME, data.get(User.FIRST_NAME));
        }
        if(User.retrieve(this, User.LAST_NAME) == null){
            User.update(this, User.LAST_NAME, data.get(User.LAST_NAME));
        }
        if(User.retrieve(this, User.EMAIL) == null){
            User.update(this, User.EMAIL, data.get(User.EMAIL));
        }
        if(User.retrieve(this, User.POSITION) == null){
            User.update(this, User.POSITION, data.get(User.POSITION));
        }

        if (companyKey == null) {
            companyKey = (String) data.get(Company.KEY);
        }

        if (User.retrieve(this, User.FIRESTORE_KEY) == null) {
            User.update(this, User.FIRESTORE_KEY, data.get(User.FIRESTORE_KEY));
        }

        if (pending != null && pending) {
            User.update(this, User.PENDING_STATUS, 1);
            goToApplicationSetup();
            return;
        }
        if (companyKey == null) {
            goToSetup();
            return;
        }

        // Using == operator since null "type" is filtered above
        if (type.equals(User.DATA)) {
            updateLocalUser(data);
            goToHome();
            return;
        }


        updateLocalCompany(data);
        goToHome();
    }

    private void updateLocalUser(HashMap<String, Object> data) {
        Context context = getApplicationContext();

        if (!(User.retrieve(this, User.UID)
                == User.getCurrentUser().getUid())) {
            User.delete(getApplicationContext());
        }

        User.update(context, User.UID, User.getCurrentUser().getUid());
        User.update(context, User.FIRST_NAME, data.get(User.FIRST_NAME));
        User.update(context, User.LAST_NAME, data.get(User.LAST_NAME));
        User.update(context, User.EMAIL, data.get(User.EMAIL));
        User.update(context, User.POSITION, data.get(User.POSITION));
        User.update(context, User.FIRESTORE_KEY, data.get(User.FIRESTORE_KEY));
        Company.update(context, Company.EMAIL, data.get(User.COMPANY_EMAIL));
        Company.update(context, Company.ADDRESS, data.get(User.COMPANY_ADDRESS));
        Company.update(context, Company.KEY, data.get(User.COMPANY_KEY));
        Company.update(context, Company.NAME, data.get(User.COMPANY_NAME));
        Company.update(context, Company.NUMBER, data.get(User.COMPANY_NUMBER));
    }

    private void updateLocalCompany(HashMap<String, Object> data) {
        Context context = getApplicationContext();
        Company.update(context, Company.EMAIL, data.get(Company.EMAIL));
        Company.update(context, Company.ADDRESS, data.get(Company.ADDRESS));
        Company.update(context, Company.KEY, data.get(Company.KEY));
        Company.update(context, Company.NAME, data.get(Company.NAME));
        Company.update(context, Company.NUMBER, data.get(Company.NUMBER));
    }

    private void goToHome() {
        startActivity(new Intent(this, Home.class));
        finish();
    }

    private void goToSetup() {
        startActivity(new Intent(this, Setup.class));
        finish();
    }

    private void goToLogin() {
        startActivity(new Intent(this, LoginRegister.class));
        finish();
    }

    private void goToApplicationSetup() {
        startActivity(new Intent(this, ApplicationSetup.class));
        finish();
    }

    private void createLoadingDialog() {
        loading = new Dialog(this);
        loading.setContentView(R.layout.dialog_loading_indicator);
        loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    //        if (user == null) {
//            intent = new Intent(this, LoginRegister.class);
//        } else if (!isSetupComplete) {
//            int option = (int) User.retrieve(this, User.OPTION);
//            if (option == 0) {
//                intent = new Intent(this, CompanySetup.class);
//            } else if (option == 1) {
//                intent = new Intent(this, UserSetup.class);
//            } else {
//                intent = new Intent(this, Setup.class);
//            }
//        } else {
//            intent = new Intent(this, Home.class);
//        }


    //        AssetManager assetManager = getAssets();
//        Queryables model = null;
//
//        try {
//            InputStream inputStream = null;
//            inputStream = assetManager.open("model/model.pmml");
//            model = Queryables.fromInputStream(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Toast.makeText(this, String.valueOf(model), Toast.LENGTH_SHORT).show();


//        private final Queryables model = Queryables.fromInputStream()
//
//        public Double getRegressionValue (Map < String, Double > values){
//            Object[] valuesMap = Arrays.stream(model.inputNames())
//                    .map(values::get)
//                    .toArray();
//
//            Object[] result = model.predict(valuesMap);
//            return (Double) result[0];
//        }
//
//        private void updateDb () {
////        DatabaseReference ref = Department.getRoot();
////        Map<String, Object> data = new HashMap<>();
////        Runnable runnable = new Runnable() {
////            @Override
////            public void run() {
////                ref.getRoot().updateChildren(data);
////                data.put("count", num.incrementAndGet());
////                Log.v("", String.valueOf(num.get()));
////                new OrderRowHandler().postDelayed(this, 200);
////            }
////        };
//////        thread = new Thread(runnable);
////        num = new AtomicInteger(0);
////        data.putIfAbsent("count", num.get());
////        runOnUiThread(runnable);
//        }
}