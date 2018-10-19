package com.jatinjulu.hope;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class volcano_eruption extends AppCompatActivity {
    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volcano_eruption);
        String filename = getIntent().getStringExtra("pdfname");
        pdfView = (PDFView) findViewById(R.id.volcano);
        pdfView.fromAsset(filename).load();
    }
}
