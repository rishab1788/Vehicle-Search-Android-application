package com.example.rishabh.mp_rto;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    TextView tvIsConnected;
    TextView textView2;
    Button button;
    EditText editText;
    String url = "http://mprest.herokuapp.com/num/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
        textView2 = (TextView) findViewById(R.id.textView2);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);


        ((Button) findViewById(R.id.button))
                .setOnClickListener(new View.OnClickListener() {

                    public void onClick(View arg0) {


                        InputStream inputStream = null;
                        String result = "";

                        try {

                            System.out.println(url + editText.getText());
                           String number=url;
                            url = url + editText.getText();

                            // create HttpClient
                            HttpClient httpclient = new DefaultHttpClient();

                            // make GET request to the given URL
                            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
url=number;
                            // receive response as inputStream
                            inputStream = httpResponse.getEntity().getContent();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            String line = "";
                            while ((line = bufferedReader.readLine()) != null)
                                result += line;

                            inputStream.close();


                            //  inputStream = httpResponse.getEntity().getContent();
                            if (inputStream != null) {
                            } else {
                                result = "Did not work!";
                            }

                        } catch (Exception e) {
                            System.out.print(e + "mprest.herokuapp.com/num/MP21m4019");
                        }
                        char r[] = result.toCharArray();
                        for (int i = 0; i < result.length(); i++) {
                            if (r[i] == '{') {
                                r[i] = ' ';
                            }

                            if (r[i] == '}') {
                                r[i] = ' ';
                            }
                            if (r[i] == '"') {
                                r[i] = ' ';
                            }
                            if (r[i] == ',') {
                                r[i] = '\n';
                            }

                        }

                        result = new String(r);
                        textView2.setText(result);

                    }
                });
        // new HttpAsyncTask().execute("mprest.herokuapp.com/num/MP21m4019");

        if (isConnected()) {
            tvIsConnected.setBackgroundColor(0xFF00CC00);
            tvIsConnected.setText("You are conncted");
        } else {
            tvIsConnected.setText("You are NOT conncted");
        }


    }


    public void buttonCliked(View view) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            if (inputStream != null) {

            } else {
                result = "Did not work!";
                textView2.setText(result);
            }
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }


/*
        textView2.setText(inputStream.toString());
*/

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("infor");
        alertDialogBuilder.setMessage(inputStream.toString());
        alertDialogBuilder.show();

    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


}
