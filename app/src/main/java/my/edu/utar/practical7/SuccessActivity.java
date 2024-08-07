package my.edu.utar.practical7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
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
            //Q3
            JSONArray jsonArray = new JSONArray(intent.getStringExtra("output"));
            String result = "Name    Age    Programme\n";
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            result = result + jsonObject.get("name").toString() + " "
                    + jsonObject.get("age").toString() + " "
                    + jsonObject.get("programme").toString() + "\n";
            tv.setText(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setContentView(tv);
    }
}