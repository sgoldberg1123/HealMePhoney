package uwstout.healmebaby;

import android.app.Application;

/**
 * Created by Student on 5/4/2017.
 */

public class JWTToken extends Application {

    private String token;

    // String s = ((JWTToken) this.getApplication()).getToken();
    public String getToken() {
        return token;
    }

    //((JWTToken) this.getApplication()).setToken("string");
    public void setToken(String token) {
        this.token = token;
    }
}
