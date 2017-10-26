package com.example.hakan.jsonusing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Profil extends AppCompatActivity {

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        txt = (TextView) findViewById(R.id.txtAdisoyadi);
        txt.setText(MainActivity.adiSoyadi);
    }
}
