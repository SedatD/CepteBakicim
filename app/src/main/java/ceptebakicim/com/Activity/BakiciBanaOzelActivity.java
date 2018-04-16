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

import ceptebakicim.com.Adapter.BanaOzelAdapter;
import ceptebakicim.com.Pojo.BanaOzelPojo;
import ceptebakicim.com.R;

public class BakiciBanaOzelActivity extends AppCompatActivity {
    private RecyclerView recyclerView_bana_ozel;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakici_bana_ozel);

        textView = findViewById(R.id.textView);
        recyclerView_bana_ozel = findViewById(R.id.recyclerView_bana_ozel);
        recyclerView_bana_ozel.setHasFixedSize(true);
        recyclerView_bana_ozel.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int userId = preferences.getInt("userId", -1);
        int userType = preferences.getInt("userType", -1);

        getRequest(userId);

    }

    private void getRequest(int userId) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.ceptebakicim.com/json/bakiciTeklifler?teklif=0&userid=" + userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("BakiciBanaOzelAct", "Response : " + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0)
                                textView.setVisibility(View.VISIBLE);

                            ArrayList results = new ArrayList<BanaOzelPojo>();
                            BanaOzelPojo obj;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                obj = new BanaOzelPojo(jsonObject.getInt("serviceID"),
                                        jsonObject.getInt("familyID"),
                                        jsonObject.getInt("interviewID"),
                                        jsonObject.getInt("interviewStatus"),
                                        jsonObject.getString("typeTitle"),
                                        jsonObject.getString("name"));
                                results.add(obj);
                            }
                            recyclerView_bana_ozel.setAdapter(new BanaOzelAdapter(results, getApplicationContext()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("BakiciBanaOzelAct", "Error : " + error);
                    }
                });
        queue.add(stringRequest);
    }

}
