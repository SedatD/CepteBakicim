package ceptebakicim.com.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import ceptebakicim.com.Adapter.CvAdapter;
import ceptebakicim.com.MainActivity;
import ceptebakicim.com.Pojo.CvPojo;
import ceptebakicim.com.R;

public class TeklifDetayActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textView_durum;
    private ListView listView;
    private int interview, userId,service;
    private String osi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teklif_detay);

        textView_durum = findViewById(R.id.textView_durum);
        listView = findViewById(R.id.listView);
        Button button_pos = findViewById(R.id.button_pos);
        Button button_neg = findViewById(R.id.button_neg);
        LinearLayout linLayBottomButton = findViewById(R.id.linLayBottomButton);
        LinearLayout linLayAile = findViewById(R.id.linLayAile);
        button_pos.setOnClickListener(this);
        button_neg.setOnClickListener(this);
        listView.setClickable(false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getInt("userId", -1);
        int userType = preferences.getInt("userType", -1);

        if (userType == 1)
            linLayBottomButton.setVisibility(View.GONE);
        else if (userType == 2 || userType == 3)
            linLayAile.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        interview = bundle.getInt("interview");
        service = bundle.getInt("pos");
        getRequest(bundle.getInt("pos"));
    }

    private void getRequest(int pos) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.ceptebakicim.com/json/TeklifDetay?serviceID=" + pos,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("TeklifDetayAct", "Response : " + response);
                        try {
                            final ArrayList<String> list = new ArrayList<String>();

                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                            osi = jsonObject.getString("oneSignalID");

                            if (jsonObject.getInt("status") == 0) {
                                textView_durum.setText("Beklemede");
                                textView_durum.setTextColor(getResources().getColor(R.color.beklemede));
                            } else if (jsonObject.getInt("status") == 1) {
                                textView_durum.setText("Onaylanan");
                                textView_durum.setTextColor(getResources().getColor(R.color.onaylanan));
                            }

                            ArrayList<CvPojo> dataModels = new ArrayList<>();

                            dataModels.add(new CvPojo("Adı Soyadı", jsonObject.getString("name")));
                            dataModels.add(new CvPojo("Hizmet Türü", jsonObject.getString("typeTitle")));
                            switch (jsonObject.getString("typeTitle")) {
                                case "Bebek / Çocuk Bakımı":
                                    dataModels.add(new CvPojo("Çocuk/Bebek Yaşı", jsonObject.getString("yas")));
                                    dataModels.add(new CvPojo("Hastalık Durumu", jsonObject.getString("hastalik")));
                                    if (!jsonObject.getString("aciklama").equals(""))
                                        dataModels.add(new CvPojo("Hastalık Açıklaması", jsonObject.getString("aciklama")));
                                    dataModels.add(new CvPojo("Anne Çalışma Durumu", jsonObject.getString("calisma")));
                                    dataModels.add(new CvPojo("Temizlik Hizmeti", jsonObject.getString("temizlik")));
                                    dataModels.add(new CvPojo("Yemek Hizmeti", jsonObject.getString("yemek")));
                                    break;
                                case "Ev Temizliği":
                                    dataModels.add(new CvPojo("Ev Türü", jsonObject.getString("evturu")));
                                    dataModels.add(new CvPojo("Kat Sayısı", jsonObject.getString("kat")));
                                    dataModels.add(new CvPojo("Oda Sayısı", jsonObject.getString("oda")));
                                    dataModels.add(new CvPojo("Yemek Hizmeti", jsonObject.getString("yemek")));
                                    break;
                                case "Hasta Bakımı":
                                    dataModels.add(new CvPojo("Hastalık Türü", jsonObject.getString("hastalikType")));
                                    dataModels.add(new CvPojo("Temizlik Hizmeti", jsonObject.getString("temizlik")));
                                    dataModels.add(new CvPojo("Yemek Hizmeti", jsonObject.getString("yemek")));
                                    break;
                                case "Yaşlı Bakımı":
                                    dataModels.add(new CvPojo("Yaşlı Durumu", jsonObject.getString("yDurum")));
                                    dataModels.add(new CvPojo("Rutin Yapılması Gerekenler", jsonObject.getString("yTemizlik")));
                                    dataModels.add(new CvPojo("Temizlik Hizmeti", jsonObject.getString("temizlik")));
                                    dataModels.add(new CvPojo("Yemek Hizmeti", jsonObject.getString("yemek")));
                                    break;
                            }

                            listView.setAdapter(new CvAdapter(dataModels, getApplicationContext()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("TeklifDetayAct", "Error : " + error);
                    }
                });
        queue.add(stringRequest);
    }

    private void postReq(final int sorgu) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                "https://www.ceptebakicim.com/json/bakiciteklifIslem?sorgu=" + sorgu + "&interviewID=" + interview + "&userid=" + userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("TeklifDetayAct", "Response : " + response);
                        int aq = Integer.parseInt(response);
                        String text = "";

                        if (aq == 1) {
                            Toast.makeText(TeklifDetayActivity.this, "İşleminiz gerçekleştirildi", Toast.LENGTH_SHORT).show();

                            if (sorgu == 0)
                                text = "Bakıcı teklifinizi reddetti";
                            else
                                text = "Bakıcı teklifinizi kabul etti";

                            try {
                                OneSignal.postNotification(new JSONObject("{'contents': {'en':'" + text + "'}, 'include_player_ids': ['" + osi + "'],'data':{'caryId':" + userId + "}}"), null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else if (aq == 0)
                            Toast.makeText(TeklifDetayActivity.this, "Bir hata oluştu", Toast.LENGTH_SHORT).show();

                        finish();
                        startActivity(new Intent(TeklifDetayActivity.this, MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("TeklifDetayAct", "Error : " + error);
                    }
                });
        queue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_pos:
                postReq(1);
                break;
            case R.id.button_neg:
                postReq(0);
                break;
        }
    }

}
