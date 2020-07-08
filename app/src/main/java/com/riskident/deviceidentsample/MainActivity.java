package com.riskident.deviceidentsample;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.riskident.device.ClientSecurityModule;
import com.riskident.device.DataCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends Activity implements DataCallback, View.OnClickListener{
    private final static int REQUEST_LOCATION_PERMISSIONS = 1;

    private String TAG = MainActivity.class.getSimpleName();

    private TextView textViewToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textViewToken = findViewById(R.id.mainactivity_token_value);
        findViewById(R.id.mainactivity_button_send).setOnClickListener(this);
        findViewById(R.id.mainactivity_button_request_permissions).setOnClickListener(this);
    }

    @Override
    public void onDataSent(String data) {
        try {
            File file = new File(getExternalFilesDir(null), "androidDevice.json");
            file.delete();
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
            file.exists();
        } catch (Exception e) {
            Log.e(TAG, "Test File could not be written.");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
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
        if(v.getId() == R.id.mainactivity_button_send) {
            String randomToken = UUID.randomUUID().toString();

            // Example custom args to submit additional infromation
            HashMap<String, String> customArgs = new HashMap<>();
            customArgs.put("cId", "123456789");
            customArgs.put("amount", "1000");

            // Please use a unique token for every call and a location. Please refer to the documentation for further information.
            ClientSecurityModule.getInstance().execute(this, this, randomToken, "<LOCATION>", customArgs);

            Log.e(TAG, "CURRENT TOKEN: " + randomToken);

            textViewToken.setText(randomToken);
        } else if(v.getId() == R.id.mainactivity_button_request_permissions) {
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
