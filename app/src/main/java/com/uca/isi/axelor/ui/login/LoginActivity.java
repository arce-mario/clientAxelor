package com.uca.isi.axelor.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tumblr.remember.Remember;
import com.uca.isi.axelor.MainActivity;
import com.uca.isi.axelor.MyApplication;
import com.uca.isi.axelor.R;
import com.uca.isi.axelor.api.Api;
import com.uca.isi.axelor.api.ApiMessage;
import com.uca.isi.axelor.data.UserData;
import com.uca.isi.axelor.entities.Login;
import com.uca.isi.axelor.entities.User;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.sign_in);

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());

            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {
            if (loginResult == null) {
                return;
            }

            //loadingProgressBar.setVisibility(View.GONE);

            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
            setResult(Activity.RESULT_OK);
            //Complete and destroy login activity once successful
            //finish();
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(v -> {
            //loadingProgressBar.setVisibility(View.VISIBLE);
            login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
        });

    }

    public void login(String username, String password) {
        Login login = new Login();
        login.setPassword(password);
        login.setUserName(username);
        Call<ResponseBody> call = Api.instance(Api.ACCESS_API_ERP).login(login);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.body() != null){
                    if(response.code() == 200 && Integer.parseInt(Objects.requireNonNull(response.headers().get("Content-Length"))) == 0){
                        saveSession("SessionID",response.headers().values("Set-Cookie").get(0).split(";")[0],0);
                        getInformationUser(username);
                    }
                }
                Log.i(getString(R.string.message), "onResponse Login(): ".concat(call.request().body().contentType().toString()));
                Log.i(getString(R.string.message), "onResponse Login(): ".concat(call.request().url().toString()));
                Log.i(getString(R.string.message), "onResponse Login(): ".concat(String.valueOf(response.code())));
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call,@NonNull Throwable t) {
                Log.e("ERROR al iniciar sesión", "onFailure: ", t);
                Toast.makeText(getApplicationContext(), new ApiMessage().sendMessageOfResponseAPI(ApiMessage.DEFAULT_ERROR_CODE,getApplicationContext()),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getInformationUser(String userName){

        String[] fields = {"code", "fullName","group","activeCompany","email","createdOn"};
        String searchFilter = String.format(MyApplication.getContext().getString(R.string.query_search_api),
                0,1,new Gson().toJson(fields),"code","code","code",userName);
        JsonObject filter = new JsonParser().parse(searchFilter).getAsJsonObject();

        Call<UserData> call = Api.instance(Api.ACCESS_API_ERP).getCurrentUserInformation(filter,Remember.getString("SessionID",""));
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(@NonNull Call<UserData> call,@NonNull Response<UserData> response) {
                if(response.body() != null){
                    User user = response.body().getData()[0];
                    saveSession("CurrentUser",new Gson().toJson(user),1);
                }
                Toast.makeText(getApplicationContext(), new ApiMessage().sendMessageOfResponseAPI(response.code(),getApplicationContext()),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NonNull Call<UserData> call,@NonNull Throwable t) {
                Log.e("ERROR", "onFailure: ", t);
                Toast.makeText(getApplicationContext(), new ApiMessage().sendMessageOfResponseAPI(ApiMessage.DEFAULT_ERROR_CODE,getApplicationContext()),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveSession(String key, String information,int action){
        try {
            Remember.putString(key, information, (Boolean success) -> {
                if (success && action == 1) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } catch (Exception e) {
            Log.e("ERROR", "saveSession: ",e);
        }
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
