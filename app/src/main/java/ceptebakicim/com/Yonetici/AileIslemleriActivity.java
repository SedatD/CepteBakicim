package ceptebakicim.com.Yonetici;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ceptebakicim.com.R;

public class AileIslemleriActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView;
    private RecyclerView recyclerView;
    private Button btnview1, btnview2, btnview3;
    private JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aile_islemleri);

        textView = findViewById(R.id.textView);
        recyclerView = findViewById(R.id.recyclerView);
        btnview1 = findViewById(R.id.btnview1);
        btnview2 = findViewById(R.id.btnview2);
        btnview3 = findViewById(R.id.btnview3);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnview1.setOnClickListener(this);
        btnview2.setOnClickListener(this);
        btnview3.setOnClickListener(this);

        getRequest();
    }

    void pushList(int status) {
        try {

            if (jsonArray.length() == 0)
                textView.setVisibility(View.VISIBLE);

            ArrayList results = new ArrayList<AileListPojo>();
            results.clear();
            AileListPojo obj;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                if (status == -1) {
                    obj = new AileListPojo(jsonObject.getInt("id"), jsonObject.getInt("status"), jsonObject.getString("name"), jsonObject.getString("oneSignalID"), jsonObject.getString("webSignalID"));
                    results.add(obj);
                } else if (status == jsonObject.getInt("status")) {
                    obj = new AileListPojo(jsonObject.getInt("id"), jsonObject.getInt("status"), jsonObject.getString("name"), jsonObject.getString("oneSignalID"), jsonObject.getString("webSignalID"));
                    results.add(obj);
                }
            }

            recyclerView.setAdapter(new AileListAdapter(results, getApplicationContext(), new AileListAdapter.AileListAdapterListener() {
                @Override
                public void iconTextViewOnClick(View v, final AileListPojo aileListObj) {
                    String text = "ONAY";
                    String textMsj = "Hesabınız onaylanmıştır.";
                    if (aileListObj.getStatus() == 1) {
                        text = "RED";
                        textMsj = "Hesabınız askıya alınmıştır.";
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(AileIslemleriActivity.this);
                    builder.setTitle("Uyarı");
                    builder.setMessage("Hangi işlemi yapmak istiyorsunuz?");

                    builder.setNegativeButton("SİL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            getRequestForOnayRed(aileListObj.getId(), 2);
                            try {
                                OneSignal.postNotification(new JSONObject("{'contents': {'en':Hesabınız silinmiştir.}, 'include_player_ids': ['" + aileListObj.getOneSignalID() + "']}"), null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                OneSignal.postNotification(new JSONObject("{'contents': {'en':Hesabınız silinmiştir.}, 'include_player_ids': ['" + aileListObj.getWebSignalID() + "']}"), null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    final String finalTextMsj = textMsj;
                    builder.setPositiveButton(text, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            int aq = 0;
                            if (aileListObj.getStatus() == 0)
                                aq = 1;
                            getRequestForOnayRed(aileListObj.getId(), aq);
                            try {
                                OneSignal.postNotification(new JSONObject("{'contents': {'en':" + finalTextMsj + "}, 'include_player_ids': ['" + aileListObj.getOneSignalID() + "']}"), null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                OneSignal.postNotification(new JSONObject("{'contents': {'en':" + finalTextMsj + "}, 'include_player_ids': ['" + aileListObj.getWebSignalID() + "']}"), null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    builder.show();
                }

                @Override
                public void iconImageViewOnClick(View v, int position) {

                }

                @Override
                public void iconImageUnFollowOnClick(View v, int position) {

                }

            }));

            if (results.size() == 0)
                textView.setVisibility(View.VISIBLE);
            else
                textView.setVisibility(View.INVISIBLE);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getRequest() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.ceptebakicim.com/json/userListAile",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("YoneticiMain AileIslemleriAct", "Response : " + response);
                        try {
                            jsonArray = new JSONArray(response);
                            pushList(-1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("YoneticiMain AileIslemleriAct", "Error : " + error);
                    }
                });
        queue.add(stringRequest);
    }

    private void getRequestForOnayRed(int id, int sorgu) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.ceptebakicim.com/json/userKabulRed?userid=" + id + "&sorgu=" + sorgu,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("YoneticiMain AileIslemleriAct", "Response : " + response);
                        if (Integer.parseInt(response) == 1) {
                            Toast.makeText(getApplicationContext(), "İşlem başarılı", Toast.LENGTH_SHORT).show();
                            getRequest();
                            btnview1.setTextColor(Color.parseColor("#000000"));
                            btnview2.setTextColor(Color.parseColor("#ffffff"));
                            btnview3.setTextColor(Color.parseColor("#ffffff"));
                        } else
                            Toast.makeText(getApplicationContext(), "İşlem gerçekleştirilemedi", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("YoneticiMain AileIslemleriAct", "Error : " + error);
                        Toast.makeText(getApplicationContext(), "İşlem gerçekleştirilemedi", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnview1:
                btnview1.setTextColor(Color.parseColor("#000000"));
                btnview2.setTextColor(Color.parseColor("#ffffff"));
                btnview3.setTextColor(Color.parseColor("#ffffff"));
                pushList(-1);
                Toast.makeText(this, "Tüm aileler", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnview2:
                btnview2.setTextColor(Color.parseColor("#000000"));
                btnview1.setTextColor(Color.parseColor("#ffffff"));
                btnview3.setTextColor(Color.parseColor("#ffffff"));
                pushList(1);
                Toast.makeText(this, "Onaylanmış aileler", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnview3:
                btnview3.setTextColor(Color.parseColor("#000000"));
                btnview1.setTextColor(Color.parseColor("#ffffff"));
                btnview2.setTextColor(Color.parseColor("#ffffff"));
                pushList(0);
                Toast.makeText(this, "Onay bekleyen aileler", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
