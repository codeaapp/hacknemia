package com.peru.hacknemia;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ResultadoActivity extends AppCompatActivity  {

    private  String resultado,nombre,hemoglobina,r;
    private double edad;

    private String gotas,frascos,jarabe,pregunta;
    private double peso,latitud,longitud;
    private TextView txtngotas,txtnfrascos, txtTratamiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        txtngotas=(TextView) findViewById(R.id.txtGotas);
        //txtPregunta=(TextView) findViewById(R.id.txtPregunta);
        txtnfrascos=(TextView) findViewById(R.id.txtFrascos);
        resultado = getIntent().getStringExtra("resultado");
        peso=Double.valueOf(getIntent().getStringExtra("peso"));
        nombre=getIntent().getStringExtra("nombre");
        pregunta=getIntent().getStringExtra("pregunta");
        edad= Double.valueOf(getIntent().getStringExtra("edad"));
        hemoglobina=getIntent().getStringExtra("hemoglobina");
        txtTratamiento=(TextView) findViewById(R.id.txtTratamiento);
        latitud= Double.parseDouble(getIntent().getStringExtra("latitud"));
        longitud=Double.parseDouble(getIntent().getStringExtra("longitud"));
        Config.mensaje(getApplicationContext(),resultado);
        r="0";

        ///0 no tiene anemia
        ///1 , 2, 3, 4 si tiene anemia

        if(resultado.equals(r)){
            String si="si";
            txtTratamiento.setText("TRATAMIENTO CON ANEMIA");
            if(pregunta.equals(si) ){ // menor a 6 memes con bajo peso al nacer
                if(peso==2.0){
                    gotas="GOTAS x dia : 6"; frascos="FRASCOS x mes : 1";
                }if((peso>=3.0 )&&(peso<6)){
                    gotas="GOTAS x dia : 15"; frascos="FRASCOS x mes : 1";
                }if((peso>=6.0 )&&(peso<9)){
                    gotas="GOTAS x dia : 22"; frascos="FRASCOS x mes:2";
                }
            }else{ // menor a 6 meses peso adecuado al nacer
                if((peso>=1.0 )&&(peso>=5.0)){
                    gotas="GOTAS x dia : 12"; frascos="FRASCOS x mes : 1";
                }if((peso>=6.0 )&&(peso>=8.0)){
                    gotas="GOTAS x dias: 17"; frascos="FRASCOS x mes : 2";
                }if((peso>=9.0 )&&(peso>=11.0)){
                    jarabe="JARABE (Cdtas) x dia : 1.5"; frascos="FRASCOSx mes : 2";
                }if((peso>=12.0 )&&(peso>=14.0)){
                    jarabe="JARABE (Cdtas) x dia : 2"; frascos="FRASCOS x mes : 3";
                }if((peso>=15.0 )&&(peso>=17.0)){
                    jarabe="JARABE (Cdtas) x dia : 3"; frascos="FRASCOS x mes : 3";

                }if((peso>=18.0 )&&(peso>=20.0)){
                    jarabe="JARABE (Cdtas) x dia : 3.5"; frascos="FRASCOS x mes : 4";
                }if((peso>=21.0 )&&(peso>=28.0)){
                    jarabe="JARABE (Cdtas) x dia : 4.5"; frascos="FRASCOS x mes : 4";
                }
            }
        }
        else
        {
            txtTratamiento.setText("TRATAMIENTO SIN ANEMIA");



        }

        txtngotas.setText(gotas);
        txtnfrascos.setText(frascos);


        /*
        AlertDialog.Builder dialog = new AlertDialog.Builder(ResultadoActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("PREGUNTA");
        dialog.setMessage("¿El niño nacio con peso bajo peso?" );
        dialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        })

                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "Cancel".
                    }
                });


        final AlertDialog alert = dialog.create();
        alert.show();
        */
        sincronizar();
    }

    public void sincronizar(){



        RequestParams params = new RequestParams();
        params.put("nombre", nombre);
        params.put("latitud", latitud);
        params.put("longitud", longitud);
        params.put("peso",peso);
        params.put("hemoglobina", hemoglobina);
        params.put("edad", edad);
        params.put("diagnostico", gotas+" - "+frascos);
        params.put("save", "ok");



        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Config.URL_SAVE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    //JSONArray jsonArray = response.getJSONArray("response");

                    Config.mensaje(getApplicationContext(),"DIAGNÓSTICO");

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                if (statusCode == 404) {
                    Config.mensaje(getApplicationContext(), "Vuelva a intentarlo");
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Config.mensaje(getApplicationContext(), "Servidor en mantenimiento");
                }
                // When Http response code other than 404, 500
                else {
                    Config.mensaje(getApplicationContext(), "Se ha perdido la conexión con internet");
                }

            }
        });
    }

}

