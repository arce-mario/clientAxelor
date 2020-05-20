package com.uca.isi.axelor.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Api {
    public static final int ACCESS_API_ERP = 9905;
    public static final int ACCESS_AZURE_AI = 2905;
    private final static String URL_ERP = "http://axelorerp.southcentralus.cloudapp.azure.com:8080/";
    private final static String URL_AZURE_AI = "https://ussouthcentral.services.azureml.net/workspaces/";

    private static String getBase(int option) {
        if(option == ACCESS_API_ERP){
            return URL_ERP;
        }else if (option == ACCESS_AZURE_AI){
            return URL_AZURE_AI;
        }
        return  URL_ERP;
    }

    public static ApiInterface instance(int option) {

        /*Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.getBase(option))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiInterface.class);
    }
}
