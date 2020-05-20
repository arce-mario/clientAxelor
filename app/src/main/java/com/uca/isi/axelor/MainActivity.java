package com.uca.isi.axelor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.tumblr.remember.Remember;
import com.uca.isi.axelor.api.Api;
import com.uca.isi.axelor.entities.User;
import com.uca.isi.axelor.ui.login.LoginActivity;
import com.uca.isi.axelor.utilities.LocalDate;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private User user;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isAuthenticated();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //FloatingActionButton fab = findViewById(R.id.fab);
        //fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //        .setAction("Action", null).show());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        this.headerView = navigationView.getHeaderView(0);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if(user != null){
            showUserInformation();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private void isAuthenticated() {
        if(Remember.getString("SessionID","").isEmpty()) {
            backToSignInActivity();
        }

        String userJson = Remember.getString("CurrentUser","");
        if(!userJson.isEmpty()){
            user = new Gson().fromJson(userJson, User.class);
        }
    }

    private void showUserInformation() {
        TextView fullNameUser = headerView.findViewById(R.id.full_name_user);
        TextView emailUser = headerView.findViewById(R.id.email_user);
        TextView groupUser = headerView.findViewById(R.id.group_user);
        TextView dateUser = headerView.findViewById(R.id.date_user);
        ImageView profileImageUser = headerView.findViewById(R.id.profile_image_user);

        fullNameUser.setText(user.getFullName());
        emailUser.setText(user.getEmail());
        groupUser.setText(user.getGroup() == null ? "" : user.getGroup().getName());
        dateUser.setText(String.format("Registrado el %s",new LocalDate().getDateInString(user.getCreatedOn())));
        getImageUser(profileImageUser);
    }

    private void getImageUser(ImageView profileImageUser){
        Call<ResponseBody> call = Api.instance(Api.ACCESS_API_ERP).getImageUser(Remember.getString("SessionID",""),
                user.getId(),
                "com.axelor.auth.db.User",
                true);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,@NonNull Response<ResponseBody> response) {
                if(response.body() != null){
                    if(response.code() == 200){
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        Glide.with(getApplicationContext()).asBitmap()
                                .load(bmp)
                                .apply(new RequestOptions().circleCrop()).into(profileImageUser);
                    }
                    Log.i(getString(R.string.message), "onResponse: ".concat(String.valueOf(response.code())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,@NonNull Throwable t) {
                Log.e(getString(R.string.error_message_api), "onFailure: ", t);
            }
        });
    }

    private void backToSignInActivity()
    {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void logout(){
        Call<ResponseBody> call = Api.instance(Api.ACCESS_API_ERP).logout(Remember.getString("SessionID",""));
        Remember.clear();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                backToSignInActivity();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,@NonNull Throwable t) {
                backToSignInActivity();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if(item.getItemId() == R.id.action_logout){
            logout();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }
}
