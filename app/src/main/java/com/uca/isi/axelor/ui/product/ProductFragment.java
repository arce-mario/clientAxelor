package com.uca.isi.axelor.ui.product;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.tumblr.remember.Remember;
import com.uca.isi.axelor.R;
import com.uca.isi.axelor.adapter.ProductAdapter;
import com.uca.isi.axelor.api.Api;
import com.uca.isi.axelor.api.ApiMessage;
import com.uca.isi.axelor.data.ProductData;
import com.uca.isi.axelor.entities.Product;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    private View root;
    private SwipeRefreshLayout swipe;
    private RecyclerView listProducts;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_product, container, false);
        setRecyclerViewListProducts();
        swipeListener();
        getProducts();
        return root;
    }

    private void swipeListener(){
        swipe = root.findViewById(R.id.swipe_product);
        swipe.setOnRefreshListener(this::getProducts);
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void setRecyclerViewListProducts(){
        listProducts = root.findViewById(R.id.list_products);
        listProducts.setHasFixedSize(true);
        listProducts.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getProducts(){
        Call<ResponseBody> call = Api.instance(Api.ACCESS_API_ERP).getProducts(Remember.getString("SessionID",""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,@NonNull Response<ResponseBody> response) {
                if(response.body() != null){
                    if(response.code() == 200 && Objects.requireNonNull(response.headers().get("Content-Type")).equals("application/json")){
                        ProductData productData = new ProductData();
                        Product[] products = productData.loadData(response);
                        listProducts.setAdapter(new ProductAdapter(products,getContext()));
                        sendMessageInSnackbar(response.code());
                        swipe.setRefreshing(false);
                    }else{
                        swipe.setRefreshing(false);
                        sendMessageInSnackbar(response.code());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,@NonNull Throwable t) {
                swipe.setRefreshing(false);
                sendMessageInSnackbar(ApiMessage.DEFAULT_ERROR_CODE);
                Log.e(getString(R.string.error_message_api), "onFailure: ", t);
            }
        });
    }

    private void sendMessageInSnackbar(int code){
        /*View contextView = root.findViewById(android.R.id.content);
        String message = new ApiMessage().sendMessageOfResponseAPI(code,getContext());
        Snackbar.make(contextView,message,
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
    }
}