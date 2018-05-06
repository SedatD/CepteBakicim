package ceptebakicim.com.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private int userId, interview;
    private String osi, webSignalID;
    private Button btn_bakici_isten_cik;
    private LinearLayout linLayBottomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teklif_detay);

        textView_durum = findViewById(R.id.textView_durum);
        listView = findViewById(R.id.listView);
        Button button_pos = findViewById(R.id.button_pos);
        Button button_neg = findViewById(R.id.button_neg);
        btn_bakici_isten_cik = findViewById(R.id.btn_bakici_isten_cik);
        linLayBottomButton = findViewById(R.id.linLayBottomButton);
        LinearLayout linLayAile = findViewById(R.id.linLayAile);
        button_pos.setOnClickListener(this);
        button_neg.setOnClickListener(this);
        btn_bakici_isten_cik.setOnClickListener(this);
        listView.setClickable(false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getInt("userId", -1);
        int userType = preferences.getInt("userType", -1);

        if (userType == 1)
            linLayAile.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();
        String jsonObjString = null;
        if (bundle != null) {
            jsonObjString = bundle.getString("jsonObj");
        } else {
            Toast.makeText(TeklifDetayActivity.this, "Bir hata oluştu", Toast.LENGTH_SHORT).show();
            finish();
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonObjString);
            fillList(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(TeklifDetayActivity.this, "Bir hata oluştu", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void fillList(JSONObject jsonObject) {
        try {
            osi = jsonObject.getString("oneSignalID");
            webSignalID = jsonObject.getString("webSignalID");
            interview = jsonObject.getInt("interviewID");

            if (jsonObject.getInt("status") == 0) {
                textView_durum.setText("Beklemede");
                textView_durum.setTextColor(getResources().getColor(R.color.beklemede));
            } else if (jsonObject.getInt("status") == 1) {
                textView_durum.setText("Onaylanan");
                textView_durum.setTextColor(getResources().getColor(R.color.onaylanan));
            }

            if (jsonObject.getInt("interviewStatus") == 0) {
                linLayBottomButton.setVisibility(View.VISIBLE);
            } else if (jsonObject.getInt("interviewStatus") == 1) {
                btn_bakici_isten_cik.setVisibility(View.VISIBLE);
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
            Toast.makeText(TeklifDetayActivity.this, "Bir hata oluştu", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void postReq(final int sorgu, final int temp) {
        final String url = "https://www.ceptebakicim.com/json/bakiciteklifIslem?sorgu=" + sorgu + "&interviewID=" + interview + "&userid=" + userId;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("TeklifDetayAct", "Response : " + response + " / url : " + url);
                        String text = "";
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")) {
                                Toast.makeText(TeklifDetayActivity.this, "İşleminiz gerçekleştirildi", Toast.LENGTH_SHORT).show();

                                if (sorgu == 0)
                                    text = "Bakıcı teklifinizi reddetti";
                                else
                                    text = "Bakıcı teklifinizi kabul etti";

                                if (temp == 1)
                                    text = "Bakıcı işten ayrıldı";

                                try {
                                    OneSignal.postNotification(new JSONObject("{'contents': {'en':'" + text + "'}, 'include_player_ids': ['" + osi + "'],'data':{'caryId':" + userId + "}}"), null);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    OneSignal.postNotification(new JSONObject("{'contents': {'en':'" + text + "'}, 'include_player_ids': ['" + webSignalID + "'],'data':{'caryId':" + userId + "}}"), null);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else
                                Toast.makeText(TeklifDetayActivity.this, "Bir hata oluştu", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(TeklifDetayActivity.this, "Bir hata oluştu", Toast.LENGTH_SHORT).show();
                        }

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
    public void onClick(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TeklifDetayActivity.this);
        builder.setTitle("Uyarı");
        builder.setMessage("Bu işlemi yapmak istediğinize emin misiniz?");
        builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                switch (view.getId()) {
                    case R.id.button_pos:
                        postReq(1, -1);
                        break;
                    case R.id.button_neg:
                        postReq(0, -1);
                        break;
                    case R.id.btn_bakici_isten_cik:
                        postReq(0, 1);
                        break;
                }
            }
        });
        builder.show();
    }

}
