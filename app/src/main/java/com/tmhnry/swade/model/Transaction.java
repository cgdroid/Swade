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

public class Transaction extends Model<Transaction> {

    public static void reset() {
        Transactions.instance = null;
    }

    public static Map<Integer, Transaction> getModels() {
        return Transactions.instance.models;
    }

    public static int getRandomPublicId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        int randomId = prefs.getInt(RANDOM_ID, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(RANDOM_ID, randomId + 1);
        editor.apply();
        return randomId;
    }

    public static List<Transaction> getTransactions(int type, Date start, Date end) {
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : Transaction.getModels().values()) {
            if (type == SELLER && transaction.entityTwo != null && transaction.date.after(start) && transaction.date.before(end)) {
                transactions.add(transaction);
            }
            if (type == BUYER && transaction.entityOne != null &&
                    transaction.date.after(start) && transaction.date.before(end)) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public static List<Transaction> getTransactions(int type, Date date) {
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : Transaction.getModels().values()) {
            if (type == SELLER && transaction.entityTwo != null && transaction.date.after(date)) {
                transactions.add(transaction);
            }
            if (type == BUYER && transaction.entityOne != null && transaction.date.after(date)) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }


    public static List<Transaction> getTransactions(int role, String key, int type, Date date) {
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : Transaction.getModels().values()) {
            if (role == EMPLOYEE
                    && type == SELLER
                    && transaction.entityTwo != null
                    && transaction.entityTwo.equals(key)) {
                if (transaction.date.after(date)) {
                    transactions.add(transaction);
                }
                continue;
            }
            if (role == EMPLOYEE
                    && type == BUYER
                    && transaction.entityOne != null
                    && transaction.entityOne.equals(key)) {
                if (transaction.date.after(date)) {
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }


    public static List<Transaction> getTransactions(int type) {
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : Transaction.getModels().values()) {
            if (type == SELLER && transaction.entityTwo != null) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }


    public static List<Transaction> getTransactions(int role, String key, int type) {
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : Transaction.getModels().values()) {
            if (role == EMPLOYEE
                    && type == SELLER
                    && transaction.entityTwo != null
                    && transaction.entityTwo.equals(key)) {
                transactions.add(transaction);
                continue;
            }
            if (role == EMPLOYEE
                    && type == BUYER
                    && transaction.entityOne != null
                    && transaction.entityOne.equals(key)) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }


    public static List<Transaction> getTransactions(int role, String key, int type, Date start, Date end) {
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : Transaction.getModels().values()) {
            if (role == EMPLOYEE
                    && type == SELLER
                    && transaction.entityTwo != null
                    && transaction.entityTwo.equals(key)) {
                if (transaction.date.before(end) && transaction.date.after(start)) {
                    transactions.add(transaction);
                    continue;
                }
            }
            if (role == EMPLOYEE
                    && type == BUYER
                    && transaction.entityOne != null
                    && transaction.entityOne.equals(key)) {
                if (transaction.date.before(end) && transaction.date.after(start)) {
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }

    public static void initModels(Context context) {
        Transactions.Create(context);
    }

    public static void append(List<Transaction> transactions) {
        Transactions instance = Transactions.instance;
        instance.appendToCloudDatabase(transactions);
    }

    public static void update(List<Transaction> transactions) {
        Transactions instance = Transactions.instance;
        instance.updateCloudDatabase(transactions);
    }

    public static void append(List<Transaction> transactions, boolean updateDatabase) {
        if (updateDatabase) {
            append(transactions);
        } else {

        }
    }

    public static void update(List<Transaction> transactions, boolean updateDatabase) {
        if (updateDatabase) {
            update(transactions);
        } else {

        }
    }

    public static void retrieve(Context context) {
        Transactions transactions = Transactions.instance;
        transactions.listener.onStartQuery(TABLE_NAME);
        Firebase.getChildReference(TABLE_NAME).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String companyKey = (String) Company.retrieve(context, Company.KEY);
                Map<String, Object> data = new HashMap<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
                    String companyOne = (String) value.get(COMPANY_ONE);
                    if (companyOne != null && companyOne.equals(companyKey)) {
                        data.put(snapshot.getKey(), snapshot.getValue());
                        continue;
                    }
                    String companyTwo = (String) value.get(COMPANY_TWO);
                    if (companyTwo != null && companyTwo.equals(companyKey)) {
                        data.put(snapshot.getKey(), snapshot.getValue());
                    }
                }

                transactions.clear();

                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> value = (Map<String, Object>) entry.getValue();
                    value.put(KEY, entry.getKey());
                    Transaction transaction = Transaction.Model(value);
                    transactions.append(transaction.id, transaction);
                }

                transactions.listener.onSuccessQuery(TABLE_NAME);
                data.clear();
            }
        }).addOnFailureListener(transactions.onFailureListener);
    }

    public static Transaction getTransaction(String key) {
        return getModels()
                .values()
                .stream()
                .filter(new Predicate<Model<Transaction>>() {
                            @Override
                            public boolean test(Model<Transaction> transaction) {
                                return transaction.key.equals(key);
                            }
                        }
                ).collect(Collectors.toList()).get(0);
    }


    public static Transaction Model(Map<String, Object> data) {
        String key = (String) data.get(KEY);
        Integer id = (Integer) data.get(ID);
        if (key != null) {
            id = key.hashCode();
        }
        assert id != null;
        return new Transaction(id, key, data);
    }

    private Float toFloat(Object value) {
        assert (value != null);
        if (value instanceof Long) {
            return ((Long) value).floatValue();
        } else if (value instanceof Double) {
            return ((Double) value).floatValue();
        } else if (value instanceof Integer) {
            return ((Integer) value).floatValue();
        } else if (value instanceof Float) {
            return (Float) value;
        }
        throw new ClassCastException();
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

    public Transaction(Integer id, String key, Map<String, Object> data) {
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

        companyOne = (String) data.get(COMPANY_ONE);
        companyOneType = toInteger(data.get(COMPANY_ONE_TYPE));
        companyOneAssets = (String) data.get(COMPANY_ONE_ASSETS);
        companyTwo = (String) data.get(COMPANY_TWO);
        companyTwoType = toInteger(data.get(COMPANY_TWO_TYPE));
        companyTwoAssets = (String) data.get(COMPANY_TWO_ASSETS);
        entityOne = (String) data.get(ENTITY_ONE);
        entityOneName = (String) data.get(ENTITY_ONE_NAME);
        entityTwo = (String) data.get(ENTITY_TWO);
        entityTwoName = (String) data.get(ENTITY_TWO_NAME);
        value = toFloat(data.get(VALUE));
        costValue = toFloat(data.get(COST_VALUE));
    }

    public static class Transactions extends Model.Queryables<Transaction> {

        private static Transactions instance;

        public Transactions(String name,
                            Map<Integer, Transaction> models,
                            Model.FirebaseQueryListener listener) {
            super(name, models, listener);
        }

        public static Transactions getInstance() {
            return instance;
        }

        public static Transactions Create(Context context) {
            if (instance == null) {
                @SuppressWarnings("unchecked")
                Model.FirebaseQueryListener listener = (FirebaseQueryListener) context;
                instance = new Transactions(TABLE_NAME, new HashMap<>(), listener);
            }
            instance.listener = (FirebaseQueryListener) context;
            return instance;
        }

        public static Transactions Create(FirebaseQueryListener listener) {
            if (instance == null) {
                instance = new Transactions(TABLE_NAME, new HashMap<>(), listener);
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
        data.put(COMPANY_ONE, companyOne);
        data.put(COMPANY_ONE_TYPE, companyOneType);
        data.put(COMPANY_TWO, companyTwo);
        data.put(COMPANY_TWO_TYPE, companyTwoType);
        data.put(ENTITY_ONE, entityOne);
        data.put(ENTITY_TWO, entityTwo);
        data.put(ENTITY_ONE_NAME, entityOneName);
        data.put(ENTITY_TWO_NAME, entityTwoName);
        data.put(COMPANY_ONE_ASSETS, companyOneAssets);
        data.put(COMPANY_TWO_ASSETS, companyTwoAssets);
        data.put(COST_VALUE, costValue);
        data.put(VALUE, value);
        return data;
    }

    private static final String PREFERENCES = "transaction-preferences";
    private static final String RANDOM_ID = "random-id";
    public static final int EMPLOYEE = 0;
    public static final int CUSTOMER = 1;
    public static final String TABLE_NAME = "transactions";
    public static final String COMPANY_ONE = "company-one";
    public static final String COMPANY_ONE_TYPE = "company-one-type";
    public static final String COMPANY_TWO = "company-two";
    public static final String COMPANY_TWO_TYPE = "company-two-type";
    public static final String ENTITY_ONE = "entity-one";
    public static final String ENTITY_TWO = "entity-two";
    public static final String ENTITY_ONE_NAME = "entity-one-name";
    public static final String ENTITY_TWO_NAME = "entity-two-name";
    public static final String COMPANY_ONE_ASSETS = "company-one-assets";
    public static final String COMPANY_TWO_ASSETS = "company-two-assets";
    public static final String VALUE = "value";
    public static final String COST_VALUE = "cost-value";
    public static final String DATE = "date";
    //    Type A refers to Buyer
    public static final int BUYER = 0;
    //    Type B refers to Seller
    public static final int SELLER = 1;
    public String companyOne;
    public Integer companyOneType;
    public String companyTwo;
    public Integer companyTwoType;
    public String entityOne;
    public String entityTwo;
    public String entityOneName;
    public String entityTwoName;
    public String companyOneAssets;
    public String companyTwoAssets;
    public Integer uniqueStocks;
    public Integer totalStocks;
    public Float value;
    public Float costValue;
    public Date date;
}
