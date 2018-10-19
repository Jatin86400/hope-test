package com.jatinjulu.hope;

/**
 * Created by HP on 14/10/2018.
 */

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class spot_me extends Fragment {


    MapView mMapView;
    private GoogleMap googleMap;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    AlertDialogManager alert = new AlertDialogManager();
    MarkerOptions options = new MarkerOptions();
    SessionManager session;
    LatLng myplace;
    User myuser = new User();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.spot_me, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        session = new SessionManager(getContext());
        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v("maps", "inside oncreate view");
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(getActivity(), "Please Provide location Access to use the Map", Toast.LENGTH_SHORT);
                    return;
                } else {
                    googleMap.setMyLocationEnabled(true);
                }
                // For dropping a marker at a point on the Map

                database.child("users").addValueEventListener(new ValueEventListener() {
                    User user = new User();

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<LatLng> latlngs = new ArrayList<>();
                        ArrayList<String> usernames = new ArrayList<>();
                        MarkerOptions options = new MarkerOptions();

                        for (DataSnapshot datas : dataSnapshot.getChildren()) {
                            user = datas.getValue(User.class);
                            if (!user.isSafe) {
                                latlngs.add(new LatLng(user.latitude, user.longitude)); //some latitude and logitude value
                                usernames.add(new String(user.username));

                            }
                        }

                        Iterator<String> username = usernames.iterator();
                        googleMap.clear();
                        for (LatLng point : latlngs) {
                            options.position(point);
                            options.title(username.next());
                            options.snippet("Help Me");
                            googleMap.addMarker(options);
                        }
                        //googleMap.clear();

                        HashMap ref = session.getUserDetails();
                        String userid = ref.get("userid").toString();
                        myuser = dataSnapshot.child(userid).getValue(User.class);
                        LatLng myplace = new LatLng(myuser.latitude, myuser.longitude);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                if(myplace!=null) {
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(myplace).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                //LatLng sydney = new LatLng(-34, 151);
                //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker


            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();



        Log.v("maps", "inside on resume");
        database.child("users").addValueEventListener(new ValueEventListener() {
            User user = new User();


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<LatLng> latlngs = new ArrayList<>();
                ArrayList<String> usernames = new ArrayList<>();
                MarkerOptions options = new MarkerOptions();

                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    user = datas.getValue(User.class);
                    if (!user.isSafe) {
                        latlngs.add(new LatLng(user.latitude, user.longitude)); //some latitude and logitude value
                        usernames.add(new String(user.username));

                    }
                }
                googleMap.clear();
                Iterator<String> username = usernames.iterator();

                for (LatLng point : latlngs) {
                    options.position(point);
                    options.title(username.next());
                    options.snippet("Help Me");
                    googleMap.addMarker(options);
                }
                HashMap ref = session.getUserDetails();
                String userid = ref.get("userid").toString();
                myuser = dataSnapshot.child(userid).getValue(User.class);
                myplace = new LatLng(myuser.latitude, myuser.longitude);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(myplace!=null) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(myplace).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }

   /* @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            database.child("users").addValueEventListener(new ValueEventListener() {
                User user = new User();

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<LatLng> latlngs = new ArrayList<>();
                    ArrayList<String> usernames = new ArrayList<>();
                    MarkerOptions options = new MarkerOptions();

                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        user = datas.getValue(User.class);
                        if (!user.isSafe) {
                            latlngs.add(new LatLng(user.latitude, user.longitude)); //some latitude and logitude value
                            usernames.add(new String(user.username));

                        }
                    }
                    googleMap.clear();
                    Iterator<String> username = usernames.iterator();

                    for (LatLng point : latlngs) {
                        options.position(point);
                        options.title(username.next());
                        options.snippet("Help Me");
                        googleMap.addMarker(options);
                    }
                    User user = new User();
                    HashMap ref = session.getUserDetails();
                    String userid = ref.get("userid").toString();
                    user = dataSnapshot.child(userid).getValue(User.class);
                    LatLng myplace = new LatLng(user.latitude, user.longitude);
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(myplace).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }
    }*/

    @Override
    public void onPause() {
        super.onPause();
        Log.v("maps", "inside on pause");
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
        Log.v("maps", "inside on start ");

       /* database.child("users").addValueEventListener(new ValueEventListener() {
            User user = new User();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<LatLng> latlngs = new ArrayList<>();
                ArrayList<String> usernames = new ArrayList<>();
                MarkerOptions options = new MarkerOptions();

                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    user = datas.getValue(User.class);
                    if (!user.isSafe) {
                        latlngs.add(new LatLng(user.latitude, user.longitude)); //some latitude and logitude value
                        usernames.add(new String(user.username));

                    }
                }

                Iterator<String> username = usernames.iterator();

                for (LatLng point : latlngs) {
                    options.position(point);
                    options.title(username.next());
                    options.snippet("Help Me");
                    googleMap.addMarker(options);
                }
                User user = new User();
                HashMap ref = session.getUserDetails();
                String userid = ref.get("userid").toString();
                user = dataSnapshot.child(userid).getValue(User.class);
                LatLng myplace = new LatLng(user.latitude, user.longitude);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(myplace).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }
}