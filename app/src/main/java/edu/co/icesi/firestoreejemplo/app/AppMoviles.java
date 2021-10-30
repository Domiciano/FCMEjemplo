package edu.co.icesi.firestoreejemplo.app;

import android.app.Application;
import android.content.Context;

public class AppMoviles extends Application {

    private static AppMoviles app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static Context getGlobalContext(){
        return app.getApplicationContext();
    }
}
