package com.example.portalrap.FragmentsEntrenamiento;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.portalrap.MainActivity;
import com.example.portalrap.R;


public class FragEntrenar extends Fragment implements View.OnClickListener {

    ImageButton btnFav,btnPlay,btnRepetir,btnCola,btnVolver;
    TextView txtArtista,txtBase,txtDuracion;
    SeekBar barradebeat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.entrenar, container, false);
        Setear(v);

        Log.d("Duracion","" + MainActivity.Segundos);
        Log.d("Duracion","" + MainActivity.Minutos);
        Log.d("Duracion","" + MainActivity.PosModo);
        Log.d("Duracion","" + MainActivity.Frecuencia);

        if(MainActivity.PosModo >= 0 && MainActivity.Segundos > 0 && MainActivity.Minutos >= 0 && MainActivity.Frecuencia > 0)
        {
            //personalizado
            btnRepetir.setImageResource(R.drawable.ic_icono_repetir);
            btnCola.setImageResource(R.drawable.ic_icono_cola_verde);
            txtDuracion.setText(MainActivity.Minutos + ":" + MainActivity.Segundos);

        }
        else {
            //predeterminado
            btnRepetir.setImageResource(R.drawable.ic_repetir_verde);
            btnCola.setImageResource(R.drawable.ic_icono_cola_blanco);
            txtDuracion.setText("5:0");

        }




        return v;
    }

    public void Setear(View v){
        btnVolver =v.findViewById(R.id.btnVOlverdeentrenar);
        btnCola = v.findViewById(R.id.btnColadeentrenar);
        btnFav = v.findViewById(R.id.favdeentrenar);
        btnPlay = v.findViewById(R.id.Playdeentrenar);
        btnRepetir = v.findViewById(R.id.btnrepetirbasedeentrenar);
        txtArtista = v.findViewById(R.id.nombreArtistaSeleccionadodeentrenar);
        txtBase = v.findViewById(R.id.nombreBaseSeleccionadadeentrenar);
        txtDuracion = v.findViewById(R.id.TiempoFreedeentrenar);
        barradebeat = v.findViewById(R.id.Barradeentrenar);

        btnVolver.setOnClickListener(this);
        btnRepetir.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnCola.setOnClickListener(this);
        btnFav.setOnClickListener(this);

    }
    DialogInterface.OnClickListener escuchador = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            if(which == -1)
            {
                MainActivity main=(MainActivity) getActivity();
                main.PasaraFragmentHome();
            }
            else if(which == -2)
            {
                dialog.cancel();
            }

        }
    };


    @Override
    public void onClick(View v) {
        ImageButton botonapretado;
        botonapretado = (ImageButton)v;
        int idbotonapretado = botonapretado.getId();
        MainActivity main=(MainActivity) getActivity();

        switch (idbotonapretado)
        {
            case R.id.btnVOlverdeentrenar:

                //tambien habria que frenar el beat

                AlertDialog.Builder mensaje;
                mensaje = new AlertDialog.Builder(getActivity());
                mensaje.setTitle("Salir del Entrenamiento");
                mensaje.setMessage("Si sales, la grabación se va a borrar");
                mensaje.setPositiveButton("Aceptar",escuchador);
                mensaje.setNegativeButton("Cancelar", escuchador);
                mensaje.create();
                mensaje.show();

                break;
            case R.id.btnColadeentrenar:

                main.PasaraFragCola();
                break;
            case R.id.btnrepetirbasedeentrenar:

                break;
            case R.id.btnGrabardeentrenar:

                break;
            case R.id.favdeentrenar:

                break;
            case R.id.Playdeentrenar:

                break;

        }

    }
}