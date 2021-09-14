package com.pay2ved.recharge.sqlite_data.model;


public class Provider {
    public static final String TABLE_Provider = "provider";
    public static final String COLUMN_Id = "id";
    public static final String COLUMN_Provider = "provider_name";
    public static final String COLUMN_TIMES = "timestamp";

    private int id;
    private String provider_name;
    private String timestamp;


    // Create table SQL query
    public static final String Provider_TABLE =
            "CREATE TABLE " + TABLE_Provider + "("
                    + COLUMN_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_Provider + " TEXT,"
                    + COLUMN_TIMES + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Provider() {
    }

    public Provider(int id, String provider_name, String timestamp) {
        this.id = id;
        this.provider_name = provider_name;
        this.timestamp = timestamp;

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
