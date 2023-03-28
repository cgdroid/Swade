package com.tmhnry.swade.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.tmhnry.swade.database.Firebase;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Department extends Model<Department> {
    public static Map<Integer, Department> getModels() {
        return Departments.instance.models;
    }

    public static void initModels(Context context) {
        Departments.Create(context);
    }

    public static void retrieve() {
        Departments departments = Departments.instance;
        departments.listener.onStartQuery(TABLE_NAME);

        Firebase.getChildReference(TABLE_NAME).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Map<String, Object> data = new HashMap<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    data.put(snapshot.getKey(), snapshot.getValue());
                }

                departments.clear();

                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> value = (Map<String, Object>) entry.getValue();
                    value.put(KEY, entry.getKey());
                    Department department = Department.Model(value);
                    departments.append(department.id, department);
                }

                departments.listener.onSuccessQuery(TABLE_NAME);
                data.clear();
            }
        }).addOnFailureListener(departments.onFailureListener);
    }

    public static void reset() {
        Departments.instance = null;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();
        data.put(NAME, name);
        data.put(CAPACITY, capacity);
        data.put(SIZE, size);
        data.put(DESCRIPTION, description);
        return data;
    }

    public static Department Model(Map<String, Object> data) {
        String key = (String) data.get(KEY);
        Integer id = (Integer) data.get(ID);
        if (key != null) {
            id = key.hashCode();
        }
        assert id != null;
        return new Department(id, key, data);
    }

    public Department(Integer id, String key, Map<String, Object> data) {
        super(id, key);
        capacity = toInteger(data.get(CAPACITY));
        size = toInteger(data.get(SIZE));

        name = (String) data.get(NAME);
        description = (String) data.get(DESCRIPTION);
    }

    public static class Departments extends Model.Queryables<Department> {
        private static Departments instance;

        public Departments(String name,
                           Map<Integer, Department> models,
                           Model.FirebaseQueryListener listener) {
            super(name, models, listener);
        }

        public static Departments getInstance() {
            return instance;
        }

        public static Departments Create(Context context) {
            if (instance == null) {
                @SuppressWarnings("unchecked")
                Model.FirebaseQueryListener listener = (FirebaseQueryListener) context;
                instance = new Departments(TABLE_NAME, new HashMap<>(), listener);
            }
            return instance;
        }

        public static Departments Create(FirebaseQueryListener listener) {
            if (instance == null) {
                instance = new Departments(TABLE_NAME, new HashMap<>(), listener);
            }
            return instance;
        }
    }

    public static Department getDepartment(String key) {
        return getModels()
                .values()
                .stream()
                .filter(new Predicate<Model<Department>>() {
                    @Override
                    public boolean test(Model<Department> department) {
                        return department.key.equals(key);
                    }
                }).collect(Collectors.toList()).get(0);
    }

    public static int getRandomPublicId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        int randomId = prefs.getInt(RANDOM_ID, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(RANDOM_ID, randomId + 1);
        editor.apply();
        return randomId;
    }

    private Integer toInteger(Object value) {
        assert (value != null);
        if (value instanceof Long) {
            return ((Long) value).intValue();
        } else if (value instanceof Float) {
            return ((Float) value).intValue();
        } else if (value instanceof Integer) {
            return (Integer) value;
        }
        throw new ClassCastException();
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getSize() {
        return size;
    }

    public String getDescription() {
        return description;
    }

    public void incrementSize() {
        size += 1;
    }

    public void decrementSize() {
        size -= 1;
    }

    private static final String PREFERENCES = "department-preferences";
    private static final String RANDOM_ID = "random-id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String CAPACITY = "max-size";
    public static final String SIZE = "size";
    public static final String TABLE_NAME = "departments";

    private Integer size;
    private final Integer capacity;
    private final String name;
    private final String description;
}
