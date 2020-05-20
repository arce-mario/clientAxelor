package com.uca.isi.axelor.entities;

public class Product {
    private int id;
    private String fullName;
    private String updatedOn;
    private String serialNumber = null;
    private int version;
    private String productTypeSelect;
    private int warrantyNbrOfMonths;
    private Double salePrice;
    private Double costPrice;
    private String createdOn;
    private String description;
    private Double purchasePrice;
    private PurchaseCurrency PurchaseCurrency;
    private MassUnit MassUnit;
    private Picture Picture;
    private UpdatedBy UpdatedBy;
    private DefaultSupplierPartner DefaultSupplierPartner;
    private Unit Unit;
    private ProductFamily ProductFamily;
    private ProductCategory ProductCategory;
    private SaleCurrency SaleCurrency;
    private CreatedBy CreatedBy;
    private int forecastValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getProductTypeSelect() {
        return productTypeSelect;
    }

    public void setProductTypeSelect(String productTypeSelect) {
        this.productTypeSelect = productTypeSelect;
    }

    public int getWarrantyNbrOfMonths() {
        return warrantyNbrOfMonths;
    }

    public void setWarrantyNbrOfMonths(int warrantyNbrOfMonths) {
        this.warrantyNbrOfMonths = warrantyNbrOfMonths;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public PurchaseCurrency getPurchaseCurrency() {
        return PurchaseCurrency;
    }

    public void setPurchaseCurrency(PurchaseCurrency purchaseCurrency) {
        PurchaseCurrency = purchaseCurrency;
    }

    public MassUnit getMassUnit() {
        return MassUnit;
    }

    public void setMassUnit(MassUnit massUnit) {
        MassUnit = massUnit;
    }

    public Picture getPicture() {
        return Picture;
    }

    public void setPicture(Picture picture) {
        Picture = picture;
    }

    public UpdatedBy getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(UpdatedBy updatedBy) {
        UpdatedBy = updatedBy;
    }

    public DefaultSupplierPartner getDefaultSupplierPartner() {
        return DefaultSupplierPartner;
    }

    public void setDefaultSupplierPartner(DefaultSupplierPartner defaultSupplierPartner) {
        DefaultSupplierPartner = defaultSupplierPartner;
    }

    public Unit getUnit() {
        return Unit;
    }

    public void setUnit(Unit unit) {
        Unit = unit;
    }

    public ProductFamily getProductFamily() {
        return ProductFamily;
    }

    public void setProductFamily(ProductFamily productFamily) {
        ProductFamily = productFamily;
    }

    public ProductCategory getProductCategory() {
        return ProductCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        ProductCategory = productCategory;
    }

    public SaleCurrency getSaleCurrency() {
        return SaleCurrency;
    }

    public void setSaleCurrency(SaleCurrency saleCurrency) {
        SaleCurrency = saleCurrency;
    }

    public CreatedBy getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        CreatedBy = createdBy;
    }

    public int getForecastValue() {
        return forecastValue;
    }

    public void setForecastValue(int forecastValue) {
        this.forecastValue = forecastValue;
    }
}

