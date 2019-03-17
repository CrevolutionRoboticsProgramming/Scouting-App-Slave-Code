package com.example.kyles.teleopscreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class teamMatchSelect extends AppCompatActivity {
    int startLevel = 1;
    boolean crossHAB = false;
    int endLevel = 0;
    int hatchScore = 0;
    int cargoScore = 0;
    int rocket1LowHatch = 0;
    int rocket1MidHatch = 0;
    int rocket1HighHatch = 0;
    int rocket1LowCargo = 0;
    int rocket1MidCargo = 0;
    int rocket1HighCargo = 0;
    int cargoShipHatch = 0;
    int cargoShipCargo = 0;
    int rocket2LowHatch = 0;
    int rocket2MidHatch = 0;
    int rocket2HighHatch = 0;
    int rocket2LowCargo = 0;
    int rocket2MidCargo = 0;
    int rocket2HighCargo = 0;

    String comments = null;

    ArrayList<Integer> pressedHatchIds = new ArrayList<>();
    ArrayList<Integer> pressedCargoIds = new ArrayList<>();
    AutoCompleteTextView autoCompleteTextView;
    ArrayList<String> compInfo = new ArrayList<>();
    String sampleTeams1[] = {"Select a Team:", "2851", "1", "4853", "982", "9273", "435"};
    String sampleTeams2[] = {"Select a Team:", "8172", "837", "82", "7263", "2341", "9182"};
    String currentTeams[];


    int originalTeamPos;

    String selectedMatch = "1";
    String selectedTeam;

    String currentTeamNum = null;
    String currentMatchNum;

    boolean backTrack = false;

    private final SimpleArrayMap<String, String[]> matchInfo = new SimpleArrayMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_match_select);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) +
        "/Events.txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                compInfo.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        matchInfo.put("01", sampleTeams1);
        matchInfo.put("02", sampleTeams2);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                compInfo);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView teamNumView = findViewById(R.id.teamNumView);
                String text = parent.getItemAtPosition(position).toString();
                String parts[] = text.split(":");
                selectedTeam = parts[0];
                if (!(currentTeamNum == null)) {
                    if (backTrack) {
                        if (!(selectedTeam.equals(currentTeamNum))) {
                        diffTeam();
                    }
                }

                } else {
                    currentTeamNum = selectedTeam;
                    teamNumView.setText(selectedTeam);
                    originalTeamPos = position;
                }
            }
        });


        Button backButton = findViewById(R.id.backButton);
        Button nextButton = findViewById(R.id.nextButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmationBox();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toTeleop();

            }
        });
        TextView currMatchView = findViewById(R.id.matchNumView);
        Button addBtn = findViewById(R.id.addBtn);
        Button subBtn = findViewById(R.id.subBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMatch = currMatchView.getText().toString();
                int intCurrMatch = Integer.parseInt(selectedMatch);
                intCurrMatch += 1;
                selectedMatch = Integer.toString(intCurrMatch);
                if (backTrack) {
                    if (!(selectedMatch.equals(currentMatchNum))) {
                        diffMatch();
                    }
                } else {
                    currMatchView.setText(selectedMatch);
                }
            }
        });
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedMatch = currMatchView.getText().toString();
                int intCurrMatch = Integer.parseInt(selectedMatch);
                if (intCurrMatch > 1) {
                    intCurrMatch -= 1;
                    selectedMatch = Integer.toString(intCurrMatch);
                    if (backTrack) {
                        if (!(selectedMatch.equals(currentMatchNum))) {
                            diffMatch();
                        }
                    } else {
                        currMatchView.setText(selectedMatch);
                    }
                }
            }
        });



    }

    private void confirmationBox() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("Backing out to the main menu will result in the current data being recorded" +
                " to be reset. Are you sure you want to go to the main menu?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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
    private void nullTeamOrMatch() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("Either the match or team number fields, or both, aren't filled in. Please" +
                " do so before proceeding.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void toTeleop() {
        backTrack = false;
        System.out.println(startLevel + "" + crossHAB + "" + endLevel);
        Intent toSummary = new Intent(this, TeleopScreen.class);
        Bundle data = new Bundle();
        data.putInt("START_LEVEL", startLevel);
        data.putBoolean("CROSS_HAB", crossHAB);
        data.putInt("END_LEVEL", endLevel);
        data.putString("TEAM_NUMBER", currentTeamNum);
        data.putString("MATCH_NUMBER", selectedMatch);
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
        data.putIntegerArrayList("PRESSED_HATCHES", pressedHatchIds);
        data.putIntegerArrayList("PRESSED_CARGO", pressedCargoIds);

        data.putString("COMMENTS", comments);
        toSummary.putExtras(data);
        startActivityForResult(toSummary, 3);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (3) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle moreData = data.getExtras();
                    startLevel = moreData.getInt("START_LEVEL");

                    crossHAB = moreData.getBoolean("CROSS_HAB");

                    endLevel = moreData.getInt("END_LEVEL");

                    currentTeamNum = moreData.getString("TEAM_NUMBER");

                    currentMatchNum = moreData.getString("MATCH_NUMBER");

                    hatchScore = moreData.getInt("HATCH_SCORE");

                    cargoScore = moreData.getInt("CARGO_SCORE");

                    rocket1LowHatch = moreData.getInt("ROCKET1_LOW_HATCH");
                    rocket1MidHatch = moreData.getInt("ROCKET1_MID_HATCH");
                    rocket1HighHatch = moreData.getInt("ROCKET1_HIGH_HATCH");
                    rocket1LowCargo = moreData.getInt("ROCKET1_LOW_CARGO");
                    rocket1MidCargo = moreData.getInt("ROCKET1_MID_CARGO");
                    rocket1HighCargo = moreData.getInt("ROCKET1_HIGH_CARGO");

                    cargoShipHatch = moreData.getInt("CARGO_SHIP_HATCH");
                    cargoShipCargo = moreData.getInt("CARGO_SHIP_CARGO");

                    rocket2LowHatch = moreData.getInt("ROCKET2_LOW_HATCH");
                    rocket2MidHatch = moreData.getInt("ROCKET2_MID_HATCH");
                    rocket2HighHatch = moreData.getInt("ROCKET2_HIGH_HATCH");
                    rocket2LowCargo = moreData.getInt("ROCKET2_LOW_CARGO");
                    rocket2MidCargo = moreData.getInt("ROCKET2_MID_CARGO");
                    rocket2HighCargo = moreData.getInt("ROCKET2_HIGH_CARGO");

                    pressedHatchIds = moreData.getIntegerArrayList("PRESSED_HATCHES");
                    pressedCargoIds = moreData.getIntegerArrayList("PRESSED_CARGO");

                    comments = moreData.getString("COMMENTS");

                    backTrack = true;
                    TextView teamNumView = findViewById(R.id.teamNumView);
                    teamNumView.setText(currentTeamNum);
                }
                break;
            }

        }
    }






    private void diffMatch() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("Changing the match or team will result in the current data being reset. Do " +
                "you want to proceed?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentTeams = matchInfo.get(selectedMatch);
                startLevel = 1;
                crossHAB = false;
                endLevel = 0;
                hatchScore = 0;
                cargoScore = 0;

                rocket1LowHatch = 0;
                rocket1MidHatch = 0;
                rocket1HighHatch = 0;
                cargoShipHatch = 0;
                rocket2LowHatch = 0;
                rocket2MidHatch = 0;
                rocket2HighHatch = 0;

                rocket1LowCargo = 0;
                rocket1MidCargo = 0;
                rocket1HighCargo = 0;
                cargoShipCargo = 0;
                rocket2LowCargo = 0;
                rocket2MidCargo = 0;
                rocket2HighCargo = 0;

                pressedHatchIds.clear();
                pressedCargoIds.clear();

                comments = null;


                backTrack = false;
                TextView matchView = findViewById(R.id.matchNumView);
                matchView.setText(selectedMatch);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView matchView = findViewById(R.id.matchNumView);
                selectedMatch = currentMatchNum;
                matchView.setText(selectedMatch);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void diffTeam() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("Changing the match or team will result in the current data being reset. Do " +
                "you want to proceed?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startLevel = 1;
                crossHAB = false;
                endLevel = 0;
                hatchScore = 0;
                cargoScore = 0;

                rocket1LowHatch = 0;
                rocket1MidHatch = 0;
                rocket1HighHatch = 0;
                cargoShipHatch = 0;
                rocket2LowHatch = 0;
                rocket2MidHatch = 0;
                rocket2HighHatch = 0;

                rocket1LowCargo = 0;
                rocket1MidCargo = 0;
                rocket1HighCargo = 0;
                cargoShipCargo = 0;
                rocket2LowCargo = 0;
                rocket2MidCargo = 0;
                rocket2HighCargo = 0;

                pressedHatchIds.clear();
                pressedCargoIds.clear();

                backTrack = false;

                currentTeamNum = selectedTeam;
                TextView teamNumView = findViewById(R.id.teamNumView);
                teamNumView.setText(selectedTeam);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView teamNumView = findViewById(R.id.teamNumView);
                selectedTeam = currentTeamNum;
                for (String i : compInfo) {
                    if (i.contains(selectedTeam)) {
                        autoCompleteTextView.setText(i);
                    }
                }
                teamNumView.setText(selectedTeam);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
