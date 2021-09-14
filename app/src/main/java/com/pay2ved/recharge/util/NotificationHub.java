package com.pay2ved.recharge.util;

public class NotificationHub {

    private int id;
    private String title;
    private String message;
    private String image;
    private String date;

    public NotificationHub() {
        super();
    }

    public NotificationHub(int id, String title, String message, String image, String date) {
        super();
        this.id = id;
        this.title = title;
        this.message = message;
        this.image = image;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NotificationHub other = (NotificationHub) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "NotificationHub [id=" + id + ", title=" + title + ", message="
                + message + ", date=" + date + "]";
    }
}
