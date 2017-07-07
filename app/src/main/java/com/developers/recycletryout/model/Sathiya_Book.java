package com.developers.recycletryout.model;

/**
 * Created by Developers on 03-06-2017.
 */

public class Sathiya_Book {

    String sid,title,name,tips,statis;

    public Sathiya_Book(String sid, String title, String name, String tips, String statis) {
        this.sid = sid;
        this.title = title;
        this.name = name;
        this.tips = tips;
        this.statis = statis;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getStatis() {
        return statis;
    }

    public void setStatis(String statis) {
        this.statis = statis;
    }



    public Sathiya_Book() {
    }
}
