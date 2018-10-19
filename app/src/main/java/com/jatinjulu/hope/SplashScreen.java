package com.jatinjulu.hope;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.microsoft.windowsazure.mobileservices.*;


public class SplashScreen extends AppCompatActivity {

    SessionManager session;
    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);
        mClient = new MobileServiceClient(
        "https://hope-test2.azurewebsites.net",      
        this);


        session = new SessionManager(getApplicationContext());
        Thread myThread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);

                    if(session.isLoggedIn()) {
                        Intent home_activity = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(home_activity);
                    }
                    else{
                        Intent login_activity = new Intent(getApplicationContext(), LoginAcitvity.class);
                        startActivity(login_activity);
                    }
                        finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_splash_screen);
    }
}
