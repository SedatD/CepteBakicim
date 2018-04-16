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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ceptebakicim.com.Adapter.CvAdapter;
import ceptebakicim.com.Pojo.CvPojo;
import ceptebakicim.com.R;

public class CvActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private ImageView imageView, imginvis;
    private Button button_teklif, button_iseAl, button_iseAlma, button_istenCikart, button_mesajlas;
    private TextView textView;
    private String name;
    private int caryId;
    private int interviewId = -1;
    private int mItem = -1;
    private int mItem2 = -1;
    private String osi;
    private TextView textName, textNat, textAge;
    private int userType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv);

        listView = findViewById(R.id.listView);
        imageView = findViewById(R.id.imageView);
        imginvis = findViewById(R.id.imginvis);
        button_teklif = findViewById(R.id.button_teklif);
        button_iseAl = findViewById(R.id.button_iseAl);
        button_iseAlma = findViewById(R.id.button_iseAlma);
        button_istenCikart = findViewById(R.id.button_istenCikart);
        button_mesajlas = findViewById(R.id.button_mesajlas);
        textView = findViewById(R.id.textView);
        textName = findViewById(R.id.textName);
        textNat = findViewById(R.id.textNat);
        textAge = findViewById(R.id.textAge);
        LinearLayout linLayAileButtonlari = findViewById(R.id.linLayAileButtonlari);

        listView.setClickable(false);
        button_teklif.setOnClickListener(this);
        button_iseAl.setOnClickListener(this);
        button_iseAlma.setOnClickListener(this);
        button_istenCikart.setOnClickListener(this);
        button_mesajlas.setOnClickListener(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int userId = preferences.getInt("userId", -1);
        userType = preferences.getInt("userType", -1);

        if (userType == 2 || userType == 3) {
            linLayAileButtonlari.setVisibility(View.GONE);
        }

        Bundle bundle = getIntent().getExtras();
        caryId = bundle.getInt("id");
        request(caryId);
    }

    private void request(int id) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        final String url = "https://www.ceptebakicim.com/json/userList?userType=2&userId=" + id;
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.wtf("CvAct request", "response : " + response.toString());
                        try {
                            final ArrayList<String> list = new ArrayList<String>();
                            JSONObject jsonObject = (JSONObject) response.get(0);

                            osi = jsonObject.getString("oneSignalID");

                            Glide.with(getApplicationContext()).load(jsonObject.getString("imageYolu")).into(imageView);

                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            int userId = preferences.getInt("userId", -1);

                            if (jsonObject.getBoolean("interviewDetailBoolean")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("interviewDetail");
                                interviewId = jsonObject1.getInt("interviewID");
                                switch (jsonObject1.getInt("status")) {
                                    case 0:
                                        button_teklif.setVisibility(View.VISIBLE);
                                        break;
                                    case 1:
                                        if (userId == jsonObject1.getInt("familyID")) {
                                            if (jsonObject1.getInt("action") == 0) {
                                                button_mesajlas.setVisibility(View.VISIBLE);
                                                button_iseAl.setVisibility(View.VISIBLE);
                                                button_iseAlma.setVisibility(View.VISIBLE);
                                            }
                                            if (jsonObject1.getInt("action") == 1) {
                                                button_mesajlas.setVisibility(View.VISIBLE);
                                                //coktan seçmeli pop up açılacak
                                                button_istenCikart.setVisibility(View.VISIBLE);
                                            }
                                        } else {
                                            if (userType == 1) {
                                                textView.setVisibility(View.VISIBLE);
                                                imginvis.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        break;
                                    case 2:
                                        if (userType == 1) {
                                            textView.setVisibility(View.VISIBLE);
                                            imginvis.setVisibility(View.VISIBLE);
                                        }
                                        break;
                                    case -1:
                                        button_teklif.setVisibility(View.VISIBLE);
                                        break;
                                }
                            } else {
                                button_teklif.setVisibility(View.VISIBLE);
                            }

                            name = jsonObject.getString("name");

                            /*list.add("Adı Soyadı : " + jsonObject.getString("name"));
                            list.add("Yaş : " + jsonObject.getString("yas"));
                            list.add("Uyruk : " + jsonObject.getString("uyruk"));
                            list.add("Sigara Kullanımı : " + jsonObject.getString("sigara"));
                            list.add("Çalışma Şekli : " + jsonObject.getString("calismaS"));
                            list.add("Bildiği Diller : " + jsonObject.getString("dil"));
                            list.add("Maaş Beklentisi : " + jsonObject.getString("maas"));
                            if (jsonObject.getInt("userType") == 2) {
                                //bakıcı
                                list.add("Çocuk Bakım : " + jsonObject.getString("cocukBakim"));
                                list.add("Hasta Bakım : " + jsonObject.getString("hastaBakim"));
                                list.add("Yaşlı Bakım : " + jsonObject.getString("yasliBakim"));
                                list.add("Temizlik : " + jsonObject.getString("temizlik"));
                                list.add("Yemek : " + jsonObject.getString("yemek"));
                                list.add("Ütü : " + jsonObject.getString("utu"));
                            } else if (jsonObject.getInt("userType") == 3) {
                                //erkek
                                list.add("Ehliyet : " + jsonObject.getString("ehliyet"));
                                list.add("Şehir Dışı : " + jsonObject.getString("sehirD"));
                                list.add("Yapabileceği İşler : " + jsonObject.getString("yetenek"));
                                list.add("Meslek : " + jsonObject.getString("meslek"));
                            }

                            final StableArrayAdapter adapter = new StableArrayAdapter(getApplicationContext(), R.layout.custom_list_item, list);
                            listView.setAdapter(adapter);*/


                            ArrayList<CvPojo> dataModels = new ArrayList<>();

                            textName.setText(jsonObject.getString("name"));
                            textNat.setText(jsonObject.getString("uyruk"));
                            textAge.setText("  /  " + jsonObject.getString("yas"));
                            //dataModels.add(new CvPojo("Adı Soyadı", jsonObject.getString("name")));
                            //dataModels.add(new CvPojo("Yaş", jsonObject.getString("yas")));
                            //dataModels.add(new CvPojo("Uyruk", jsonObject.getString("uyruk")));
                            dataModels.add(new CvPojo("Sigara Kullanımı", jsonObject.getString("sigara")));
                            dataModels.add(new CvPojo("Çalışma Şekli", jsonObject.getString("calismaS")));
                            dataModels.add(new CvPojo("Bildiği Diller", jsonObject.getString("dil")));
                            dataModels.add(new CvPojo("Maaş Beklentisi", jsonObject.getString("maas")));
                            if (jsonObject.getInt("userType") == 2) {
                                //bakıcı
                                dataModels.add(new CvPojo("Çocuk Bakım", jsonObject.getString("cocukBakim")));
                                dataModels.add(new CvPojo("Hasta Bakım", jsonObject.getString("hastaBakim")));
                                dataModels.add(new CvPojo("Yaşlı Bakım", jsonObject.getString("yasliBakim")));
                                dataModels.add(new CvPojo("Temizlik", jsonObject.getString("temizlik")));
                                dataModels.add(new CvPojo("Yemek", jsonObject.getString("yemek")));
                                dataModels.add(new CvPojo("Ütü", jsonObject.getString("utu")));
                            } else if (jsonObject.getInt("userType") == 3) {
                                //erkek
                                dataModels.add(new CvPojo("Ehliyet", jsonObject.getString("ehliyet")));
                                dataModels.add(new CvPojo("Şehir Dışı", jsonObject.getString("sehirD")));
                                dataModels.add(new CvPojo("Yapabileceği İşler", jsonObject.getString("yetenek")));
                                dataModels.add(new CvPojo("Meslek", jsonObject.getString("meslek")));
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
                        Log.wtf("CvAct request", "error : " + error);
                    }
                }
        );
        queue.add(getRequest);
    }

    private void requestIseAlAlma(String url, final int iseAl) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        StringRequest getRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("CvAct requestIseAlAlma", "response : " + response);
                        if (Integer.parseInt(response) == 1) {
                            Toast.makeText(CvActivity.this, "İstek işleme alındı", Toast.LENGTH_SHORT).show();
                            try {
                                Log.wtf("osi ", "" + osi);
                                String text = "";
                                if (iseAl == 0)
                                    text = "İşe alınmadınız";
                                else if (iseAl == 1)
                                    text = "İşe alındınız";
                                else if (iseAl == -1)
                                    text = "İşten çıkartıldınız";

                                OneSignal.postNotification(new JSONObject("{'contents': {'en':'" + text + "'}, 'include_player_ids': ['" + osi + "']}"), null);
                                //OneSignal.postNotification(new JSONObject("{'contents': {'en':'" + text + "'}, 'include_player_ids': ['" + osi + "'],'data':{'interviewid':" + interviewId + ",'serviceid':" + serviceID + "}}"), null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else
                            Toast.makeText(CvActivity.this, "İstek işleme alınamadı bir hata oluştu", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("CvAct requestIseAlAlma", "error : " + error);
                        Toast.makeText(CvActivity.this, "İstek işleme alınamadı bir hata oluştu", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(getRequest);
    }

    private void requestTeklif(final Integer serviceID) {
        RequestQueue queue = Volley.newRequestQueue(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int userId = preferences.getInt("userId", -1);
        final String url = "https://www.ceptebakicim.com/json/AileTeklifGonder?familyID=" + userId + "&caryID=" + caryId + "&serviceID=" + serviceID;
        StringRequest getRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("CvAct", "response : " + response);
                        if (Integer.parseInt(response) == 1) {
                            Toast.makeText(CvActivity.this, "Teklif gönderildi", Toast.LENGTH_SHORT).show();
                            try {
                                OneSignal.postNotification(new JSONObject("{'contents': {'en':'Yeni bir teklifin var'}, 'include_player_ids': ['" + osi + "'],'data':{'banaOzel':" + true + "}}"), null);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            finish();
                        } else
                            Toast.makeText(CvActivity.this, "Teklif gönderilemedi bir hata oluştu", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("CvAct", "error : " + error);
                        Toast.makeText(CvActivity.this, "Teklif gönderilemedi bir hata oluştu", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(getRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_teklif:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String jsonArray_kabulEdilen = preferences.getString("kabulEdilen", null);
                if (jsonArray_kabulEdilen != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(jsonArray_kabulEdilen);
                        if (jsonArray.length() == 0) {
                            startActivity(new Intent(this, TeklifIslemleriActivity.class));
                            finish();
                        } else {
                            String[] typeTitles = new String[jsonArray.length()];
                            Integer[] serviceIDs = new Integer[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                typeTitles[i] = jsonObject.getString("typeTitle");
                                serviceIDs[i] = jsonObject.getInt("serviceID");
                            }
                            showCustomDialog2(typeTitles, serviceIDs);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "İşlem gerçekleştirilemedi. Tekrar deneyiniz.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    Toast.makeText(this, "İşlem gerçekleştirilemedi. Tekrar deneyiniz.", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case R.id.button_iseAl:
                if (interviewId != -1)
                    requestIseAlAlma("https://www.ceptebakicim.com/json/AileIseAlAlmaCikart?interviewID=" + interviewId + "&iseAl=1", 1);
                break;
            case R.id.button_iseAlma:
                if (interviewId != -1)
                    requestIseAlAlma("https://www.ceptebakicim.com/json/AileIseAlAlmaCikart?interviewID=" + interviewId + "&iseAl=0", 0);
                break;
            case R.id.button_istenCikart:
                if (interviewId != -1) {
                    String[] grpname = new String[4];
                    grpname[0] = "Uygunsuz Davranış";
                    grpname[1] = "Cinsel Taciz";
                    grpname[2] = "Hırsızlık";
                    grpname[3] = "İhtiyaç Dışı";
                    showCustomDialog(grpname);
                }
                break;
            case R.id.button_mesajlas:
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra("chatId", caryId);
                startActivity(intent);
                finish();
                break;
        }
    }

    void showCustomDialog(final String[] grpname) {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        //alt_bld.setIcon(R.drawable.icon);
        alt_bld.setTitle("İşten çıkartma sebebinizi seçiniz");
        alt_bld
                .setSingleChoiceItems(grpname, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Log.wtf("CvAct", "isten cikart item : " + item + " / " + grpname[item]);
                        mItem = item;
                    }
                })
                .setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (mItem == -1)
                            Toast.makeText(CvActivity.this, "Lütfen bir sebep seçiniz", Toast.LENGTH_SHORT).show();
                        else {
                            requestIseAlAlma("https://www.ceptebakicim.com/json/AileIseAlAlmaCikart?interviewID=" + interviewId + "&iseAl=-1&radioValue=" + (mItem + 1), -1);
                            dialog.dismiss();
                        }
                    }
                })
                .setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    void showCustomDialog2(final String[] typeTitles, final Integer[] serviceIDs) {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        //alt_bld.setIcon(R.drawable.icon);
        alt_bld.setTitle(name + " için teklif seçiniz");
        alt_bld
                .setSingleChoiceItems(typeTitles, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Log.wtf("CvAct", "teklif seç item : " + item + " / " + typeTitles[item]);
                        mItem2 = item;
                    }
                })
                .setPositiveButton("Gönder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (mItem2 == -1)
                            Toast.makeText(CvActivity.this, "Lütfen bir teklif seçiniz", Toast.LENGTH_SHORT).show();
                        else {
                            requestTeklif(serviceIDs[mItem2]);
                            dialog.dismiss();

                        }
                    }
                })
                .setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
