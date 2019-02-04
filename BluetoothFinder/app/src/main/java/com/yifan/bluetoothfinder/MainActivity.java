package com.yifan.bluetoothfinder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    TextView statusTextView;
    Button searchButton;

    BluetoothAdapter bluetoothAdapter;

    ArrayList<String> devicesList;
    ArrayList<String> addresses;
    ArrayAdapter<String> arrayAdapter;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("Action", action);

            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) { // search has finished, re-enable the button
                statusTextView.setText("FInished");
                searchButton.setEnabled(true);
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) { // found an actual BT device, get the detailed information
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String name = device.getName();
                String address = device.getAddress();
                String rssi = String.valueOf(intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)); // rssi is the intensity of the signal
                Log.i("Device Found", "Name: " + name + " Address: " + address + " RSSI: " + rssi );
                if (!addresses.contains(address)) {
                    addresses.add(address);
                    String deviceString = "";
                    if (name == null || name.equals("")) {
                        deviceString = address + " - RSSI " + rssi + "dBm";
                    } else {
                        deviceString = name + " - RSSI " + rssi + "dBm";
                    }
                    devicesList.add(deviceString);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    public void searchClicked(View view) {
        statusTextView.setText("Searching...");
        searchButton.setEnabled(false); // disable the button when searching
        devicesList.clear();
        addresses.clear();
        arrayAdapter.notifyDataSetChanged();

        bluetoothAdapter.startDiscovery(); // searching for bluetooth devices
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        statusTextView = findViewById(R.id.statusTextView);
        searchButton = findViewById(R.id.searchButton);

        devicesList = new ArrayList<>();
        addresses = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, devicesList);
        listView.setAdapter(arrayAdapter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // setup adapter

        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "Enable Bluetooth First", Toast.LENGTH_SHORT).show();
        }

        // intent filter helps us manage the information we're looking for
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(broadcastReceiver, intentFilter);
    }
}
