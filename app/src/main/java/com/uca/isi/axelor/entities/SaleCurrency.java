package com.uca.isi.axelor.entities;

import com.google.gson.annotations.SerializedName;

public class SaleCurrency {
    private String code;
    private String name;
    private float id;
    @SerializedName("$version")
    private float version;

    // Getter Methods

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public float getId() {
        return id;
    }

    // Setter Methods


    public float getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }

    public void setCode(String code ) {
        this.code = code;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setId( float id ) {
        this.id = id;
    }
}
