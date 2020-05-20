package com.uca.isi.axelor.entities;

public class UpdatedBy {
    private String code;
    private String fullName;
    private float id;
    private float version;

    // Getter Methods

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public float getId() {
        return id;
    }

    // Setter Methods

    public void setCode( String code ) {
        this.code = code;
    }

    public void setFullName( String fullName ) {
        this.fullName = fullName;
    }

    public void setId( float id ) {
        this.id = id;
    }

    public float getVersion() {
        return version;
    }

    public void setVersion(float version) {
        this.version = version;
    }
}
