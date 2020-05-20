package com.uca.isi.axelor.entities;

public class ProductFamily {
    private String code;
    private String name;
    private float id;
    private float $version;


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

    public float get$version() {
        return $version;
    }

    // Setter Methods

    public void setCode( String code ) {
        this.code = code;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setId( float id ) {
        this.id = id;
    }

    public void set$version( float $version ) {
        this.$version = $version;
    }
}
