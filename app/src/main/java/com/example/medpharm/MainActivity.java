package com.example.medpharm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button MAJ,CALC,SYNTH;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MAJ=(Button) findViewById(R.id.maj);
        CALC=(Button)findViewById(R.id.calc);
        SYNTH=(Button)findViewById(R.id.synth);
        MAJ.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
             Intent intent=new Intent(MainActivity.this,Maj.class);
             startActivity(intent);
            }
        });
        CALC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte=new Intent(MainActivity.this,CalculDos.class);
                startActivity(inte);
            }
        });
        SYNTH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Synthese.class);
                startActivity(intent);
            }
        });

    }


}
