package com.tmhnry.swade.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.tmhnry.swade.database.Firebase;
import com.tmhnry.swade.singleton.Company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Notification extends Model<Notification> {
    private static final String PREFERENCES = "notification-preferences";
    private static final String RANDOM_ID = "random-id";
    public static final String TABLE_NAME = "notifications";
    public static final String MESSAGE = "message";
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String OPENED = "opened";
    public static final String COMPANY_KEY = "company-key";
    public static final String SENDER_KEY = "sender-key";
    public static final String SENDER_NAME = "sender-name";
    public static final String GENDER = "gender";
    public static final String MARITAL_STATUS = "marital-status";
    public static final String ADDRESS = "address";
    public static final String CONTACT_NUMBER = "contact-number";
    public static final String DESIRED_SALARY = "desired-salary";
    public String companyKey;
    public String message;
    public String title;
    public String senderKey;
    public String senderName;
    public String maritalStatus;
    public String gender;
    public String address;
    public String contactNumber;
    public Integer desiredSalary;
    public Boolean opened;
    public Date date;

    public static void initModels(Context context) {
        Notifications.Create(context);
    }

    public static void reset() {
        Notifications.instance = null;
    }

    public static Map<Integer, Notification> getModels() {
        return Notifications.instance.models;
    }


    public static void update(List<Notification> notifications, boolean updateDatabase) {
        if (updateDatabase) {
            update(notifications);
        }
    }

    public static void update(List<Notification> notifications) {
        Notifications instance = Notifications.instance;
        instance.updateCloudDatabase(notifications);
    }

    public static void append(List<Notification> notifications) {
        Notifications instance = Notifications.instance;
        instance.appendToCloudDatabase(notifications);
    }


    public static void retrieve(Context context) {
        Notifications notifications = Notifications.instance;
        notifications.listener.onStartQuery(TABLE_NAME);

        Firebase.getChildReference(TABLE_NAME).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Map<String, Object> data = new HashMap<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
                    String companyKey = (String) Company.retrieve(context, COMPANY_KEY);
                    if (((String) value.get(COMPANY_KEY)).equals(companyKey) && !(Boolean) value.get(Notification.OPENED)) {
                        data.put(snapshot.getKey(), snapshot.getValue());
                    }
                }

                notifications.clear();

                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> value = (Map<String, Object>) entry.getValue();
                    value.put(KEY, entry.getKey());
                    Notification notification = Notification.Model(value);
                    notifications.append(notification.id, notification);
                }

                notifications.listener.onSuccessQuery(TABLE_NAME);
                data.clear();
            }
        }).addOnFailureListener(notifications.onFailureListener);
    }

    public static int getRandomPublicId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        int randomId = prefs.getInt(RANDOM_ID, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(RANDOM_ID, randomId + 1);
        editor.apply();
        return randomId;
    }

    public static Notification getNotification(String key) {
        return getModels()
                .values()
                .stream()
                .filter(new Predicate<Model<Notification>>() {
                    @Override
                    public boolean test(Model<Notification> transaction) {
                        return transaction.key.equals(key);
                    }
                }).collect(Collectors.toList()).get(0);
    }


    public static Notification Model(Map<String, Object> data) {
        String key = (String) data.get(KEY);
        Integer id = (Integer) data.get(ID);
        if (key != null) {
            id = key.hashCode();
        }
        assert id != null;
        return new Notification(id, key, data);
    }

    public Notification(Integer id, String key, Map<String, Object> data) {
        super(id, key);

        Object date = data.get(DATE);
        if (date instanceof String) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                this.date = format.parse((String) date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            this.date = (Date) date;
        }

        maritalStatus = (String) data.get(MARITAL_STATUS);
        gender = (String) data.get(GENDER);
        address = (String) data.get(ADDRESS);
        companyKey = (String) data.get(COMPANY_KEY);
        senderKey = (String) data.get(SENDER_KEY);
        senderName = (String) data.get(SENDER_NAME);
        contactNumber = (String) data.get(CONTACT_NUMBER);
        opened = (Boolean) data.get(OPENED);
        title = (String) data.get(TITLE);
        message = (String) data.get(MESSAGE);
        desiredSalary = toInteger(data.get(DESIRED_SALARY));
    }

    private Integer toInteger(Object value) {
        assert (value != null);
        if (value instanceof Long) {
            return ((Long) value).intValue();
        } else if (value instanceof Double) {
            return ((Double) value).intValue();
        } else if (value instanceof Float) {
            return ((Float) value).intValue();
        } else if (value instanceof Integer) {
            return (Integer) value;
        }
        throw new ClassCastException();
    }

    public static class Notifications extends Model.Queryables<Notification> {

        private static Notifications instance;

        public Notifications(String name,
                             Map<Integer, Notification> models,
                             Model.FirebaseQueryListener listener) {
            super(name, models, listener);
        }

        public static Notifications getInstance() {
            return instance;
        }

        public static Notifications Create(Context context) {
            if (instance == null) {
                @SuppressWarnings("unchecked")
                Model.FirebaseQueryListener listener = (FirebaseQueryListener) context;
                instance = new Notifications(TABLE_NAME, new HashMap<>(), listener);
            }
            instance.listener = (FirebaseQueryListener) context;
            return instance;
        }

        public static Notifications Create(FirebaseQueryListener listener) {
            if (instance == null) {
                instance = new Notifications(TABLE_NAME, new HashMap<>(), listener);
            }
            instance.listener = listener;
            return instance;
        }
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String _date = dateFormat.format(date);
        data.put(DATE, _date);
        data.put(MESSAGE, message);
        data.put(TITLE, title);
        data.put(OPENED, opened);
        data.put(COMPANY_KEY, companyKey);
        data.put(SENDER_NAME, senderName);
        data.put(SENDER_KEY, senderKey);
        data.put(GENDER, gender);
        data.put(MARITAL_STATUS, maritalStatus);
        data.put(ADDRESS, address);
        data.put(DESIRED_SALARY, desiredSalary);
        data.put(CONTACT_NUMBER, contactNumber);
        return data;
    }
}
