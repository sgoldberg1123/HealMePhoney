package uwstout.healmebaby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "healmebaby.healthapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /** go to the planner page */
    public void userPage(View view) {
        Intent planner_Intent = new Intent(this, UserProfile.class);
        startActivity(planner_Intent);
    }

    public void openPedometer(View view) {
        Intent intent = new Intent(this, PedometerActivity.class);
        startActivity(intent);
    }
}
