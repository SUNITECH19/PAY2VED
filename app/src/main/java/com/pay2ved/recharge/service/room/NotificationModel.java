package com.pay2ved.recharge.service.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static com.pay2ved.recharge.other.Constant.DB_TABLE_NAME_NOTIFICATIONS;

@Entity(tableName = DB_TABLE_NAME_NOTIFICATIONS)
public class NotificationModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "body")
    private String body;
    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "type")
    private String type;

    //===========================

    public NotificationModel() {
    }

    @Ignore
    public NotificationModel(int id) {
        this.id = id;
    }

    @Ignore
    public NotificationModel(String title, String body, String image, String type ) {
        this.title = title;
        this.body = body;
        this.image = image;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
