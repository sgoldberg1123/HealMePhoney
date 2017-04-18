package uwstout.healmebaby;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
    }

    public void add_Workout(View view) {
        Intent planner_Intent = new Intent(this, PlannerActivity.class);
        startActivity(planner_Intent);
    }
    public void view_Calendar(View view) {
        Intent calendar_Intent = new Intent(this, ViewCalendar.class);
        startActivity(calendar_Intent);
    }
    public void add_Results(View view) {
        Intent results_Intent = new Intent(this, EnterWorkoutResults.class);
        startActivity(results_Intent);
    }
}
