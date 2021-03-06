package uwstout.healmebaby;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class WorkoutRecords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_records);
    }

    public void back(View view) {
        Intent back_Intent = new Intent(this, UserProfile.class);
        startActivity(back_Intent);
    }

    public void makeVisible(View view) {
        findViewById(R.id.repDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.weightDisplay).setVisibility(View.VISIBLE);
    }
}
