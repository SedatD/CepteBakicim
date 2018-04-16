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

import ceptebakicim.com.Adapter.AcceptedKeepersAdapter;
import ceptebakicim.com.Pojo.AcceptedKeepersPojo;
import ceptebakicim.com.R;

public class KabulEdenlerActivity extends AppCompatActivity {
    private RecyclerView recyclerView_accepted_keepers;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_keepers);

        textView = findViewById(R.id.textView);
        recyclerView_accepted_keepers = findViewById(R.id.recyclerView_accepted_keepers);
        recyclerView_accepted_keepers.setHasFixedSize(true);
        recyclerView_accepted_keepers.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int userId = preferences.getInt("userId", -1);
        getRequest(userId);
    }

    private void getRequest(int userId) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.ceptebakicim.com/json/AileBakiciTeklif?teklif=2&userid=" + userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("AcceptedKeepersAct", "Response : " + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0)
                                textView.setVisibility(View.VISIBLE);
                            ArrayList results = new ArrayList<AcceptedKeepersPojo>();
                            AcceptedKeepersPojo obj;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                                String durum = "";
                                if (jsonObject.getInt("status") == 0)
                                    durum = "Beklemede";
                                else if (jsonObject.getInt("status") == 1)
                                    durum = "Onaylanan";
                                else if (jsonObject.getInt("status") == -1)
                                    durum = "İşlem Bitti";

                                obj = new AcceptedKeepersPojo(jsonObject.getInt("caryID"), jsonObject.getString("name"), durum);
                                results.add(obj);
                            }
                            recyclerView_accepted_keepers.setAdapter(new AcceptedKeepersAdapter(results, getApplicationContext()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.wtf("AcceptedKeepersAct", "Error : " + error);
            }
        });
        queue.add(stringRequest);
    }
}
