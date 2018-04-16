package ceptebakicim.com.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ceptebakicim.com.Adapter.MainKabulBekleyenCardAdapter;
import ceptebakicim.com.Adapter.MainKabulEdilenCardAdapter;
import ceptebakicim.com.Pojo.MainKabulBekleyenCardPojo;
import ceptebakicim.com.Pojo.MainKabulEdilenCardPojo;
import ceptebakicim.com.R;

public class TeklifIslemleriActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnview1, btnview2, btnview3;
    private LinearLayout view1, view2, view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teklif_islemleri);

        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        ImageButton btnHome = findViewById(R.id.btnHome);
        btnview1 = findViewById(R.id.btnview1);
        btnview2 = findViewById(R.id.btnview2);
        btnview3 = findViewById(R.id.btnview3);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnview1.setOnClickListener(this);
        btnview2.setOnClickListener(this);
        btnview3.setOnClickListener(this);

        TextView textViewKabulEdilen = findViewById(R.id.textViewKabulEdilen);
        RecyclerView recyclerView_main_kabulEdilen = findViewById(R.id.recyclerView_main_kabulEdilen);
        recyclerView_main_kabulEdilen.setHasFixedSize(true);
        recyclerView_main_kabulEdilen.setLayoutManager(new LinearLayoutManager(this));

        TextView textViewKabulBekleyen = findViewById(R.id.textViewKabulBekleyen);
        RecyclerView recyclerView_main_kabulBekleyen = findViewById(R.id.recyclerView_main_kabulBekleyen);
        recyclerView_main_kabulBekleyen.setHasFixedSize(true);
        recyclerView_main_kabulBekleyen.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String kabulEdilen = preferences.getString("kabulEdilen", null);
        String kabulBekleyen = preferences.getString("kabulBekleyen", null);

        if (kabulEdilen != null) {
            try {
                JSONArray jsonArray_kabulEdilen = new JSONArray(kabulEdilen);
                if (jsonArray_kabulEdilen.length() == 0) {
                    recyclerView_main_kabulEdilen.setVisibility(View.GONE);
                    textViewKabulEdilen.setVisibility(View.VISIBLE);
                } else {
                    ArrayList results = new ArrayList<MainKabulEdilenCardPojo>();
                    MainKabulEdilenCardPojo obj;
                    for (int i = 0; i < jsonArray_kabulEdilen.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray_kabulEdilen.get(i);
                        obj = new MainKabulEdilenCardPojo(jsonObject.getInt("serviceID"), jsonObject.getString("typeTitle"));
                        results.add(obj);
                    }
                    recyclerView_main_kabulEdilen.setAdapter(new MainKabulEdilenCardAdapter(results, getApplicationContext()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            recyclerView_main_kabulEdilen.setVisibility(View.GONE);
            textViewKabulEdilen.setVisibility(View.VISIBLE);
        }

        if (kabulBekleyen != null) {
            try {
                JSONArray jsonArray_kabulBekleyen = new JSONArray(kabulBekleyen);
                if (jsonArray_kabulBekleyen.length() == 0) {
                    recyclerView_main_kabulBekleyen.setVisibility(View.GONE);
                    textViewKabulBekleyen.setVisibility(View.VISIBLE);
                } else {
                    ArrayList results = new ArrayList<MainKabulBekleyenCardPojo>();
                    MainKabulBekleyenCardPojo obj;
                    for (int i = 0; i < jsonArray_kabulBekleyen.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray_kabulBekleyen.get(i);
                        obj = new MainKabulBekleyenCardPojo(jsonObject.getInt("serviceID"), jsonObject.getString("typeTitle"));
                        results.add(obj);
                    }
                    recyclerView_main_kabulBekleyen.setAdapter(new MainKabulBekleyenCardAdapter(results, getApplicationContext()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            recyclerView_main_kabulBekleyen.setVisibility(View.GONE);
            textViewKabulBekleyen.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(TeklifIslemleriActivity.this, TeklifGonderActivity.class);
        switch (view.getId()) {
            case R.id.btn1:
                intent.putExtra("id", 1);
                startActivity(intent);
                break;
            case R.id.btn2:
                intent = new Intent(TeklifIslemleriActivity.this, TeklifGonderActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
                break;
            case R.id.btn3:
                intent = new Intent(TeklifIslemleriActivity.this, TeklifGonderActivity.class);
                intent.putExtra("id", 3);
                startActivity(intent);
                break;
            case R.id.btn4:
                intent = new Intent(TeklifIslemleriActivity.this, TeklifGonderActivity.class);
                intent.putExtra("id", 4);
                startActivity(intent);
                break;
            case R.id.btnHome:
                finish();
                break;
            case R.id.btnview1:
                btnview1.setTextColor(Color.parseColor("#000000"));
                view1.setVisibility(View.VISIBLE);

                btnview2.setTextColor(Color.parseColor("#ffffff"));
                btnview3.setTextColor(Color.parseColor("#ffffff"));
                view2.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                Toast.makeText(this, "Teklif işlemleri", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnview2:
                btnview2.setTextColor(Color.parseColor("#000000"));
                view2.setVisibility(View.VISIBLE);

                btnview1.setTextColor(Color.parseColor("#ffffff"));
                btnview3.setTextColor(Color.parseColor("#ffffff"));
                view1.setVisibility(View.GONE);
                view3.setVisibility(View.GONE);
                Toast.makeText(this, "Onaylanan teklifler", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnview3:
                btnview3.setTextColor(Color.parseColor("#000000"));
                view3.setVisibility(View.VISIBLE);

                btnview1.setTextColor(Color.parseColor("#ffffff"));
                btnview2.setTextColor(Color.parseColor("#ffffff"));
                view1.setVisibility(View.GONE);
                view2.setVisibility(View.GONE);
                Toast.makeText(this, "Kabul bekleyen teklifler", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
