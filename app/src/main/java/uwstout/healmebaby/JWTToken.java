package uwstout.healmebaby;

import android.app.Application;

/**
 * Created by Student on 5/4/2017.
 */

public class JWTToken extends Application {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
