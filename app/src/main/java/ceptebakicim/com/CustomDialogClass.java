package ceptebakicim.com;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by SD
 * on 1.05.2018.
 */

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {
    private EditText editText_password_eski, editText_password_yeni, editText_password_yeni_tekrar;
    private Activity activity;
    private int userId;

    public CustomDialogClass(Activity activity, int userId) {
        super(activity);
        this.activity = activity;
        this.userId = userId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_profile);

        Button yes = findViewById(R.id.btn_yes);
        Button no = findViewById(R.id.btn_no);
        editText_password_eski = findViewById(R.id.editText_password_eski);
        editText_password_yeni = findViewById(R.id.editText_password_yeni);
        editText_password_yeni_tekrar = findViewById(R.id.editText_password_yeni_tekrar);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                String eski = editText_password_eski.getText().toString();
                String yeni = editText_password_yeni.getText().toString();
                String tekrar = editText_password_yeni_tekrar.getText().toString();
                if (!TextUtils.isEmpty(eski) || !TextUtils.isEmpty(yeni) || !TextUtils.isEmpty(tekrar)) {
                    if (yeni.equals(tekrar))
                        postRequest(eski, yeni);
                    else
                        Toast.makeText(activity, "Şifre tekrarı doğru değil", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(activity, "Bu alanlar boş bırakılamaz", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_no:
                dismiss();
                break;
        }
    }

    private void postRequest(final String eski, final String yeni) {
        RequestQueue queue = Volley.newRequestQueue(activity);
        String mUrl = "https://www.ceptebakicim.com/json/profilGuncelle";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                mUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf("Dialog", "Response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("status")) {
                                Toast.makeText(activity, "Şifreniz güncellenmiştir", Toast.LENGTH_LONG).show();
                                dismiss();
                            } else
                                Toast.makeText(activity, "İşlem başarısız", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("Dialog", "Error : " + error);
                        Toast.makeText(activity, "İşlem başarısız", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> jsonParams = new HashMap<String, String>();
                jsonParams.put("userid", userId + "");
                jsonParams.put("eskiSifre", eski);
                jsonParams.put("yeniSifre", yeni);
                jsonParams.put("gonder", 2 + "");
                return jsonParams;
            }

        };
        queue.add(stringRequest);
    }

}
