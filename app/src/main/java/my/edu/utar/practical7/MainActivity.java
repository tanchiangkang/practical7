package my.edu.utar.practical7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
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

//                try {
//                    URL url = new URL("https://tools.ietf.org/html/rfc2616");
//                    HttpURLConnection hc = (HttpURLConnection) url.openConnection();
//                    final String result;
//
//                    int length = hc.getContentLength();
//
//                    InputStream input = url.openStream();
//                    //Fixed buffer size of 1KBytes
//                    byte[] bInput = new byte[1000];
//                    int readSize = input.read(bInput);
//                    result = new String(bInput);
//
//                    handler.post(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            tv.setText(result);
//                        }
//                    });
//                    //tv.setText(result);
//
//                    input.close();
//                } catch (IOException e) {
//                    Log.e("Net", "Error", e);
//                }

                //Optional
                //Use BufferedInputStream to more efficiently read content
                //over the network. getContentLength() does not always return
                //actual content length of a page. It is however okay to use
                //when accessing web services.
                try {
                    URL url = new URL("https://tools.ietf.org/rfc/rfc2616.txt");
                    HttpURLConnection hc = (HttpURLConnection) url.openConnection();
                    final String result;
                    InputStream in = new BufferedInputStream(hc.getInputStream());
                    result = readStream(in);

                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            tv.setText(result);
                        }
                    });
                } catch (Exception e) {
                    Log.e("Net", "Error", e);
                }
            }
        }.start();
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