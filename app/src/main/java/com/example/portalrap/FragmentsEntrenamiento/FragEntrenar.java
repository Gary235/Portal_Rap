package com.example.portalrap.FragmentsEntrenamiento;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.portalrap.Clases.Base;
import com.example.portalrap.Clases.Palabras;
import com.example.portalrap.FragBases;
import com.example.portalrap.FragmentsInicio.FragIniciarSesion;
import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


public class FragEntrenar extends Fragment implements View.OnClickListener {

    ImageButton btnFav, btnPlay, btnRepetir, btnCola, btnVolver, btnGrabar;
    TextView txtArtista, txtBase, txtDuracion,txtConfirmar;
    ImageView fondoDifuminado;
    SeekBar barradebeat;
    ProgressBar cargadebeats;

    MediaRecorder grabacion = null;

    String archivoSalida = null,palabrarandom;
    CountDownTimer timer,timerinicial;
    long tiemporestanteDuracion = 300000,tiemporestanteInicial = 3500;

    public static FrameLayout holderparacola;
    FragmentManager adminFragment;
    FragmentTransaction transaccionFragment;
    Fragment fragdeCola;

    int ModoElegido = MainActivity.PosModo,FrecuenciaElegida = MainActivity.Frecuencia, aleatorio, index = 0;
    FirebaseFirestore db;
    Random generador = new Random();
    Boolean repetirverde = false, favblanco = false;

    ArrayList<Palabras> arrPalabras;
    ArrayList<MediaPlayer> arrMediaPlayer = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.entrenar, container, false);
        Setear(v);
        ListenersAdicionales();
        db = FirebaseFirestore.getInstance();


        if (MainActivity.PosModo != -1 && MainActivity.Segundos != -1 && MainActivity.Minutos != -1 && MainActivity.Frecuencia != -1) {
            //personalizado
            tiemporestanteDuracion = (MainActivity.Minutos * 60000) + (MainActivity.Segundos * 1000);
        } else {
            //predeterminado
            ModoElegido = 2;
            FrecuenciaElegida = 1;
        }
        if(FragBases.UserSelection.size() == 1){
            btnRepetir.setImageResource(R.drawable.ic_repetir_verde);
            repetirverde = true;
        }
        else {
            btnRepetir.setImageResource(R.drawable.ic_icono_repetir);
        }

        obtenerPalabraRandom();
        Log.d("Entrenamiento","Frecuencia Elegida: " + FrecuenciaElegida + "Frecuencia Seleccionada: " + MainActivity.Frecuencia);
        Log.d("Entrenamiento","Modo Elegido: " + ModoElegido + "Modo Seleccionado: " + MainActivity.PosModo);
        switch (FrecuenciaElegida) {
            case 0: FrecuenciaElegida = 2 * 1000;
                break;
            case 1: FrecuenciaElegida = 5 * 1000;
                break;
            case 2: FrecuenciaElegida = 10 * 1000;
                break;
            case 3: FrecuenciaElegida = 20 * 1000;
                break;
            case 4: FrecuenciaElegida = 30 * 1000;
                break;
        }

        descargarAudioDeEntrenamiento();

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
        txtConfirmar = v.findViewById(R.id.txtConfirmar);
        fondoDifuminado = v.findViewById(R.id.recdifuminado);
        barradebeat = v.findViewById(R.id.Barradeentrenar);
        cargadebeats = v.findViewById(R.id.cargainicial);

        btnVolver.setEnabled(false);
        btnRepetir.setEnabled(false);
        btnCola.setEnabled(false);
        btnFav.setEnabled(false);
        btnGrabar.setEnabled(false);
        btnPlay.setEnabled(false);
        barradebeat.setEnabled(false);
        txtConfirmar.setEnabled(false);

        holderparacola = v.findViewById(R.id.holderdecola);

        holderparacola.setVisibility(View.GONE);

        btnVolver.setOnClickListener(this);
        btnRepetir.setOnClickListener(this);
        btnCola.setOnClickListener(this);
        btnFav.setOnClickListener(this);

        aleatorio = (int) (Math.random() * 2) + 1;
    }

    public void ListenersAdicionales() {
        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recorder();
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pausayReproducir();
            }
        });
        txtConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empezarTimerInicial();
            }
        });

    }

    DialogInterface.OnClickListener escuchador = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == -1) {
                MainActivity main = (MainActivity) getActivity();
                main.PasaraFragmentHome();
            }
            else if (which == -2) {
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
                adminFragment = getFragmentManager();
                fragdeCola = new FragCola();
                transaccionFragment=adminFragment.beginTransaction();
                transaccionFragment.replace(R.id.holderdecola, fragdeCola);
                transaccionFragment.commit();

                holderparacola.setVisibility(View.VISIBLE);
                break;
            case R.id.btnrepetirbasedeentrenar:
                if(!repetirverde) {
                    btnRepetir.setImageResource(R.drawable.ic_repetir_verde);
                    repetirverde = true;
                }
                else {
                    btnRepetir.setImageResource(R.drawable.ic_icono_repetir);
                    repetirverde = false;
                }
                break;
            case R.id.favdeentrenar:
                if(FragBases.UserSelection.get(index).getFavoritos() != null)
                {
                    if(FragBases.UserSelection.get(index).getFavoritos()){
                        FragBases.UserSelection.get(index).setFavoritos(false);
                        btnFav.setImageResource(R.drawable.ic_icono_nofav_blanco);

                    }else{
                        FragBases.UserSelection.get(index).setFavoritos(true);
                        btnFav.setImageResource(R.drawable.ic_icono_fav_blanco);

                    }
                }
                actualizarFav(FragBases.UserSelection.get(index).getNombre(),FragBases.UserSelection.get(index).getArtista(),false,FragBases.UserSelection.get(index).getId(),FragBases.UserSelection.get(index).getUrl(),FragBases.UserSelection.get(index).getFavoritos());

                break;
        }
    }

    public void empezarTimerDuracion(){
        timer = new CountDownTimer(tiemporestanteDuracion,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tiemporestanteDuracion = millisUntilFinished;
                actualizarTimerDuracion();
            }

            @Override
            public void onFinish() {
                txtDuracion.setText("yey");
            }
        }.start();
    }
    public void actualizarTimerDuracion() {
        int minutos =(int) (tiemporestanteDuracion / 1000) / 60;
        int segundos =(int) (tiemporestanteDuracion / 1000) % 60;
        String txttiempores;
        Log.d("timer", "" + MainActivity.Segundos);
        Log.d("timer", "" + segundos);

        txttiempores = "" + minutos;
        txttiempores += ":";
        if(segundos < 10) {txttiempores += "0";}
        txttiempores += "" + segundos;

        txtDuracion.setText(txttiempores);
    }


    public void empezarTimerInicial() {
        timerinicial = new CountDownTimer(tiemporestanteInicial,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tiemporestanteInicial = millisUntilFinished;
            actualizarTimerInicial();
        }

        @Override
        public void onFinish() {
            actualizarTimerDuracion();
            empezarTimerDuracion();
            actualizarTimerFrecuencia();
            empezarTimerFrecuencia();
            empezarReproduccion();

            btnVolver.setEnabled(true);
            btnRepetir.setEnabled(true);
            btnCola.setEnabled(true);
            btnFav.setEnabled(true);
            btnGrabar.setEnabled(true);
            btnPlay.setEnabled(true);
            barradebeat.setEnabled(true);
            fondoDifuminado.setVisibility(View.GONE);
            txtConfirmar.setEnabled(false);

            txtBase.setText(FragBases.UserSelection.get(index).getNombre());
            txtArtista.setText(FragBases.UserSelection.get(index).getArtista());

            if(FragBases.UserSelection.get(index).getFavoritos())
            {
                //desfavear
                btnFav.setImageResource(R.drawable.ic_icono_fav_blanco);
            }
            else if(!FragBases.UserSelection.get(index).getFavoritos()) {
                //fav
                btnFav.setImageResource(R.drawable.ic_icono_nofav_blanco);
            }
        }
    }.start();

    }
    public void actualizarTimerInicial(){
        int segundos =(int) (tiemporestanteInicial / 1000) % 60;
        String txttiempores = "";

        txttiempores += "" + segundos;
        if(segundos < 1){txttiempores = "TIEMPO!!";}
        txtConfirmar.setText(txttiempores);
    }

    public void empezarTimerFrecuencia() {
        timerinicial = new CountDownTimer(tiemporestanteDuracion,FrecuenciaElegida) {
            @Override
            public void onTick(long millisUntilFinished) {
                tiemporestanteDuracion = millisUntilFinished;
                actualizarTimerFrecuencia();
            }

            @Override
            public void onFinish() {
            }
        }.start();

    }
    public void actualizarTimerFrecuencia(){

        switch (ModoElegido){
            case 0:
                ModoElegido = aleatorio;
                break;
            case 1:
                txtConfirmar.setVisibility(View.GONE);
                break;
            case 2:
                obtenerPalabraRandom();
                txtConfirmar.setTextSize(50);
                txtConfirmar.setText(palabrarandom.toUpperCase());
                break;
        }


    }

    public void obtenerPalabraRandom(){
     arrPalabras = new ArrayList<>();
     db.collection("Palabras")
             .get()
             .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     if (task.isSuccessful()) {
                         for (QueryDocumentSnapshot document : task.getResult()) {
                             Palabras pal = document.toObject(Palabras.class);
                             pal.setId(document.getId());
                             arrPalabras.add(pal);
                         }
                         if(arrPalabras != null) {
                             if (arrPalabras.isEmpty()) {
                                 Log.d("PalabraRandom", "vacio");
                             } else{
                                 palabrarandom = arrPalabras.get(generador.nextInt(arrPalabras.size())).getPalabra();
                                 Log.d("PalabraRandom", palabrarandom + "");
                             }
                         }
                     }
                 }
             });

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

    public void pausayReproducir(){

        if(!arrMediaPlayer.get(index).isPlaying()) {
            btnPlay.setImageResource(R.drawable.ic_icono_pausa_blanco);
            Toast toast1 = Toast.makeText(getActivity(), "Reproduciendo", Toast.LENGTH_SHORT);
            toast1.show();
            while (!arrMediaPlayer.get(index).isPlaying()) {
                arrMediaPlayer.get(index).start();
            }
        }
        else {
            Toast toast1 = Toast.makeText(getActivity(), "Pausa", Toast.LENGTH_SHORT);
            toast1.show();
            arrMediaPlayer.get(index).pause();
            btnPlay.setImageResource(R.drawable.ic_icono_play_blanco);
        }

    }


    public void empezarReproduccion() {

        while (!arrMediaPlayer.get(index).isPlaying()) {
            arrMediaPlayer.get(index).start();
        }
        btnPlay.setImageResource(R.drawable.ic_icono_pausa_blanco);

        /*arrMediaPlayer.get(index).setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.reset();
                btnPlay.setImageResource(R.drawable.ic_icono_play_blanco);
                index++;
                empezarReproduccion();
            }
        });*/
    }


    public void descargarAudioDeEntrenamiento()
    {
        final FirebaseStorage storage = FirebaseStorage.getInstance();

        for(int i = 0; i < FragBases.UserSelection.size(); i++)
        {
            final MediaPlayer mediaPlayer = new MediaPlayer();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://portal-rap-4b1fe.appspot.com/Beats/" + FragBases.UserSelection.get(i).getUrl());
            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    final String url = uri.toString();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                        }
                    });
                    // wait for media player to get prepare
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    Log.d("TAGERROR", "error: " + e.getMessage());
                    Toast toast1 = Toast.makeText(getActivity(), "Error de Red", Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
        });
        storageRef.getDownloadUrl().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAGERROR", "error: " + e.getMessage());
                Toast toast1 = Toast.makeText(getActivity(), "Error de Red", Toast.LENGTH_SHORT);
                toast1.show();
            }
        });
        arrMediaPlayer.add(mediaPlayer);
        }
        // termina de cargar los beats, habilita la palabra empezar y saca la progress bar en forma de circulo
        cargadebeats.setVisibility(View.GONE);
        txtConfirmar.setEnabled(true);
    }

    private void actualizarFav(String nombre,String artista,Boolean destacado, String id, String url, Boolean fav) {
        Map<String, Object> beat = (new Base(artista,nombre, url,destacado, id,fav)).toMap();

        db.collection("Beats")
                .document(id)
                .update(beat)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("CambiarFav", "Bien Ahi");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("CambiarFav", "Error al actualizar: " + e);

                    }
                });
    }

}