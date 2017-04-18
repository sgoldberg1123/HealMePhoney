package uwstout.healmebaby;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static uwstout.healmebaby.R.styleable.Spinner;

public class PlannerActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "healmebaby.healthapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planner);

    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        /**
         String days;
         switch(view.getId()) {
         case R.id.checkbox_monday:

         if (checked)
         days += "Monday ";
         break;
         case R.id.checkbox_tuesday:
         if (checked)
         days += "Tuesday ";
         break;
         case R.id.checkbox_wednesday:

         if (checked)
         days += "Wednesday ";
         break;
         case R.id.checkbox_thursday:
         if (checked)
         days += "Thursday ";
         break;
         case R.id.checkbox_friday:

         if (checked)
         days += "Friday ";
         break;
         case R.id.checkbox_saturday:
         if (checked)
         days += "Saturday ";
         break;
         case R.id.checkbox_sunday:

         if (checked)
         days += "Sunday ";
         break;
         }**/
    }

    public void save_Plan(View view) {
        //Do something in response to button
        //EditText editPlan = (EditText) findViewById(R.id.edit_plan);
        //String message = editPlan.getText().toString();
        CheckBox checkMonday = (CheckBox) findViewById(R.id.checkbox_monday);
        CheckBox checkTuesday = (CheckBox) findViewById(R.id.checkbox_tuesday);
        CheckBox checkWednesday = (CheckBox) findViewById(R.id.checkbox_wednesday);
        CheckBox checkThursday = (CheckBox) findViewById(R.id.checkbox_thursday);
        CheckBox checkFriday = (CheckBox) findViewById(R.id.checkbox_friday);
        CheckBox checkSaturday = (CheckBox) findViewById(R.id.checkbox_saturday);
        CheckBox checkSunday = (CheckBox) findViewById(R.id.checkbox_sunday);

        EditText workout_plan_text = (EditText) findViewById(R.id.edit_plan);
        String workout_plan_done = workout_plan_text.getText().toString();

        CharSequence toast_Message = "You just added the plan " + workout_plan_done + " on: ";


        Intent main_Intent = new Intent(this, MainActivity.class);
        startActivity(main_Intent);

        Context context = getApplicationContext();
        CharSequence text = toast_Message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void back(View view) {
        Intent back_Intent = new Intent(this, UserProfile.class);
        startActivity(back_Intent);
    }

}
