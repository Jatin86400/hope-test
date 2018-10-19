package com.jatinjulu.hope;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.telephony.SmsManager;

import static com.jatinjulu.hope.SessionManager.KEY_NAME;
import static com.jatinjulu.hope.SessionManager.contact1;
import static com.jatinjulu.hope.SessionManager.contact2;
import static com.jatinjulu.hope.SessionManager.contact3;
import static com.jatinjulu.hope.SessionManager.contact4;
import static com.jatinjulu.hope.SessionManager.contact5;
import static com.jatinjulu.hope.SessionManager.userid;

public class HomeActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{
    public Connection connect;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0;
    Statement st;
    HashMap res;
    SessionManager session;
    String z;
    PreparedStatement preparedStatement;
    private LocationManager locationManager;
    private LocationListener locationListener;
    public Location curr_location = new Location("dummyprovider");
    int smscount=0;
    private GoogleApiClient mGoogleApiClient;
    //public getLocation getlocation;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // connect = (Connection) connectionclass();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        session = new SessionManager(getApplicationContext());
        res = session.getUserDetails();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton sos = (FloatingActionButton) findViewById(R.id.sos);
        final FloatingActionButton marksafe = (FloatingActionButton) findViewById(R.id.marksafe);
        database.child("users").child(res.get("userid").toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean now = dataSnapshot.child("isSafe").getValue(boolean.class);
                if(now)
                {
                    marksafe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1E90FF")));
                    marksafe.setImageResource(R.drawable.ic_marksafe);

                }
                else
                {
                    marksafe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFF00")));
                    marksafe.setImageResource(R.drawable.ic_marksafe2);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
            marksafe.setOnClickListener(new View.OnClickListener() {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            @Override
            public void onClick(View view) {
               database.child("users").child(res.get("userid").toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       boolean now = dataSnapshot.child("isSafe").getValue(boolean.class);
                       database.child("users").child(res.get("userid").toString()).child("isSafe").setValue(!now);
                       if(!now)
                       {
                           marksafe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1E90FF")));
                           marksafe.setImageResource(R.drawable.ic_marksafe);
                           Toast.makeText(HomeActivity.this,"You are marked safe!",Toast.LENGTH_LONG).show();
                       }
                       else
                       {
                           marksafe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFF00")));
                           marksafe.setImageResource(R.drawable.ic_marksafe2);
                           Toast.makeText(HomeActivity.this, "You are marked Unsafe!", Toast.LENGTH_LONG).show();
                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });

            }
        });
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address="";
                if(curr_location!=null) {
                    LatLng current_location = new LatLng(curr_location.getLatitude(), curr_location.getLongitude());
                    address = getAddress(current_location);
                }
                else
                    Toast.makeText(view.getContext(), "Location not Detected", Toast.LENGTH_SHORT).show();
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Log.v("SOSMessage","user pressed yes");
                                sendsms();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Log.v("SOSMessage","user pressed no");
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            }
        });
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }

    private String getAddress(LatLng current_location) {
        String address="";
        String city="";
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            if(current_location!=null) {
                List<Address> addresses = geocoder.getFromLocation(current_location.latitude, current_location.longitude, 1);
                address = addresses.get(0).getAddressLine(0);
                city = addresses.get(0).getLocality();
                address = address + " City: " + city;
                //Toast.makeText(this, address, Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            Log.e("getLocation",e.getMessage());
        }
        return address;

    }
    public void sendsms()
    {

        if(ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.SEND_SMS)){
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
            }else{
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        }else{
            String number1 = res.get(contact1).toString();
            String number2 = res.get(contact2).toString();
            String number3 = res.get(contact3).toString();
            String number4 = res.get(contact4).toString();
            String number5 = res.get(contact5).toString();
            String address = " ";
            if(curr_location!=null) {
                address = getAddress(new LatLng(curr_location.getLatitude(), curr_location.getLongitude()));
            }
            else
                address=" ";
            String sms = " ";
            if(address.trim().length()>0) {
                sms = "Help Me!" + "\n" + "Name: " + res.get(KEY_NAME).toString() + "\n" + "I am at: " + address;
            }
            else
                sms = "Help Me!" + "\n" + "Name: " + res.get(KEY_NAME).toString();
            try{
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number1, null, sms,null,null);
                smsManager.sendTextMessage(number2, null, sms,null,null);
                smsManager.sendTextMessage(number3, null, sms,null,null);
                smsManager.sendTextMessage(number4, null, sms,null,null);
                smsManager.sendTextMessage(number5, null, sms,null,null);
                Toast.makeText(HomeActivity.this,"SOS Message Sent",Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                if(smscount<=2) {
                    Toast.makeText(HomeActivity.this, "Please Press once more", Toast.LENGTH_SHORT).show();
                    smscount+=1;
                }
                else
                {
                    Toast.makeText(HomeActivity.this, "Failed to send SOS Message", Toast.LENGTH_SHORT).show();
                }
            }


        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        switch (requestCode){
            case 1:{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(HomeActivity.this,Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"Permission granted!", Toast.LENGTH_SHORT).show();
                        sendsms();
                    }
                }else{
                    Toast.makeText(this,"No Permission granted!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent settings = new Intent(this, settings.class);
            this.startActivity(settings);
            //this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        curr_location=LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(curr_location!=null) {
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            User user = new User();
            user.isSafe = true;
            user.username = res.get(KEY_NAME).toString();
            user.latitude = curr_location.getLatitude();
            user.longitude = curr_location.getLongitude();
            database.child("users").child(res.get(userid).toString()).child("latitude").setValue(curr_location.getLatitude());
            database.child("users").child(res.get(userid).toString()).child("longitude").setValue(curr_location.getLongitude());
            //Toast.makeText(this,curr_location.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("getLocation", "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("getLocation", "Connection failed. Error: " + connectionResult.getErrorCode());
    }
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    //Deleted Placeholder class

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           switch(position){
               case 0:
                   survival tab1 = new survival();
                   return tab1;
               case 1:
                   spot_me tab2  = new spot_me();
                   return tab2;
               case 2:
                   firstaid tab3 = new firstaid();
                   return tab3;
               case 3:
                   emergency tab4 = new emergency();
                   return tab4;
              // case 4:
                //   weather tab5 = new weather();
                  // return tab5;
               default:
                   return null;
           }
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position){
                case 0:
                    return "Survival";
                case 1:
                    return "Spot-me";
                case 2:
                    return "First-aid";
                case 3:
                    return "Emergency";
               /// case 4:
                  //  return "Weather";
                default:
                    return null;
            }

        }
    }

}

