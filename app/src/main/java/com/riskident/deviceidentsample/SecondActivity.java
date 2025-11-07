package com.riskident.deviceidentsample;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.riskident.device.ClientSecurityModule;

import java.util.HashMap;
import java.util.UUID;

public class SecondActivity extends Activity implements View.OnClickListener {
    private final static int REQUEST_LOCATION_PERMISSIONS = 1;

    private String TAG = SecondActivity.class.getSimpleName();

    private TextView textViewToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        this.textViewToken = findViewById(R.id.secondactivity_token_value);
        findViewById(R.id.secondactivity_button_send).setOnClickListener(this);
        findViewById(R.id.secondactivity_button_request_permissions).setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_LOCATION_PERMISSIONS) {
            String grantedLocation = grantResults[0] == PackageManager.PERMISSION_GRANTED ? "granted" : "not granted";
            Log.d(TAG, "Location Permissions "+grantedLocation);

            String grantedFileSystem = grantResults[1] == PackageManager.PERMISSION_GRANTED ? "granted" : "not granted";
            Log.d(TAG, "External Storage Permissions "+grantedFileSystem);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.secondactivity_button_send) {
            String randomToken = UUID.randomUUID().toString();

            // Example custom args to submit additional information
            HashMap<String, String> customArgs = new HashMap<>();
            customArgs.put("cId", "987654321");
            customArgs.put("amount", "2000");
            customArgs.put("transactionid + 2", "456 def + 2");
            System.out.println(randomToken);
            // Please use a unique token for every call and a location. Please refer to the documentation for further information.
            ClientSecurityModule.getInstance().execute("<SNIPPET_ID>", this, randomToken, "transaction", customArgs, null);

            Log.e(TAG, "CURRENT TOKEN: " + randomToken);

            // textViewToken.setText(randomToken);
        } else if(v.getId() == R.id.secondactivity_button_request_permissions) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE
                }, REQUEST_LOCATION_PERMISSIONS);
            }
        }
    }
}

