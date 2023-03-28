package com.tmhnry.swade.model;

import java.util.Date;
import java.util.Map;

public class Report extends Model<Report>{
    Date date;


    public static Report Model(Map<String, Object> data){
        String key = (String) data.get(KEY);
        Integer id = (Integer) data.get(ID);
        if (key != null) {
            id = key.hashCode();
        }
        assert id != null;
        return new Report(id, key, data);
    }

    public Report(Integer id, String key, Map<String, Object> data) {
        super(id, key);
    }

    @Override
    public Map<String, Object> toMap() {
        return null;
    }


}
