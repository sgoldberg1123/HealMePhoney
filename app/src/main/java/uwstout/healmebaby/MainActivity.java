package uwstout.healmebaby;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "healmebaby.healthapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button calcButton = (Button) findViewById(R.id.btn_calculate);
        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText weightInput = (EditText) findViewById(R.id.input_weight);
                EditText heightInput = (EditText) findViewById(R.id.input_height);
                TextView bmiTotal = (TextView) findViewById(R.id.text_bmi_display);

                if (weightInput.getText().toString().matches("") || heightInput.getText().toString().matches("")) {
                    Toast.makeText(MainActivity.this, "Please enter a weight and height", Toast.LENGTH_SHORT).show();
                } else {
                    bmiTotal.setText("BMI: ");
                    double weight = Double.parseDouble(weightInput.getText().toString());
                    double height = Double.parseDouble(heightInput.getText().toString());

                    double calcBMI = ((weight) / (height * height)) * 703;
                    String stringBMI = String.format("%.2f", calcBMI);

                    bmiTotal.append(stringBMI);
                }
            }
        });
    }
    /** go to the planner page */
    public void userPage(View view) {
        Intent planner_Intent = new Intent(this, UserProfile.class);
        startActivity(planner_Intent);
    }

    public void openPedometer(View view) {
        Intent intent = new Intent(this, PedometerActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
