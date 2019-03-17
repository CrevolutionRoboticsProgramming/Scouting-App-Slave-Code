package com.example.kyles.teleopscreen;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button sendFilesButton = (Button) findViewById(R.id.sendFilesButton);
        Button scoutButton = (Button) findViewById(R.id.scoutButton);

        sendFilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSendScreen();
            }
        });

        scoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTeamMatchScreen();
            }
        });
    }

    public void toSendScreen() {
        Intent intent = new Intent(this, connectionToMaster.class);
        intent.putExtra("START_POINT", "Main Menu");
        startActivity(intent);
    }

    public void toTeamMatchScreen() {
        Intent intent = new Intent(this, teamMatchSelect.class);
        startActivity(intent);
    }

}
