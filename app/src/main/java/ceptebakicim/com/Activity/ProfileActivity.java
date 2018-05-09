package ceptebakicim.com.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ceptebakicim.com.CustomDialogClass;
import ceptebakicim.com.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private int userId;
    private ImageView imageView;
    private TextView textView_name;
    private EditText editText_phone, editText_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageView = findViewById(R.id.imageView);
        textView_name = findViewById(R.id.textView_name);
        editText_phone = findViewById(R.id.editText_phone);
        editText_address = findViewById(R.id.editText_address);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.textView_sifreDegistir).setOnClickListener(this);
        imageView.setOnClickListener(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getInt("userId", -1);
        int userType = preferences.getInt("userType", -1);

        request(userId, userType);
    }

    private void request(int userId, int userType) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        final String url = "https://www.ceptebakicim.com/json/userList?userType=" + userType + "&userId=" + userId;
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.wtf("ProfileAct request", "response : " + response.toString());
                        try {
                            JSONObject jsonObject = (JSONObject) response.get(0);

                            Glide.with(getApplicationContext()).load(jsonObject.getString("imageYolu")).into(imageView);

                            textView_name.setText(jsonObject.getString("name"));
                            editText_phone.setText(jsonObject.getString("phone"));
                            editText_address.setText(jsonObject.getString("adres"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("ProfileAct request", "error : " + error);
                    }
                }
        );
        queue.add(getRequest);
    }

    private void postRequest(final String tel, final String adres) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        String mUrl = "https://www.ceptebakicim.com/json/profilGuncelle";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("ProfileAct", "Response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")) {
                                Toast.makeText(ProfileActivity.this, "Bilgileriniz güncellenmiştir", Toast.LENGTH_LONG).show();
                                finish();
                            } else
                                Toast.makeText(ProfileActivity.this, "İşlem başarısız", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("ProfileAct", "Error : " + error);
                        Toast.makeText(ProfileActivity.this, "İşlem başarısız", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> jsonParams = new HashMap<String, String>();
                jsonParams.put("userid", userId + "");
                jsonParams.put("phone", tel);
                jsonParams.put("adres", adres);
                jsonParams.put("gonder", 1 + "");
                Log.wtf("ProfileAct", "params : " + jsonParams);
                return jsonParams;
            }

        };
        queue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Uyarı");
                builder.setMessage("Bu işlemi yapmak istediğinize emin misiniz?");
                builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });

                builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        postRequest(editText_phone.getText().toString(), editText_address.getText().toString());
                    }
                });
                builder.show();
                break;
            case R.id.imageView:

                break;
            case R.id.textView_sifreDegistir:
                new CustomDialogClass(this, userId).show();
                break;
        }
    }

}
