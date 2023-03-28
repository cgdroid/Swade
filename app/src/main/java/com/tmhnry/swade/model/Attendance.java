package com.tmhnry.swade.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.tmhnry.swade.database.Firebase;
import com.tmhnry.swade.singleton.Company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Attendance extends Model<Attendance> {

    public static Map<Integer, Attendance> getModels() {
        return Attendances.instance.models;
    }

    public static void initModels(Context context) {
        Attendances.Create(context);
    }

    public static int EMPLOYEE = 0;

    public static List<Attendance> getAttendances(int role, String key) {
        List<Attendance> attendances = new ArrayList<>();
        for (Attendance attendance : Attendance.getModels().values()) {
            if (role == EMPLOYEE && attendance.employeeKey.equals(key)) {
                attendances.add(attendance);
            }
        }
        return attendances;
    }

    public static List<Attendance> getAttendances(int role, String key, Date start, Date end, String status) {
        List<Attendance> attendances = new ArrayList<>();
        for (Attendance attendance : Attendance.getModels().values()) {
            if (role == EMPLOYEE && attendance.employeeKey.equals(key) && attendance.status.equals(status)) {
                if (attendance.date.before(end) && attendance.date.after(start)) {
                    attendances.add(attendance);
                }
            }
        }
        return attendances;
    }


//    public static Attendance getAttendance(Date date){
//
//    }

    public static void append(List<Attendance> attendances) {
        Attendances instance = Attendances.instance;
        instance.appendToCloudDatabase(attendances);
    }


    public static void retrieve(Context context, String entityKey) {
        Attendances attendances = Attendances.instance;
        attendances.listener.onStartQuery(TABLE_NAME);

        Firebase.getChildReference(TABLE_NAME).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Map<String, Object> data = new HashMap<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
                    String companyKey = (String) Company.retrieve(context, COMPANY_KEY);
                    boolean forCompany = ((String) value.get(COMPANY_KEY)).equals(companyKey);
                    boolean forEntity = ((String) value.get(EMPLOYEE_KEY)).equals(entityKey);
                    if(entityKey == null){
                        forEntity = true;
                    }
                    if (forCompany && forEntity) {
                        data.put(snapshot.getKey(), snapshot.getValue());
                    }
                }

                attendances.clear();

                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> value = (Map<String, Object>) entry.getValue();
                    value.put(KEY, entry.getKey());
                    Attendance attendance = Attendance.Model(value);
                    attendances.append(attendance.id, attendance);
                }

                attendances.listener.onSuccessQuery(TABLE_NAME);
                data.clear();
            }
        }).addOnFailureListener(attendances.onFailureListener);
    }

    public static void reset() {
        Attendances.instance = null;
    }

    public static int getRandomPublicId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        int randomId = prefs.getInt(RANDOM_ID, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(RANDOM_ID, randomId + 1);
        editor.apply();
        return randomId;
    }

    public static Attendance getAttendance(String key) {
        return getModels()
                .values()
                .stream()
                .filter(new Predicate<Model<Attendance>>() {
                    @Override
                    public boolean test(Model<Attendance> transaction) {
                        return transaction.key.equals(key);
                    }
                }).collect(Collectors.toList()).get(0);
    }


    public static Attendance Model(Map<String, Object> data) {
        String key = (String) data.get(KEY);
        Integer id = (Integer) data.get(ID);
        if (key != null) {
            id = key.hashCode();
        }
        assert id != null;
        return new Attendance(id, key, data);
    }

    public Attendance(Integer id, String key, Map<String, Object> data) {
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
        employeeKey = (String) data.get(EMPLOYEE_KEY);
        status = (String) data.get(STATUS);
        type = (String) data.get(TYPE);
        companyKey = (String) data.get(COMPANY_KEY);
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

    public static class Attendances extends Model.Queryables<Attendance> {

        private static Attendances instance;

        public Attendances(String name,
                           Map<Integer, Attendance> models,
                           Model.FirebaseQueryListener listener) {
            super(name, models, listener);
        }

        public static Attendances getInstance() {
            return instance;
        }

        public static Attendances Create(Context context) {
            if (instance == null) {
                @SuppressWarnings("unchecked")
                Model.FirebaseQueryListener listener = (FirebaseQueryListener) context;
                instance = new Attendances(TABLE_NAME, new HashMap<>(), listener);
            }
            instance.listener = (FirebaseQueryListener) context;
            return instance;
        }

        public static Attendances Create(FirebaseQueryListener listener) {
            if (instance == null) {
                instance = new Attendances(TABLE_NAME, new HashMap<>(), listener);
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
        data.put(EMPLOYEE_KEY, employeeKey);
        data.put(COMPANY_KEY, companyKey);
        data.put(TYPE, type);
        data.put(STATUS, status);
        return data;
    }

    private static final String PREFERENCES = "attendance-preferences";
    private static final String RANDOM_ID = "random-id";
    public static final String TABLE_NAME = "attendances";
    public static final String STATUS = "status";
    public static final String DATE = "date";
    public static final String TYPE = "type";
    public static final String ENTER = "enter";
    public static final String PRESENT = "present";
    public static final String ABSENT = "absent";
    public static final String EXCUSED = "excused";
    public static final String LEAVE = "leave";
    public static final String COMPANY_KEY = "company-key";
    public static final String EMPLOYEE_KEY = "employee-key";
    public Date date;
    public String employeeKey;
    public String companyKey;
    public String type;
    public String status;
}
