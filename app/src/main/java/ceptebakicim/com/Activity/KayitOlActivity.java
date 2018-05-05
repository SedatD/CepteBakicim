package ceptebakicim.com.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

import ceptebakicim.com.R;

public class KayitOlActivity extends AppCompatActivity {
    private EditText name, email, tel, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        tel = findViewById(R.id.tel);
        password = findViewById(R.id.password);

        findViewById(R.id.button_kayit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mname, memail, mtel, mpassword;
                mname = name.getText().toString();
                memail = email.getText().toString();
                mtel = tel.getText().toString();
                mpassword = password.getText().toString();
                if (!TextUtils.isEmpty(mname) && !TextUtils.isEmpty(memail) && !TextUtils.isEmpty(mtel) && !TextUtils.isEmpty(mpassword))
                    request(mname, memail, mtel, mpassword);
                else
                    Toast.makeText(KayitOlActivity.this, "Bu alanlar boş bırakılamaz", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void request(final String mname, final String memail, final String mtel, final String mpassword) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        String mUrl = "https://www.ceptebakicim.com/json/signup";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("KayitOlActivity", "Response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")) {
                                Toast.makeText(KayitOlActivity.this, "Kaydınız işleme alındı onay beklemektedir", Toast.LENGTH_LONG).show();
                                finish();
                            } else
                                Toast.makeText(KayitOlActivity.this, "İşlem başarısız", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("KayitOlActivity", "Error : " + error);
                        Toast.makeText(KayitOlActivity.this, "İşlem başarısız", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> jsonParams = new HashMap<String, String>();
                jsonParams.put("name", mname);
                jsonParams.put("email", memail);
                jsonParams.put("pass", mpassword);
                jsonParams.put("phone", mtel);
                jsonParams.put("osi", OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId());
                return jsonParams;
            }

        };
        queue.add(stringRequest);
    }

}
