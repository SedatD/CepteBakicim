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

import ceptebakicim.com.Adapter.MainKabulEdilenCardAdapter;
import ceptebakicim.com.Pojo.MainKabulEdilenCardPojo;
import ceptebakicim.com.R;

public class KabulEdilenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabul_edilen);

        TextView textView = findViewById(R.id.textView);

        RecyclerView recyclerView_main_kabulEdilen = findViewById(R.id.recyclerView_main_kabulEdilen);
        recyclerView_main_kabulEdilen.setHasFixedSize(true);
        recyclerView_main_kabulEdilen.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();
        String string = bundle.getString("jsonArray_kabulEdilen");
        JSONArray jsonArray_kabulEdilen = null;
        try {
            jsonArray_kabulEdilen = new JSONArray(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (jsonArray_kabulEdilen != null) {
            try {
                if (jsonArray_kabulEdilen.length() == 0) {
                    recyclerView_main_kabulEdilen.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                }
                ArrayList results = new ArrayList<MainKabulEdilenCardPojo>();
                MainKabulEdilenCardPojo obj;
                for (int i = 0; i < jsonArray_kabulEdilen.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray_kabulEdilen.get(i);
                    obj = new MainKabulEdilenCardPojo(jsonObject.getInt("serviceID"), jsonObject.getString("typeTitle"));
                    results.add(obj);
                }
                recyclerView_main_kabulEdilen.setAdapter(new MainKabulEdilenCardAdapter(results, getApplicationContext()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
