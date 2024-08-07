package my.edu.utar.practical7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        Intent intent = getIntent();
        TextView tv = new TextView(this);

        try {
            //Q2
            JSONObject jsonObject = new JSONObject(intent.getStringExtra("output"));
            String age = jsonObject.getString("age");
            tv.setText("You have successfully accessed!\n"
                        + "Your age is " + age);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(tv);
    }
}