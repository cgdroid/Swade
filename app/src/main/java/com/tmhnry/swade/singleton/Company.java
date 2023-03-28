package com.tmhnry.swade.singleton;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tmhnry.swade.database.Firebase;
import com.tmhnry.swade.model.Notification;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Company {
    private static final String PREFERENCES = "company-preferences";
    public static final String SETUP_POSITION = "setup-position";
    public static final String KEY = "key";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String NAME = "name";
    public static final String NUMBER = "number";
    public static final String DATA = "company-data";

    public static void update(Context context, Map<String, Object> companyData) {
        companyData.forEach((s, o) -> {
            assert (Objects.equals(s, SETUP_POSITION)
                    || Objects.equals(s, EMAIL)
                    || Objects.equals(s, ADDRESS)
                    || Objects.equals(s, NAME)
                    || Objects.equals(s, NUMBER)
                    || Objects.equals(s, KEY));
            update(context, s, o);
        });
    }

    public static String getCompanyName(Context context){
        return (String) Company.retrieve(context, NAME);
    }

    public static void update(Context context, String key, Object value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        switch (key) {
            case EMAIL:
                editor.putString(EMAIL, (String) value);
                break;
            case ADDRESS:
                editor.putString(ADDRESS, (String) value);
                break;
            case NAME:
                editor.putString(NAME, (String) value);
                break;
            case NUMBER:
                editor.putString(NUMBER, (String) value);
                break;
            case SETUP_POSITION:
                editor.putInt(SETUP_POSITION, (Integer) value);
                break;
            default:
                editor.putString(KEY, (String) value);
                break;
        }
        editor.apply();
    }

    public static void delete(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt(SETUP_POSITION, 0);

        editor.putString(EMAIL, null);

        editor.putString(ADDRESS, null);

        editor.putString(NAME, null);

        editor.putString(NUMBER, null);

        editor.putString(KEY, null);

        editor.apply();
    }

    public static Object retrieve(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        switch (key) {
            case EMAIL:
                return prefs.getString(EMAIL, null);
            case NAME:
                return prefs.getString(NAME, null);
            case ADDRESS:
                return prefs.getString(ADDRESS, null);
            case NUMBER:
                return prefs.getString(NUMBER, null);
            case SETUP_POSITION:
                return prefs.getInt(SETUP_POSITION, 0);
            default:
                return prefs.getString(KEY, null);
        }
    }

    public static void addUser(Map<String, Object> data) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userFirestoreId = (String) data.get(Notification.SENDER_KEY);
        Map<String, Object> _data = new HashMap<>();
        _data.put(User.PENDING_STATUS, null);
        _data.put(User.POSITION, data.get(User.POSITION));
        String companyKey = (String) data.get(Notification.COMPANY_KEY);
        if (companyKey != null) {
            _data.put(User.COMPANY_KEY, companyKey);
        }
        firestore.collection(Firebase.USERS).document(userFirestoreId)
                .update(_data);
    }

    private static void updateUser(Map<String, Object> data, OnJoinCompanyListener listener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userFirestoreId = (String) data.get(User.FIRESTORE_KEY);
        Map<String, Object> _data = new HashMap<>();
        _data.put(User.PENDING_STATUS, true);
        firestore.collection(Firebase.USERS).document(userFirestoreId)
                .update(_data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onJoinSuccess(data);
                    } else {
                        listener.onJoinFailure();
                    }
                });
    }


    private static void updateUser(Map<String, Object> data, OnCreateCompanyListener listener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String userFirestoreId = (String) data.get(User.FIRESTORE_KEY);
        Map<String, Object> _data = new HashMap<>();
        _data.put(User.COMPANY_KEY, data.get(KEY));
        _data.put(User.POSITION, data.get(User.POSITION));
        firestore.collection(Firebase.USERS).document(userFirestoreId)
                .update(_data)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onCreateSuccess(data);
                    } else {
                        listener.onCreateFailure();
                    }
                });
    }

    private static void addCompany(Map<String, Object> data, OnCreateCompanyListener createListener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Map<String, Object> _data = new HashMap<>();
        data.forEach((s, o) -> {
            if (!s.equals(User.FIRESTORE_KEY) && !s.equals(User.POSITION)) {
                _data.put(s, o);
            }
        });
        firestore.collection(Firebase.COMPANIES).add(_data).addOnSuccessListener(documentReference -> {
            data.put(KEY, documentReference.getId());
            updateUser(data, createListener);
        }).addOnFailureListener(e -> {
            e.printStackTrace();
            createListener.onCreateFailure();
        });
    }

    private static void addUserToCompany(Map<String, Object> userCredentials) {

    }

    public static void join(Map<String, Object> data, OnJoinCompanyListener listener) {
        assert (data.containsKey(EMAIL));
        listener.onJoinStart();
        checkJoinCompany(data, listener);
    }

    public static void checkJoinCompany(Map<String, Object> data, OnJoinCompanyListener listener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(Firebase.COMPANIES)
                .whereEqualTo(EMAIL, data.get(EMAIL)).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.size() != 0) {
                        DocumentSnapshot snapshot = queryDocumentSnapshots.getDocuments().get(0);
                        data.put(Notification.COMPANY_KEY, snapshot.getId());
                        updateUser(data, listener);
                    } else {
                        listener.onCompanyDoesntExist();
                    }
                }).addOnFailureListener(e -> {
                    e.printStackTrace();
                    listener.onJoinFailure();
                });
    }

    public static void create(Map<String, Object> data, OnCreateCompanyListener listener) {
        assert (data.containsKey(EMAIL));
        listener.onCreateStart();
        checkCreateCompany(data, listener);
    }

    public static String getKey(Context context){
        return (String) Company.retrieve(context, KEY);
    }


//    public static void join(Map<String, Object> companyData,
//                            OnJoinCompanyListener listener) {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        assert (companyData.containsKey(EMAIL));
//        String email = (String) companyData.get(EMAIL);
//        listener.onStart();
//        auth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        getData(userCredentials, listener);
//                    } else {
//                        listener.onFailure();
//                    }
//                });
//    }

    private static void checkCreateCompany(Map<String, Object> data, OnCreateCompanyListener listener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(Firebase.COMPANIES)
                .whereEqualTo(EMAIL, data.get(EMAIL)).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.size() != 0) {
                        listener.onCompanyExists();
                    } else {
                        addCompany(data, listener);
                    }
                }).addOnFailureListener(e -> {
                    e.printStackTrace();
                    listener.onCreateFailure();
                });
    }

    public interface OnCreateCompanyListener {
        void onCreateStart();

        void onCreateSuccess(Map<String, Object> data);

        void onCreateFailure();

        void onCompanyExists();
    }

    public interface OnJoinCompanyListener {
        void onJoinStart();

        void onJoinSuccess(Map<String, Object> data);

        void onJoinFailure();

        void onCompanyDoesntExist();
    }
}
