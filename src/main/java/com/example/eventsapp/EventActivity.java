package com.example.eventsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    private ImageView imageViewEvent;
    private TextView textViewEventTitle;
    private TextView textViewEventDescription;
    private EditText editTextParticipantName;
    private Button buttonSubmitRegistration;
    private ListView listViewParticipants;
    private DatabaseHelper databaseHelper;
    private ArrayAdapter<String> participantsAdapter;
    private ArrayList<String> participantsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        imageViewEvent = findViewById(R.id.imageViewEvent);
        textViewEventTitle = findViewById(R.id.textViewEventTitle);
        textViewEventDescription = findViewById(R.id.textViewEventDescription);
        editTextParticipantName = findViewById(R.id.editTextParticipantName);
        buttonSubmitRegistration = findViewById(R.id.buttonSubmitRegistration);
        listViewParticipants = findViewById(R.id.listViewParticipants);


        databaseHelper = new DatabaseHelper(this);


        String eventName = getIntent().getStringExtra("eventName");
        int eventId = getIntent().getIntExtra("eventId", -1);


        setEventDetails(eventId, eventName);


        participantsList = databaseHelper.getEventParticipants(eventId);
        participantsAdapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_list_item_1, 
            participantsList);
        listViewParticipants.setAdapter(participantsAdapter);


        buttonSubmitRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String participantName = editTextParticipantName.getText().toString().trim();

                if (participantName.isEmpty()) {
                    Toast.makeText(EventActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isRegistered = databaseHelper.registerForEvent(eventId, participantName);
                    if (isRegistered) {

                        participantsList.add(participantName);
                        participantsAdapter.notifyDataSetChanged();
                        

                        editTextParticipantName.setText("");
                        
                        Toast.makeText(EventActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EventActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void setEventDetails(int eventId, String eventName) {
        textViewEventTitle.setText(eventName);
        

        switch (eventId) {
            case 1:
                imageViewEvent.setImageResource(R.drawable.enactus_event);
                textViewEventDescription.setText("SIMU Enactus Event: " +
                        "Hey there, Trailblazers!\n" +
                        "\n" +
                        "Get ready to dive headfirst into the thrilling world of SimuEnactus.\n" +
                        "At our upcoming event, we're not just talking about impact – we're making it happen\n" +
                        "Join us to unleash your boundless creativity by filling the  form and becoming a part of a high-energy movement that's all about exploration, innovation, and leadership. The adventure kicks off now – are you in for the ride of a lifetime?");
                break;
                
            case 2:
                imageViewEvent.setImageResource(R.drawable.android_event);
                textViewEventDescription.setText("HACK4DATA Event" +
                        "And as usual the Esen Android Club comes back with a bigger , larger event that will change your life.\n" +
                        "\n" +
                        "HACK4DATA is a 48 hours competition which combines two large fields innovation and Data science .\n" +
                        "You will live an amazing experience thanks to our international experts and speakers who will share with you the latest technologies and we can't forget about the big prizes given by our beloved sponsors\n" +
                        "The fee is only 10TND .");
                break;
                
            case 3:
                imageViewEvent.setImageResource(R.drawable.hive_event);
                textViewEventDescription.setText("BEE BATTLE CONTEST 2.0" +
                        "ESEN Hive Club Presents The Second Edition of the BEE BATTLE Contest!\n" +
                        "Get ready for the Battle once again! We’re back with the second edition of its local competitive programming contest this year! \n" +
                        "You will face a new set of challenging problems to solve. If you’re passionate about competitive programming and eager to test your skills, now’s the time to prove yourself!  \n" +
                        "Bring your laptop and gear up for 4-5 hours of intense problem-solving! Rewards Await: Top competitors will earn cash prizes.");
                break;
                
            case 4:
                imageViewEvent.setImageResource(R.drawable.joker_event);
                textViewEventDescription.setText("YALLA GHANI Event" +
                        "Joker Esen organise un événement de karaoké exceptionnel YALLA GHANI où les étudiants pourront montrer leurs talents musicaux dans une ambiance conviviale et festive. Cet événement est une excellente occasion de se détendre, de chanter ses chansons préférées. La participation est ouverte à tous les étudiants, avec un droit d’entrée fixé à 3.500 DT. Ne manquez pas cette chance de briller sur scène et de partager des moments inoubliables.");
                break;
        }
    }
}
