package com.example.portalrap.FragmentsEntrenamiento;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.portalrap.MainActivity;
import com.example.portalrap.R;

import java.io.IOException;


public class FragEntrenar extends Fragment implements View.OnClickListener {

    ImageButton btnFav, btnPlay, btnRepetir, btnCola, btnVolver, btnGrabar;
    TextView txtArtista, txtBase, txtDuracion;
    SeekBar barradebeat;
    MediaRecorder grabacion = null;
    String archivoSalida = null;
    MediaPlayer mediaPlayer;
    Boolean f = false,f1 = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.entrenar, container, false);
        Setear(v);

        mediaPlayer = new MediaPlayer();

        if (MainActivity.PosModo >= 0 && MainActivity.Segundos > 0 && MainActivity.Minutos >= 0 && MainActivity.Frecuencia > 0) {
            //personalizado
            btnRepetir.setImageResource(R.drawable.ic_icono_repetir);
            btnCola.setImageResource(R.drawable.ic_icono_cola_verde);
            txtDuracion.setText(MainActivity.Minutos + ":" + MainActivity.Segundos);
        } else {
            //predeterminado
            btnRepetir.setImageResource(R.drawable.ic_repetir_verde);
            btnCola.setImageResource(R.drawable.ic_icono_cola_blanco);
            txtDuracion.setText("5:0");
        }

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Recorder();
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reproducir();
            }
        });
        return v;
    }

    public void Setear(View v) {
        btnGrabar = v.findViewById(R.id.btnGrabardeentrenar);
        btnVolver = v.findViewById(R.id.btnVOlverdeentrenar);
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
        btnGrabar.setOnClickListener(this);


    }
    DialogInterface.OnClickListener escuchador = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            if (which == -1) {
                MainActivity main = (MainActivity) getActivity();
                main.PasaraFragmentHome();
            } else if (which == -2) {
                dialog.cancel();
            }

        }
    };

    @Override
    public void onClick(View v) {
        ImageButton botonapretado;
        botonapretado = (ImageButton) v;
        int idbotonapretado = botonapretado.getId();
        MainActivity main = (MainActivity) getActivity();

        switch (idbotonapretado) {
            case R.id.btnVOlverdeentrenar:

                //tambien habria que frenar el beat

                AlertDialog.Builder mensaje;
                mensaje = new AlertDialog.Builder(getActivity());
                mensaje.setTitle("Salir del Entrenamiento");
                mensaje.setMessage("Si sales, la grabación se va a borrar");
                mensaje.setPositiveButton("Aceptar", escuchador);
                mensaje.setNegativeButton("Cancelar", escuchador);
                mensaje.create();
                mensaje.show();

                break;
            case R.id.btnColadeentrenar:

                main.PasaraFragCola();
                break;
            case R.id.btnrepetirbasedeentrenar:

                break;
            case R.id.favdeentrenar:
                break;


        }
    }

    public void Recorder(){
        if(grabacion == null){
            archivoSalida = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Grabacion.mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            grabacion.setOutputFile(archivoSalida);

            try{
                grabacion.prepare();
                grabacion.start();
            } catch (IOException e){
            }

            grabacion.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                @Override
                public void onError(MediaRecorder mr, int what, int extra) {
                    Toast.makeText(getActivity(), "error...", Toast.LENGTH_SHORT).show();

                }
            });

            btnGrabar.setImageResource(R.drawable.ic_icono_grabar_rojo);
            Toast.makeText(getActivity(), "Grabando...", Toast.LENGTH_SHORT).show();
        } else if(grabacion != null){


            grabacion.stop();

            grabacion.release();


            grabacion = null;
            btnGrabar.setImageResource(R.drawable.ic_icono_grabar_blanco);
            Toast.makeText(getActivity(), "Grabación finalizada", Toast.LENGTH_SHORT).show();
        }
    }
    public void reproducir() {

        try {
            mediaPlayer.setDataSource(archivoSalida);
            mediaPlayer.prepare();
        } catch (IOException e){
        }

        mediaPlayer.start();
        Toast.makeText(getActivity(), "Reproduciendo audio", Toast.LENGTH_SHORT).show();

    }
}