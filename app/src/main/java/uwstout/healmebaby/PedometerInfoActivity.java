package uwstout.healmebaby;

import android.os.Bundle;
import android.os.StrictMode;
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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static uwstout.healmebaby.R.id.step;


public class PedometerInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedometer_info);

        TextView todaySteps = (TextView) findViewById(R.id.txt2);
        int tStep = 0;
        try {
            tStep = getCurrentStep("2017-05-07");
        } catch (Exception ex) {
        }
        String todayStepCount = String.format(getString(R.string.info_2), String.valueOf(tStep));
        todaySteps.setText(todayStepCount);

        TextView todayCalories = (TextView) findViewById(R.id.txt3);
        String tCal = String.valueOf(tStep*50*0.00001578*350*0.57);
        String todayCalCount = String.format(getString(R.string.info_3), tCal);
        todayCalories.setText(todayCalCount);

        TextView todayMiles = (TextView) findViewById(R.id.txt4);
        String tMile = String.valueOf(tStep*50*0.00001578);
        String todayMileCount = String.format(getString(R.string.info_4), tMile);
        todayMiles.setText(todayMileCount);

    }

    private int getCurrentStep(String date) throws Exception {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String url = "http://71.95.85.102/api/dailystepcount/date";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("date",date));

        //JWT Token Stuff
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MTM3fQ.IYwfPbg9GyL7Gn4HZV5-KOD-HjnJIB7xJqqffCXIo38";
        urlParameters.add(new BasicNameValuePair("JWT", token));

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

        ObjectMapper mapper = new ObjectMapper();
        try
        {
            //PedometerData p = mapper.readValue(result.toString(), PedometerData.class);
            String s = "{\"daily_step_count_id\":6,\"date\":\"2017-05-08\",\"step_count\":1,\"user_id\":137}";
            PedometerData p = mapper.readValue(s, PedometerData.class);
            int step = p.step_count;
            System.out.println(step);
        }
        catch (JsonGenerationException e)
        {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return step;
    }

}
