package com.uca.isi.axelor.entities;

public class CreatedBy {
    private String code;
    private String fullName;
    private float id;
    private float $version;


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

    public float get$version() {
        return $version;
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

    public void set$version( float $version ) {
        this.$version = $version;
    }
}
