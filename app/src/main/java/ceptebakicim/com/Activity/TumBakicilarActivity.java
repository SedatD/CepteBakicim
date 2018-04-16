package ceptebakicim.com.Activity;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ceptebakicim.com.Adapter.AcceptedKeepersAdapter;
import ceptebakicim.com.Adapter.AllKeepersAdapter;
import ceptebakicim.com.Adapter.OfferedKeepersAdapter;
import ceptebakicim.com.Pojo.AcceptedKeepersPojo;
import ceptebakicim.com.Pojo.AllKeepersPojo;
import ceptebakicim.com.Pojo.OfferedKeepersPojo;
import ceptebakicim.com.R;

public class TumBakicilarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private Spinner spinner_uyruk, spinner_calisma, spinner_dil, spinner_maas;
    private RecyclerView recyclerView_allKeepers;
    private JSONArray responseArray;
    private List<AllKeepersPojo> resultsFinal = new ArrayList<>();
    private Button btnview1, btnview2, btnview3;
    private LinearLayout view1, view2, view3;
    private RecyclerView recyclerView_offered_keepers;
    private TextView textView;
    private RecyclerView recyclerView_accepted_keepers;
    private TextView textViewOnayVerenler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_keepers);

        spinner_uyruk = findViewById(R.id.spinner_uyruk);
        spinner_calisma = findViewById(R.id.spinner_calisma);
        spinner_dil = findViewById(R.id.spinner_dil);
        spinner_maas = findViewById(R.id.spinner_maas);
        btnview1 = findViewById(R.id.btnview1);
        btnview2 = findViewById(R.id.btnview2);
        btnview3 = findViewById(R.id.btnview3);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int userId = preferences.getInt("userId", -1);

        //view3
        textView = findViewById(R.id.textView);
        recyclerView_offered_keepers = findViewById(R.id.recyclerView_offered_keepers);
        recyclerView_offered_keepers.setHasFixedSize(true);
        recyclerView_offered_keepers.setLayoutManager(new LinearLayoutManager(this));
        getRequest(userId);

        //view2
        textViewOnayVerenler = findViewById(R.id.textViewOnayVerenler);
        recyclerView_accepted_keepers = findViewById(R.id.recyclerView_accepted_keepers);
        recyclerView_accepted_keepers.setHasFixedSize(true);
        recyclerView_accepted_keepers.setLayoutManager(new LinearLayoutManager(this));
        getRequestOnayVerenler(userId);

        btnview1.setOnClickListener(this);
        btnview2.setOnClickListener(this);
        btnview3.setOnClickListener(this);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        spinnerSetData();

        recyclerView_allKeepers = findViewById(R.id.recyclerView_allKeepers);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView_allKeepers.setLayoutManager(mLayoutManager);
        recyclerView_allKeepers.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView_allKeepers.setItemAnimator(new DefaultItemAnimator());

        Request();
    }

    private void Request() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        final String url = "https://www.ceptebakicim.com/json/userList?userType=2";
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.wtf("Response AllKeepersAct", response.toString());
                        responseArray = response;
                        try {
                            for (int i = 0; i < responseArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) responseArray.get(i);
                                resultsFinal.add(new AllKeepersPojo(jsonObject.getInt("id"), jsonObject.getString("name"), jsonObject.getString("uyruk"), jsonObject.getString("imageYolu"), jsonObject.getString("calismaS"), jsonObject.getString("dil"), jsonObject.getString("maas"), "  /  " + jsonObject.getString("yas")));
                            }
                            recyclerView_allKeepers.setAdapter(new AllKeepersAdapter(getApplicationContext(), resultsFinal));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("Error.Response AllKeepersAct", error + "");
                        Toast.makeText(TumBakicilarActivity.this, getString(R.string.con_error) + "", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(getRequest);
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

    private void getRequestOnayVerenler(int userId) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.ceptebakicim.com/json/AileBakiciTeklif?teklif=2&userid=" + userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("AcceptedKeepersAct", "Response : " + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() == 0)
                                textViewOnayVerenler.setVisibility(View.VISIBLE);
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

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void spinnerSetData() {
        spinner_uyruk.setOnItemSelectedListener(this);
        spinner_calisma.setOnItemSelectedListener(this);
        spinner_dil.setOnItemSelectedListener(this);
        spinner_maas.setOnItemSelectedListener(this);

        // spinner uyruk
        List<String> categories = new ArrayList<String>();
        categories.add(getString(R.string.select_nationality));
        categories.add("Özbekistan");
        categories.add("Türkmenistan");
        categories.add("Gürcistan");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_uyruk.setAdapter(dataAdapter);

        // spinner calisma
        categories = new ArrayList<String>();
        categories.add(getString(R.string.select_working_style));
        categories.add("Yatılı");
        categories.add("Gündüzlü");
        categories.add("Yatılı/Gündüzlü");
        dataAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_calisma.setAdapter(dataAdapter);

        // spinner dil
        categories = new ArrayList<String>();
        categories.add(getString(R.string.select_language));
        categories.add("Türkçe");
        categories.add("İngilizce");
        categories.add("Rusça");
        categories.add("Türkçe/Rusça");
        categories.add("Türkçe/İngilizce");
        categories.add("Türkçe/İngilizce/Rusça");
        dataAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_dil.setAdapter(dataAdapter);

        // spinner maas
        categories = new ArrayList<String>();
        categories.add(getString(R.string.salary));
        categories.add("400$/500$");
        categories.add("500$/600$");
        categories.add("600$/700$");
        categories.add("700$/800$");
        categories.add("800$/900$");
        categories.add("900$+");
        dataAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_maas.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        List<AllKeepersPojo> results = new ArrayList<>();
        for (int i = 0; i < resultsFinal.size(); i++) {
            boolean isUyruk = spinner_uyruk.getSelectedItemPosition() == 0 || spinner_uyruk.getSelectedItem().toString().toLowerCase().equals(resultsFinal.get(i).getNationality().toLowerCase());
            boolean isCalisma = spinner_calisma.getSelectedItemPosition() == 0 || spinner_calisma.getSelectedItem().toString().toLowerCase().equals(resultsFinal.get(i).getCalisma().toLowerCase());
            boolean isDil = spinner_dil.getSelectedItemPosition() == 0 || spinner_dil.getSelectedItem().toString().toLowerCase().equals(resultsFinal.get(i).getDil().toLowerCase());
            boolean isMaas = spinner_maas.getSelectedItemPosition() == 0 || spinner_maas.getSelectedItem().toString().toLowerCase().equals(resultsFinal.get(i).getMaas().toLowerCase());

            if (isUyruk && isCalisma && isDil && isMaas)
                results.add(resultsFinal.get(i));
        }
        recyclerView_allKeepers.setAdapter(new AllKeepersAdapter(getApplicationContext(), results));
    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnview1:
                btnview1.setTextColor(Color.parseColor("#000000"));
                view1.setVisibility(View.VISIBLE);

                btnview2.setTextColor(Color.parseColor("#ffffff"));
                btnview3.setTextColor(Color.parseColor("#ffffff"));
                view2.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                Toast.makeText(this, "Tüm bakıcılar listesi", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnview2:
                btnview2.setTextColor(Color.parseColor("#000000"));
                view2.setVisibility(View.VISIBLE);

                btnview1.setTextColor(Color.parseColor("#ffffff"));
                btnview3.setTextColor(Color.parseColor("#ffffff"));
                view1.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                Toast.makeText(this, "Onay veren bakıcılar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnview3:
                btnview3.setTextColor(Color.parseColor("#000000"));
                view3.setVisibility(View.VISIBLE);

                btnview1.setTextColor(Color.parseColor("#ffffff"));
                btnview2.setTextColor(Color.parseColor("#ffffff"));
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                Toast.makeText(this, "Teklif gönderdiğim bakıcılar", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}
