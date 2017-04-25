package uwstout.healmebaby;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static uwstout.healmebaby.R.styleable.Spinner;

public class PlannerActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "healmebaby.healthapp.MESSAGE";

    boolean REG_SUCCESS = false;

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

        String toast_Message = "You just added the plan " + workout_plan_done + " on: ";
        String daysList = "";

        if (checkMonday.isChecked()) {
            toast_Message = toast_Message + "Monday ";
            daysList = daysList + "2";
        }
        if (checkTuesday.isChecked()) {
            toast_Message = toast_Message + "Tuesday ";
            daysList = daysList + "3";
        }
        if (checkWednesday.isChecked()) {
            toast_Message = toast_Message + "Wednesday ";
            daysList = daysList + "4";
        }
        if (checkThursday.isChecked()) {
            toast_Message = toast_Message + "Thursday ";
            daysList = daysList + "5";
        }
        if (checkFriday.isChecked()) {
            toast_Message = toast_Message + "Friday ";
            daysList = daysList + "6";
        }
        if (checkSaturday.isChecked()) {
            toast_Message = toast_Message + "Saturday ";
            daysList = daysList + "7";
        }
        if (checkSunday.isChecked()) {
            toast_Message = toast_Message + "Sunday ";
            daysList = daysList + "1";
        }

        EditText workout_Name = (EditText) findViewById(R.id.edit_plan);

        String woValue = workout_Name.getText().toString();

        List<String> registerValues = new ArrayList<String>();
        registerValues.add(woValue);
        registerValues.add(daysList);

        try {
            sendPost(registerValues);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (REG_SUCCESS == true) {
            Intent main_Intent = new Intent(this, MainActivity.class);
            startActivity(main_Intent);

            Context context = getApplicationContext();
            CharSequence text = toast_Message;
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    public void back(View view) {
        Intent back_Intent = new Intent(this, UserProfile.class);
        startActivity(back_Intent);
    }


    // HTTP POST request
    private void sendPost(List<String> values) throws Exception {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String url = "http://71.95.85.102/api/user/signup";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        // post.setHeader("Authorization", "JWT" + TOKEN);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("email",values.get(0)));
        urlParameters.add(new BasicNameValuePair("firstName", values.get(1)));
        urlParameters.add(new BasicNameValuePair("lastName", values.get(1)));
        urlParameters.add(new BasicNameValuePair("password", values.get(1)));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

        //Tells user that registration is successful
        if (response.getStatusLine().getStatusCode() == 200) {
            REG_SUCCESS = true;
        }

    }
    public static void connect(String url)
    {

        HttpClient httpclient = new DefaultHttpClient();

        // Prepare a request object
        HttpGet httpget = new HttpGet(url);

        // Execute the request
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            // Examine the response status
            Log.i("Praeda",response.getStatusLine().toString());

            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release

            if (entity != null) {

                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                //  String result= convertStreamToString(instream);
                // now you have the string representation of the HTML request
                // instream.close();
            }


        } catch (Exception e) {}
    }
}
