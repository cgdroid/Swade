package com.tmhnry.swade.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.tmhnry.swade.database.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Asset extends Model<Asset> {

    public static void reset() {
        Assets.instance = null;
    }

    public static Map<Integer, Asset> getModels() {
        return Assets.instance.models;
    }

    public static int getRandomPublicId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        int randomId = prefs.getInt(RANDOM_ID, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(RANDOM_ID, randomId + 1);
        editor.apply();
        return randomId;
    }

    public static void initModels(Context context) {
        Assets.Create(context);
    }

    public static void append(List<Asset> assets) {
        Assets instance = Assets.instance;
        instance.appendToCloudDatabase(assets);
    }

    public static void update(List<Asset> assets) {
        Assets instance = Assets.instance;
        instance.updateCloudDatabase(assets);
    }

    public static void append(List<Asset> assets, boolean updateDatabase) {
        if (updateDatabase) {
            append(assets);
        } else {

        }
    }

    public static void update(List<Asset> assets, boolean updateDatabase) {
        if (updateDatabase) {
            update(assets);
        } else {

        }
    }

    public static final int BY_COMPANY = 0;
    public static final int BY_TRANSACTION = 1;
    public static final int BY_STOCK = 2;
    public static final int BY_ENTITY = 3;

    public static void retrieve(List<Transaction> transactions) {
        List<String> transactionKeys = new ArrayList<>();
        for(Transaction transaction : transactions){
            transactionKeys.add(transaction.key);
        }
        Assets assets = Assets.instance;
        assets.listener.onStartQuery(TABLE_NAME);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot _snapshot) {
                Map<String, Object> data = new HashMap<>();

                for (DataSnapshot snapshot : _snapshot.getChildren()) {
                    if (!snapshot.exists()) {
                        continue;
                    }

                    Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
                    if (transactionKeys.contains((String) value.get(TRANSACTION))) {
                        data.put(snapshot.getKey(), value);
                    }
                }

                assets.clear();

                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> value = (Map<String, Object>) entry.getValue();
                    value.put(KEY, entry.getKey());
                    Asset transaction = Asset.Model(value);
                    assets.append(transaction.id, transaction);
                }

                assets.listener.onSuccessQuery(TABLE_NAME);
                data.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.v(TAG, error.getMessage());
            }
        };

        Firebase.getChildReference(TABLE_NAME)
                .orderByChild(TRANSACTION)
                .addListenerForSingleValueEvent(listener);

    }

    public static Asset getTransactionAsset(String key) {
        return getModels()
                .values()
                .stream()
                .filter(new Predicate<Model<Asset>>() {
                            @Override
                            public boolean test(Model<Asset> transaction) {
                                return transaction.key.equals(key);
                            }
                        }
                ).collect(Collectors.toList()).get(0);
    }


    public static Asset Model(Map<String, Object> data) {
        String key = (String) data.get(KEY);
        Integer id = (Integer) data.get(ID);
        if (key != null) {
            id = key.hashCode();
        }
        assert id != null;
        return new Asset(id, key, data);
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


    public Asset(Integer id, String key, Map<String, Object> data) {
        super(id, key);
        assetQuantity = toInteger(data.get(ASSET_QUANTITY));
        assetUnit = (String) data.get(ASSET_UNIT);
        assetCategory = (String) data.get(ASSET_CATEGORY);
        assetValue = toFloat(data.get(ASSET_VALUE));
        Object transaction = data.get(TRANSACTION);

        if (transaction instanceof String) {
            this.transaction = Transaction.getTransaction((String) data.get(TRANSACTION));
        } else if (transaction instanceof Transaction) {
            this.transaction = (Transaction) transaction;
        } else {
            throw new ClassCastException();
        }

        assetName = (String) data.get(ASSET_NAME);
    }

    public static class Assets extends Model.Queryables<Asset> {

        private static Assets instance;

        public Assets(String name,
                      Map<Integer, Asset> models,
                      Model.FirebaseQueryListener listener) {
            super(name, models, listener);
        }

        public static Assets getInstance() {
            return instance;
        }

        public static Assets Create(Context context) {
            if (instance == null) {
                @SuppressWarnings("unchecked")
                Model.FirebaseQueryListener listener = (FirebaseQueryListener) context;
                instance = new Assets(TABLE_NAME, new HashMap<>(), listener);
            }
            instance.listener = (FirebaseQueryListener) context;
            return instance;
        }

        public static Assets Create(FirebaseQueryListener listener) {
            if (instance == null) {
                instance = new Assets(TABLE_NAME, new HashMap<>(), listener);
            }
            instance.listener = listener;
            return instance;
        }

    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();
        data.put(ASSET_QUANTITY, assetQuantity);
        data.put(ASSET_UNIT, assetUnit);
        data.put(ASSET_CATEGORY, assetCategory);
        data.put(ASSET_VALUE, assetValue);
        data.put(ASSET_NAME, assetName);
        data.put(TRANSACTION, transaction.key);
        return data;
    }

    public static final String TABLE_NAME = "assets";
    public static final String TRANSACTION = "transaction";
    public static final String ASSET_QUANTITY = "asset-quantity";
    public static final String ASSET_UNIT = "asset-unit";
    public static final String ASSET_VALUE = "asset-value";
    public static final String ASSET_NAME = "asset-name";
    public static final String ASSET_CATEGORY = "asset-category";

    // assetName is the name of the asset at the time of transaction
    public Transaction transaction;
    public String assetName;
    public Integer assetQuantity;
    public Float assetValue;
    public String assetUnit;
    public String assetCategory;

    private static final String TAG = "asset";
    private static final String PREFERENCES = "asset-preferences";
    private static final String RANDOM_ID = "random-id";
}
