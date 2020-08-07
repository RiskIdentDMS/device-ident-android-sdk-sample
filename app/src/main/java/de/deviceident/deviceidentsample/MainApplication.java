package de.deviceident.deviceidentsample;

import android.app.Application;
import com.riskident.device.ClientSecurityModule;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ClientSecurityModule.initialize("<SNIPPETID>"); // Add your SNIPPETID here
    }
}
