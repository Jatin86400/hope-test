package com.jatinjulu.hope;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class emergency  extends Fragment {
    private static final int REQUEST_CALL = 1;
    private final String[] contact;
    private int number_permission;

    public emergency() {
        final String[] contactss= new String[5];
        contactss[0]="102";
        contactss[1]="100";
        contactss[2]="101";
        contactss[3]="108";
        contactss[4]="9540161344";
        this.contact = contactss;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.emergency, container, false);
        ImageView imageCall_1 = rootView.findViewById(R.id.image_call_1);
        ImageView imageCall_2 = rootView.findViewById(R.id.image_call_2);
        ImageView imageCall_3 = rootView.findViewById(R.id.image_call_3);
        ImageView imageCall_4 = rootView.findViewById(R.id.image_call_4);
        ImageView imageCall_5 = rootView.findViewById(R.id.image_call_5);
        imageCall_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number_permission=0;
                makePhoneCall(contact[0]);

            }
        });
        imageCall_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number_permission=1;
                makePhoneCall(contact[1]);
            }
        });

        imageCall_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number_permission=2;
                makePhoneCall(contact[2]);
            }
        });

        imageCall_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number_permission=3;
                makePhoneCall(contact[3]);
            }
        });

        imageCall_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number_permission=4;
                makePhoneCall(contact[4]);
            }
        });

        return rootView;
    }
    private void makePhoneCall(String number){
        // String number = mEditTextNumber.getText().toString();  // iske jagha eek string jo tera desired number de !
        //    number="7477718333";
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }else{
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL) {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makePhoneCall(contact[number_permission]);
            }else{
                Toast.makeText(getContext(),"Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
