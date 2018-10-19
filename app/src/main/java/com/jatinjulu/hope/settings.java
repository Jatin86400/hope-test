package com.jatinjulu.hope;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import static com.jatinjulu.hope.SessionManager.KEY_NAME;
import static com.jatinjulu.hope.SessionManager.contact1;
import static com.jatinjulu.hope.SessionManager.contact2;
import static com.jatinjulu.hope.SessionManager.contact3;
import static com.jatinjulu.hope.SessionManager.contact4;
import static com.jatinjulu.hope.SessionManager.contact5;



public class settings extends AppCompatActivity {
    SessionManager session;
    TextView name,first,second,third,fourth,fifth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        session = new SessionManager(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView name = (TextView) findViewById(R.id.username);
        TextView first = (TextView) findViewById(R.id.firstcontact);
        TextView second = (TextView) findViewById(R.id.secondcontact);
        TextView third = (TextView) findViewById(R.id.thirdcontact);
        TextView fourth = (TextView) findViewById(R.id.fourthcontact);
        TextView fifth = (TextView) findViewById(R.id.fifthcontact);
        Button button = (Button) findViewById(R.id.btnChangeInfo);
        if(session.isLoggedIn()) {
            HashMap res = session.getUserDetails();
            name.setText(res.get(KEY_NAME).toString());
            first.setText(res.get(contact1).toString());
            second.setText(res.get(contact2).toString());
            third.setText(res.get(contact3).toString());
            fourth.setText(res.get(contact4).toString());
            fifth.setText(res.get(contact5).toString());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    Log.v("SOSMessage", "user pressed yes");
                                    session.logoutUser();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    Log.v("SOSMessage", "user pressed no");
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(settings.this);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                }
            });
        }
        else
        {

        }
    }
}
