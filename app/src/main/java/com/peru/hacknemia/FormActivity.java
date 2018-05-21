package com.peru.hacknemia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FormActivity extends AppCompatActivity implements LocationListener {

    private EditText nombre,hb,peso,edad,procedencia,pregunta;

    private Spinner tipo;
    private Button btnDiagnosticar;
    private double nedad,npeso, latitud, longitud;

    private double nhb;
    private String ntipo;
    private  int r;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, this);

        ntipo= getIntent().getStringExtra("tipo");
        edad = (EditText) findViewById(R.id.txtEdad);
        hb=(EditText) findViewById(R.id.txtHb);
        peso=(EditText) findViewById(R.id.txtPeso);
        nombre=(EditText) findViewById(R.id.txtNombre);
        hb=(EditText) findViewById(R.id.txtHb);
        pregunta=(EditText) findViewById(R.id.txtPregunta);

        btnDiagnosticar=(Button) findViewById(R.id.btnDiagnostico);
        btnDiagnosticar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nedad= Double.parseDouble(edad.getText().toString());
                nhb= Double.parseDouble(hb.getText().toString());
                npeso= Double.parseDouble(peso.getText().toString());
                switch (ntipo){
                    case "p":
                        // nedad= semanas
                        if(nedad<=1){
                            if (nhb<=13)
                                r=1; //con anemia
                            else
                                r=0; // sin anemia
                        }
                        if((nedad>=2) &&  (nedad<=4)){
                            if (nhb<=10)
                                r=1; // con anemia
                            else
                                r=0; //sin anemia
                        }
                        if((nedad>=5) &&  (nedad<=8)){
                            if (nhb<=8)
                                r=1; //con anemia
                            else
                                r=0; // sin anemia
                        }
                        break;
                    case "t":
                        // nedad= meses
                        if(nedad<=2){
                            if (nhb<=13.5)
                                r=1; //con anemia
                            else
                                r=0; // sin anemia
                        }
                        if((nedad>=2) &&  (nedad<=6)){
                            if (nhb<=9.5)
                                r=1; // con anemia
                            else
                                r=0; //sin anemia
                        }
                        break;
                    case "m":
                        // nedad= meses  6 mayor
                        if(nedad>=6){ //severa
                            if (nhb<7)
                                r=2; //severa
                            if ((nhb>=7) && (nhb<10))
                                r=3; // modera
                            if ((nhb>=10) && (nhb<11))
                                r=4; //leve
                            if(nhb>=11)
                                r=0; // sin anemia
                        }
                        break;
                }
                if(nombre.getText().toString()!=""){

                Config.mensaje(getApplicationContext(),"ANEMIA "+r);
                Intent i= new Intent(getApplicationContext(),ResultadoActivity.class);
                i.putExtra("resultado",r+"");
                i.putExtra("peso",npeso+"");
                i.putExtra("edad",nedad+"");
                i.putExtra("nombre",nombre.getText().toString());
                i.putExtra("hemoglobina",hb.getText().toString());
                i.putExtra("pregunta",pregunta.getText().toString());
                i.putExtra("latitud", String.valueOf(latitud));
                i.putExtra("longitud",String.valueOf(longitud));


                //Config.mensaje(getApplicationContext(),npeso+"");

                startActivity(i);
                }
            }
        });




    }

    @Override
    public void onLocationChanged(Location location) {

        latitud=location.getLatitude();
        longitud=location.getLongitude();
        String msg = "New Latitude: " + location.getLatitude()
                + "New Longitude: " + location.getLongitude();

        //Config.mensaje(getApplicationContext(),msg);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
