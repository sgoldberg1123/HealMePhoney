package uwstout.healmebaby;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.DecimalFormat;
import java.util.Calendar;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import uwstout.healmebaby.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;


/*
* The interactive user interface of the pedometer
*/
@SuppressLint("HandlerLeak")
public class PedometerActivity extends Activity {

    //Variables
    private TextView tv_show_step;// Step count
    private TextView tv_week_day;// Weekday
    private TextView tv_date;// Date

    private TextView tv_timer;// Timer

    private TextView tv_distance;// Distance
    private TextView tv_calories;// Calories
    private TextView tv_velocity;//Speed

    private Button btn_start;// Start button
    private Button btn_stop;// Stop button

    private GifView gifView;

    //Stars for rating
    private ImageView iv_star_1;
    private ImageView iv_star_2;
    private ImageView iv_star_3;
    private ImageView iv_star_4;
    private ImageView iv_star_5;
    private ImageView iv_star_6;
    private ImageView iv_star_7;
    private ImageView iv_star_8;
    private ImageView iv_star_9;
    private ImageView iv_star_10;

    private long timer = 0;
    private  long startTimer = 0;

    private  long tempTime = 0;

    private Double distance = 0.0;// mi
    private Double calories = 0.0;// cal
    private Double velocity = 0.0;// mph

    private int step_length = 0; //in
    private int weight = 0; // lbs
    private int total_step = 0;

    private Thread thread;  //Thread

    private TableRow hide1, hide2;
    private TextView step_counter;


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            countDistance();

            if (timer != 0 && distance != 0.0) {

                calories = weight * distance * 0.57;
                velocity = (distance *3600) / timer;

            } else {
                calories = 0.0;
                velocity = 0.0;
            }

            countStep();

            tv_show_step.setText(total_step + "");

            tv_distance.setText(formatDouble(distance));
            tv_calories.setText(formatDouble(calories));
            tv_velocity.setText(formatDouble(velocity));

            tv_timer.setText(getFormatTime(timer));

            changeStep();

        }

        /*
         * Set current step count and stars
         */
        private void changeStep() {
            int level = PedometerStepDetector.CURRENT_STEP / 100; //Lighten up a star for every 100 steps
            switch (level) {
                case 10:
                    iv_star_10.setImageResource(R.drawable.start_disable);
                case 9:
                    iv_star_9.setImageResource(R.drawable.start_red);
                case 8:
                    iv_star_8.setImageResource(R.drawable.start_red);
                case 7:
                    iv_star_7.setImageResource(R.drawable.start_red);
                case 6:
                    iv_star_6.setImageResource(R.drawable.start_red);
                case 5:
                    iv_star_5.setImageResource(R.drawable.start_green);
                case 4:
                    iv_star_4.setImageResource(R.drawable.start_green);
                case 3:
                    iv_star_3.setImageResource(R.drawable.start_green);
                case 2:
                    iv_star_2.setImageResource(R.drawable.start_green);
                case 1:
                    iv_star_1.setImageResource(R.drawable.start_green);
                    break;
                case 0:
                    iv_star_1.setImageResource(R.drawable.star_enable);
                    iv_star_2.setImageResource(R.drawable.star_enable);
                    iv_star_3.setImageResource(R.drawable.star_enable);
                    iv_star_4.setImageResource(R.drawable.star_enable);
                    iv_star_5.setImageResource(R.drawable.star_enable);
                    iv_star_6.setImageResource(R.drawable.star_enable);
                    iv_star_7.setImageResource(R.drawable.star_enable);
                    iv_star_8.setImageResource(R.drawable.star_enable);
                    iv_star_9.setImageResource(R.drawable.star_enable);
                    iv_star_10.setImageResource(R.drawable.star_enable);
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.activity_pedometer); //Set current display

        if (PedometerSettingsActivity.sharedPreferences == null) {
            PedometerSettingsActivity.sharedPreferences = this.getSharedPreferences(
                    PedometerSettingsActivity.SETP_SHARED_PREFERENCES,
                    Context.MODE_PRIVATE);
        }

        Bundle extras = getIntent().getExtras();
        //Pedometer
        gifView = (GifView)findViewById(R.id.gif_view);
        gifView.setGifImageType(GifImageType.COVER);
        gifView.setShowDimension(100, 100);
        gifView.setGifImage(R.drawable.walk_gif);
        gifView.showCover();

        if (thread == null) {

            thread = new Thread() {//Monitor current step count

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    super.run();
                    int temp = 0;
                    while (true) {
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (PedometerService.FLAG) {
                            Message msg = new Message();
                            if (temp != PedometerStepDetector.CURRENT_STEP) {
                                temp = PedometerStepDetector.CURRENT_STEP;
                            }
                            if (startTimer != System.currentTimeMillis()) {
                                timer = tempTime + System.currentTimeMillis()
                                        - startTimer;
                            }
                            handler.sendMessage(msg);// Send msg to main thread
                        }
                    }
                }
            };
            thread.start();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        Log.i("APP", "on resume.");
        addView();
        init();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    /**
     * Activity
     */
    private void addView() {
        tv_show_step = (TextView) this.findViewById(R.id.show_step);
        tv_week_day = (TextView) this.findViewById(R.id.week_day);
        tv_date = (TextView) this.findViewById(R.id.date);

        tv_timer = (TextView) this.findViewById(R.id.timer);

        tv_distance = (TextView) this.findViewById(R.id.distance);
        tv_calories = (TextView) this.findViewById(R.id.calories);
        tv_velocity = (TextView) this.findViewById(R.id.velocity);

        btn_start = (Button) this.findViewById(R.id.start);
        btn_stop = (Button) this.findViewById(R.id.stop);

        // Star icons
        iv_star_1 = (ImageView) this.findViewById(R.id.iv_1);
        iv_star_2 = (ImageView) this.findViewById(R.id.iv_2);
        iv_star_3 = (ImageView) this.findViewById(R.id.iv_3);
        iv_star_4 = (ImageView) this.findViewById(R.id.iv_4);
        iv_star_5 = (ImageView) this.findViewById(R.id.iv_5);
        iv_star_6 = (ImageView) this.findViewById(R.id.iv_6);
        iv_star_7 = (ImageView) this.findViewById(R.id.iv_7);
        iv_star_8 = (ImageView) this.findViewById(R.id.iv_8);
        iv_star_9 = (ImageView) this.findViewById(R.id.iv_9);
        iv_star_10 = (ImageView) this.findViewById(R.id.iv_10);

        hide1 = (TableRow)findViewById(R.id.hide1);
        hide2 = (TableRow)findViewById(R.id.hide2);
        step_counter = (TextView)findViewById(R.id.step_counter);

        Intent service = new Intent(this, PedometerService.class);
        stopService(service);
        PedometerStepDetector.CURRENT_STEP = 0;
        tempTime = timer = 0;
        tv_timer.setText(getFormatTime(timer)); // Set timer
        tv_show_step.setText("0");
        tv_distance.setText(formatDouble(0.0));
        tv_calories.setText(formatDouble(0.0));
        tv_velocity.setText(formatDouble(0.0));

        handler.removeCallbacks(thread);

    }

    /**
     * Initialization
     */
    private void init() {

        step_length = PedometerSettingsActivity.sharedPreferences.getInt(
                PedometerSettingsActivity.STEP_LENGTH_VALUE, 70);
        weight = PedometerSettingsActivity.sharedPreferences.getInt(
                PedometerSettingsActivity.WEIGHT_VALUE, 50);

        countDistance();
        countStep();
        if ((timer += tempTime) != 0 && distance != 0.0) {

            calories = weight * distance * 0.57;
            velocity = (distance * 3600) / timer;

        } else {
            calories = 0.0;
            velocity = 0.0;
        }

        tv_timer.setText(getFormatTime(timer + tempTime));

        tv_distance.setText(formatDouble(distance));
        tv_calories.setText(formatDouble(calories));
        tv_velocity.setText(formatDouble(velocity));

        tv_show_step.setText(total_step + "");

        btn_start.setEnabled(!PedometerService.FLAG);
        btn_stop.setEnabled(PedometerService.FLAG);

        if (PedometerService.FLAG) {
            btn_stop.setText(getString(R.string.pause));
        } else if (PedometerStepDetector.CURRENT_STEP > 0) {
            btn_stop.setEnabled(true);
            btn_stop.setText(getString(R.string.cancel));
        }

        setDate();
    }

    /**
     * Set current date
     */
    private void setDate() {
        Calendar mCalendar = Calendar.getInstance();
        int weekDay = mCalendar.get(Calendar.DAY_OF_WEEK);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        tv_date.setText(month + getString(R.string.month) + day
                + getString(R.string.day));

        String week_day_str = new String();
        switch (weekDay) {
            case Calendar.SUNDAY:
                week_day_str = getString(R.string.sunday);
                break;

            case Calendar.MONDAY:
                week_day_str = getString(R.string.monday);
                break;

            case Calendar.TUESDAY:
                week_day_str = getString(R.string.tuesday);
                break;

            case Calendar.WEDNESDAY:
                week_day_str = getString(R.string.wednesday);
                break;

            case Calendar.THURSDAY:
                week_day_str = getString(R.string.thursday);
                break;

            case Calendar.FRIDAY:
                week_day_str = getString(R.string.friday);
                break;

            case Calendar.SATURDAY:
                week_day_str = getString(R.string.saturday);
                break;
        }
        tv_week_day.setText(week_day_str);
    }

    /**
     * Format numbers
     */
    private String formatDouble(Double doubles) {
        DecimalFormat format = new DecimalFormat("####.##");
        String distanceStr = format.format(doubles);
        return distanceStr.equals(getString(R.string.zero)) ? getString(R.string.double_zero)
                : distanceStr;
    }

    public void onClick(View view) {
        Intent service = new Intent(this, PedometerService.class);
        switch (view.getId()) {
            case R.id.start:
                gifView.showAnimation();
                startService(service);
                btn_start.setEnabled(false);
                btn_stop.setEnabled(true);
                btn_stop.setText(getString(R.string.pause));
                startTimer = System.currentTimeMillis();
                tempTime = timer;
                break;

            case R.id.stop:
                stopService(service);
                gifView.showCover();
                if (PedometerService.FLAG && PedometerStepDetector.CURRENT_STEP > 0) {
                    btn_stop.setText(getString(R.string.cancel));
                } else {
                    PedometerStepDetector.CURRENT_STEP = 0;
                    tempTime = timer = 0;

                    btn_stop.setText(getString(R.string.pause));
                    btn_stop.setEnabled(false);

                    tv_timer.setText(getFormatTime(timer));

                    tv_show_step.setText("0");
                    tv_distance.setText(formatDouble(0.0));
                    tv_calories.setText(formatDouble(0.0));
                    tv_velocity.setText(formatDouble(0.0));

                    handler.removeCallbacks(thread);
                }
                btn_start.setEnabled(true);
                break;
        }
    }

    /**
     * Return formatted time
     */
    private String getFormatTime(long time) {
        time = time / 1000;
        long second = time % 60;
        long minute = (time % 3600) / 60;
        long hour = time / 3600;

        String strSecond = ("00" + second)
                .substring(("00" + second).length() - 2);

        String strMinute = ("00" + minute)
                .substring(("00" + minute).length() - 2);

        String strHour = ("00" + hour).substring(("00" + hour).length() - 2);

        return strHour + ":" + strMinute + ":" + strSecond;
        // + strMillisecond;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_step, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, PedometerSettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_information:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Calculate distance
     */
    private void countDistance() {
        distance = PedometerStepDetector.CURRENT_STEP * step_length * 0.00001578;
    }

    /**
     * Calculate steps
     */
    private void countStep() {
        if (PedometerStepDetector.CURRENT_STEP % 2 == 0) {
            total_step = PedometerStepDetector.CURRENT_STEP;
        } else {
            total_step = PedometerStepDetector.CURRENT_STEP +1;
        }

        total_step = PedometerStepDetector.CURRENT_STEP;
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();
    }

}
