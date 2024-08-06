package my.edu.utar.practical7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Q1
        Handler handler = new Handler();
        ScrollView sv = new ScrollView(this);
        TextView tv = new TextView(this);
        sv.addView(tv);
        setContentView(sv);

        new Thread() {
            public void run() {

                try {
                    URL url = new URL("https://tools.ietf.org/html/rfc2616");
                    HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                    final String result;

                    int length = hc.getContentLength();

                    InputStream input = url.openStream();
                    //Fixed buffer size of 1KBytes
                    byte[] bInput = new byte[1000];
                    int readSize = input.read(bInput);
                    result = new String(bInput);

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            tv.setText(result);
                        }
                    });
                    //tv.setText(result);

                    input.close();
                } catch (IOException e) {
                    Log.e("Net", "Error", e);
                }
            }
        }.start();

    }
}