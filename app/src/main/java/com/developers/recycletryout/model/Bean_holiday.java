package com.developers.recycletryout.model;

/**
 * Created by Developers on 07-06-2017.
 */

public class Bean_holiday {

    public String date;
    public String content;
    public String key;
    public Bean_holiday(String date, String content,String key) {
        super();
        this.date = date;
        this.content = content;
        this.key = key;
    }

    public Bean_holiday() {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



}

