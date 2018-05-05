package ceptebakicim.com;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ceptebakicim.com.Activity.BakiciBanaOzelActivity;
import ceptebakicim.com.Activity.ChatActivity;
import ceptebakicim.com.Activity.ChatRoomActivity;
import ceptebakicim.com.Activity.CvActivity;
import ceptebakicim.com.Activity.LoginActivity;
import ceptebakicim.com.Activity.ProfileActivity;
import ceptebakicim.com.Activity.TeklifIslemleriActivity;
import ceptebakicim.com.Activity.TumBakicilarActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RequestQueue queue;
    private int userType = -1, userId = -1, caryId = -1, chatId = -99;

    /*@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    private static Bitmap getBitmap(VectorDrawableCompat vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmap(Context context, @DrawableRes int drawableResId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableResId);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawableCompat) {
            return getBitmap((VectorDrawableCompat) drawable);
        } else if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable);
        } else {
            throw new IllegalArgumentException("Unsupported drawable type");
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getInt("userId", -1);
        userType = preferences.getInt("userType", -1);
        String userName = preferences.getString("userName", "Error");
        String userEmail = preferences.getString("userEmail", "Error");
        String userPhoto = preferences.getString("userPhoto", "Error");

        TextView textMerhaba = findViewById(R.id.textMerhaba);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        ImageButton btnLogout = findViewById(R.id.btnLogout);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        queue = Volley.newRequestQueue(this);
        if (userId != -1 || userType != -1)
            mainRequest(userType, userId);
        else
            btnLogout.performClick();

        textMerhaba.setText("Merhaba " + userName);

        if (userType == 2 || userType == 3) {
            btn1.setText("gelen teklİfler");
            btn2.setText("cv gÖrÜntÜle");
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            caryId = bundle.getInt("caryId", -1);
            chatId = bundle.getInt("chatId", -99);
        }

    }

    private void mainRequest(final int mUserType, int mUserId) {
        final String url = "https://www.ceptebakicim.com/json/home?userType=" + mUserType + "&userid=" + mUserId;
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.wtf("Response MainAct", response.toString());
                        try {
                            if (mUserType == 1) {
                                //aile

                                //kabul bekleyen - 1
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("kabulBekleyen", response.getJSONArray("kabulBekleyen") + "");
                                JSONArray jsonArray_kabulBekleyen = response.getJSONArray("kabulBekleyen");

                                //kabul edilen - 1
                                editor.putString("kabulEdilen", response.getJSONArray("kabulEdilen") + "");
                                JSONArray jsonArray_kabulEdilen = response.getJSONArray("kabulEdilen");

                                editor.apply();

                                //gorusmede oldugum - 2
                                JSONArray jsonArray_gorusmedeOldugum = response.getJSONArray("gorusmedeOldugum");

                                //daha once gorustugum - 2
                                JSONArray jsonArray_dahaOnceGorustugum = response.getJSONArray("dahaOnceGorustugum");

                            } else if (mUserType == 2) {
                                //bakici

                                //tum teklifler - 3
                                JSONArray jsonArray_tumTeklifler = response.getJSONArray("tumTeklifler");

                                //gorusmede oldugum bakici - 4
                                JSONArray jsonArray_gorusmedeOldugum = response.getJSONArray("gorusmedeOldugum");

                            } else if (mUserType == 3) {
                                //erkek isci

                            } else if (mUserType == 4) {
                                //firma

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (caryId != -1) {
                            Intent intent = new Intent(MainActivity.this, CvActivity.class);
                            intent.putExtra("id", caryId);
                            startActivity(intent);
                        }

                        if (chatId != -99) {
                            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                            intent.putExtra("chatId", chatId);
                            startActivity(intent);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("Error.Response MainAct", error + "");
                        Toast.makeText(MainActivity.this, "Bir hata oluştu.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(getRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                if (userType == 2 || userType == 3) {
                    startActivity(new Intent(MainActivity.this, BakiciBanaOzelActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, TeklifIslemleriActivity.class));
                }
                break;
            case R.id.btn2:
                if (userType == 2 || userType == 3) {
                    Intent intent = new Intent(MainActivity.this, CvActivity.class);
                    intent.putExtra("id", userId);
                    startActivity(intent);
                } else
                    startActivity(new Intent(MainActivity.this, TumBakicilarActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(MainActivity.this, ChatRoomActivity.class));
                break;
            case R.id.btn4:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
            case R.id.btnLogout:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Uyarı");
                builder.setMessage("Çıkış yapmak istediğinize emin misiniz?");
                builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

                builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("userId");
                        editor.remove("userType");
                        editor.remove("userName");
                        editor.remove("userEmail");
                        editor.remove("userPhoto");
                        editor.remove("mEmail");
                        editor.remove("mPassword");
                        editor.apply();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();

                        AuthUI.getInstance().signOut(MainActivity.this).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(MainActivity.this, "Çıkış yaptınız", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                builder.show();
                break;
        }
    }

}
