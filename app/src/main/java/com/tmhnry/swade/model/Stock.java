package com.tmhnry.swade.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.tmhnry.swade.database.Firebase;
import com.tmhnry.swade.singleton.Company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Stock extends Model<Stock> {
    public static Map<Integer, Stock> getOrders() {
        return Stocks.instance.orders;
    }

    public static Map<Integer, Stock> getSupply() {
        return Stocks.instance.supply;
    }

    public static Map<Integer, Stock> getModels() {
        return Stocks.instance.models;
    }

//    User here means the Entity selected
    public static Map<Integer, Stock> getUserSoldStocks() {
        return Stocks.instance.userSoldStocks;
    }

    public static Map<Integer, Stock> getCompanySoldStocks() {
        return Stocks.instance.companySoldStocks;
    }

    public static void initModels(Context context) {
        Stocks.Create(context);
    }

    public static void update(List<Stock> stocks) {
        Stocks instance = Stocks.instance;
        instance.updateCloudDatabase(stocks);
    }

    public static void update(List<Stock> stocks, boolean updateDatabase) {
        if (updateDatabase) {
            update(stocks);
        }
    }

    public Float getRelativeRevenue() {
        float revenue = 0f;
        for (Stock stock : getUserSoldStocks().values()) {
            revenue += stock.netIncome;
        }
        if(revenue == 0f){
            return 0f;
        }
        return netIncome / revenue;
    }

    public static void append(List<Stock> stocks) {
        Stocks instance = Stocks.instance;
        instance.appendToCloudDatabase(stocks);
    }

    public static void reset() {
        Stocks.instance = null;
    }

    public static void retrieve(Context context) {
        Stocks stocks = Stocks.instance;
        stocks.listener.onStartQuery(TABLE_NAME);
        Firebase.getChildReference(TABLE_NAME).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Map<String, Object> data = new HashMap<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
                    String companyKey = (String) Company.retrieve(context, COMPANY_KEY);
                    if (((String) value.get(COMPANY_KEY)).equals(companyKey)) {
                        data.put(snapshot.getKey(), snapshot.getValue());
                    }
                }

                stocks.clear();

                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> value = (Map<String, Object>) entry.getValue();
                    value.put(KEY, entry.getKey());
                    Stock stock = Stock.Model(value);
                    stocks.append(stock.id, stock);
                }

                stocks.listener.onSuccessQuery(TABLE_NAME);
                data.clear();
            }
        }).addOnFailureListener(stocks.onFailureListener);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();
        data.put(QUANTITY_REMAINING, quantityRemaining);
        data.put(NAME, name);
        data.put(GROUP, group);
        data.put(CODE, barcode);
        data.put(UNIT, unit);
        data.put(PRICE, price);
        data.put(COST, cost);
        data.put(COMPANY_KEY, companyKey);
        data.put(QUANTITY_SOLD, quantitySold);
        data.put(QUANTITY_ORDERED, quantityOrdered);
        data.put(NET_COST, netCost);
        data.put(NET_INCOME, netIncome);
        return data;
    }

    public static Stock Model(Map<String, Object> data) {
        String key = (String) data.get(KEY);
        Integer id = (Integer) data.get(ID);
        if (key != null) {
            id = key.hashCode();
        }
        assert id != null;
        return new Stock(id, key, data);
    }

    public Stock(Integer id, String key, Map<String, Object> data) {
        super(id, key);
        name = (String) data.get(NAME);
        group = (String) data.get(GROUP);
        unit = (String) data.get(UNIT);
        price = toFloat(data.get(PRICE));
        cost = toFloat(data.get(COST));
        companyKey = (String) data.get(COMPANY_KEY);
        quantitySold = toInteger(data.get(QUANTITY_SOLD));
        quantityRemaining = toInteger(data.get(QUANTITY_REMAINING));
        quantityOrdered = toInteger(data.get(QUANTITY_ORDERED));
        barcode = (String) data.get(CODE);
        netIncome = toFloat(data.get(NET_INCOME));
        netCost = toFloat(data.get(NET_COST));
    }


    public static class Stocks extends Model.Queryables<Stock> {
        public Map<Integer, Stock> companySoldStocks;
        public Map<Integer, Stock> orders;
        public Map<Integer, Stock> supply;
        public Map<Integer, Stock> userSoldStocks;

        private static Stocks instance;

        public static Stocks getInstance() {
            return instance;
        }

        public Stocks(String name, Map<Integer, Stock> models, FirebaseQueryListener listener) {
            super(name, models, listener);
            orders = new HashMap<>();
            supply = new HashMap<>();
            companySoldStocks = new HashMap<>();
            userSoldStocks = new HashMap<>();
        }

        public static Stocks Create(Context context) {
            if (instance == null) {
                @SuppressWarnings("unchecked")
                FirebaseQueryListener listener = (FirebaseQueryListener) context;
                instance = new Stocks(TABLE_NAME, new HashMap<>(), listener);
            }
            return instance;
        }

        public static Stocks Create(FirebaseQueryListener listener) {
            if (instance == null) {
                instance = new Stocks(TABLE_NAME, new HashMap<>(), listener);
            }
            return instance;
        }
    }

    public static void clearOrders() {
        for (Stock stock : getOrders().values()) {
            stock.quantityOrdered = 0;
        }
        getOrders().clear();
    }

    public int confirmOrder() {
//        Should I remove the first two statements?
        netIncome += quantityOrdered * price;
        netCost += quantityOrdered * cost;
        quantityRemaining -= quantityOrdered;
        quantitySold += quantityOrdered;
        return quantityOrdered;
    }

    public static int totalQuantity() {
        int quantity = 0;
        for (Stock stock : Stock.getModels().values()) {
            quantity += stock.quantityRemaining;
        }
        return quantity;
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

    public void addToCart() {
        if (getOrders().containsKey(id)) {
            Stock order = getOrders().get(id);
            assert (order != null);
            order.quantityOrdered += 1;
            return;
        }
        quantityOrdered += 1;
        getOrders().put(id, this);
        Log.v("Stock.addToCart", "Successful");
    }

    public void addToCart(int quantity) {
        if (getOrders().containsKey(id)) {
            Stock order = getOrders().get(id);
            assert (order != null);
            order.quantityOrdered += quantity;
            if (order.quantityRemaining == 0) {
                getOrders().remove(id);
            }
            return;
        }
        quantityOrdered += quantity;
        getOrders().put(id, this);
        Log.v("Stock.addToCart", "Successful");
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

    public static Stock getStock(String key) {
        return getModels().values().stream().filter(new Predicate<Stock>() {
            @Override
            public boolean test(Stock stock) {
                return stock.key.equals(key);
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

    private static final String PREFERENCES = "stock-preferences";
    private static final String RANDOM_ID = "random-id";
    public static final String QUANTITY_REMAINING = "quantity-remaining";
    public static final String QUANTITY_SOLD = "quantity-sold";
    public static final String QUANTITY_ORDERED = "quantity-ordered";
    public static final String NAME = "name";
    public static final String GROUP = "group";
    public static final String CODE = "code";
    public static final String PRICE = "price";
    public static final String COST = "cost";
    public static final String UNIT = "unit";
    public static final String COMPANY_KEY = "company-key";
    public static final String NET_COST = "net-cost";
    public static final String NET_INCOME = "net-income";
    public static final String TABLE_NAME = "stocks";

    private String companyKey;
    public String name;
    public String group;
    public String barcode;
    public String unit;
    public Integer quantitySold;
    public Integer quantityRemaining;
    public Integer quantityOrdered;
    public Float netCost;
    public Float netIncome;
    public Float price;
    public Float cost;
    //    Fields below this comment are not stored in the database. They are locally and temporarily recorded to display the relevant data in Stocks reports
    public int customers = 0;

    public void setCompanyKey(String companyKey) {
        if (this.companyKey == null) {
            this.companyKey = companyKey;
        }
    }

}
