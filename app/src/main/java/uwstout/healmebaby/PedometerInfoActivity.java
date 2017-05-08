package uwstout.healmebaby;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;



public class PedometerInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer_info);

        TextView todaySteps = (TextView) findViewById(R.id.txt2);
        String tStep = "1000";
        String todayStepCount = String.format(getString(R.string.info_2), tStep);
        todaySteps.setText(todayStepCount);

        TextView todayCalories = (TextView) findViewById(R.id.txt3);
        String tCal = "2000";
        String todayCalCount = String.format(getString(R.string.info_3), tCal);
        todayCalories.setText(todayCalCount);

        TextView todayMiles = (TextView) findViewById(R.id.txt4);
        String tMile = "3000";
        String todayMileCount = String.format(getString(R.string.info_4), tMile);
        todayMiles.setText(todayMileCount);

    }

}
