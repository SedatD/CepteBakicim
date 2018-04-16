package ceptebakicim.com.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ceptebakicim.com.Adapter.MainKabulBekleyenCardAdapter;
import ceptebakicim.com.Pojo.MainKabulBekleyenCardPojo;
import ceptebakicim.com.R;

public class KabulBekleyenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabul_bekleyen);

        TextView textView = findViewById(R.id.textView);

        RecyclerView recyclerView_main_kabulBekleyen = findViewById(R.id.recyclerView_main_kabulBekleyen);
        recyclerView_main_kabulBekleyen.setHasFixedSize(true);
        recyclerView_main_kabulBekleyen.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        String string = bundle.getString("jsonArray_kabulBekleyen");
        JSONArray jsonArray_kabulBekleyen = null;
        try {
            jsonArray_kabulBekleyen = new JSONArray(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray_kabulBekleyen != null) {
            try {
                if (jsonArray_kabulBekleyen.length() == 0) {
                    recyclerView_main_kabulBekleyen.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                }
                ArrayList results = new ArrayList<MainKabulBekleyenCardPojo>();
                MainKabulBekleyenCardPojo obj;
                for (int i = 0; i < jsonArray_kabulBekleyen.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray_kabulBekleyen.get(i);
                    obj = new MainKabulBekleyenCardPojo(jsonObject.getInt("serviceID"), jsonObject.getString("typeTitle"));
                    results.add(obj);
                }
                recyclerView_main_kabulBekleyen.setAdapter(new MainKabulBekleyenCardAdapter(results, getApplicationContext()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
