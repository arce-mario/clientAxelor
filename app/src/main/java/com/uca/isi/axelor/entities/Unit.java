package com.uca.isi.axelor.entities;

import com.google.gson.annotations.SerializedName;

public class Unit {
    private String name;
    private int id;
    @SerializedName("$version")
    private int version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
