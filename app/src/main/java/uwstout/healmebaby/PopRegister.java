package uwstout.healmebaby;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

/**
 * Created by Student on 3/18/2017.
 * Followed Tutorial: https://www.youtube.com/watch?v=fn5OlqQuOCk&ab_channel=FilipVujovic
 */
public class PopRegister extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pop_register);

        DisplayMetrics registerMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(registerMetrics);

        int width = registerMetrics.widthPixels;
        int height = registerMetrics.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.7));
    }
}
