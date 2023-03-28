package com.tmhnry.swade.singleton;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tmhnry.swade.database.Firebase;

import java.util.Map;

public class User {
    private static final String PREFERENCES = "user-preferences";
    public static final String SETUP_COMPLETE = "setup-complete";
    public static final String DATA = "user-data";
    public static final String OPTION = "option";
    public static final String UID = "uid";
    public static final String FIRESTORE_KEY = "firestore-key";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "first-name";
    public static final String LAST_NAME = "last-name";
    public static final String PASSWORD = "password";
    public static final String POSITION = "position";
    public static final String COMPANY_ADDRESS = "company-address";
    public static final String COMPANY_EMAIL = "company-email";
    public static final String COMPANY_KEY = "company-key";
    public static final String COMPANY_NAME = "company-name";
    public static final String COMPANY_NUMBER = "company-number";
    public static final String PENDING_STATUS = "pending-status";

    public static String getAccountType(Context context) {
        return (String) retrieve(context, User.POSITION);
    }

    public static void update(Context context, String key, Object value) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        switch (key) {
            case SETUP_COMPLETE:
                editor.putBoolean(SETUP_COMPLETE, (boolean) value);
                break;
            case EMAIL:
                editor.putString(EMAIL, (String) value);
                break;
            case FIRST_NAME:
                editor.putString(FIRST_NAME, (String) value);
                break;
            case LAST_NAME:
                editor.putString(LAST_NAME, (String) value);
                break;
            case POSITION:
                editor.putString(POSITION, (String) value);
                break;
            case PENDING_STATUS:
                editor.putInt(PENDING_STATUS, (Integer) value);
                break;
            case COMPANY_KEY:
                editor.putString(COMPANY_KEY, (String) value);
                break;
            case OPTION:
                editor.putInt(OPTION, (int) value);
                break;
            case FIRESTORE_KEY:
                editor.putString(FIRESTORE_KEY, (String) value);
                break;
            default:
                editor.putString(UID, (String) value);
        }
        editor.apply();
    }

    public static String getFullName(Context context) {
        String firstName = (String) User.retrieve(context, User.FIRST_NAME);
        String lastName = (String) User.retrieve(context, User.LAST_NAME);
        return firstName + " " + lastName;
    }

    public static String getKey(Context context) {
        return (String) User.retrieve(context, FIRESTORE_KEY);
    }


    public static void delete(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(SETUP_COMPLETE, false);

        editor.putString(EMAIL, null);

        editor.putString(FIRST_NAME, null);

        editor.putString(LAST_NAME, null);

        editor.putString(POSITION, null);

        editor.putInt(PENDING_STATUS, -1);

        editor.putString(COMPANY_KEY, null);

        editor.putString(UID, null);

        editor.putInt(OPTION, -1);

        editor.putString(FIRESTORE_KEY, null);

        editor.apply();
    }

    public static Object retrieve(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        switch (key) {
            case SETUP_COMPLETE:
                return prefs.getBoolean(SETUP_COMPLETE, false);
            case EMAIL:
                return prefs.getString(EMAIL, null);
            case FIRST_NAME:
                return prefs.getString(FIRST_NAME, null);
            case LAST_NAME:
                return prefs.getString(LAST_NAME, null);
            case POSITION:
                return prefs.getString(POSITION, null);
            case PENDING_STATUS:
                return prefs.getInt(PENDING_STATUS, -1);
            case COMPANY_KEY:
                return prefs.getString(COMPANY_KEY, null);
            case OPTION:
                return prefs.getInt(OPTION, -1);
            case FIRESTORE_KEY:
                return prefs.getString(FIRESTORE_KEY, null);
            default:
                return prefs.getString(UID, null);
        }
    }

    public static FirebaseUser getCurrentUser() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return auth.getCurrentUser();
    }

    public static void logout() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
    }

    private static void createUserInFirestore(Map<String, Object> userCredentials,
                                              OnRegisterListener listener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(Firebase.USERS).add(userCredentials).addOnSuccessListener(documentReference -> {
            listener.onSuccess(userCredentials);
        }).addOnFailureListener(e -> {
            e.printStackTrace();
            listener.onFailed();
        });
    }

    private static void createUserInAuth(
            Map<String, Object> userCredentials,
            OnRegisterListener listener) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String email = (String) userCredentials.get(EMAIL);
        assert (userCredentials.containsKey(PASSWORD));
        String password = (String) userCredentials.get(PASSWORD);

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                createUserInFirestore(userCredentials, listener);
            } else {
                listener.onFailed();
            }
        });
    }


    private static void checkExistingUser(
            Map<String, Object> userCredentials,
            OnRegisterListener listener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(Firebase.USERS)
                .whereEqualTo(User.EMAIL, userCredentials.get(User.EMAIL)).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.size() != 0) {
                        listener.onUserExists();
                    } else {
                        createUserInAuth(userCredentials, listener);
                    }
                }).addOnFailureListener(e -> {
                    e.printStackTrace();
                });
    }

    public static void register(
            Map<String, Object> userCredentials,
            OnRegisterListener listener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        assert (auth != null);
        assert (firestore != null);
        assert (userCredentials.containsKey(EMAIL));
        listener.onStart();
        checkExistingUser(userCredentials, listener);
    }

    public static void login(Map<String, Object> data, OnLoginListener listener) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        assert (auth != null);
        assert (data.containsKey(EMAIL));
        String email = (String) data.get(EMAIL);
        assert (data.containsKey(PASSWORD));
        String password = (String) data.get(PASSWORD);

        listener.onStart();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getCredentials(data, listener);
                    } else {
                        listener.onFailed();
                    }
                });
    }

    private static void getCompanyData(Map<String, Object> data, OnLoginListener listener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(Firebase.COMPANIES).document((String) data.get(COMPANY_KEY))
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    data.put(COMPANY_EMAIL, queryDocumentSnapshots.get(Company.EMAIL));
                    data.put(COMPANY_ADDRESS, queryDocumentSnapshots.get(Company.ADDRESS));
                    data.put(COMPANY_NAME, queryDocumentSnapshots.get(Company.NAME));
                    data.put(COMPANY_NUMBER, queryDocumentSnapshots.get(Company.NUMBER));
                    listener.onSuccess(data);
                }).addOnFailureListener(e -> {
                    e.printStackTrace();
                    listener.onFailed();
                });
    }

    public static void getCredentials(Map<String, Object> data, OnLoginListener listener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(Firebase.USERS)
                .whereEqualTo(User.EMAIL, data.get(User.EMAIL))
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.size() != 0) {
                        DocumentSnapshot snapshot = queryDocumentSnapshots.getDocuments().get(0);
                        data.put(FIRESTORE_KEY, snapshot.getId());
                        data.put(User.FIRST_NAME, snapshot.get(User.FIRST_NAME));
                        data.put(User.LAST_NAME, snapshot.get(User.LAST_NAME));
                        data.put(User.PENDING_STATUS, snapshot.get(User.PENDING_STATUS));
                        String company = (String) snapshot.get(COMPANY_KEY);
                        if (company != null) {
                            data.put(COMPANY_KEY, company);
                            data.put(POSITION, snapshot.get(POSITION));
                            getCompanyData(data, listener);
                        } else {
                            listener.onSuccess(data);
                        }
                    } else {
                        listener.onFailed();
                    }
                }).addOnFailureListener(e -> {
                    e.printStackTrace();
                });
    }

    public enum Gender {MALE, FEMALE}

    public interface OnRegisterListener {
        void onStart();

        void onSuccess(Map<String, Object> data);

        void onUserExists();

        void onFailed();
    }

    public interface OnLoginListener {
        void onStart();

        void onSuccess(Map<String, Object> data);

        void onFailed();
    }

}
