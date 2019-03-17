package com.example.kyles.teleopscreen;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes;
import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;
import com.google.android.gms.nearby.connection.Payload;
import com.google.android.gms.nearby.connection.PayloadCallback;
import com.google.android.gms.nearby.connection.PayloadTransferUpdate;
import com.google.android.gms.nearby.connection.Strategy;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class connectionToMaster extends AppCompatActivity {
    String endpointID = null;
    String endpointName = null;
    boolean connectionSuccess = false;
    boolean isDiscovering = false;
    boolean isConnected = false;
    String startPoint;
    String slaveName = "CREVOSLAVE1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_to_master);

        checkStartPoint();
        checkFiles();

        final TextView connectStatusView = (TextView) findViewById(R.id.connectStatusView);
        ///Button requestConnectButton  = (Button) findViewById(R.id.requestConnectButton);
        Button saveSendButton = (Button) findViewById(R.id.saveSendButton);
        ///Button disconnectButton  = (Button) findViewById(R.id.disconnectButton);
        Button backToMainMenuButton = (Button) findViewById(R.id.backToMainMenuButton);


        Nearby.getConnectionsClient(connectionToMaster.this).stopAllEndpoints();

        /*requestConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected) {
                    endpointID = null;
                    Nearby.getConnectionsClient(connectionToMaster.this).stopAllEndpoints();
                    isDiscovering = false;
                    connectStatusView.setText("Stopping Discovery...");
                    stopDiscovery();
                    connectStatusView.setText("Discovery is OFF!");
                    if (!isDiscovering) {
                        connectStatusView.setText("Starting Discovery...");
                        long startTime = System.currentTimeMillis();
                        long currTime = System.currentTimeMillis();
                        long timeDiff = 0;
                        startDiscovery();
                        Button requestConnectButton  = (Button) findViewById(R.id.requestConnectButton);
                        requestConnectButton.setEnabled(false);
                        timeDiff = currTime - startTime;
                        while ((timeDiff < 5000) && (endpointID == null)) {
                            currTime = System.currentTimeMillis();
                            timeDiff = currTime - startTime;
                        }
                        if (endpointID == null) {
                            connectStatusView.setText("Master Tablet is Unavailable!");
                            requestConnectButton.setEnabled(true);
                        }
                    }
                }
            }
        });*/

        saveSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    endpointID = null;
                    Nearby.getConnectionsClient(connectionToMaster.this).stopAllEndpoints();
                    isDiscovering = false;
                    connectStatusView.setText("Stopping Discovery...");
                    stopDiscovery();
                    connectStatusView.setText("Discovery is OFF!");
                    if (!isDiscovering) {
                        connectStatusView.setText("Starting Discovery...");
                        long startTime = System.currentTimeMillis();
                        long currTime = System.currentTimeMillis();
                        long timeDiff = 0;
                        startDiscovery();
                    }
                }

        });


        backToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(startPoint);
                stopDiscovery();
                if (isConnected) {
                    disconnect();
                }
                exit(startPoint);
            }
        });
    }

    private void checkStartPoint() {
        Intent intent = getIntent();
        startPoint = intent.getStringExtra("START_POINT");
    }

    private void exit(String startpoint) {
            Intent intent = new Intent(this, mainMenu.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
    }



    private void disconnect() {
        TextView connectStatusView = (TextView) findViewById(R.id.connectStatusView);
        Nearby.getConnectionsClient(this).disconnectFromEndpoint(endpointID);
        isConnected = false;
        connectStatusView.setText("Disconnected from " + endpointName + "!");
        Nearby.getConnectionsClient(connectionToMaster.this).stopAllEndpoints();
        endpointID = null;
    }


    private void startDiscovery() {
        TextView connectStatusView = (TextView) findViewById(R.id.connectStatusView);
        isDiscovering = false;
        Nearby.getConnectionsClient(this)
                .startDiscovery("CREVOMASTER", endpointDiscoveryCallback,
                        new DiscoveryOptions.Builder().setStrategy(Strategy.P2P_STAR).build())
                .addOnSuccessListener(
                        (Void unused) -> {
                            connectStatusView.setText("Looking for Master Tablet...");
                            isDiscovering = true;
                        })
                .addOnFailureListener(
                        (Exception e) -> {
                            connectStatusView.setText("Discovery Error!");
                            Nearby.getConnectionsClient(connectionToMaster.this).stopAllEndpoints();
                            isDiscovering = false;
                        });
    }

    private void stopDiscovery() {
        Nearby.getConnectionsClient(this).stopDiscovery();
        isDiscovering = false;
    }

    private final EndpointDiscoveryCallback endpointDiscoveryCallback =
            new EndpointDiscoveryCallback() {
                @Override
                public void onEndpointFound(String endpointId, DiscoveredEndpointInfo info) {
                    // An endpoint was found. We request a connection to it.
                    TextView connectStatusView = (TextView) findViewById(R.id.connectStatusView);
                    endpointName = info.getEndpointName();
                    connectStatusView.setText(endpointName + " found!");
                    endpointID = endpointId;
                    System.out.println("Device Found!");
                    if (!isConnected) {
                        connectStatusView.setText("Requesting Connection to " + endpointName + "...");
                        requestConnection();
                    }
                }

                @Override
                public void onEndpointLost(String endpointId) {
                    // A previously discovered endpoint has gone away.
                    TextView connectStatusView = (TextView) findViewById(R.id.connectStatusView);
                    connectStatusView.setText("Disconnected from " + endpointName + "!");
                    Nearby.getConnectionsClient(connectionToMaster.this).stopAllEndpoints();
                    isConnected = false;
                }
            };

    private final ConnectionLifecycleCallback connectionLifecycleCallback =
            new ConnectionLifecycleCallback() {
                @Override
                public void onConnectionInitiated(String endpointId, ConnectionInfo connectionInfo) {
                    // Automatically accept the connection on both sides.
                    Nearby.getConnectionsClient(connectionToMaster.this).acceptConnection(endpointId, mPayloadCallback);
                }

                @Override
                public void onConnectionResult(String endpointId, ConnectionResolution result) {
                    TextView connectStatusView = (TextView) findViewById(R.id.connectStatusView);
                    switch (result.getStatus().getStatusCode()) {
                        case ConnectionsStatusCodes.STATUS_OK:
                            // We're connected! Can now start sending and receiving data.
                            System.out.println("We are Connected!");
                            stopDiscovery();
                            connectStatusView.setText("Connected to " + endpointName + "!");
                            isConnected = true;
                            connectionSuccess = true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            sendText();
                            checkFiles();
/*                            Toast toast = Toast.makeText(connectionToMaster.this, "Files sent!", Toast.LENGTH_SHORT);
                            toast.show();   */

//                            successBox(endpointId);
                            break;
                        case ConnectionsStatusCodes.STATUS_CONNECTION_REJECTED:
                            // The connection was rejected by one or both sides.
                            connectStatusView.setText("Connection rejected by " + endpointName + "!");
                            Nearby.getConnectionsClient(connectionToMaster.this).stopAllEndpoints();
                            isConnected = false;
                            break;
                        case ConnectionsStatusCodes.STATUS_ERROR:
                            // The connection broke before it was able to be accepted.
                            connectStatusView.setText("Connection Error!");
                            Nearby.getConnectionsClient(connectionToMaster.this).stopAllEndpoints();
                            isConnected = false;
                            break;
                        default:
                            // Unknown status code
                            connectStatusView.setText(result.getStatus().toString());
                            Nearby.getConnectionsClient(connectionToMaster.this).stopAllEndpoints();
                            isConnected = false;
                    }
                }

                @Override
                public void onDisconnected(String endpointId) {
                    TextView connectStatusView = (TextView) findViewById(R.id.connectStatusView);
                    // We've been disconnected from this endpoint. No more data can be
                    // sent or received.
                    System.out.println("Disconnected!");
                    connectionSuccess = false;
                    isConnected = false;
                    connectStatusView.setText("Disconnected from " + endpointName + "!");

                    Nearby.getConnectionsClient(connectionToMaster.this).stopAllEndpoints();
                    endpointID = null;
                }
            };

    private final PayloadCallback mPayloadCallback =
            new PayloadCallback() {
                @Override
                public void onPayloadReceived(String endpointId, Payload payload) {
                    // A new payload is being sent over.
                }

                @Override
                public void onPayloadTransferUpdate(String endpointId, PayloadTransferUpdate update) {
                    TextView connectStatusView = (TextView) findViewById(R.id.connectStatusView);
                    switch (update.getStatus()) {
                        case PayloadTransferUpdate.Status.IN_PROGRESS:
                            long size = update.getTotalBytes();
                            if (size == -1) {
                                // This is a stream payload, so we don't need to update anything at this point.
                                return;
                            }
                            int percentTransferred =
                                    (int) (100.0 * (update.getBytesTransferred() / (double) update.getTotalBytes()));
                            //connectStatusView.setText(String.valueOf(percentTransferred) + "% transferred ...");
                            break;
                        case PayloadTransferUpdate.Status.SUCCESS:
                            // SUCCESS always means that we transferred 100%.
                            connectStatusView.setText("File Transfer Complete!");
                            break;
                        case PayloadTransferUpdate.Status.FAILURE:
                        case PayloadTransferUpdate.Status.CANCELED:
                            // SUCCESS always means that we transferred 100%.
                            break;
                        default:
                            // Unknown status.
                    }
                }
            };

    public void sendText() {
        TextView connectStatusView = (TextView) findViewById(R.id.connectStatusView);

        File processedFolder = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS) + "/Processed Files");

        boolean successProcessedCreate = true;

        if (!processedFolder.exists()) {
            successProcessedCreate = processedFolder.mkdir();
        }

        String unprocessedPath = Environment.getExternalStorageDirectory().toString() + "/Documents/Unprocessed Files";
        Log.d("Files", "Path: " + unprocessedPath);
        File directory = new File(unprocessedPath);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            Log.d("Files", "File name: " + files[i].getName());
            Payload filePayload;
            File currFile = files[i];
            try {
                filePayload = Payload.fromFile(currFile);
            } catch (FileNotFoundException e) {
                Log.e("MyApp", "File not Found", e);
                return;
            }
            connectStatusView.setText("Sending file " + currFile.getName() + "!");

            try {
                String processedPath = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOCUMENTS).toString() + "/Processed Files";
                String fileNameMessage = filePayload.getId() + ":" + currFile.getName();
                System.out.println(fileNameMessage);
                //Payload fileNamePayload = Payload.fromBytes(fileNameMessage.getBytes(StandardCharsets.UTF_8));
                //Nearby.getConnectionsClient(this).sendPayload(endpointID, fileNamePayload);
                Thread.sleep(50);
                Nearby.getConnectionsClient(this).sendPayload(endpointID, filePayload);
                Thread.sleep(50);
                File processedFile = new File(processedPath, currFile.getName());
                FileReader fileReader = new FileReader(currFile.getAbsolutePath());
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                String line = bufferedReader.readLine();

                FileWriter fileWriter2 = new FileWriter(processedFile);
                fileWriter2.append(line);
                fileWriter2.flush();
                fileWriter2.close();

                new File(currFile.getAbsolutePath()).delete();
                connectStatusView.setText("File " + currFile.getName() + " sent!");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void checkFiles() {
        String unprocessedPath = Environment.getExternalStorageDirectory().toString() + "/Documents/Unprocessed Files";
        TextView unprocessedView = (TextView) findViewById(R.id.unprocessedFilesView);
        Log.d("Files", "Path: " + unprocessedPath);
        File directory = new File(unprocessedPath);
        File[] files = directory.listFiles();
        String fileList = "";
        for (int i = 0; i < files.length; i++) {
            Log.d("Files", "File name: " + files[i].getName());
            fileList = fileList + (files[i].getName() + System.lineSeparator());
        }
        unprocessedView.setText(fileList);
    }


    private void moveCopyFile(File inputFile, String fileName) {

        try {
            new File(inputFile.getAbsolutePath()).delete();
        } catch (Exception e) {
            Log.e("Deletion of File", "Error");
        }

        try {
            FileReader fileReader = new FileReader(inputFile);
            File processedFile = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS) + "/Processed Files" + "/" + fileName);
        } catch (Exception e) {
            Log.e("Copying of File", "Error");
        }
    }




    private void requestConnection() {
        System.out.println("End point ID: " + endpointID);
        String deviceName = Settings.Secure.getString(getContentResolver(), "bluetooth_name");

        //do {
        //for (r = 0; r < 5; r++) {
        Nearby.getConnectionsClient(connectionToMaster.this)
                .requestConnection(slaveName, endpointID, connectionLifecycleCallback)
                .addOnSuccessListener(
                        (Void unused) -> {
                            System.out.println("Connection Request Successfully sent...");
                        })
                .addOnFailureListener(
                        (Exception e) -> {
                            String message = e.getMessage();
                            e.printStackTrace();
                            connectionSuccess = false;
                            isConnected = false;
                            TextView connectStatusView = (TextView) findViewById(R.id.connectStatusView);
                            connectStatusView.setText(message);
                            Nearby.getConnectionsClient(connectionToMaster.this).stopAllEndpoints();

//                        failBox();
                        }
                );
    }


    private void successBox(String endpointId) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Connection Alert!");
        builder.setMessage("Successfully connected to " + endpointId);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void failBox() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Connection Alert!");
        builder.setMessage("Failed to connect to" + endpointID + ", do you want to attempt to connect again?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                endpointID = null;
                stopDiscovery();
                Nearby.getConnectionsClient(connectionToMaster.this).stopAllEndpoints();
                startDiscovery();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (endpointID != null) {
                    requestConnection();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //quit to main screen goes here
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
