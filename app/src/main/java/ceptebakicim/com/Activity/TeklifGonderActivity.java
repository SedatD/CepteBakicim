package ceptebakicim.com.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ceptebakicim.com.R;

public class TeklifGonderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private int type = 0;
    private EditText editText;
    private Spinner spinner_cocuk_yas, spinner_cocuk_okul, spinner_anne_calisiyor_mu, spinner_cocuk_hasta_mi, spinner_temizlik, spinner_yemek;
    private Spinner spinner_yasli_durumu, spinner_yasli_rutin, spinner_yasli_temizlik, spinner_yasli_yemek;
    private Spinner spinner_hasta_durumu, spinner_hasta_temizlik, spinner_hasta_yemek;
    private Spinner spinner_ev_turu, spinner_ev_kat, spinner_ev_oda, spinner_ev_cam_duvar, spinner_ev_yemek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_offer);

        ViewStub viewStub = (ViewStub) findViewById(R.id.viewStub);
        TextView textView_title = (TextView) findViewById(R.id.textView_title);
        ImageButton buttonSend = findViewById(R.id.buttonSend);
        TextView textSend = findViewById(R.id.textSend);

        buttonSend.setOnClickListener(this);
        textSend.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("id");

        switch (type) {
            case 1:
                //bebek
                textView_title.setText("Bebek Çocuk Bakımı");
                viewStub.setLayoutResource(R.layout.include_send_offer_bebek);
                View inflated = viewStub.inflate();
                editText = inflated.findViewById(R.id.editText);
                editText.setText("");

                spinner_cocuk_yas = inflated.findViewById(R.id.spinner_cocuk_yas);
                String[] spinnerItems = new String[]{"Çocuğunuzun Yaş Aralığı", "0-5 yaş", "6-12 yaş"};
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_cocuk_yas.setAdapter(spinnerArrayAdapter);
                spinner_cocuk_yas.setOnItemSelectedListener(this);

                spinner_cocuk_okul = inflated.findViewById(R.id.spinner_cocuk_okul);
                spinnerItems = new String[]{"Çocuğunuzun Okul Durumu", "Var", "Yok"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_cocuk_okul.setAdapter(spinnerArrayAdapter);
                spinner_cocuk_okul.setOnItemSelectedListener(this);

                spinner_anne_calisiyor_mu = inflated.findViewById(R.id.spinner_anne_calisiyor_mu);
                spinnerItems = new String[]{"Anne Çalışıyor Mu", "Çalışıyor", "Çalışmıyor"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_anne_calisiyor_mu.setAdapter(spinnerArrayAdapter);
                spinner_anne_calisiyor_mu.setOnItemSelectedListener(this);

                spinner_cocuk_hasta_mi = inflated.findViewById(R.id.spinner_cocuk_hasta_mi);
                spinnerItems = new String[]{"Çocuğunuzun Hastalığı Var Mı", "Var", "Yok"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_cocuk_hasta_mi.setAdapter(spinnerArrayAdapter);
                spinner_cocuk_hasta_mi.setOnItemSelectedListener(this);

                spinner_temizlik = inflated.findViewById(R.id.spinner_temizlik);
                spinnerItems = new String[]{"Temizlik Yapılacak Mı", "Yapılacak", "Yapılmayacak"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_temizlik.setAdapter(spinnerArrayAdapter);
                spinner_temizlik.setOnItemSelectedListener(this);

                spinner_yemek = inflated.findViewById(R.id.spinner_yemek);
                spinnerItems = new String[]{"Yemek Yapılacak Mı", "Yapılacak", "Yapılmayacak"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_yemek.setAdapter(spinnerArrayAdapter);
                spinner_yemek.setOnItemSelectedListener(this);

                break;
            case 2:
                //yasli
                textView_title.setText("Yaşlı Bakımı");
                viewStub.setLayoutResource(R.layout.include_send_offer_yasli);
                inflated = viewStub.inflate();

                spinner_yasli_durumu = inflated.findViewById(R.id.spinner_yasli_durumu);
                spinnerItems = new String[]{"Yaşlı Durumu", "Ayakta", "Yatalak"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_yasli_durumu.setAdapter(spinnerArrayAdapter);
                spinner_yasli_durumu.setOnItemSelectedListener(this);

                spinner_yasli_rutin = inflated.findViewById(R.id.spinner_yasli_rutin);
                spinnerItems = new String[]{"Rutin Yapılması Gerekenler", "Alt Değişimi", "Banyo", "Alt Değişimi & Banyo"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_yasli_rutin.setAdapter(spinnerArrayAdapter);
                spinner_yasli_rutin.setOnItemSelectedListener(this);

                spinner_yasli_temizlik = inflated.findViewById(R.id.spinner_yasli_temizlik);
                spinnerItems = new String[]{"Temizlik Yapılacak Mı", "Yapılacak", "Yapılmayacak"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_yasli_temizlik.setAdapter(spinnerArrayAdapter);
                spinner_yasli_temizlik.setOnItemSelectedListener(this);

                spinner_yasli_yemek = inflated.findViewById(R.id.spinner_yasli_yemek);
                spinnerItems = new String[]{"Yemek Yapılacak Mı", "Yapılacak", "Yapılmayacak"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_yasli_yemek.setAdapter(spinnerArrayAdapter);
                spinner_yasli_yemek.setOnItemSelectedListener(this);
                break;
            case 3:
                //hasta
                textView_title.setText("Hasta Bakımı");
                viewStub.setLayoutResource(R.layout.include_send_offer_hasta);
                inflated = viewStub.inflate();

                spinner_hasta_durumu = inflated.findViewById(R.id.spinner_hasta_durumu);
                spinnerItems = new String[]{"Hasta Durumu", "Alzhemier", "MS", "Demans", "Engelli", "Felçli", "Yoğun Bakım", "Yatalak", "Refakatçi", "Fizyoterapist", "DMD Kas Hastalığı"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_hasta_durumu.setAdapter(spinnerArrayAdapter);
                spinner_hasta_durumu.setOnItemSelectedListener(this);

                spinner_hasta_temizlik = inflated.findViewById(R.id.spinner_hasta_temizlik);
                spinnerItems = new String[]{"Temizlik Yapılacak Mı", "Yapılacak", "Yapılmayacak"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_hasta_temizlik.setAdapter(spinnerArrayAdapter);
                spinner_hasta_temizlik.setOnItemSelectedListener(this);

                spinner_hasta_yemek = inflated.findViewById(R.id.spinner_hasta_yemek);
                spinnerItems = new String[]{"Yemek Yapılacak Mı", "Yapılacak", "Yapılmayacak"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_hasta_yemek.setAdapter(spinnerArrayAdapter);
                spinner_hasta_yemek.setOnItemSelectedListener(this);
                break;
            case 4:
                //ev temizligi
                textView_title.setText("Ev Temizliği");
                viewStub.setLayoutResource(R.layout.include_send_offer_ev_temizligi);
                inflated = viewStub.inflate();

                spinner_ev_turu = inflated.findViewById(R.id.spinner_ev_turu);
                spinnerItems = new String[]{"Ev Türü", "Apartman Dairesi", "Villa", "Müstakil"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_ev_turu.setAdapter(spinnerArrayAdapter);
                spinner_ev_turu.setOnItemSelectedListener(this);

                spinner_ev_kat = inflated.findViewById(R.id.spinner_ev_kat);
                spinnerItems = new String[]{"Kat Sayısı", "1 Katlı", "2 Katlı", "3 Katlı", "4 Katlı", "5 Katlı"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_ev_kat.setAdapter(spinnerArrayAdapter);
                spinner_ev_kat.setOnItemSelectedListener(this);

                spinner_ev_oda = inflated.findViewById(R.id.spinner_ev_oda);
                spinnerItems = new String[]{"Oda Sayısı", "1+1", "2+1", "3+1", "4+1", "5+1", "5+2", "6+"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_ev_oda.setAdapter(spinnerArrayAdapter);
                spinner_ev_oda.setOnItemSelectedListener(this);

                spinner_ev_cam_duvar = inflated.findViewById(R.id.spinner_ev_cam_duvar);
                spinnerItems = new String[]{"Cam/Duvar Silme", "Yapılacak", "Yapılmayacak"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_ev_cam_duvar.setAdapter(spinnerArrayAdapter);
                spinner_ev_cam_duvar.setOnItemSelectedListener(this);

                spinner_ev_yemek = inflated.findViewById(R.id.spinner_ev_yemek);
                spinnerItems = new String[]{"Yemek Yapılacak Mı", "Yapılacak", "Yapılmayacak"};
                spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_ev_yemek.setAdapter(spinnerArrayAdapter);
                spinner_ev_yemek.setOnItemSelectedListener(this);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_cocuk_hasta_mi:
                if (spinner_cocuk_hasta_mi.getSelectedItemPosition() == 1)
                    editText.setVisibility(View.VISIBLE);
                else
                    editText.setVisibility(View.GONE);
                break;
            case R.id.spinner_cocuk_yas:
                if (spinner_cocuk_yas.getSelectedItemPosition() == 2)
                    spinner_cocuk_okul.setVisibility(View.VISIBLE);
                else
                    spinner_cocuk_okul.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textSend:
                switch (type) {
                    case 1:
                        //bebek
                        if (spinner_cocuk_yas.getSelectedItemPosition() == 0
                                || spinner_anne_calisiyor_mu.getSelectedItemPosition() == 0
                                || spinner_cocuk_hasta_mi.getSelectedItemPosition() == 0
                                || spinner_temizlik.getSelectedItemPosition() == 0
                                || spinner_yemek.getSelectedItemPosition() == 0)
                            Toast.makeText(this, "Lütfen bütün alanları seçiniz", Toast.LENGTH_SHORT).show();
                        else if (spinner_cocuk_yas.getSelectedItemPosition() == 2 && spinner_cocuk_okul.getSelectedItemPosition() == 0) {
                            Toast.makeText(this, "Lütfen bütün alanları seçiniz", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            int userId = preferences.getInt("userId", -1);
                            request(userId);
                        }
                        break;
                    case 2:
                        //yasli
                        if (spinner_yasli_durumu.getSelectedItemPosition() == 0
                                || spinner_yasli_rutin.getSelectedItemPosition() == 0
                                || spinner_yasli_temizlik.getSelectedItemPosition() == 0
                                || spinner_yasli_yemek.getSelectedItemPosition() == 0)
                            Toast.makeText(this, "Lütfen bütün alanları seçiniz", Toast.LENGTH_SHORT).show();
                        else {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            int userId = preferences.getInt("userId", -1);
                            request(userId);
                        }
                        break;
                    case 3:
                        //hasta
                        if (spinner_hasta_durumu.getSelectedItemPosition() == 0
                                || spinner_hasta_temizlik.getSelectedItemPosition() == 0
                                || spinner_hasta_yemek.getSelectedItemPosition() == 0)
                            Toast.makeText(this, "Lütfen bütün alanları seçiniz", Toast.LENGTH_SHORT).show();
                        else {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            int userId = preferences.getInt("userId", -1);
                            request(userId);
                        }
                        break;
                    case 4:
                        //ev temizligi
                        if (spinner_ev_turu.getSelectedItemPosition() == 0
                                || spinner_ev_kat.getSelectedItemPosition() == 0
                                || spinner_ev_oda.getSelectedItemPosition() == 0
                                || spinner_ev_cam_duvar.getSelectedItemPosition() == 0
                                || spinner_ev_yemek.getSelectedItemPosition() == 0)
                            Toast.makeText(this, "Lütfen bütün alanları seçiniz", Toast.LENGTH_SHORT).show();
                        else {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            int userId = preferences.getInt("userId", -1);
                            request(userId);
                        }
                        break;
                }
                break;
            case R.id.buttonSend:
                switch (type) {
                    case 1:
                        //bebek
                        if (spinner_cocuk_yas.getSelectedItemPosition() == 0
                                || spinner_anne_calisiyor_mu.getSelectedItemPosition() == 0
                                || spinner_cocuk_hasta_mi.getSelectedItemPosition() == 0
                                || spinner_temizlik.getSelectedItemPosition() == 0
                                || spinner_yemek.getSelectedItemPosition() == 0)
                            Toast.makeText(this, "Lütfen bütün alanları seçiniz", Toast.LENGTH_SHORT).show();
                        else if (spinner_cocuk_yas.getSelectedItemPosition() == 2 && spinner_cocuk_okul.getSelectedItemPosition() == 0) {
                            Toast.makeText(this, "Lütfen bütün alanları seçiniz", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            int userId = preferences.getInt("userId", -1);
                            request(userId);
                        }
                        break;
                    case 2:
                        //yasli
                        if (spinner_yasli_durumu.getSelectedItemPosition() == 0
                                || spinner_yasli_rutin.getSelectedItemPosition() == 0
                                || spinner_yasli_temizlik.getSelectedItemPosition() == 0
                                || spinner_yasli_yemek.getSelectedItemPosition() == 0)
                            Toast.makeText(this, "Lütfen bütün alanları seçiniz", Toast.LENGTH_SHORT).show();
                        else {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            int userId = preferences.getInt("userId", -1);
                            request(userId);
                        }
                        break;
                    case 3:
                        //hasta
                        if (spinner_hasta_durumu.getSelectedItemPosition() == 0
                                || spinner_hasta_temizlik.getSelectedItemPosition() == 0
                                || spinner_hasta_yemek.getSelectedItemPosition() == 0)
                            Toast.makeText(this, "Lütfen bütün alanları seçiniz", Toast.LENGTH_SHORT).show();
                        else {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            int userId = preferences.getInt("userId", -1);
                            request(userId);
                        }
                        break;
                    case 4:
                        //ev temizligi
                        if (spinner_ev_turu.getSelectedItemPosition() == 0
                                || spinner_ev_kat.getSelectedItemPosition() == 0
                                || spinner_ev_oda.getSelectedItemPosition() == 0
                                || spinner_ev_cam_duvar.getSelectedItemPosition() == 0
                                || spinner_ev_yemek.getSelectedItemPosition() == 0)
                            Toast.makeText(this, "Lütfen bütün alanları seçiniz", Toast.LENGTH_SHORT).show();
                        else {
                            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            int userId = preferences.getInt("userId", -1);
                            request(userId);
                        }
                        break;
                }
                break;
        }
    }

    private void request(final int userId) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        String mUrl = "https://www.ceptebakicim.com/json/aileTeklifOlustur";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("SendOfferAct", "Response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")) {
                                Toast.makeText(TeklifGonderActivity.this, "Teklifiniz işleme alındı", Toast.LENGTH_SHORT).show();
                                finish();
                            } else
                                Toast.makeText(TeklifGonderActivity.this, "İşlem başarısız", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("SendOfferAct", "Error : " + error);
                        Toast.makeText(TeklifGonderActivity.this, "İşlem başarısız", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> jsonParams = new HashMap<String, String>();
                jsonParams.put("teklif", type + "");
                jsonParams.put("userid", userId + "");
                if (type == 1) {
                    jsonParams.put("cocukYasAralik", spinner_cocuk_yas.getSelectedItem().toString());
                    jsonParams.put("cocukOkulDurumu", spinner_cocuk_okul.getSelectedItem().toString());
                    jsonParams.put("anneCalisiyorMu", spinner_anne_calisiyor_mu.getSelectedItem().toString());
                    jsonParams.put("cocukHastaMi", spinner_cocuk_hasta_mi.getSelectedItem().toString());
                    jsonParams.put("cocukHastalikDetay", editText.getText().toString());
                    jsonParams.put("temizlik", spinner_temizlik.getSelectedItem().toString());
                    jsonParams.put("yemek", spinner_yemek.getSelectedItem().toString());
                }
                if (type == 2) {
                    jsonParams.put("yasliDurumu", spinner_yasli_durumu.getSelectedItem().toString());
                    jsonParams.put("yapilmasiGerekenler", spinner_yasli_rutin.getSelectedItem().toString());
                    jsonParams.put("temizlik", spinner_yasli_temizlik.getSelectedItem().toString());
                    jsonParams.put("yemek", spinner_yasli_yemek.getSelectedItem().toString());
                }
                if (type == 3) {
                    jsonParams.put("hastalikType", spinner_hasta_durumu.getSelectedItem().toString());
                    jsonParams.put("temizlik", spinner_hasta_temizlik.getSelectedItem().toString());
                    jsonParams.put("yemek", spinner_hasta_yemek.getSelectedItem().toString());
                }
                if (type == 4) {
                    jsonParams.put("evTuru", spinner_ev_turu.getSelectedItem().toString());
                    jsonParams.put("kat", spinner_ev_kat.getSelectedItem().toString());
                    jsonParams.put("oda", spinner_ev_oda.getSelectedItem().toString());
                    jsonParams.put("camDuvar", spinner_ev_cam_duvar.getSelectedItem().toString());
                    jsonParams.put("yemek", spinner_ev_yemek.getSelectedItem().toString());
                }
                return jsonParams;
            }

        };
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
