package com.tmhnry.swade.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.tmhnry.swade.database.Firebase;
import com.tmhnry.swade.classifier.RandomForestClassifier;
import com.tmhnry.swade.singleton.Company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Entity extends Model<Entity> {
    public static int MODE_ATTRITION = 0;
    public static int MODE_WORK_HOURS = 1;

    public static Map<Integer, Entity> getModels() {
        return Entities.instance.models;
    }

    public static void initModels(Context context) {
        Entities.Create(context);
    }

    public static void update(List<Entity> notifications, boolean updateDatabase) {
        if (updateDatabase) {
            update(notifications);
        }
    }

    public static List<Entity> getEmployees() {
        return Entity.getModels().values().stream().filter(entity -> {
            return !(entity.position.equals(Entity.OWNER));
        }).collect(Collectors.toList());
    }

    public static void update(List<Entity> entities) {
        Entities instance = Entities.instance;
        instance.updateCloudDatabase(entities);
    }

    public static void append(List<Entity> entities) {
        Entities instance = Entities.instance;
        instance.appendToCloudDatabase(entities);
    }

    public static void retrieve(Context context) {
        Entities entities = Entities.instance;
        entities.listener.onStartQuery(TABLE_NAME);
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

                entities.clear();
                String companyName = Company.getCompanyName(context);

                for (Map.Entry<String, Object> entry : data.entrySet()) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> value = (Map<String, Object>) entry.getValue();
                    value.put(KEY, entry.getKey());
                    value.put(COMPANY_NAME, companyName);
                    Entity entity = Entity.Model(value);
                    entities.append(entity.id, entity);
                }

                entities.listener.onSuccessQuery(TABLE_NAME);
                data.clear();
            }
        }).addOnFailureListener(entities.onFailureListener);
    }

    public static void reset() {
        Entities.instance = null;
    }

    public float getEmployeeScore(int mode, Date start, Date end) {
        float maxScore = getMaxScore(mode, start, end);

        float revenue = 0f;
        float profit = 0f;
        int hours = 0;
        int customers = 0;

        List<Attendance> _attendances = Attendance.getAttendances(Attendance.EMPLOYEE, key, start, end, Attendance.PRESENT);
        hours = _attendances.size() * 8;

        if(mode == MODE_WORK_HOURS){
            if(maxScore == 0f)
                return 0f;
            return (revenue + profit + (float) customers + (float) hours) / maxScore;
        }

        List<Transaction> _transactions = Transaction.getTransactions(Transaction.EMPLOYEE, key, Transaction.SELLER, start, end);

        for (Transaction transaction : _transactions) {
            revenue += transaction.value;
            profit += transaction.value - transaction.costValue;
        }

        customers = _transactions.size();

        if (maxScore == 0f) {
            return 0f;
        }

        return (revenue + profit + (float) customers + (float) hours) / maxScore;
    }

    private float getMaxScore(int mode, Date start, Date end) {
        float revenue = 0f;
        float profit = 0f;
        int customers = 0;
        int hours = 0;


        if(mode == MODE_WORK_HOURS){

            for (Entity entity : Entity.getEmployees()) {
                List<Attendance> _attendances = Attendance.getAttendances(Attendance.EMPLOYEE, entity.key, start, end, Attendance.PRESENT);
                hours += _attendances.size() * 8;
            }

            return revenue + profit + (float) customers + (float) hours;
        }

        for (Entity entity : Entity.getEmployees()) {
            float _revenue = 0f;
            float _profit = 0f;

            List<Transaction> _transactions = Transaction.getTransactions(Transaction.EMPLOYEE, entity.key, Transaction.SELLER, start, end);
            List<Attendance> _attendances = Attendance.getAttendances(Attendance.EMPLOYEE, entity.key, start, end, Attendance.PRESENT);

            for (Transaction transaction : _transactions) {
                _revenue += transaction.value;
                _profit += transaction.value - transaction.costValue;
            }

            revenue += _revenue;
            profit += _profit;
            hours += _attendances.size() * 8;
            customers += _transactions.size();
        }

        return revenue + profit + (float) customers + (float) hours;
    }


    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String _joinDate = dateFormat.format(joinDate);
        data.put(JOIN_DATE, _joinDate);
        data.put(HOURS_WORKED, hoursWorked);
        data.put(NAME, name);
        data.put(MONTHLY_INCOME, monthlyIncome);
        data.put(MARITAL_STATUS, maritalStatus.code);
        data.put(CONTACT_NUMBER, contactNumber);
        data.put(STOCKS_SOLD, stocksSold);
        data.put(GENDER, gender.code);
        data.put(ADDRESS, address);
        data.put(USER_KEY, userKey);
        data.put(COMPANY_KEY, companyKey);
        data.put(POSITION, position);
        return data;
    }

    public static Entity Model(Map<String, Object> data) {
        String key = (String) data.get(KEY);
        Integer id = (Integer) data.get(ID);
        if (key != null) {
            id = key.hashCode();
        }
        assert id != null;
        return new Entity(id, key, data);
    }

    public double getAttritionScore() {
        Integer totalWorkingHours = 0;
        Integer totalSales = 0;
        for (Entity entity : Entity.getModels().values()) {
            totalWorkingHours += entity.hoursWorked;
            totalSales += entity.stocksSold;
        }
        if ((totalWorkingHours + totalSales) == 0) {
            return 0D;
        }
        return (hoursWorked + stocksSold * 1f) / (totalWorkingHours + totalSales * 1f);
    }

    public int getAttrition() {
        double[] features = new double[30];
        features[0] = 2;
        features[1] = monthlyIncome / (30 * 50);
        features[2] = 1;
//        DistanceFromHome = 3
        features[3] = 3;
//        Education = 3
        features[4] = 3;
//        EducationalField =  "Marketing"
        features[5] = 2;
//        EnvironmentSatisfaction = 3
//        features[6] = 3;
        features[7] = gender.code;
        features[8] = monthlyIncome / (30 * 24 * 50);
//        Job Involvement = 3
        features[9] = 3;
//        JobLevel = 1
        features[10] = 1;
//        JobRole = "Sales Representative"
        features[11] = 8;
//        JobSatisfaction = 4
        features[12] = 4;
        features[13] = maritalStatus.code;
        features[14] = monthlyIncome / 30;
        features[15] = monthlyIncome / 30;
//        NumCompaniesWorked = 1
        features[16] = 1;
//        Overtime = "No"
        features[17] = 0;
//        PercentSalaryHike = 11
        features[18] = 11;
//        PerformanceRating = 3
        features[19] = 3;
//        RelationshipSatisfaction = 3
        features[20] = 3;
//        StockOptionLevel = 0
        features[21] = 0;
//        TotalWorkingYears = 10
        features[22] = 10;
//        TrainingTimesLastYear = 2
        features[23] = 2;
//        WorkLifeBalance = 3
        features[24] = 3;
//        YearsAtCompany = 5
        features[25] = 5;
//        YearsInCurrentRole = 2
        features[26] = 2;
//        YearsSinceLastPromotion = 0
        features[27] = 0;
//        YearsWithCurrentManager = 2
        features[28] = 2;
        features[29] = age.doubleValue();
        return RandomForestClassifier.predict(features);
    }

    public static int getRandomPublicId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        int randomId = prefs.getInt(RANDOM_ID, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(RANDOM_ID, randomId++);
        editor.apply();
        return randomId;
    }

    public Double getMonthlyIncome() {
        return monthlyIncome;
    }

    public Integer getStocksSold() {
        return stocksSold;
    }

    public Department getDepartment() {
        return department;
    }

    public void addStocks(int stocks) {
        this.stocksSold = this.stocksSold + stocks;
    }

    public Integer getAge() {
        return age;
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

    private Double toDouble(Object value) {
        assert (value != null);
        if (value instanceof Long) {
            return ((Long) value).doubleValue();
        } else if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        } else if (value instanceof Float) {
            return ((Float) value).doubleValue();
        } else if (value instanceof Double) {
            return (Double) value;
        }
        throw new ClassCastException();
    }

    public String getName() {
        return name;
    }

    public Entity(Integer id, String key, Map<String, Object> data) {
        super(id, key);

        userKey = (String) data.get(USER_KEY);
        companyKey = (String) data.get(COMPANY_KEY);
        address = (String) data.get(ADDRESS);
        monthlyIncome = toDouble(data.get(MONTHLY_INCOME));
        companyName = (String) data.get(COMPANY_NAME);
        position = (String) data.get(POSITION);
        contactNumber = (String) data.get(CONTACT_NUMBER);

        data.put(USER_KEY, userKey);
//        Object department = data.get(DEPARTMENT);
//        if (department instanceof String) {
//            this.department = Department.getDepartment((String) department);
//        } else if (department instanceof Department) {
//            this.department = (Department) department;
//        } else {
//            throw new ClassCastException();
//        }

        Object gender = data.get(GENDER);
        if (gender instanceof Long) {
            this.gender = Gender.getGender(toInteger(gender));
        } else if (gender instanceof Gender) {
            this.gender = (Gender) gender;
        } else {
            throw new ClassCastException();
        }

        Object maritalStatus = data.get(MARITAL_STATUS);
        if (maritalStatus instanceof Long) {
            this.maritalStatus = MaritalStatus.getMaritalStatus(toInteger(maritalStatus));
        } else if (maritalStatus instanceof MaritalStatus) {
            this.maritalStatus = (MaritalStatus) maritalStatus;
        } else {
            throw new ClassCastException();
        }

        Object joinDate = data.get(JOIN_DATE);
        if (joinDate instanceof String) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                this.joinDate = format.parse((String) joinDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            this.joinDate = (Date) joinDate;
        }

        Object hoursWorked = data.get(HOURS_WORKED);
        if (hoursWorked == null) {
            this.hoursWorked = 0;
        } else {
            this.hoursWorked = toInteger(hoursWorked);
        }

        Object distanceFromHome = data.get(DISTANCE_FROM_HOME);
        if (distanceFromHome == null) {
            this.distanceFromHome = 0;
        } else {
            this.distanceFromHome = toInteger(distanceFromHome);
        }

        Object stocksSold = data.get(STOCKS_SOLD);
        if (stocksSold == null) {
            this.stocksSold = 0;
        } else {
            this.stocksSold = toInteger(stocksSold);
        }

//        age = toInteger(data.get(AGE));
        name = (String) data.get(NAME);
        monitor = (Boolean) data.get(MONITOR);
        monthlyIncome = toInteger(data.get(MONTHLY_INCOME)).doubleValue();
        // Secondary Factors
        travel = (Travel) data.get(TRAVEL);
        education = (Education) data.get(EDUCATION);
        educationalField = (EducationalField) data.get(EDUCATIONAL_FIELD);
        jobRole = (JobRole) data.get(JOB_ROLE);
        environmentSatisfaction = (Integer) data.get(ENVIRONMENT_SATISFACTION);
        jobInvolvement = (Integer) data.get(JOB_INVOLVEMENT);
        jobLevel = (Integer) data.get(JOB_LEVEL);
        jobSatisfaction = (Integer) data.get(JOB_SATISFACTION);
        overtime = (Integer) data.get(OVERTIME);
        companiesWorked = (Integer) data.get(COMPANIES_WORKED);
        relationshipSatisfaction = (Integer) data.get(RELATIONSHIP_SATISFACTION);
        stockOptionLevel = (Integer) data.get(STOCK_OPTION_LEVEL);
        totalWorkingYears = (Integer) data.get(TOTAL_WORKING_YEARS);
        trainingTimesLastYear = (Integer) data.get(TRAINING_TIMES_LAST_YEAR);
        workLifeBalance = (Integer) data.get(WORK_LIFE_BALANCE);
        yearsAtCompany = (Integer) data.get(YEARS_AT_COMPANY);
        yearsInCurrentRole = (Integer) data.get(YEARS_IN_CURRENT_ROLE);
        yearsSinceLastPromotion = (Integer) data.get(YEARS_SINCE_LAST_PROMOTION);
        yearsWithCurrentManager = (Integer) data.get(YEARS_WITH_CURRENT_MANAGER);
        dailyRate = (Double) data.get(DAILY_RATE);
        hourlyRate = (Double) data.get(HOURLY_RATE);
        monthlyRate = (Double) data.get(MONTHLY_RATE);
        percentSalaryHike = (Double) data.get(PERCENT_SALARY_HIKE);
        performanceRating = (Integer) data.get(PERFORMANCE_RATING);
        monitor = (Boolean) data.get(MONITOR);
    }

    public void addHours(int hours) {
        this.hoursWorked = this.hoursWorked + hours;
    }

    public static class Entities extends Model.Queryables<Entity> {
        private static Entities instance;

        public static Entities getInstance() {
            return instance;
        }

        public Entities(String name, Map<Integer, Entity> models, FirebaseQueryListener listener) {
            super(name, models, listener);
        }

        public static Entities Create(Context context) {
            if (instance == null) {
                @SuppressWarnings("unchecked")
                FirebaseQueryListener listener = (FirebaseQueryListener) context;
                instance = new Entities(TABLE_NAME, new HashMap<>(), listener);
            }
            return instance;
        }

        public static Entities Create(FirebaseQueryListener listener) {
            if (instance == null) {
                instance = new Entities(TABLE_NAME, new HashMap<>(), listener);
            }
            return instance;
        }
    }

    public enum Travel {
        NEVER(0),
        FREQUENTLY(1),
        RARELY(2);

        int code;

        Travel(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum Education {
        PRIMARY,
        SECONDARY,
        TERTIARY,
        INFORMAL,
        NONFORMAL
    }

    public enum EducationalField {
        LIFE_SCIENCES,
        OTHER,
        MEDICAL,
        MARKETING,
        TECHNICAL_DEGREE,
        HUMAN_RESOURCES
    }

    public enum Gender {
        MALE(1),
        FEMALE(0);
        int code;

        Gender(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            if (code == 0) {
                return "Female";
            }
            return "Male";
        }

        public static Gender getGender(int code) {
            if (code == 0) {
                return FEMALE;
            }
            return MALE;
        }
    }

    public enum JobRole {
        SALES_EXECUTIVE,
        RESEARCH_SCIENTIST,
        LABORATORY_TECHNICIAN,
        MANUFACTURING_DIRECTOR,
        HEALTHCARE_REPRESENTATIVE,
        MANAGER,
        SALES_REPRESENTATIVE,
        RESEARCH_DIRECTOR,
        HUMAN_RESOURCES
    }

    public enum MaritalStatus {
        SINGLE(2),
        MARRIED(1),
        DIVORCED(0);
        int code;

        MaritalStatus(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static MaritalStatus getMaritalStatus(int code) {
            if (code == 0) {
                return DIVORCED;
            } else if (code == 1) {
                return MARRIED;
            } else {
                return SINGLE;
            }
        }

        public String getName() {
            switch (code) {
                case 0:
                    return "Single";
                case 1:
                    return "Married";
                default:
                    return "Divorced";
            }
        }
    }

    private static final String PREFERENCES = "employee-preferences";
    private static final String RANDOM_ID = "random-id";
    public static final String NAME = "name";
    public static final String AGE = "age";
    public static final String MONITOR = "add-to-monitoring";
    public static final String DISTANCE_FROM_HOME = "distance-from-home";
    public static final String MONTHLY_INCOME = "monthly-income";
    public static final String DEPARTMENT = "department";
    public static final String STOCKS_SOLD = "stocks-sold";
    public static final String MARITAL_STATUS = "marital-status";

    public static final Double MAX_INCOME = 200000D;
    public static final Double MIN_INCOME = 10000D;
    public static final Integer MAX_PRODUCT = 1400;
    public static final Integer MIN_PRODUCT = 0;
    public static final Integer MAX_AGE = 60;
    public static final Integer MIN_AGE = 15;
    public static final Integer MAX_DISTANCE = 20;
    public static final Integer MIN_DISTANCE = 1;

    public static final String USER_KEY = "user-key";
    public static final String COMPANY_KEY = "company-key";
    public static final String TRAVEL = "travel";
    public static final String EDUCATION = "education";
    public static final String EDUCATIONAL_FIELD = "educational-field";
    public static final String GENDER = "gender";
    public static final String JOB_ROLE = "job-role";
    public static final String ENVIRONMENT_SATISFACTION = "environment-satisfaction";
    public static final String JOB_INVOLVEMENT = "job-involvement";
    public static final String JOB_LEVEL = "job-level";
    public static final String JOB_SATISFACTION = "job-satisfaction";
    public static final String OVERTIME = "overtime";
    public static final String COMPANIES_WORKED = "companies-worked";
    public static final String RELATIONSHIP_SATISFACTION = "relationship-satisfaction";
    public static final String STOCK_OPTION_LEVEL = "stock-option-level";
    public static final String TOTAL_WORKING_YEARS = "total-working-years";
    public static final String TRAINING_TIMES_LAST_YEAR = "training-times-last-year";
    public static final String WORK_LIFE_BALANCE = "work-life-balance";
    public static final String YEARS_AT_COMPANY = "years-at-company";
    public static final String YEARS_IN_CURRENT_ROLE = "years-in-current-role";
    public static final String YEARS_SINCE_LAST_PROMOTION = "years-since-last-promotion";
    public static final String YEARS_WITH_CURRENT_MANAGER = "years-with-current-manager";
    public static final String DAILY_RATE = "daily-rate";
    public static final String HOURLY_RATE = "hourly-rate";
    public static final String MONTHLY_RATE = "monthly-rate";
    public static final String PERCENT_SALARY_HIKE = "percent-salary-hike";
    public static final String PERFORMANCE_RATING = "performance-rating";
    public static final String JOIN_DATE = "join-date";
    public static final String HOURS_WORKED = "hours-worked";
    public static final String ADDRESS = "address";
    public static final String TABLE_NAME = "entities";
    public static final String COMPANY_NAME = "company-name";
    public static final String OWNER = "Owner";
    public static final String EMPLOYEE = "Employee";
    public static final String POSITION = "position";
    public static final String CONTACT_NUMBER = "contact-number";

    public String userKey;
    public String position;
    public String companyName;
    public String address;
    public String companyKey;
    public String contactNumber;
    public Date joinDate;
    public Travel travel;
    public Department department;
    public Education education;
    public EducationalField educationalField;
    public Gender gender;
    public JobRole jobRole;
    public MaritalStatus maritalStatus;
    public String name;
    public Integer hoursWorked;
    public Integer age;
    public Integer distanceFromHome;
    public Integer environmentSatisfaction;
    public Integer jobInvolvement;
    public Integer jobLevel;
    public Integer jobSatisfaction;
    public Integer overtime;
    public Integer companiesWorked;
    public Integer relationshipSatisfaction;
    public Integer stockOptionLevel;
    public Integer totalWorkingYears;
    public Integer trainingTimesLastYear;
    public Integer workLifeBalance;
    public Integer yearsAtCompany;
    public Integer yearsInCurrentRole;
    public Integer yearsSinceLastPromotion;
    public Integer yearsWithCurrentManager;
    public Integer performanceRating;
    public Integer stocksSold;
    public Double dailyRate;
    public Double hourlyRate;
    public Double monthlyIncome;
    public Double monthlyRate;
    public Double percentSalaryHike;
    public Boolean monitor;
}
