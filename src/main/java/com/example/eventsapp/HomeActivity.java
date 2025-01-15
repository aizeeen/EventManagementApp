package com.example.eventsapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import android.content.Intent;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewEvents;
    private EventAdapter eventAdapter;
    private List<Event> eventList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        recyclerViewEvents = findViewById(R.id.recyclerViewEvents);


        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this));


        recyclerViewEvents.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, 
                                     @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.bottom = 16;
            }
        });

        databaseHelper = new DatabaseHelper(this);


        eventList = getEvents();
        eventAdapter = new EventAdapter(this, eventList, new EventAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(Event event) {

                Intent intent = new Intent(HomeActivity.this, EventActivity.class);
                intent.putExtra("eventId", event.getId());
                intent.putExtra("eventName", event.getName());
                startActivity(intent);
            }
        });
        recyclerViewEvents.setAdapter(eventAdapter);
    }

    private List<Event> getEvents() {

        List<Event> events = new ArrayList<>();
        events.add(new Event(1, "Enactus Esen", "enactus_icon"));
        events.add(new Event(2, "Esen Android Club", "android_club_icon"));
        events.add(new Event(3, "Esen Hive Club", "hive_club_icon"));
        events.add(new Event(4, "Joker Esen", "joker_icon"));
        return events;
    }
}
