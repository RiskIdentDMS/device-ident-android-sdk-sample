package com.riskident.deviceidentsample;

import android.app.Application;
import com.riskident.device.ClientSecurityModule;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ClientSecurityModule.initialize("www-test.jsctool.com"); // Add your SNIPPETID here
    }
}
