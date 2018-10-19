package com.jatinjulu.hope;

import android.*;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginAcitvity extends Activity {
    // Email, password edittext
    EditText txtUsername,userid, contact1,contact2,contact3,contact4,contact5;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    // login button
    Button btnLogin,checkid;

    // Alert Dialog Manager
    AlertDialogManager alert = new AlertDialogManager();
    User user;
    // Session Manager Class
    SessionManager session;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Log.v("database","firebase is connected");
                } else {
                    Log.v("database","firebase is disconnected");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });*/
        // Session Manager

        session = new SessionManager(getApplicationContext());

        // Email, Password input text
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        userid = (EditText) findViewById(R.id.userid);
        contact1 = (EditText) findViewById(R.id.contact1);
        contact2 =(EditText) findViewById(R.id.contact2);
        contact3 = (EditText) findViewById(R.id.contact3);
        contact4 = (EditText) findViewById(R.id.contact4);
        contact5 = (EditText) findViewById(R.id.contact5);


        //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();


        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);
        checkid = (Button) findViewById(R.id.btnCheckid);

        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
                txtUsername = (EditText) findViewById(R.id.txtUsername);
                userid = (EditText) findViewById(R.id.userid);
                contact1 = (EditText) findViewById(R.id.contact1);
                contact2 =(EditText) findViewById(R.id.contact2);
                contact3 = (EditText) findViewById(R.id.contact3);
                contact4 = (EditText) findViewById(R.id.contact4);
                contact5 = (EditText) findViewById(R.id.contact5);
                final String username = txtUsername.getText().toString();
                final String userId = userid.getText().toString();
                final String first = contact1.getText().toString();
                final String second = contact2.getText().toString();
                final String third = contact3.getText().toString();
                final String fourth = contact4.getText().toString();
                final String fifth = contact5.getText().toString();



                // Check if username, password is filled
                if(username.trim().length() >0 && userId.trim().length() >0 && first.trim().length() > 0 && second.trim().length() > 0 && third.trim().length() > 0 && fourth.trim().length() > 0 && fifth.trim().length() > 0){
                    // For testing puspose username, password is checked with sample data
                    // username = test
                    // password = test

                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                    database.child("users").child(userId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {
                                alert.showAlertDialog(LoginAcitvity.this,"Error","Please enter unique userid",false);
                            }
                            else
                            {
                                user = new User();
                                user.username = username;
                                user.latitude = 0;
                                user.longitude=0;
                                user.isSafe = true;
                                database.child("users").child(userId).setValue(user);
                                session.createLoginSession(username,userId,first,second,third,fourth,fifth);
                                alert.showAlertDialog(LoginAcitvity.this,"Success","You are logged in",true);
                                // Staring MainActivity
                                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(i);
                                finish();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    alert.showAlertDialog(LoginAcitvity.this,"Error","Please enter valid username and contact details",false);

                }

            }
        });
        checkid.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
                connectedRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        boolean connected = snapshot.getValue(Boolean.class);
                        if (connected) {

                        } else {
                            alert.showAlertDialog(LoginAcitvity.this,"Error","No network available",false);
                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                        //System.err.println("Listener was cancelled");
                    }
                });
                EditText userid = (EditText) findViewById(R.id.userid);
                String userId = userid.getText().toString();
                Log.v("database","above database");
                    database.child("users").child(userId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.v("database", "below database");

                            if (dataSnapshot.exists()) {
                                alert.showAlertDialog(LoginAcitvity.this, "Error", "Id already in use", false);

                            } else
                                alert.showAlertDialog(LoginAcitvity.this, "OK", "Id available", true);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.v("database", "inside database error");
                        }
                    });
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.INTERNET
                }, 10);
                return;
            } else {

            }
        } else {

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 10:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    Log.v("getLocation","granted permission now executing");
                }
                else
                {
                    alert.showAlertDialog(LoginAcitvity.this,"Error","Please allow access to location service",false);
                    break;
                }
            }
        }
    }

}
