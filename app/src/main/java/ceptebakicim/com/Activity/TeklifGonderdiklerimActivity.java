package ceptebakicim.com.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ceptebakicim.com.Adapter.OfferedKeepersAdapter;
import ceptebakicim.com.Pojo.OfferedKeepersPojo;
import ceptebakicim.com.R;

public class TeklifGonderdiklerimActivity extends AppCompatActivity {
    private RecyclerView recyclerView_offered_keepers;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offered_keepers);

        textView = findViewById(R.id.textView);
        recyclerView_offered_keepers = findViewById(R.id.recyclerView_offered_keepers);
        recyclerView_offered_keepers.setHasFixedSize(true);
        recyclerView_offered_keepers.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int userId = preferences.getInt("userId", -1);
        getRequest(userId);
    }

    private void getRequest(int userId) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.ceptebakicim.com/json/AileBakiciTeklif?teklif=1&userid=" + userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("OfferedKeepersAct", "Response : " + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0)
                                textView.setVisibility(View.VISIBLE);

                            ArrayList results = new ArrayList<OfferedKeepersPojo>();
                            OfferedKeepersPojo obj;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                String durum = "";
                                if (jsonObject.getInt("status") == 0)
                                    durum = "Beklemede";
                                else if (jsonObject.getInt("status") == 1)
                                    durum = "Onaylanan";
                                else if (jsonObject.getInt("status") == -1)
                                    durum = "İşlem Bitti";
                                obj = new OfferedKeepersPojo(jsonObject.getInt("serviceID"), jsonObject.getInt("interviewID"), jsonObject.getString("name"), durum,jsonObject.getInt("caryID"));
                                results.add(obj);
                            }
                            recyclerView_offered_keepers.setAdapter(new OfferedKeepersAdapter(results, getApplicationContext()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.wtf("OfferedKeepersAct", "Error : " + error);
            }
        });
        queue.add(stringRequest);
    }

}
