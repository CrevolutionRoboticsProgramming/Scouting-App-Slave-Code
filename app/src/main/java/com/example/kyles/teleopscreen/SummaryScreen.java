package com.example.kyles.teleopscreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

public class SummaryScreen extends AppCompatActivity {


    int startLevel;
    boolean crossHAB;
    int endLevel;
    int hatchScore;
    int cargoScore;
    int rocket1LowHatch;
    int rocket1MidHatch;
    int rocket1HighHatch;
    int rocket1LowCargo;
    int rocket1MidCargo;
    int rocket1HighCargo;
    int cargoShipHatch;
    int cargoShipCargo;
    int rocket2LowHatch;
    int rocket2MidHatch;
    int rocket2HighHatch;
    int rocket2LowCargo;
    int rocket2MidCargo;
    int rocket2HighCargo;
    String teamNumber;
    String matchNumber;

    String comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_screen);

        getData();

        Button backButton = (Button) findViewById(R.id.backButton);
        Button toConnectionButton = (Button) findViewById(R.id.toConnectionButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                Bundle data = new Bundle();
                data.putInt("START_LEVEL", startLevel);
                data.putBoolean("CROSS_HAB", crossHAB);
                data.putInt("END_LEVEL", endLevel);
                data.putString("TEAM_NUMBER", teamNumber);
                data.putString("MATCH_NUMBER", matchNumber);
                data.putInt("HATCH_SCORE", hatchScore);
                data.putInt("CARGO_SCORE", cargoScore);

                data.putInt("ROCKET1_LOW_HATCH", rocket1LowHatch);
                data.putInt("ROCKET1_MID_HATCH", rocket1MidHatch);
                data.putInt("ROCKET1_HIGH_HATCH", rocket1HighHatch);
                data.putInt("ROCKET1_LOW_CARGO", rocket1LowCargo);
                data.putInt("ROCKET1_MID_CARGO", rocket1MidCargo);
                data.putInt("ROCKET1_HIGH_CARGO", rocket1HighCargo);

                data.putInt("CARGO_SHIP_HATCH", cargoShipHatch);
                data.putInt("CARGO_SHIP_CARGO", cargoShipCargo);

                data.putInt("ROCKET2_LOW_HATCH", rocket2LowHatch);
                data.putInt("ROCKET2_MID_HATCH", rocket2MidHatch);
                data.putInt("ROCKET2_HIGH_HATCH", rocket2HighHatch);
                data.putInt("ROCKET2_LOW_CARGO", rocket2LowCargo);
                data.putInt("ROCKET2_MID_CARGO", rocket2MidCargo);
                data.putInt("ROCKET2_HIGH_CARGO", rocket2HighCargo);

                data.putString("COMMENTS", comments);
                resultIntent.putExtras(data);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        toConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationBox();
            }
        });
    }

    public void getData() {
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        TextView summaryView = (TextView) findViewById(R.id.summaryView);
        startLevel = data.getInt("START_LEVEL");

        crossHAB = data.getBoolean("CROSS_HAB");

        endLevel = data.getInt("END_LEVEL");

        teamNumber = data.getString("TEAM_NUMBER");

        matchNumber = data.getString("MATCH_NUMBER");

        hatchScore = data.getInt("HATCH_SCORE");

        cargoScore = data.getInt("CARGO_SCORE");

        rocket1LowHatch = data.getInt("ROCKET1_LOW_HATCH");
        rocket1MidHatch = data.getInt("ROCKET1_MID_HATCH");
        rocket1HighHatch = data.getInt("ROCKET1_HIGH_HATCH");
        rocket1LowCargo = data.getInt("ROCKET1_LOW_CARGO");
        rocket1MidCargo = data.getInt("ROCKET1_MID_CARGO");
        rocket1HighCargo = data.getInt("ROCKET1_HIGH_CARGO");

        cargoShipHatch = data.getInt("CARGO_SHIP_HATCH");
        cargoShipCargo = data.getInt("CARGO_SHIP_CARGO");

        rocket2LowHatch = data.getInt("ROCKET2_LOW_HATCH");
        rocket2MidHatch = data.getInt("ROCKET2_MID_HATCH");
        rocket2HighHatch = data.getInt("ROCKET2_HIGH_HATCH");
        rocket2LowCargo = data.getInt("ROCKET2_LOW_CARGO");
        rocket2MidCargo = data.getInt("ROCKET2_MID_CARGO");
        rocket2HighCargo = data.getInt("ROCKET2_HIGH_CARGO");

        comments = data.getString("COMMENTS");

        String summary = "MATCH #: " + matchNumber
                + System.lineSeparator() + "TEAM #: " + teamNumber
                + System.lineSeparator() + "STARTING LEVEL: " + startLevel
                + System.lineSeparator() + "CROSSED HAB?: " + crossHAB
                + System.lineSeparator()
                + System.lineSeparator() + "HATCH SCORE: " + hatchScore
                + System.lineSeparator() + "NEAR ROCKET LOW HATCH: " + rocket1LowHatch
                + System.lineSeparator() + "NEAR ROCKET MID HATCH: " + rocket1MidHatch
                + System.lineSeparator() + "NEAR ROCKET HIGH HATCH: " + rocket1HighHatch
                + System.lineSeparator() + "CARGO SHIP HATCH: " + cargoShipHatch
                + System.lineSeparator() + "FAR ROCKET LOW HATCH: " + rocket2LowHatch
                + System.lineSeparator() + "FAR ROCKET MID HATCH: " + rocket2MidHatch
                + System.lineSeparator() + "FAR ROCKET HIGH HATCH: " + rocket2HighHatch
                + System.lineSeparator()
                + System.lineSeparator() + "CARGO SCORE: " + cargoScore
                + System.lineSeparator() + "NEAR ROCKET LOW CARGO: " + rocket1LowCargo
                + System.lineSeparator() + "NEAR ROCKET MID CARGO: " + rocket1MidCargo
                + System.lineSeparator() + "NEAR ROCKET HIGH CARGO: " + rocket1HighCargo
                + System.lineSeparator() + "CARGO SHIP CARGO: " + cargoShipCargo
                + System.lineSeparator() + "FAR ROCKET LOW CARGO: " + rocket2LowCargo
                + System.lineSeparator() + "FAR ROCKET MID CARGO: " + rocket2MidCargo
                + System.lineSeparator() + "FAR ROCKET HIGH CARGO: " + rocket2HighCargo
                + System.lineSeparator()
                + System.lineSeparator() + "CLIMB LEVEL: " + endLevel;
        summaryView.setText(summary);

    }

    private void confirmationBox() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("You are about to save and connect to the Master. Are you sure the data is correct?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fileName = fileName(teamNumber, matchNumber);
                String inputData = combineData();
                saveDataToFile(inputData, fileName);
                toConnection();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void toConnection() {
        Intent toConnection = new Intent(this, connectionToMaster.class);
        toConnection.putExtra("START_POINT", "Summary Screen");
        startActivity(toConnection);
    }


    private void saveDataToFile(String input, String filename) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS + "/Unprocessed Files"), filename);
        File unprocessedFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS) +  "/Unprocessed Files");
        boolean successUnprocessedCreate = true;

        if (!unprocessedFolder.exists()) {
            successUnprocessedCreate = unprocessedFolder.mkdir();
        }

/*        if (successUnprocessedCreate) {
            Toast.makeText(SummaryScreen.this, "Unprocessed Directory created",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SummaryScreen.this, "Error",
                    Toast.LENGTH_SHORT).show();
        }  */

        try {
            FileWriter fileWriter1 = new FileWriter(file);
            fileWriter1.append(input);
            fileWriter1.flush();
            fileWriter1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String date() {
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH) + 1;
        int year = c.get(Calendar.YEAR);
        String[] fixedDayMonth = fixDate(day, month);
        String formatteddate = fixedDayMonth[0] + "" + fixedDayMonth[1] + "" + year;
        return formatteddate;
    }

    private String[] fixDate (int day, int month) {
        int lengthDay = (int)(Math.log10(day) + 1);
        int lengthMonth = (int)(Math.log10(month) + 1);
        String stringDay = day + "";
        String stringMonth = month + "";
        if (lengthDay == 1) {
            stringDay = "0" + stringDay;
        }
        if (lengthMonth == 1) {
            stringMonth = "0" + stringMonth;
        }

        String[] toReturn = {stringMonth, stringDay};
        return toReturn;
    }


    private String fileName(String teamNum, String matchNum) {
        String currDate = date();
        return currDate + teamNum + matchNum + ".txt";
    }

    private String combineData() {
        String combined = date() + "," + teamNumber + "," + matchNumber
                + "," + startLevel + "," + crossHAB + "," + hatchScore
                + "," + rocket1LowHatch + "," + rocket1MidHatch + "," + rocket1HighHatch
                + "," + cargoShipHatch
                + "," + rocket2LowHatch + "," + rocket2MidHatch + "," + rocket2HighHatch
                + "," + cargoScore
                + "," + rocket1LowCargo + "," + rocket1MidCargo + "," + rocket1HighCargo
                + "," + cargoShipCargo
                + "," + rocket2LowCargo + "," + rocket2MidCargo + "," + rocket2HighCargo
                + "," + endLevel
                + "," + comments;
        return combined;
    }

}
