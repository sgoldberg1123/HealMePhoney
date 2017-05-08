package uwstout.healmebaby;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.Output;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Student on 3/18/2017.
 * Followed Tutorial: https://www.youtube.com/watch?v=fn5OlqQuOCk&ab_channel=FilipVujovic
 */
public class PopRegister extends Activity {

    boolean REG_SUCCESS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initiation of UI
        setContentView(R.layout.pop_register);

        DisplayMetrics registerMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(registerMetrics);

        int width = registerMetrics.widthPixels;
        int height = registerMetrics.heightPixels;

        getWindow().setLayout((int)(width*.9),(int)(height*.7));

        //buttons and posts

        Button confirmButton = (Button) findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                EditText emailText = (EditText)findViewById(R.id.register_email);
                EditText firstName = (EditText)findViewById(R.id.register_firstname);
                EditText lastName = (EditText)findViewById(R.id.register_lastname);
                EditText passwordText = (EditText)findViewById(R.id.register_password);

                String evalue = emailText.getText().toString();
                String fvalue = firstName.getText().toString();
                String lvalue = lastName.getText().toString();
                String pvalue = passwordText.getText().toString();

                List<String> registerValues = new ArrayList<String>();
                registerValues.add(evalue);
                registerValues.add(fvalue);
                registerValues.add(lvalue);
                registerValues.add(pvalue);

                try {
                    sendPost(registerValues);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(REG_SUCCESS == true) {
                    Toast.makeText(PopRegister.this, "REGISTRATION SUCCESSFUL",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
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

        ((JWTToken) this.getApplication()).setToken(line);
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

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
            //    sendPost();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }



    // HTTP POST request
//    private void sendPost(List<String> values) throws Exception {
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//
//        StrictMode.setThreadPolicy(policy);
//
//        URL url = new URL("http://71.95.85.102/mobile/signup");
//        HttpURLConnection client = (HttpURLConnection) url.openConnection();
//
//        client.setRequestMethod("POST");
//        client.setRequestProperty("email",values.get(0));
//        client.setRequestProperty("firstName", values.get(1));
//        client.setRequestProperty("lastName", values.get(2));
//        client.setRequestProperty("password", values.get(3));
//        client.setDoOutput(true);
//
//        OutputStream outputPost = new BufferedOutputStream(client.getOutputStream());
//        System.out.println(outputPost);
//
//        outputPost.flush();
//        outputPost.close();
//
//        if(client != null) {
//            client.disconnect();
//        }
//    }
}
