package my.edu.utar.practical7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();

        TextView tv1 = new TextView(this);
        tv1.setText("Name:");
        final EditText et1 = new EditText(this);
        Button bt = new Button(this);
        bt.setText("Submit");

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(tv1);
        ll.addView(et1);
        ll.addView(bt);
        setContentView(ll);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyThread connectThread = new MyThread(
                        et1.getText().toString(), handler);
                connectThread.start();
            }
        });
    }

    private class MyThread extends Thread {
        private String mName;
        private Handler mHandler;

        public MyThread(String name, Handler handler) {
            this.mName = name;
            mHandler = handler;
        }

        public void run() {
            try {
                //Q4
                //For HTTP POST
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", mName);
                URL url = new URL("https://fhehrdzrnflooriioogj.supabase.co/rest/v1/Students");

                Log.i("Net", url.toString());

                HttpURLConnection hc =
                        (HttpURLConnection) url.openConnection();

                //For Q3 & Q4
                hc.setRequestProperty("apikey", getString(R.string.SUPABASE_KEY));
                hc.setRequestProperty("Authorization", "Bearer " + getString(R.string.SUPABASE_KEY));

                //Q4 To set request method and some additional properties
                hc.setRequestMethod("POST");
                hc.setRequestProperty("Content-Type", "application/json");
                hc.setRequestProperty("Prefer", "return=minimal");

                //For HTTP POST
                hc.setDoOutput(true);
                OutputStream output = hc.getOutputStream();
                output.write(jsonObject.toString().getBytes());
                output.flush();

                InputStream input = hc.getInputStream();

                String result = readStream(input);

                if (hc.getResponseCode() == 200) {
                    //OK
                    Intent i = new Intent(MainActivity.this,
                            SuccessActivity.class);
                    i.putExtra("output", result);
                    Log.i("MainActivity", "output = " + result);
                    startActivity(i);
                } else if (hc.getResponseCode() == 201) {
                    //New resource is created
                    Log.i("MainActivity2", "Name successfully inserted");

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Name successfully inserted", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Log.i("MainActivity", "Response code = " + hc.getResponseCode());
                }

                input.close();
                output.close();
            } catch (Exception e) {
                Log.e("Net", "Error", e);
            }
        }
    }

    //Optional
    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while (i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
        }
    }
}