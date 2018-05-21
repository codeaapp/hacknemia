package com.peru.hacknemia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TipoActivity extends AppCompatActivity {

    private Button btnPrematuro,btnTermino,btnMayor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo);

        btnPrematuro=(Button) findViewById(R.id.btnPrematuro);
        btnTermino=(Button) findViewById(R.id.btnTermino);
        btnMayor=(Button) findViewById(R.id.btnMayor);

        btnPrematuro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),FormActivity.class);
                i.putExtra("tipo","p");
                startActivity(i);
            }
        });

        btnTermino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),FormActivity.class);
                i.putExtra("tipo","t");
                startActivity(i);
            }
        });

        btnMayor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),FormActivity.class);
                i.putExtra("tipo","m");
                startActivity(i);
            }
        });


    }
}
