package com.example.portalrap;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class FragDuracion extends Fragment implements View.OnClickListener {


    ImageButton btnSiguiente,btnAnterior,btnInfo;
    Button btnSec1,btnSec2,btnSec3,btnSec4,btnSec5,btnMin1,btnMin2,btnMin3,btnMin4,btnMin5;
    TextView txtDurMin,txtDurSec;
    public static int SegundosElegidos =-1, MinutosElegidos = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_duracion, container, false);
        Setear(v);
        Listeners();


        return v;
    }

    public void Setear(View v){

        btnSiguiente = v.findViewById(R.id.botonsiguientededuracion);
        btnAnterior = v.findViewById(R.id.botonanteriordeduracion);
        btnInfo = v.findViewById(R.id.botoninfodeduracion);
        btnSec1 = v.findViewById(R.id.btn10segdeduracion);
        btnSec2 = v.findViewById(R.id.btn20segdeduracion);
        btnSec3 = v.findViewById(R.id.btn30segdeduracion);
        btnSec4 = v.findViewById(R.id.btn40segdeduracion);
        btnSec5 = v.findViewById(R.id.btn50segdeduracion);
        btnMin1 = v.findViewById(R.id.btn0mindeduracion);
        btnMin2 = v.findViewById(R.id.btn1mindeduracion);
        btnMin3 = v.findViewById(R.id.btn2mindeduracion);
        btnMin4 = v.findViewById(R.id.btn3mindeduracion);
        btnMin5 = v.findViewById(R.id.btn4mindeduracion);
        txtDurSec = v.findViewById(R.id.duracionelegidosec);
        txtDurMin = v.findViewById(R.id.duracionelegidomin);
    }
    public void Listeners(){

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main=(MainActivity) getActivity();
                main.PasaraFragmentElegirBases();
            }
        });
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main=(MainActivity) getActivity();
                main.PasaraFragmentEstimulo();
            }
        });
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mensaje;
                mensaje = new AlertDialog.Builder(getActivity());
                mensaje.setTitle("Elegir Duración");
                mensaje.setMessage("Elige el tiempo de duración del\n" +
                        "entrenamiento.");
                mensaje.setPositiveButton("Aceptar",null);
                mensaje.create();
                mensaje.show();
            }
        });

        btnSec1.setOnClickListener(this);
        btnSec2.setOnClickListener(this);
        btnSec3.setOnClickListener(this);
        btnSec4.setOnClickListener(this);
        btnSec5.setOnClickListener(this);
        btnMin1.setOnClickListener(this);
        btnMin2.setOnClickListener(this);
        btnMin3.setOnClickListener(this);
        btnMin4.setOnClickListener(this);
        btnMin5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Button botonapretado2 = (Button)v;
        int idboton2 = botonapretado2.getId();

        switch (idboton2)
        {
            case R.id.btn10segdeduracion:
                txtDurSec.setText(btnSec1.getText().toString());
                SegundosElegidos = 10;
                break;
            case R.id.btn20segdeduracion:
                txtDurSec.setText(btnSec2.getText().toString());
                SegundosElegidos = 20;break;
            case R.id.btn30segdeduracion:
                txtDurSec.setText(btnSec3.getText().toString());
                SegundosElegidos = 30;break;
            case R.id.btn40segdeduracion:
                txtDurSec.setText(btnSec4.getText().toString());
                SegundosElegidos = 40;break;
            case R.id.btn50segdeduracion:
                txtDurSec.setText(btnSec5.getText().toString());
                SegundosElegidos = 50;break;
            case  R.id.btn0mindeduracion:
                txtDurMin.setText(btnMin1.getText().toString());
                MinutosElegidos = 0;
                break;
            case  R.id.btn1mindeduracion:
                txtDurMin.setText(btnMin2.getText().toString());
                MinutosElegidos = 1; break;
            case  R.id.btn2mindeduracion:
                txtDurMin.setText(btnMin3.getText().toString());
                MinutosElegidos = 2;break;
            case  R.id.btn3mindeduracion:
                txtDurMin.setText(btnMin4.getText().toString());
                MinutosElegidos = 3;break;
            case  R.id.btn4mindeduracion:
                txtDurMin.setText(btnMin5.getText().toString());
                MinutosElegidos = 4;break;

        }

    }
}