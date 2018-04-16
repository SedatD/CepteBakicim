package ceptebakicim.com.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ceptebakicim.com.Adapter.ChatRoomAdapter;
import ceptebakicim.com.Pojo.ChatRoomActPojo;
import ceptebakicim.com.Pojo.ChatRoomPojo;
import ceptebakicim.com.R;

public class ChatRoomActivity extends AppCompatActivity {
    private ArrayList<String> roomList = new ArrayList<>();
    private ArrayList<ChatRoomActPojo> dataModels = new ArrayList<>();
    //private ListView listView;
    private int userId, userType;
    //private TextView textView_public, textView_public_aile;
    private ProgressBar progress_chat_room;
    private RecyclerView recyclerView_chat_room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        //textView_public = findViewById(R.id.textView_public);
        //textView_public_aile = findViewById(R.id.textView_public_aile);
        progress_chat_room = findViewById(R.id.progress_chat_room);
        recyclerView_chat_room = findViewById(R.id.recyclerView_chat_room);
        recyclerView_chat_room.setHasFixedSize(true);
        recyclerView_chat_room.setLayoutManager(new LinearLayoutManager(this));
        //listView = findViewById(R.id.listview);
        //listView.setDivider(null);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userId = preferences.getInt("userId", -1);
        userType = preferences.getInt("userType", -1);

        //if (userType != 1) textView_public.setVisibility(View.VISIBLE);
        //if (userType == 1) textView_public_aile.setVisibility(View.VISIBLE);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceChats = firebaseDatabase.getReference("Chats");
        final DatabaseReference databaseReferenceUsers = firebaseDatabase.getReference("Users");

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_list_item, android.R.id.text1, roomList);

        /*//SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:dd");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy (HH:mm:ss)");
        final String zaman = sdf.format(new Date());
        databaseReferenceChats.getRef().child("updateTime").setValue(zaman);
        burayı açarsan childları gezerken bu elemanı atlatmayı unutma*/

        databaseReferenceChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList results = new ArrayList<ChatRoomPojo>();
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
                roomList.clear();
                dataModels.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String[] parts = ds.getKey().split("-");
                    int part1 = Integer.parseInt(parts[0]);
                    int part2 = Integer.parseInt(parts[1]);
                    if (part1 == userId || part2 == userId) {
                        int chatId = part1;
                        if (part1 == userId)
                            chatId = part2;

                        final String[] name = new String[1];
                        DatabaseReference child = databaseReferenceUsers.child(chatId + "").child("name");
                        final int finalChatId = chatId;
                        child.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                name[0] = dataSnapshot.getValue(String.class);
                                obj[0] = new ChatRoomPojo(finalChatId, name[0]);
                                results.add(obj[0]);
                                recyclerView_chat_room.setAdapter(new ChatRoomAdapter(results, getApplicationContext()));

                                /*name[0] = dataSnapshot.getValue(String.class);
                                roomList.add(name[0]);
                                dataModels.add(new ChatRoomActPojo(finalChatId, name[0]));
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();*/
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                ChatRoomActPojo dataModel = dataModels.get(i);
                Intent intent = new Intent(ChatRoomActivity.this, ChatActivity.class);
                intent.putExtra("chatId", dataModel.getId());
                startActivity(intent);
            }
        });

        textView_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatRoomActivity.this, ChatPublicActivity.class));
            }
        });

        textView_public_aile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatRoomActivity.this, ChatPublicAileActivity.class));
            }
        });*/

    }

}
