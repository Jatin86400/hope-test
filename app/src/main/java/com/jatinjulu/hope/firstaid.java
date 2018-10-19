package com.jatinjulu.hope;

/**
 * Created by HP on 14/10/2018.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class firstaid extends Fragment {
    ImageView b1,b2,b3,b4,b5,b6,b7,b8,b9,b10;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.firstaid, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b1 = (ImageView) view.findViewById(R.id.fa1);
        b2 = (ImageView) view.findViewById(R.id.fa2);
        b3 = (ImageView) view.findViewById(R.id.fa3);
        b4 = (ImageView) view.findViewById(R.id.fa4);
        b5 = (ImageView) view.findViewById(R.id.fa5);
        b6 = (ImageView) view.findViewById(R.id.fa6);
        b7 = (ImageView) view.findViewById(R.id.fa7);
        b8 = (ImageView) view.findViewById(R.id.fa8);
        b9 = (ImageView) view.findViewById(R.id.fa9);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","fracture.pdf");
                getActivity().startActivity(intent);


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","BURN.pdf");
                getActivity().startActivity(intent);


            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","Cardiac_arrest.pdf");
                getActivity().startActivity(intent);


            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","child_birth.pdf");
                getActivity().startActivity(intent);


            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","heart_attack.pdf");
                getActivity().startActivity(intent);

            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","choking1.pdf");
                getActivity().startActivity(intent);


            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","poisoning.pdf");
                getActivity().startActivity(intent);

            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","wound.pdf");
                getActivity().startActivity(intent);


            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","cramp.pdf");
                getActivity().startActivity(intent);

            }
        });
    }
}
