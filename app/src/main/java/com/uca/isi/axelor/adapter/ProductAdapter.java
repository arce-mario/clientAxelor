package com.uca.isi.axelor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tumblr.remember.Remember;
import com.uca.isi.axelor.R;
import com.uca.isi.axelor.api.Api;
import com.uca.isi.axelor.entities.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ViewHolder>  {
    private Product[] products;
    private Context context;

    public ProductAdapter(Product[] products, Context context) {
        this.products = products;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView name;
        private TextView description;
        private TextView forecast;
        private TextView price;

        ViewHolder(@NonNull View root) {
            super(root);
            image = root.findViewById(R.id.image_product);
            name = root.findViewById(R.id.name_product);
            description = root.findViewById(R.id.description_product);
            forecast = root.findViewById(R.id.forecast_product);
            price = root.findViewById(R.id.price_product);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_product, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    @SuppressLint("DefaultLocale")
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        Product product = products[i];
        if(product.getPicture() != null && product.getPicture().getImage() == null){
            getImage(product,holder.image);
        }else {
            try {
                Glide.with(context).asBitmap().load(product.getPicture().getImage()).apply(new RequestOptions().centerInside()).into(holder.image);
            }catch (Exception e){
                Log.e(context.getString(R.string.message), "onBindViewHolder: Image Null");
            }
        }

        if(product.getForecastValue() == 0.0){
            if(product.getSalePrice() > 0){
                getForecastInformation(product,holder.forecast);
            }else{
                holder.forecast.setText("No disponible");
            }
        }else{
            holder.forecast.setText(String.format("Pronóstico: %d %s",product.getForecastValue(),
                    product.getUnit().getName().toLowerCase().substring(0,4)));
        }

        holder.name.setText(product.getFullName());
        if(product.getDescription().equals("null")){
            holder.description.setText("");
        }else{
            holder.description.setText(Html.fromHtml(product.getDescription()));
        }
        if(product.getSaleCurrency() != null){
            holder.price.setText(String.format("%.2f %s",product.getSalePrice(),product.getSaleCurrency().getCode()));
        }else{
            holder.price.setText(String.format("%.2f",product.getSalePrice()));
        }
    }

    private void getImage(Product product, ImageView image){
        Call<ResponseBody> call = Api.instance(Api.ACCESS_API_ERP).getImage(
                Remember.getString("SessionID",""),
                product.getPicture().getId(),
                product.getPicture().getVersion(),
                product.getId(),
                "com.axelor.apps.base.db.Product"
        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,@NonNull Response<ResponseBody> response) {
                if(response.body() != null){
                    if(response.code() == 200){
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        product.getPicture().setImage(bmp);
                        Glide.with(context).asBitmap().load(bmp).apply(new RequestOptions().centerInside()).into(image);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,@NonNull Throwable t) {
                Log.e(context.getString(R.string.error_message_api), "onFailure: ", t);
            }
        });
    }

    private void getForecastInformation(Product product, TextView forecast){

        Gson gson = new Gson();
        String[] columnNames = {"amountStart","category","month","productFamily","productID","productName","sales","unit","year"};
        String[] values = {
                "0",
                product.getProductCategory()==null?"":product.getProductCategory().getName(),
                String.valueOf(new Date().getMonth()),
                product.getProductFamily()==null?"":product.getProductFamily().getName(),
                String.valueOf(product.getId()),
                product.getFullName(),
                "0",product.getUnit()==null?"":product.getUnit().getName(),"0"
        };

        String infoBody = String.format(context.getString(R.string.forecast_information_body),
                gson.toJson(columnNames),gson.toJson(values));

        JsonObject requestBody = new JsonParser().parse(infoBody).getAsJsonObject();

        Log.i(context.getString(R.string.message), gson.toJson(requestBody));

        Call<ResponseBody> call = Api.instance(Api.ACCESS_AZURE_AI).getForecastInfo(requestBody,
                "Bearer ".concat(context.getString(R.string.access_token)),
                2.0,false);

        call.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,@NonNull Response<ResponseBody> response) {
                if(response.body() != null){
                    if(response.code() == 200){
                        try {
                            JSONObject res = new JSONObject(response.body().string());
                            JSONArray result = res.getJSONObject("Results")
                                    .getJSONObject("output1")
                                    .getJSONObject("value")
                                    .getJSONArray("Values")
                                    .getJSONArray(0);
                            if(!result.get(6).toString().equals("null")){
                                product.setForecastValue((int)Double.parseDouble(result.get(6).toString()));
                                forecast.setText(String.format("Pronóstico: %d %s",product.getForecastValue(),
                                        product.getUnit().getName().toLowerCase().substring(0,4)));
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    if(product.getForecastValue() == 0.0){
                        forecast.setText(String.format("Pronóstico: %d %s",0,
                                product.getUnit().getName().toLowerCase().substring(0,4)));
                    }
                }
                Log.i(context.getString(R.string.message), "onResponse Azure connection: ".concat(String.valueOf(response.code())));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,@NonNull Throwable t) {
                Log.e(context.getString(R.string.error_message_api), "onFailure: ", t);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.length;
    }
}
