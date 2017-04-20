package uwstout.healmebaby;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class EnterWorkoutResults extends AppCompatActivity {

    boolean REG_SUCCESS = false;

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


    // HTTP POST request
    private void sendPost(List<String> values) throws Exception {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String url = "http://71.95.85.102/signup";

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);

        // add header
        // post.setHeader("Authorization", "JWT" + TOKEN);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("email",values.get(0)));
        urlParameters.add(new BasicNameValuePair("firstName", values.get(1)));
        urlParameters.add(new BasicNameValuePair("lastName", values.get(2)));
        urlParameters.add(new BasicNameValuePair("password", values.get(3)));

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
        if(response.getStatusLine().getStatusCode() == 200) {
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
