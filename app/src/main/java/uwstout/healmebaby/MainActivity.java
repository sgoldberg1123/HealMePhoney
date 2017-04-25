package uwstout.healmebaby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;

import uwstout.healmebaby.DisplayMessageActivity;
import uwstout.healmebaby.PedometerActivity;
import uwstout.healmebaby.PlannerActivity;
import uwstout.healmebaby.R;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "healmebaby.healthapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /** Called when a user clicks the Send button */
    public void sendMessage(View view) {
        //Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    /** go to the planner page */
    public void plannerPage(View view) {
        Intent planner_Intent = new Intent(this, PlannerActivity.class);
        startActivity(planner_Intent);
    }

    public void openPedometer(View view) {
        Intent intent = new Intent(this, PedometerActivity.class);
        startActivity(intent);
    }
}
