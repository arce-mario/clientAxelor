package com.uca.isi.axelor.ui.login;

import com.uca.isi.axelor.entities.Login;
import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<Login> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            Login fakeUser =
                    new Login();
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
