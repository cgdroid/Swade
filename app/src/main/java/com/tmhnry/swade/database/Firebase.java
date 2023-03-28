package com.tmhnry.swade.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class Firebase {
    private static final String BASE_URL = "https://employeeproductivity-53ba5-default-rtdb.asia-southeast1.firebasedatabase.app/";
    public static final String USERS = "users";
    public static final String COMPANIES = "companies";
    public static Firebase instance;
    public FirebaseDatabase database;
    public FirebaseAuth auth;
    public static final int CREATE = 0;
    public static final int READ = 1;
    public static final int UPDATE = 2;
    public static final int DELETE = 3;

    public Firebase(FirebaseDatabase database, FirebaseAuth auth) {
        this.database = database;
        this.auth = auth;
    }

    public static void Create() {
        if (instance == null) {
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance(BASE_URL);
            FirebaseAuth auth = FirebaseAuth
                    .getInstance();
            instance = new Firebase(database, auth);
        }
    }

    public static FirebaseDatabase getDatabase() {
        return instance.database;
    }

    public static FirebaseUser getUser() {
        return instance.auth.getCurrentUser();
    }

    public static DatabaseReference getChildReference(String child) {
        return instance.database.getReference().child(child);
    }

    public interface QueryListener {

        void onStartQuery(String tag);

        void onSuccessQuery(String tag, Map<String, Object> result);

        void onFailQuery(String tag, Map<String, Object> result);

    }
}
