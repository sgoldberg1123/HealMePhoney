package uwstout.healmebaby;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class EnterWorkoutResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_workout_results);
    }

    public void back(View view) {
        Intent back_Intent = new Intent(this, UserProfile.class);
        startActivity(back_Intent);
    }
    public void save_Results(View view) {
        //Do something in response to button
        //EditText editPlan = (EditText) findViewById(R.id.edit_plan);
        //String message = editPlan.getText().toString();

        EditText workout_name = (EditText) findViewById(R.id.workout_name);
        String workout_name_added = workout_name.getText().toString();

        CharSequence toast_Message = "Your results for " + workout_name_added + " have been recorded.";


        Intent userProfile_Intent = new Intent(this, UserProfile.class);
        startActivity(userProfile_Intent);

        Context context = getApplicationContext();
        CharSequence text = toast_Message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
