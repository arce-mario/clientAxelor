package com.uca.isi.axelor.data;

import com.google.gson.Gson;
import com.uca.isi.axelor.entities.CreatedBy;
import com.uca.isi.axelor.entities.DefaultSupplierPartner;
import com.uca.isi.axelor.entities.MassUnit;
import com.uca.isi.axelor.entities.Picture;
import com.uca.isi.axelor.entities.Product;
import com.uca.isi.axelor.entities.ProductCategory;
import com.uca.isi.axelor.entities.ProductFamily;
import com.uca.isi.axelor.entities.PurchaseCurrency;
import com.uca.isi.axelor.entities.SaleCurrency;
import com.uca.isi.axelor.entities.Unit;
import com.uca.isi.axelor.entities.UpdatedBy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ProductData {

    /**
     * Método para cargar los registros provinientes del API en las correspondientes clases locales
     */
    public Product[] loadData(Response<ResponseBody> responseAPI) {
        JSONObject productResponse = changeDataType(responseAPI);
        try {
            assert productResponse != null;
            JSONArray productsJSONList = productResponse.getJSONArray("data");
            Product[] products = new Product[productsJSONList.length()];

            for (int i = 0; i < products.length; i++) {
                Product product = new Product();

                product.setId(Integer.parseInt(productsJSONList.getJSONObject(i).getString("id")));
                product.setFullName(productsJSONList.getJSONObject(i).getString("fullName"));
                product.setUpdatedOn(productsJSONList.getJSONObject(i).getString("updatedOn"));
                product.setSerialNumber(productsJSONList.getJSONObject(i).getString("serialNumber"));
                product.setVersion(Integer.parseInt(productsJSONList.getJSONObject(i).getString("version")));
                product.setProductTypeSelect(productsJSONList.getJSONObject(i).getString("productTypeSelect"));
                product.setWarrantyNbrOfMonths(Integer.parseInt(productsJSONList.getJSONObject(i).getString("warrantyNbrOfMonths")));
                product.setCostPrice(Double.parseDouble(productsJSONList.getJSONObject(i).getString("costPrice")));
                product.setCreatedOn(productsJSONList.getJSONObject(i).getString("createdOn"));
                product.setSalePrice(Double.parseDouble(productsJSONList.getJSONObject(i).getString("salePrice")));
                product.setDescription(productsJSONList.getJSONObject(i).getString("description"));
                product.setPurchasePrice(Double.parseDouble(productsJSONList.getJSONObject(i).getString("purchasePrice")));

                products[i] = setInformationRelatedEntities(productsJSONList.getJSONObject(i), product);
            }
            return products;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Método encargado de convertir la cadena json del response en un JSONObject
     */
    private JSONObject changeDataType(Response<ResponseBody> responseAPI){
        try {
            assert responseAPI.body() != null;
            String responseJSON = responseAPI.body().string();
            return new JSONObject(responseJSON);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     *  Método para cargar la información en las entidades relacionadas
     */
    private Product setInformationRelatedEntities(JSONObject jsonObject, Product product) throws JSONException {

        product.setMassUnit(new Gson().fromJson(jsonObject.getString("massUnit"), MassUnit.class));
        product.setPicture(new Gson().fromJson(jsonObject.getString("picture"), Picture.class));
        product.setUpdatedBy(new Gson().fromJson(jsonObject.getString("updatedBy"), UpdatedBy.class));
        product.setDefaultSupplierPartner(new Gson().fromJson(jsonObject.getString("defaultSupplierPartner"), DefaultSupplierPartner.class));
        product.setProductFamily(new Gson().fromJson(jsonObject.getString("productFamily"), ProductFamily.class));
        product.setUnit(new Gson().fromJson(jsonObject.getString("unit"), Unit.class));
        product.setProductCategory(new Gson().fromJson(jsonObject.getString("productCategory"), ProductCategory.class));
        product.setSaleCurrency(new Gson().fromJson(jsonObject.getString("saleCurrency"), SaleCurrency.class));
        product.setCreatedBy(new Gson().fromJson(jsonObject.getString("createdBy"), CreatedBy.class));
        product.setPurchaseCurrency(new Gson().fromJson(jsonObject.getString("purchaseCurrency"), PurchaseCurrency.class));

        return product;
    }
}
