package com.developers.recycletryout.model;

/**
 * Created by Developers on 30-06-2017.
 */

public class Beans_getcities {

    String Name, State, Latitude, Longitude, Enabled, LastUpdate, CreatedDate;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getEnabled() {
        return Enabled;
    }

    public void setEnabled(String enabled) {
        Enabled = enabled;
    }

    public String getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        LastUpdate = lastUpdate;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public Beans_getcities() {
    }


    public Beans_getcities(String name, String state, String latitude, String longitude, String enabled, String lastUpdate, String createdDate) {
        Name = name;
        State = state;
        Latitude = latitude;
        Longitude = longitude;
        Enabled = enabled;
        LastUpdate = lastUpdate;
        CreatedDate = createdDate;
    }
}
