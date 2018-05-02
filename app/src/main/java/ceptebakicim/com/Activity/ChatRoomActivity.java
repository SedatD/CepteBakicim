package ceptebakicim.com.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ceptebakicim.com.Adapter.ChatRoomAdapter;
import ceptebakicim.com.Pojo.ChatRoomPojo;
import ceptebakicim.com.R;

public class ChatRoomActivity extends AppCompatActivity {
    private int userId, userType;
    private ProgressBar progress_chat_room;
    private RecyclerView recyclerView_chat_room;
    private ArrayList results = new ArrayList<ChatRoomPojo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        progress_chat_room = findViewById(R.id.progress_chat_room);
        recyclerView_chat_room = findViewById(R.id.recyclerView_chat_room);
        recyclerView_chat_room.setHasFixedSize(true);
        recyclerView_chat_room.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getInt("userId", -1);
        userType = preferences.getInt("userType", -1);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceChats = firebaseDatabase.getReference("Chats");
        final DatabaseReference databaseReferenceUsers = firebaseDatabase.getReference("Users");

        databaseReferenceChats.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ChatRoomPojo[] obj = new ChatRoomPojo[1];

                if (userType != 1) {
                    obj[0] = new ChatRoomPojo(-1, "Genel Mesajlaşma");
                }
                if (userType == 1) {
                    obj[0] = new ChatRoomPojo(-2, "Aileler Arası Mesajlaşma");
                }
                results.add(obj[0]);

                recyclerView_chat_room.setAdapter(new ChatRoomAdapter(results, getApplicationContext()));

                progress_chat_room.setVisibility(View.GONE);

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String[] parts = ds.getKey().split("-");
                    int part1 = Integer.parseInt(parts[0]);
                    int part2 = Integer.parseInt(parts[1]);
                    if (part1 == userId || part2 == userId) {
                        int chatId = part1;
                        if (part1 == userId)
                            chatId = part2;

                        int reqUserType = 1;
                        if (userType == 1)
                            reqUserType = 2;

                        request(chatId, reqUserType);

                        /*final String[] name = new String[1];
                        DatabaseReference child = databaseReferenceUsers.child(chatId + "").child("name");
                        final int finalChatId = chatId;
                        child.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                name[0] = dataSnapshot.getValue(String.class);
                                obj[0] = new ChatRoomPojo(finalChatId, name[0]);
                                results.add(obj[0]);
                                recyclerView_chat_room.setAdapter(new ChatRoomAdapter(results, getApplicationContext()));
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });*/
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    private void request(final int userId, int userType) {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext().getApplicationContext());
        final String url = "https://www.ceptebakicim.com/json/userList?userType=" + userType + "&userId=" + userId;
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.wtf("ChatRoomAct request", "response : " + response.toString());
                        try {
                            JSONObject jsonObject = (JSONObject) response.get(0);

                            /*Glide.with(getApplicationContext()).load(jsonObject.getString("imageYolu")).into(imageView);
                            textView_name.setText(jsonObject.getString("name"));
                            editText_phone.setText(jsonObject.getString("phone"));
                            editText_address.setText(jsonObject.getString("adres"));*/

                            results.add(new ChatRoomPojo(userId, jsonObject.getString("name")));
                            recyclerView_chat_room.setAdapter(new ChatRoomAdapter(results, getApplicationContext()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("ChatRoomAct request", "error : " + error);
                    }
                }
        );
        queue.add(getRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
