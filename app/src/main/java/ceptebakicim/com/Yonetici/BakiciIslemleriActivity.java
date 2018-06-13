package ceptebakicim.com.Yonetici;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import ceptebakicim.com.R;

public class BakiciIslemleriActivity extends AppCompatActivity implements View.OnClickListener {
    private int wpID = -1;
    private String imageString;
    private ImageView imageView;
    private EditText editText_name, editText_sifre, editText_email, editText_telefon, editText_pasaport, editText_ilce, editText_deneyim;
    private Spinner spinner_il, spinner_uyruk, spinner_dil, spinner_yemek, spinner_utu, spinner_temizlik, spinner_sigara, spinner_cocuk_bakim,
            spinner_yasli_bakim, spinner_maas, spinner_calisma_sekli, spinner_oturma_izni, spinner_ist_disi;
    private EditText date_field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakici_islemleri);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            wpID = bundle.getInt("wpID", -1);
        }

        Button button_galeri = findViewById(R.id.button_galeri);
        Button button_gonder = findViewById(R.id.button_gonder);

        button_galeri.setOnClickListener(this);
        button_gonder.setOnClickListener(this);

        imageView = findViewById(R.id.imageView);

        editText_name = findViewById(R.id.editText_name);
        editText_sifre = findViewById(R.id.editText_sifre);
        editText_email = findViewById(R.id.editText_email);
        editText_telefon = findViewById(R.id.editText_telefon);
        editText_pasaport = findViewById(R.id.editText_pasaport);
        editText_ilce = findViewById(R.id.editText_ilce);
        editText_deneyim = findViewById(R.id.editText_deneyim);

        spinner_il = findViewById(R.id.spinner_il);
        spinner_uyruk = findViewById(R.id.spinner_uyruk);
        spinner_dil = findViewById(R.id.spinner_dil);
        spinner_yemek = findViewById(R.id.spinner_yemek);
        spinner_utu = findViewById(R.id.spinner_utu);
        spinner_temizlik = findViewById(R.id.spinner_temizlik);
        spinner_sigara = findViewById(R.id.spinner_sigara);
        spinner_cocuk_bakim = findViewById(R.id.spinner_cocuk_bakim);
        spinner_yasli_bakim = findViewById(R.id.spinner_yasli_bakim);
        spinner_maas = findViewById(R.id.spinner_maas);
        spinner_calisma_sekli = findViewById(R.id.spinner_calisma_sekli);
        spinner_oturma_izni = findViewById(R.id.spinner_oturma_izni);
        spinner_ist_disi = findViewById(R.id.spinner_ist_disi);

        date_field = findViewById(R.id.date_field);
        date_field.setFocusable(false); // disable editing of this field
        date_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                chooseDate();
            }
        });

        String[] spinnerItems = new String[]{"İl seçin", "İstanbul", "Ankara", "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Amasya", "Antalya", "Artvin", "Aydın", "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane", "Hakkâri", "Hatay", "Isparta", "Mersin", "İzmir", "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya", "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak", "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman", "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"};
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_il.setAdapter(spinnerArrayAdapter);
        //spinner_il.setOnItemSelectedListener(this);

        spinnerItems = new String[]{"Uyruk seçin", "Özbekistan", "Türkmenistan", "Kırgızistan", "Gürcistan", "Azerbaycan"};
        spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_uyruk.setAdapter(spinnerArrayAdapter);

        spinnerItems = new String[]{"Dil seçin", "Türkçe", "İngilizce", "Rusça", "Türkçe/Rusça", "Türkçe/İngilizce", "Türkçe/İngilizce/Rusça"};
        spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_dil.setAdapter(spinnerArrayAdapter);

        spinnerItems = new String[]{"Yemek yapabilir mi", "Yapar", "Yapamaz"};
        spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_yemek.setAdapter(spinnerArrayAdapter);

        spinnerItems = new String[]{"Ütü yapabilir mi", "Yapar", "Yapamaz"};
        spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_utu.setAdapter(spinnerArrayAdapter);

        spinnerItems = new String[]{"Temizlik yapabilir mi", "Yapar", "Yapamaz"};
        spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_temizlik.setAdapter(spinnerArrayAdapter);

        spinnerItems = new String[]{"Sigara kullanıyor mu", "Kullanmıyor", "Kullanıyor", "Mesai Saatleri Dışında"};
        spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_sigara.setAdapter(spinnerArrayAdapter);

        spinnerItems = new String[]{"Çocuk bakabilir mi", "Bakamaz", "0-3 Yaş Arası Bakabilir", "3-6 Yaş Arası Bakabilir", "6-12 Yaş Arası Bakabilir",};
        spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cocuk_bakim.setAdapter(spinnerArrayAdapter);

        spinnerItems = new String[]{"Yaşlı bakabilir mi", "Yapar", "Yapamaz"};
        spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_yasli_bakim.setAdapter(spinnerArrayAdapter);

        spinnerItems = new String[]{"Maaş beklentisi", "500$/600$", "600$/700$", "700$/800$", "800$/900$", "900$+"};
        spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_maas.setAdapter(spinnerArrayAdapter);

        spinnerItems = new String[]{"Çalışma şekli", "Gündüzlü", "Yatılı", "Yatılı/Gündüzlü"};
        spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_calisma_sekli.setAdapter(spinnerArrayAdapter);

        spinnerItems = new String[]{"Oturma izni?", "Var", "Yok", "Randevuda"};
        spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_oturma_izni.setAdapter(spinnerArrayAdapter);

        spinnerItems = new String[]{"istanbul dışı çalışır mı", "Evet", "Hayır"};
        spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, spinnerItems);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ist_disi.setAdapter(spinnerArrayAdapter);

    }

    private void chooseDate() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker =
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view, final int year, final int month, final int dayOfMonth) {
                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        calendar.set(year, month, dayOfMonth);
                        String dateString = sdf.format(calendar.getTime());
                        date_field.setText(dateString);
                        Log.wtf("BakiciIslemleriAct", "date : " + date_field.getText());
                    }
                }, year, month, day);

        datePicker.show();

        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(final DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }

    private void request() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        String mUrl = "https://www.ceptebakicim.com/json/bakiciKayit";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("BakiciİslemleriAct", "Response : " + response);
                        int aq = Integer.parseInt(response);
                        if (aq == 1) {
                            Toast.makeText(BakiciIslemleriActivity.this, "İşleme alındı.", Toast.LENGTH_LONG).show();
                            finish();
                        } else if (aq == 0) {
                            Toast.makeText(BakiciIslemleriActivity.this, "İşlem başarısız. Tekrar deneyin.", Toast.LENGTH_LONG).show();
                        } else if (aq == 2) {
                            Toast.makeText(BakiciIslemleriActivity.this, "Girdiğiniz mail kullanılmaktadır. Başka mail deneyin.", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("BakiciİslemleriAct", "Error : " + error);
                        Toast.makeText(BakiciIslemleriActivity.this, "İşlem başarısız", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> jsonParams = new HashMap<String, String>();
                jsonParams.put("name", editText_name.getText().toString() + "");
                jsonParams.put("email", editText_email.getText().toString() + "");
                jsonParams.put("pass", editText_sifre.getText().toString() + "");
                jsonParams.put("uyruk", spinner_uyruk.getSelectedItem().toString());
                jsonParams.put("yemek", spinner_yemek.getSelectedItem().toString());
                jsonParams.put("utu", spinner_utu.getSelectedItem().toString());
                jsonParams.put("temizlik", spinner_temizlik.getSelectedItem().toString());
                jsonParams.put("cocukBakim", spinner_cocuk_bakim.getSelectedItem().toString());
                jsonParams.put("yasliBakim", spinner_yasli_bakim.getSelectedItem().toString());
                jsonParams.put("dil", spinner_dil.getSelectedItem().toString());
                jsonParams.put("sigara", spinner_sigara.getSelectedItem().toString());
                jsonParams.put("yas", date_field.getText().toString() + ""); // Yyyy-mm-dd
                jsonParams.put("ilAdi", spinner_il.getSelectedItem().toString());
                jsonParams.put("ilceAdi", editText_ilce.getText().toString() + "");
                jsonParams.put("phone", editText_telefon.getText().toString() + "");
                jsonParams.put("maas", spinner_maas.getSelectedItem().toString());
                jsonParams.put("deneyim", editText_deneyim.getText().toString() + "");
                jsonParams.put("pasaport", editText_pasaport.getText().toString() + "");
                jsonParams.put("calismaS", spinner_calisma_sekli.getSelectedItem().toString());
                jsonParams.put("oturum", spinner_oturma_izni.getSelectedItem().toString());
                jsonParams.put("sehirD", spinner_ist_disi.getSelectedItem().toString());
                jsonParams.put("imageNew", imageString + "");
                jsonParams.put("wpID", wpID + "");
                return jsonParams;
            }

        };
        queue.add(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (data != null) {
                if (data.getData() != null) {
                    InputStream stream = getApplicationContext().getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    if (stream != null) {
                        stream.close();
                    }

                    imageView.setImageBitmap(bitmap);

                    if (bitmap != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageBytes = baos.toByteArray();
                        imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                        Log.wtf("BakiciIslemleriAct", "imageString : " + imageString);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_galeri:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                break;
            case R.id.button_gonder:
                if (spinner_il.getSelectedItemPosition() == 0
                        || spinner_uyruk.getSelectedItemPosition() == 0
                        || spinner_dil.getSelectedItemPosition() == 0
                        || spinner_yemek.getSelectedItemPosition() == 0
                        || spinner_utu.getSelectedItemPosition() == 0
                        || spinner_temizlik.getSelectedItemPosition() == 0
                        || spinner_sigara.getSelectedItemPosition() == 0
                        || spinner_cocuk_bakim.getSelectedItemPosition() == 0
                        || spinner_yasli_bakim.getSelectedItemPosition() == 0
                        || spinner_maas.getSelectedItemPosition() == 0
                        || spinner_calisma_sekli.getSelectedItemPosition() == 0
                        || spinner_oturma_izni.getSelectedItemPosition() == 0
                        || spinner_ist_disi.getSelectedItemPosition() == 0)
                    Toast.makeText(this, "Lütfen bütün alanları seçiniz", Toast.LENGTH_LONG).show();
                else {
                    if (wpID == -1) {
                        Toast.makeText(this, "Bir hata oluştu", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        request();
                    }
                }
                break;
        }
    }

}
