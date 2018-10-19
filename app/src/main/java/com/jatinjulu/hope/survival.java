package com.jatinjulu.hope;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.github.barteksc.pdfviewer.PDFView;

public class survival extends Fragment {
    ImageView b1,b2,b3,b4,b5,b6,b7,b8,b9,b10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.survival, container, false);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b1 = (ImageView) view.findViewById(R.id.button1);
        b2 = (ImageView) view.findViewById(R.id.button2);
        b3 = (ImageView) view.findViewById(R.id.button3);
        b4 = (ImageView) view.findViewById(R.id.button4);
        b5 = (ImageView) view.findViewById(R.id.button5);
        b6 = (ImageView) view.findViewById(R.id.button6);
        b7 = (ImageView) view.findViewById(R.id.button7);
        b8 = (ImageView) view.findViewById(R.id.button8);
        b9 = (ImageView) view.findViewById(R.id.button9);
        b10 = (ImageView) view.findViewById(R.id.button10);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","volcano.pdf");
                getActivity().startActivity(intent);


               }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","cyclone.pdf");
                getActivity().startActivity(intent);


            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","tsunami.pdf");
                getActivity().startActivity(intent);


            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","flood.pdf");
                getActivity().startActivity(intent);


            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","earthquake.pdf");
                getActivity().startActivity(intent);

            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","landslide.pdf");
                getActivity().startActivity(intent);


            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","avalanche.pdf");
                getActivity().startActivity(intent);

            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","forest.pdf");
                getActivity().startActivity(intent);


            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","heat.pdf");
                getActivity().startActivity(intent);

            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),volcano_eruption.class);
                intent.putExtra("pdfname","sandstorm.pdf");
                getActivity().startActivity(intent);

            }
        });

    }
    /*public class survival2 extends AppCompatActivity{
        PDFView pdfview;
        Button b1,b2;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.survival);
            b1 = (Button) findViewById(R.id.button1);
            b2 = (Button) findViewById(R.id.button2);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pdfview = (PDFView) findViewById(R.id.PDFView);
                    pdfview.fromAsset("jatin_transcripterp.pdf").load();
                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pdfview = (PDFView) findViewById(R.id.PDFView);
                    pdfview.fromAsset("jee main scorecard.pdf").load();
                }
            });

        }
    }*/
}