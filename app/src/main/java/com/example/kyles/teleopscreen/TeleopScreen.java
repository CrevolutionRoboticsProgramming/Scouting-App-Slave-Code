package com.example.kyles.teleopscreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class TeleopScreen extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private static FragmentManager fragmentManager;


    Integer[] rocket1Ids = {R.id.rocket1_hatch1, R.id.rocket1_hatch2, R.id.rocket1_hatch3,
                        R.id.rocket1_hatch4, R.id.rocket1_hatch5, R.id.rocket1_hatch6,

                        R.id.rocket1_cargo1, R.id.rocket1_cargo2, R.id.rocket1_cargo3,
                        R.id.rocket1_cargo4, R.id.rocket1_cargo5, R.id.rocket1_cargo6};

    Integer[] cargoShipIds = {R.id.cargoShip_hatch1, R.id.cargoShip_hatch2, R.id.cargoShip_hatch3,
                        R.id.cargoShip_hatch4, R.id.cargoShip_hatch5, R.id.cargoShip_hatch6,
                        R.id.cargoShip_hatch7, R.id.cargoShip_hatch8,

                        R.id.cargoShip_cargo1, R.id.cargoShip_cargo2, R.id.cargoShip_cargo3,
                        R.id.cargoShip_cargo4, R.id.cargoShip_cargo5, R.id.cargoShip_cargo6,
                        R.id.cargoShip_cargo7, R.id.cargoShip_cargo8};
    Integer[] rocket2Ids = {R.id.rocket2_hatch1, R.id.rocket2_hatch2, R.id.rocket2_hatch3,
                        R.id.rocket2_hatch4, R.id.rocket2_hatch5, R.id.rocket2_hatch6,

                        R.id.rocket2_cargo1, R.id.rocket2_cargo2, R.id.rocket2_cargo3,
                        R.id.rocket2_cargo4, R.id.rocket2_cargo5, R.id.rocket2_cargo6};

    Integer[] hatchIds = {R.id.rocket1_hatch1, R.id.rocket1_hatch2, R.id.rocket1_hatch3,
                      R.id.rocket1_hatch4, R.id.rocket1_hatch5, R.id.rocket1_hatch6,
                    R.id.cargoShip_hatch1, R.id.cargoShip_hatch2, R.id.cargoShip_hatch3,
                    R.id.cargoShip_hatch4, R.id.cargoShip_hatch5, R.id.cargoShip_hatch6,
                    R.id.cargoShip_hatch7, R.id.cargoShip_hatch8,
                    R.id.rocket2_hatch1, R.id.rocket2_hatch2, R.id.rocket2_hatch3,
                    R.id.rocket2_hatch4, R.id.rocket2_hatch5, R.id.rocket2_hatch6};

    Integer[] cargoIds = {R.id.rocket1_cargo1, R.id.rocket1_cargo2, R.id.rocket1_cargo3,
                    R.id.rocket1_cargo4, R.id.rocket1_cargo5, R.id.rocket1_cargo6,
            R.id.cargoShip_cargo1, R.id.cargoShip_cargo2, R.id.cargoShip_cargo3,
            R.id.cargoShip_cargo4, R.id.cargoShip_cargo5, R.id.cargoShip_cargo6,
            R.id.cargoShip_cargo7, R.id.cargoShip_cargo8,
                    R.id.rocket2_cargo1, R.id.rocket2_cargo2, R.id.rocket2_cargo3,
                    R.id.rocket2_cargo4, R.id.rocket2_cargo5, R.id.rocket2_cargo6};

    Integer[] rocket1LowHatchIds = {R.id.rocket1_hatch1, R.id.rocket1_hatch4};
    Integer[] rocket1MidHatchIds = {R.id.rocket1_hatch2, R.id.rocket1_hatch5};
    Integer[] rocket1HighHatchIds = {R.id.rocket1_hatch3, R.id.rocket1_hatch6};
    Integer[] rocket1LowCargoIds = {R.id.rocket1_cargo1, R.id.rocket1_cargo4};
    Integer[] rocket1MidCargoIds = {R.id.rocket1_cargo2, R.id.rocket1_cargo5};
    Integer[] rocket1HighCargoIds = {R.id.rocket1_cargo3, R.id.rocket1_cargo6};

    Integer[] cargoShipHatchIds = {R.id.cargoShip_hatch1, R.id.cargoShip_hatch2, R.id.cargoShip_hatch3,
            R.id.cargoShip_hatch4, R.id.cargoShip_hatch5, R.id.cargoShip_hatch6,
            R.id.cargoShip_hatch7, R.id.cargoShip_hatch8};
    Integer[] cargoShipCargoIds = {R.id.cargoShip_cargo1, R.id.cargoShip_cargo2, R.id.cargoShip_cargo3,
            R.id.cargoShip_cargo4, R.id.cargoShip_cargo5, R.id.cargoShip_cargo6,
            R.id.cargoShip_cargo7, R.id.cargoShip_cargo8};

    Integer[] rocket2LowHatchIds = {R.id.rocket2_hatch1, R.id.rocket2_hatch4};
    Integer[] rocket2MidHatchIds = {R.id.rocket2_hatch2, R.id.rocket2_hatch5};
    Integer[] rocket2HighHatchIds = {R.id.rocket2_hatch3, R.id.rocket2_hatch6};
    Integer[] rocket2LowCargoIds = {R.id.rocket2_cargo1, R.id.rocket2_cargo4};
    Integer[] rocket2MidCargoIds = {R.id.rocket2_cargo2, R.id.rocket2_cargo5};
    Integer[] rocket2HighCargoIds = {R.id.rocket2_cargo3, R.id.rocket2_cargo6};


    RadioGroup endGameRGroup;
    RadioGroup teleopRGroup;
    static int startLevel = 1;
    static boolean crossHAB = false;
    static int hatchScore = 0;
    static int cargoScore = 0;
    static int endLevel = 0;

    static int rocket1LowHatch = 0;
    static int rocket1MidHatch = 0;
    static int rocket1HighHatch = 0;
    static int rocket1LowCargo = 0;
    static int rocket1MidCargo = 0;
    static int rocket1HighCargo = 0;
    static int cargoShipHatch = 0;
    static int cargoShipCargo = 0;
    static int rocket2LowHatch = 0;
    static int rocket2MidHatch = 0;
    static int rocket2HighHatch = 0;
    static int rocket2LowCargo = 0;
    static int rocket2MidCargo = 0;
    static int rocket2HighCargo = 0;

    static String comments = null;

    static ArrayList<Integer> pressedHatchIds = new ArrayList<>();
    static ArrayList<Integer> pressedCargoIds = new ArrayList<>();

    String matchNumber;
    String teamNumber;
    ImageView imageView;
    ArrayList<String> compInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teleop_screen);


        getData();

        setTitles(teamNumber, matchNumber);

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



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        endGameRGroup = findViewById(R.id.sandstormRadioGroup);
        teleopRGroup = findViewById(R.id.teleopRadioGroup);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (2) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle moreData = data.getExtras();
                    startLevel = moreData.getInt("START_LEVEL");

                    crossHAB = moreData.getBoolean("CROSS_HAB");

                    endLevel = moreData.getInt("END_LEVEL");

                    teamNumber = moreData.getString("TEAM_NUMBER");

                    matchNumber = moreData.getString("MATCH_NUMBER");

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

                    comments = moreData.getString("COMMENTS");

                }
                break;
            }

        }
    }

    public void getData() {
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
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

        pressedHatchIds = data.getIntegerArrayList("PRESSED_HATCHES");
        pressedCargoIds = data.getIntegerArrayList("PRESSED_CARGO");

        comments = data.getString("COMMENTS");

        System.out.println(startLevel + "" + crossHAB + "" + endLevel);
    }

    public void check(View view) {
        if (!(teamNumber == null)) {
            sendData();
        } else {
            nullTeamOrMatch();
        }
    }
    public void sendData() {
            Intent toSummary = new Intent(this, SummaryScreen.class);
            EditText editText = findViewById(R.id.CommentBox);
            comments = editText.getText().toString();
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

            toSummary.putExtras(data);
            startActivityForResult(toSummary, 2);
        }


    private void nullTeamOrMatch() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert!");
        builder.setMessage("The Team Number input field isn't filled in yet. Please do so before proceeding");

        final AutoCompleteTextView input = new AutoCompleteTextView(TeleopScreen.this);
        input.setThreshold(1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                compInfo);
        input.setAdapter(arrayAdapter);
        input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
                String parts[] = text.split(":");
                teamNumber = parts[0];
                setTitles(teamNumber, matchNumber);
            }
        });
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        builder.setView(input);
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!(teamNumber == null)) {
                    sendData();

                } else {
                    nullTeamOrMatch();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                teamNumber = null;
                setTitles(teamNumber, matchNumber);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void backToTeamMatch(View v) {
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

        data.putIntegerArrayList("PRESSED_HATCHES", pressedHatchIds);
        data.putIntegerArrayList("PRESSED_CARGO", pressedCargoIds);

        data.putString("COMMENTS", comments);
        resultIntent.putExtras(data);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void checkButton(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        TextView sandstormDataView = findViewById(R.id.sandstormDataView);
        switch(v.getId()) {
            case R.id.LevOneNoCross:
                if (checked) {
                    sandstormDataView.setText("LEVEL ONE, DIDN'T CROSS");
                    startLevel = 1;
                    crossHAB = false;
                }
                break;
            case R.id.LevOneCross:
                if (checked) {
                    sandstormDataView.setText("LEVEL ONE, CROSSED HAB LINE");
                    startLevel = 1;
                    crossHAB = true;
                }
                break;
            case R.id.LevTwoNoCross:
                if (checked) {
                    sandstormDataView.setText("LEVEL TWO, DIDN'T CROSS");
                    startLevel = 2;
                    crossHAB = false;
                }
                break;
            case R.id.LevTwoCross:
                if (checked) {
                    sandstormDataView.setText("LEVEL TWO, CROSSED HAB LINE");
                    startLevel = 2;
                    crossHAB = true;
                }
                break;
        }
    }

    public void checkImageButton(View v) {
        imageView = findViewById(R.id.rocketCargoView);
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.rocket1Button:
                if (checked) {
                    imageView.setImageResource(R.drawable.rocket);
                    rocket1Visibility(true);
                    cargoShipVisibility(false);
                    rocket2Visibility(false);
                }
                break;
            case R.id.cargoShipButton:
                if (checked) {
                    imageView.setImageResource(R.drawable.cargoship);
                    rocket1Visibility(false);
                    cargoShipVisibility(true);
                    rocket2Visibility(false);
                }
                break;
            case R.id.rocket2Button:
                if (checked) {
                    imageView.setImageResource(R.drawable.rocket);
                    rocket1Visibility(false);
                    cargoShipVisibility(false);
                    rocket2Visibility(true);
                }
                break;

        }
    }

    private void rocket1Visibility(boolean flag) {
        for (int i = 0; i < rocket1Ids.length; i++) {
            int currentId = rocket1Ids[i];
            CheckBox checkBox = findViewById(currentId);
            if (flag) {
                checkBox.setVisibility(View.VISIBLE);
            } else {
                checkBox.setVisibility(View.GONE);
            }

        }
    }

    private void cargoShipVisibility(boolean flag) {
        for (int i = 0; i < cargoShipIds.length; i++) {
            int currentId = cargoShipIds[i];


            CheckBox checkBox = findViewById(currentId);
            if (flag) {
                checkBox.setVisibility(View.VISIBLE);
            } else {
                checkBox.setVisibility(View.GONE);
            }
        }
    }

    private void rocket2Visibility(boolean flag) {
        for (int i = 0; i < rocket2Ids.length; i++) {
            int currentId = rocket2Ids[i];
            CheckBox checkBox = findViewById(currentId);
            if (flag) {
                checkBox.setVisibility(View.VISIBLE);
            } else {
                checkBox.setVisibility(View.GONE);
            }
        }
    }

    public void hatchUpdate(View v) {
        hatchScore = 0;
        pressedHatchIds.clear();
        ArrayList<Integer> currentRocket1Low = new ArrayList();
        ArrayList<Integer> currentRocket1Mid = new ArrayList();
        ArrayList<Integer> currentRocket1High = new ArrayList();
        ArrayList<Integer> currentCargoShipHatch = new ArrayList();
        ArrayList<Integer> currentRocket2Low = new ArrayList();
        ArrayList<Integer> currentRocket2Mid = new ArrayList();
        ArrayList<Integer> currentRocket2High = new ArrayList();
        for (int i = 0; i < hatchIds.length; i++) {
            int currentId = hatchIds[i];


            CheckBox checkBox = findViewById(currentId);
            if (checkBox.isChecked()) {
                hatchScore += 1;
                pressedHatchIds.add(currentId);
                if (Arrays.asList(rocket1LowHatchIds).contains(currentId)) {
                    currentRocket1Low.add(currentId);
                } else if (Arrays.asList(rocket1MidHatchIds).contains(currentId)) {
                    currentRocket1Mid.add(currentId);
                } else if (Arrays.asList(rocket1HighHatchIds).contains(currentId)) {
                    currentRocket1High.add(currentId);
                } else if (Arrays.asList(cargoShipHatchIds).contains(currentId)) {
                    currentCargoShipHatch.add(currentId);
                } else if (Arrays.asList(rocket2LowHatchIds).contains(currentId)) {
                    currentRocket2Low.add(currentId);
                } else if (Arrays.asList(rocket2MidHatchIds).contains(currentId)) {
                    currentRocket2Mid.add(currentId);
                } else if (Arrays.asList(rocket2HighHatchIds).contains(currentId)) {
                    currentRocket2High.add(currentId);
                }
            }
        }
        if (currentRocket1Low.isEmpty()) {
            rocket1LowHatch = 0;
        } else {
            rocket1LowHatch = currentRocket1Low.size();
        }

        if (currentRocket1Mid.isEmpty()) {
            rocket1MidHatch = 0;
        } else {
            rocket1MidHatch = currentRocket1Mid.size();
        }

        if (currentRocket1High.isEmpty()) {
            rocket1HighHatch = 0;
        } else {
            rocket1HighHatch = currentRocket1High.size();
        }

        if (currentCargoShipHatch.isEmpty()) {
            cargoShipHatch = 0;
        } else {
            cargoShipHatch = currentCargoShipHatch.size();
        }

        if (currentRocket2Low.isEmpty()) {
            rocket2LowHatch = 0;
        } else {
            rocket2LowHatch = currentRocket2Low.size();
        }

        if (currentRocket2Mid.isEmpty()) {
            rocket2MidHatch = 0;
        } else {
            rocket2MidHatch = currentRocket2Mid.size();
        }

        if (currentRocket2High.isEmpty()) {
            rocket2HighHatch = 0;
        } else {
            rocket2HighHatch = currentRocket2High.size();
        }
        System.out.print(pressedHatchIds);
        rocket1Info(findViewById(R.id.rocketOneInfo));
        cargoShipInfo(findViewById(R.id.cargoShipInfo));
        rocket2Info(findViewById(R.id.rocket2Info));
    }
    private void recheckTeleopInfo(View view) {

    }

    public void cargoUpdate(View v) {
        ArrayList<Integer> currentRocket1Low = new ArrayList();
        ArrayList<Integer> currentRocket1Mid = new ArrayList();
        ArrayList<Integer> currentRocket1High = new ArrayList();
        ArrayList<Integer> currentCargoShipCargo = new ArrayList();
        ArrayList<Integer> currentRocket2Low = new ArrayList();
        ArrayList<Integer> currentRocket2Mid = new ArrayList();
        ArrayList<Integer> currentRocket2High = new ArrayList();

        pressedCargoIds.clear();
        cargoScore = 0;
        for (int i = 0; i < cargoIds.length; i++) {
            int currentId = cargoIds[i];
            CheckBox checkBox = findViewById(currentId);
            if (checkBox.isChecked()) {
                cargoScore += 1;
                pressedCargoIds.add(currentId);
                if (Arrays.asList(rocket1LowCargoIds).contains(currentId)) {
                    currentRocket1Low.add(currentId);
                } else if (Arrays.asList(rocket1MidCargoIds).contains(currentId)) {
                    currentRocket1Mid.add(currentId);
                } else if (Arrays.asList(rocket1HighCargoIds).contains(currentId)) {
                    currentRocket1High.add(currentId);
                } else if (Arrays.asList(cargoShipCargoIds).contains(currentId)) {
                    currentCargoShipCargo.add(currentId);
                } else if (Arrays.asList(rocket2LowCargoIds).contains(currentId)) {
                    currentRocket2Low.add(currentId);
                } else if (Arrays.asList(rocket2MidCargoIds).contains(currentId)) {
                    currentRocket2Mid.add(currentId);
                } else if (Arrays.asList(rocket2HighCargoIds).contains(currentId)) {
                    currentRocket2High.add(currentId);
                }
            }
        }

        if (currentRocket1Low.isEmpty()) {
            rocket1LowCargo = 0;
        } else {
            rocket1LowCargo = currentRocket1Low.size();
        }

        if (currentRocket1Mid.isEmpty()) {
            rocket1MidCargo = 0;
        } else {
            rocket1MidCargo = currentRocket1Mid.size();
        }

        if (currentRocket1High.isEmpty()) {
            rocket1HighCargo = 0;
        } else {
            rocket1HighCargo = currentRocket1High.size();
        }

        if (currentCargoShipCargo.isEmpty()) {
            cargoShipCargo = 0;
        } else {
            cargoShipCargo = currentCargoShipCargo.size();
        }

        if (currentRocket2Low.isEmpty()) {
            rocket2LowCargo = 0;
        } else {
            rocket2LowCargo = currentRocket2Low.size();
        }

        if (currentRocket2Mid.isEmpty()) {
            rocket2MidCargo = 0;
        } else {
            rocket2MidCargo = currentRocket2Mid.size();
        }

        if (currentRocket2High.isEmpty()) {
            rocket2HighCargo = 0;
        } else {
            rocket2HighCargo = currentRocket2High.size();
        }


        rocket1Info(findViewById(R.id.rocketOneInfo));
        cargoShipInfo(findViewById(R.id.cargoShipInfo));
        rocket2Info(findViewById(R.id.rocket2Info));
    }

    public void checkEndButton(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.EndLevOne:
                if (checked) {
                    endLevel = 1;
                }
                break;
            case R.id.EndLevTwo:
                if (checked) {
                    endLevel = 2;
                }
                break;
            case R.id.EndLevThree:
                if (checked) {
                    endLevel = 3;
                }
                break;
            case R.id.EndLevNone:
                if (checked) {
                    endLevel = 0;
                }
                break;
        }
        System.out.println(endLevel);
    }

    public void sendDataToTeleFrag() {
        Bundle bundle = new Bundle();
        bundle.putInt("ROCKET1_LOW_HATCH", rocket1LowHatch);
        bundle.putInt("ROCKET1_MID_HATCH", rocket1MidHatch);
        bundle.putInt("ROCKET1_HIGH_HATCH", rocket1HighHatch);
        bundle.putInt("ROCKET1_LOW_CARGO", rocket1LowCargo);
        bundle.putInt("ROCKET1_MID_CARGO", rocket1MidCargo);
        bundle.putInt("ROCKET1_HIGH_CARGO", rocket1HighCargo);

        bundle.putInt("CARGO_SHIP_HATCH", cargoShipHatch);
        bundle.putInt("CARGO_SHIP_CARGO", cargoShipCargo);

        bundle.putInt("ROCKET2_LOW_HATCH", rocket2LowHatch);
        bundle.putInt("ROCKET2_MID_HATCH", rocket2MidHatch);
        bundle.putInt("ROCKET2_HIGH_HATCH", rocket2HighHatch);
        bundle.putInt("ROCKET2_LOW_CARGO", rocket2LowCargo);
        bundle.putInt("ROCKET2_MID_CARGO", rocket2MidCargo);
        bundle.putInt("ROCKET2_HIGH_CARGO", rocket2HighCargo);
         teleop fragobj = new teleop();
         fragobj.setArguments(bundle);
    }


    public void rocket1Info (TextView textView) {
        TextView rocket1Info = textView;

        String rocket1String = "HIGH HATCH: " + rocket1HighHatch
                + System.lineSeparator() + "MID HATCH: " + rocket1MidHatch
                + System.lineSeparator() + "LOW HATCH: " + rocket1LowHatch
                + System.lineSeparator() + "HIGH CARGO: " + rocket1HighCargo
                + System.lineSeparator() + "MID CARGO: " + rocket1MidCargo
                + System.lineSeparator() + "LOW CARGO: " + rocket1LowCargo;
        if (rocket1Info != null) {
            rocket1Info.setText(rocket1String);
        }
    }

    public void cargoShipInfo(TextView textView) {
        TextView cargoShipInfo = textView;
        String cargoShipString = "HATCH: " + cargoShipHatch
                + System.lineSeparator() + "CARGO: " + cargoShipCargo;
        cargoShipInfo.setText(cargoShipString);
    }

    public void rocket2Info(TextView textView) {
        TextView rocket2Info = textView;
        String rocket2String = "HIGH HATCH: " + rocket2HighHatch
                + System.lineSeparator() + "MID HATCH: " + rocket2MidHatch
                + System.lineSeparator() + "LOW HATCH: " + rocket2LowHatch
                + System.lineSeparator() + "HIGH CARGO: " + rocket2HighCargo
                + System.lineSeparator() + "MID CARGO: " + rocket2MidCargo
                + System.lineSeparator() + "LOW CARGO: " + rocket2LowCargo;
        rocket2Info.setText(rocket2String);
    }

    public static ArrayList<Integer> returnHatchIds() {
        return pressedHatchIds;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_teleop_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = null;
            switch(getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    //do something
                    rootView = inflater.inflate(R.layout.fragment_sandstorm, container, false);
                    RadioGroup radioGroup = rootView.findViewById(R.id.sandstormRadioGroup);
                    TextView sandstormText = rootView.findViewById(R.id.sandstormDataView);

                    RadioButton levOneNoCross = rootView.findViewById(R.id.LevOneNoCross);
                    RadioButton levOneCross = rootView.findViewById(R.id.LevOneCross);
                    RadioButton levTwoNoCross = rootView.findViewById(R.id.LevTwoNoCross);
                    RadioButton levTwoCross = rootView.findViewById(R.id.LevTwoCross);


                    radioGroup.clearCheck();
                    switch (startLevel) {
                        case 1:
                            if (!crossHAB) {
                                levOneNoCross.setChecked(true);
                                sandstormText.setText("LEVEL ONE, DIDN'T CROSS");
                            } else {
                                levOneCross.setChecked(true);
                                sandstormText.setText("LEVEL ONE, CROSSED HAB LINE");
                            }
                            break;
                        case 2:
                            if (!crossHAB) {
                                levTwoNoCross.setChecked(true);
                                sandstormText.setText("LEVEL TWO, DIDN'T CROSS");
                            } else {
                                levTwoCross.setChecked(true);
                                sandstormText.setText("LEVEL TWO, CROSSED HAB LINE");
                            }
                            break;
                    }

                    break;
                case 2:
                    //do something
                    rootView = inflater.inflate(R.layout.fragment_teleop, container, false);
                    TeleopScreen t = new TeleopScreen();
                    TextView p = rootView.findViewById(R.id.rocketOneInfo);
                    TextView q = rootView.findViewById(R.id.cargoShipInfo);
                    TextView r = rootView.findViewById(R.id.rocket2Info);
                    if (!(pressedHatchIds.isEmpty())) {
                        for (int i = 0; i < pressedHatchIds.size(); i++) {
                            int currentid = pressedHatchIds.get(i);
                            CheckBox checkBox = rootView.findViewById(currentid);
                            checkBox.setChecked(true);
                        }
                    }

                    if (!(pressedCargoIds.isEmpty())) {
                        for (int i = 0; i < pressedCargoIds.size(); i++) {
                            int currentId = pressedCargoIds.get(i);
                            CheckBox checkBox = rootView.findViewById(currentId);
                            checkBox.setChecked(true);
                        }
                    }
                    //t.hatchUpdate(rootView);
                    t.rocket1Info(p);
                    t.cargoShipInfo(q);
                    t.rocket2Info(r);
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_endgame, container, false);
                    RadioGroup endgameGroup = rootView.findViewById(R.id.teleopRadioGroup);

                    EditText commentbox = rootView.findViewById(R.id.CommentBox);
                    commentbox.setText(comments);

                    /*commentbox.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
                                EditText commentBox = (EditText) v;
                                comments = commentBox.getText().toString();
                            }
                        }
                    });*/

                    RadioButton level1 = rootView.findViewById(R.id.EndLevOne);
                    RadioButton level2 = rootView.findViewById(R.id.EndLevTwo);
                    RadioButton level3 = rootView.findViewById(R.id.EndLevThree);
                    RadioButton level0 = rootView.findViewById(R.id.EndLevNone);
                    endgameGroup.clearCheck();
                    switch (endLevel) {
                        case 1:
                            level1.setChecked(true);
                            break;
                        case 2:
                            level2.setChecked(true);
                            break;
                        case 3:
                            level3.setChecked(true);
                            break;
                        case 0:
                            level0.setChecked(true);
                            break;
                    }


                    break;
            }

            return rootView;
        }
    }


    public void setTitles(String teamNumber, String matchNumber) {
        TextView teamMatchView = (TextView) findViewById(R.id.teamMatchView);
        String combined = "    Match " + matchNumber + ", Team " + teamNumber;
        teamMatchView.setText(combined);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position) {
                case 1:
                    break;
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }

}
