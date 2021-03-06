package com.example.portalrap.FragmentsEntrenamiento;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.portalrap.Clases.Objetos;
import com.example.portalrap.Clases.Palabras;
import com.example.portalrap.FragBases;
import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;


public class FragEntrenar extends Fragment implements View.OnClickListener {

    ImageButton btnFav, btnPlay, btnRepetir, btnCola, btnVolver, btnGrabar;
    TextView txtArtista, txtBase, txtDuracion, txtConfirmar;
    ImageView fondoDifuminado, fotoObjeto;
    SeekBar barradebeat;
    EditText input;
    MediaRecorder grabacion = null;
    MediaPlayer mediaPlayer = new MediaPlayer();
    MediaObserver observer = null;

    String path_file = "", name, carpeta = "/Portal Rap/", archivo = "default_name";
    public static File localfile;
    String palabrarandom;
    CountDownTimer timer = null, timerinicial;
    long tiemporestanteDuracion = 300000, tiemporestanteInicial = 3500;

    public static FrameLayout holderparacola;
    FragmentManager adminFragment;
    FragmentTransaction transaccionFragment;
    Fragment fragdeCola;

    int ModoElegido = MainActivity.PosModo, FrecuenciaElegida = MainActivity.Frecuencia, aleatorio, index = 0;
    FirebaseFirestore db;
    FirebaseUser user ;

    Random generador = new Random();
    Boolean repetirverde = false;

    ArrayList<Palabras> arrPalabras;
    ArrayList<Objetos> arrObjetos =  new ArrayList<>();
    File file;

    ProgressDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        db = FirebaseFirestore.getInstance();
        aleatorio = (int) (Math.random() * 2) + 1;

        MainActivity main = (MainActivity) getActivity();
        user = main.obtenerUsuario();

        if(ModoElegido == 1  || aleatorio == 1){
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Cargando Objetos");
            dialog.show();
            obtenerURLS();
        }

        descargarAudioDeEntrenamiento();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        
        MainActivity main = (MainActivity) getActivity();
        int alto = main.obtenerAlto();

        if (alto <= 1600) {
            v = inflater.inflate(R.layout.entrenar_pantalla_chica, container, false);
            SetearVariablesPantallaChica(v);
        } else {
            v = inflater.inflate(R.layout.entrenar, container, false);
            SetearVariables(v);
        }

        mediaPlayer = new MediaPlayer();
        observer = new MediaObserver();
        ListenersAdicionales();
        SetearEntrenamiento();

        obtenerPalabraRandom();

        return v;
    }



    public void SetearVariables(View v) {

        btnGrabar = v.findViewById(R.id.btnGrabardeentrenar);
        btnVolver = v.findViewById(R.id.btnVOlverdeentrenar);
        btnCola = v.findViewById(R.id.btnColadeentrenar);
        btnFav = v.findViewById(R.id.favdeentrenar);
        btnPlay = v.findViewById(R.id.Playdeentrenar);
        btnRepetir = v.findViewById(R.id.btnrepetirbasedeentrenar);
        fotoObjeto = v.findViewById(R.id.imagenObjeto);

        txtArtista = v.findViewById(R.id.nombreArtistaSeleccionadodeentrenar);
        txtBase = v.findViewById(R.id.nombreBaseSeleccionadadeentrenar);
        txtDuracion = v.findViewById(R.id.TiempoFreedeentrenar);
        txtConfirmar = v.findViewById(R.id.txtConfirmar);
        fondoDifuminado = v.findViewById(R.id.recdifuminado);
        barradebeat = v.findViewById(R.id.Barradeentrenar);

        btnVolver.setEnabled(false);
        btnRepetir.setEnabled(false);
        btnCola.setEnabled(false);
        btnFav.setEnabled(false);
        btnGrabar.setEnabled(false);
        btnPlay.setEnabled(false);
        barradebeat.setEnabled(false);

        holderparacola = v.findViewById(R.id.holderdecola);

        holderparacola.setVisibility(View.GONE);

        btnVolver.setOnClickListener(this);
        btnRepetir.setOnClickListener(this);
        btnCola.setOnClickListener(this);
        btnFav.setOnClickListener(this);

    }
    public void SetearVariablesPantallaChica(View v) {

        btnGrabar = v.findViewById(R.id.btnGrabardeentrenarpantallachica);
        btnVolver = v.findViewById(R.id.btnVOlverdeentrenarpantallachica);
        btnCola = v.findViewById(R.id.btnColadeentrenarpantallachica);
        btnFav = v.findViewById(R.id.favdeentrenarpantallachica);
        btnPlay = v.findViewById(R.id.Playdeentrenarpantallachica);
        btnRepetir = v.findViewById(R.id.btnrepetirbasedeentrenarpantallachica);
        fotoObjeto = v.findViewById(R.id.imagenObjetopantallachica);

        txtArtista = v.findViewById(R.id.nombreArtistaSeleccionadodeentrenarpantallachica);
        txtBase = v.findViewById(R.id.nombreBaseSeleccionadadeentrenarpantallachica);
        txtDuracion = v.findViewById(R.id.TiempoFreedeentrenarpantallachica);
        txtConfirmar = v.findViewById(R.id.txtConfirmarpantallachica);
        fondoDifuminado = v.findViewById(R.id.recdifuminadopantallachica);
        barradebeat = v.findViewById(R.id.Barradeentrenarpantallachica);

        btnVolver.setEnabled(false);
        btnRepetir.setEnabled(false);
        btnCola.setEnabled(false);
        btnFav.setEnabled(false);
        btnGrabar.setEnabled(false);
        btnPlay.setEnabled(false);
        barradebeat.setEnabled(false);
        //fotoObjeto.setVisibility(View.GONE);

        holderparacola = v.findViewById(R.id.holderdecolapantallachica);

        holderparacola.setVisibility(View.GONE);

        btnVolver.setOnClickListener(this);
        btnRepetir.setOnClickListener(this);
        btnCola.setOnClickListener(this);
        btnFav.setOnClickListener(this);

        aleatorio = (int) (Math.random() * 2) + 1;
    }

    public void ListenersAdicionales() {
        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Grabar();
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
        /* mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("Reproduccion", "termino de reproducir");
                observer.stop();
                barradebeat.setProgress(mediaPlayer.getCurrentPosition());
                btnPlay.setImageResource(R.drawable.ic_icono_play_blanco);
                index ++;
                Log.d("Reproduccion", "setea barra y va a descargar audio");
                descargarAudioDeEntrenamiento();
                //descargarXBytes();
                Log.d("Reproduccion", "termino de descargar audio y va a runMedia");
                runMedia();
                Log.d("Reproduccion", "ejecuto runMedia");
            }
        });*/
    }
    public void SetearEntrenamiento(){
        if (MainActivity.PosModo != -1 && MainActivity.Segundos != -1 && MainActivity.Minutos != -1 && MainActivity.Frecuencia != -1) {
            //personalizado
            tiemporestanteDuracion = (MainActivity.Minutos * 60000) + (MainActivity.Segundos * 1000);
        } else {
            //predeterminado
            ModoElegido = 2;
            FrecuenciaElegida = 1;
        }

        if (FragBases.UserSelection.size() == 1) {
            btnRepetir.setImageResource(R.drawable.ic_repetir_verde);
            repetirverde = true;
            mediaPlayer.setLooping(true);
            btnRepetir.setEnabled(false);
        } else {
            btnRepetir.setImageResource(R.drawable.ic_icono_repetir);
        }


        Log.d("Entrenamiento", "Frecuencia Elegida: " + FrecuenciaElegida + "Frecuencia Seleccionada: " + MainActivity.Frecuencia);
        Log.d("Entrenamiento", "Modo Elegido: " + ModoElegido + "Modo Seleccionado: " + MainActivity.PosModo);
        switch (FrecuenciaElegida) {
            case 0:
                FrecuenciaElegida = 2 * 1000;
                break;
            case 1:
                FrecuenciaElegida = 5 * 1000;
                break;
            case 2:
                FrecuenciaElegida = 10 * 1000;
                break;
            case 3:
                FrecuenciaElegida = 20 * 1000;
                break;
            case 4:
                FrecuenciaElegida = 30 * 1000;
                break;
        }

    }
    @Override
    public void onClick(View v) {
        ImageButton botonapretado;
        botonapretado = (ImageButton) v;
        int idbotonapretado = botonapretado.getId();
        MainActivity main = (MainActivity) getActivity();

        switch (idbotonapretado) {
            case R.id.btnVOlverdeentrenar:
            case R.id.btnVOlverdeentrenarpantallachica:
                //tambien habria que frenar el beat

                AlertDialog.Builder mensaje;
                mensaje = new AlertDialog.Builder(getActivity());
                mensaje.setTitle("Salir del Entrenamiento");
                mensaje.setMessage("Si sales, la grabación se va a borrar");
                mensaje.setPositiveButton("Aceptar", escuchadordesalir);
                mensaje.setNegativeButton("Cancelar", escuchadordesalir);
                mensaje.create();
                mensaje.show();

                break;

            case R.id.btnColadeentrenar:
                adminFragment = getFragmentManager();
                fragdeCola = new FragCola();
                transaccionFragment = adminFragment.beginTransaction();
                transaccionFragment.replace(R.id.holderdecola, fragdeCola);
                transaccionFragment.commit();

                holderparacola.setVisibility(View.VISIBLE);
                break;

            case R.id.btnrepetirbasedeentrenar:
            case R.id.btnrepetirbasedeentrenarpantallachica:
                if (!repetirverde) {
                    btnRepetir.setImageResource(R.drawable.ic_repetir_verde);
                    repetirverde = true;
                    mediaPlayer.setLooping(true);
                } else {
                    btnRepetir.setImageResource(R.drawable.ic_icono_repetir);
                    repetirverde = false;
                    mediaPlayer.setLooping(false);
                }
                break;

            case R.id.favdeentrenar:
            case R.id.favdeentrenarpantallachica:
                if (FragBases.UserSelection.get(index).getFavoritos() != null && user != null ) {
                    if (FragBases.UserSelection.get(index).getFavoritos()) {
                        FragBases.UserSelection.get(index).setFavoritos(false);
                        btnFav.setImageResource(R.drawable.ic_icono_nofav_blanco);
                        main.eliminarFav(FragBases.UserSelection.get(index).getId());
                    } else {
                        FragBases.UserSelection.get(index).setFavoritos(true);
                        btnFav.setImageResource(R.drawable.ic_icono_fav_blanco);
                        main.agregarFav(FragBases.UserSelection.get(index).getNombre(), FragBases.UserSelection.get(index).getArtista(), false, FragBases.UserSelection.get(index).getId(), FragBases.UserSelection.get(index).getUrl(), FragBases.UserSelection.get(index).getFavoritos(), FragBases.UserSelection.get(index).getDuracion());
                    }
                }
                break;

            case R.id.btnColadeentrenarpantallachica:
                adminFragment = getFragmentManager();
                fragdeCola = new FragCola();
                transaccionFragment = adminFragment.beginTransaction();
                transaccionFragment.replace(R.id.holderdecolapantallachica, fragdeCola);
                transaccionFragment.commit();

                holderparacola.setVisibility(View.VISIBLE);
                break;


        }
    }

    public void empezarTimerInicial() {
        timerinicial = new CountDownTimer(tiemporestanteInicial, 1000) {
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
                runMedia();

                btnVolver.setEnabled(true);

                if (FragBases.UserSelection.size() > 1)
                    btnRepetir.setEnabled(true);

                btnCola.setEnabled(true);
                btnFav.setEnabled(true);
                btnGrabar.setEnabled(true);
                btnPlay.setEnabled(true);
                barradebeat.setEnabled(true);
                fondoDifuminado.setVisibility(View.GONE);
                txtConfirmar.setEnabled(false);

            }
        }.start();

    }

    public void actualizarTimerInicial() {
        int segundos = (int) (tiemporestanteInicial / 1000) % 60;
        String txttiempores = "";

        txttiempores += "" + segundos;
        if (segundos < 1) {
            txttiempores = "TIEMPO!!";
        }
        txtConfirmar.setText(txttiempores);
    }

    public void empezarTimerDuracion() {
        timer = new CountDownTimer(tiemporestanteDuracion, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tiemporestanteDuracion = millisUntilFinished;
                actualizarTimerDuracion();
            }

            @Override
            public void onFinish() {
                timer.cancel();
                mediaPlayer.stop();
                observer.stop();

                if(grabacion != null){


                    AlertDialog.Builder mensaje;
                    mensaje = new AlertDialog.Builder(getActivity());
                    mensaje.setTitle("Nombrar Grabacion");
                    mensaje.setMessage(" \n No la nombres igual a otra grabacion porque se va a sobreescribir \n \n");
                    input = new EditText(getActivity());
                    input.setTag("timer");
                    mensaje.setView(input);
                    mensaje.setPositiveButton("Guardar", escuchadordeguardargrabacion);
                    mensaje.setNegativeButton("Volver", escuchadordeguardargrabacion);
                    mensaje.create();
                    mensaje.show();
                }
                else {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.PasaraFragUsuario();
                    MainActivity.bottom.setSelectedItemId(R.id.item3);

                }

            }

        }.start();
    }

    public void actualizarTimerDuracion() {
        int minutos = (int) (tiemporestanteDuracion / 1000) / 60;
        int segundos = (int) (tiemporestanteDuracion / 1000) % 60;
        String txttiempores;
        Log.d("timer", "" + MainActivity.Segundos);
        Log.d("timer", "" + segundos);

        txttiempores = "" + minutos;
        txttiempores += ":";
        if (segundos < 10) {
            txttiempores += "0";
        }
        txttiempores += "" + segundos;

        txtDuracion.setText(txttiempores);
    }

    public void empezarTimerFrecuencia() {
        timerinicial = new CountDownTimer(tiemporestanteDuracion, FrecuenciaElegida) {
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

    public void actualizarTimerFrecuencia() {

        switch (ModoElegido) {
            case 0:
                ModoElegido = aleatorio;
                break;
            case 1:
                txtConfirmar.setVisibility(View.GONE);
                fotoObjeto.setVisibility(View.VISIBLE);
                descargarFotoObjeto();
                break;
            case 2:
                fotoObjeto.setVisibility(View.GONE);
                obtenerPalabraRandom();
                txtConfirmar.setTextSize(50);
                if (palabrarandom != null) txtConfirmar.setText(palabrarandom.toUpperCase());
                break;
        }
    }


    public void SetearInfodeBase(){
        txtBase.setText(FragBases.UserSelection.get(index).getNombre());
        txtArtista.setText(FragBases.UserSelection.get(index).getArtista());
        if (FragBases.UserSelection.get(index).getFavoritos() && user != null) {
            btnFav.setImageResource(R.drawable.ic_icono_fav_blanco);
        } else if (!FragBases.UserSelection.get(index).getFavoritos() && user != null) {
            btnFav.setImageResource(R.drawable.ic_icono_nofav_blanco);
        }

        barradebeat.setMax(mediaPlayer.getDuration());

        observer = new MediaObserver();

    }

    public void runMedia() {
        SetearInfodeBase();

        while (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }

        btnPlay.setImageResource(R.drawable.ic_icono_pausa_blanco);

        new Thread(observer).start();
    }

    public void descargarAudioDeEntrenamiento() {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        //mediaPlayer = new MediaPlayer();
        mediaPlayer.setWakeMode(getActivity(), PowerManager.FULL_WAKE_LOCK);
        mediaPlayer.reset();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://portal-rap-4b1fe.appspot.com/Beats/" + FragBases.UserSelection.get(index).getUrl());
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    final String url = uri.toString();
                    Log.d("Reproduccion", "Url: " + url);
                    Log.d("Reproduccion", "Index: " + index);

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
    }

    public void Grabar() {
        if (grabacion == null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();

            timer.cancel();

            AlertDialog.Builder mensaje;
            mensaje = new AlertDialog.Builder(getActivity());
            mensaje.setTitle("Empezar Grabacion");
            mensaje.setPositiveButton("Empezar", escuchadordeempezargrabacion);
            mensaje.setNegativeButton("Cancelar", escuchadordeempezargrabacion);
            mensaje.create();
            mensaje.show();
        } else {
            btnGrabar.setImageResource(R.drawable.ic_icono_grabar_blanco);

            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();

            timer.cancel();

            try {
                grabacion.stop();
            } catch (RuntimeException e) {
                Log.d("Grabacion", "grabacion: " + grabacion);
            }
            grabacion.release();

            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlay.setImageResource(R.drawable.ic_icono_play_blanco);
            }

            AlertDialog.Builder mensaje;
            mensaje = new AlertDialog.Builder(getActivity());
            mensaje.setTitle("Nombrar Grabacion");
            mensaje.setMessage(" \n No la nombres igual a otra grabacion porque se va a sobreescribir \n \n");
            input = new EditText(getActivity());
            input.setTag(null);
            mensaje.setView(input);
            mensaje.setPositiveButton("Guardar", escuchadordeguardargrabacion);
            mensaje.setNegativeButton("Volver", escuchadordeguardargrabacion);
            mensaje.create();
            mensaje.show();

        }

    }

    public void pausayReproducir() {

        if (!mediaPlayer.isPlaying()) {
            btnPlay.setImageResource(R.drawable.ic_icono_pausa_blanco);
            //Toast toast1 = Toast.makeText(getActivity(), "Reproduciendo", Toast.LENGTH_SHORT);
            //toast1.show();
            while (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        } else {
            //Toast toast1 = Toast.makeText(getActivity(), "Pausa", Toast.LENGTH_SHORT);
            //toast1.show();
            mediaPlayer.pause();
            btnPlay.setImageResource(R.drawable.ic_icono_play_blanco);
        }

    }

    public void guardarGrabacion() {
        MainActivity main = (MainActivity) getActivity();

        //cambiar nombre del archivo
        File currentFile = new File(localfile, name);
        File newFile = new File(localfile, input.getText().toString().trim() + ".mp3");

        if (NombrarFile(currentFile, newFile))
            Log.i("Tag", "Success");
        else
            Log.i("Tag", "Fail");

        if (user != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();

            StorageReference reference = storage.getReference();
            Uri fromFile = Uri.fromFile(newFile);
            StorageReference ref = reference.child("Grabaciones/" + user.getUid() + "/" + fromFile.getLastPathSegment());

            ref.putFile(fromFile)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });

            HashMap<String, Object> mapGrab = new HashMap<>();
            mapGrab.put("Nombre", input.getText().toString().trim());
            mapGrab.put("Favoritos", false);
            mapGrab.put("Url", input.getText().toString().trim() + ".mp3");

            firestore.collection("Usuarios").document(user.getUid()).collection("Grabaciones").add(mapGrab);
            grabacion = null;

        }

    }

    private void obtenerURLS(){
        db.collection("Objetos")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        assert snapshots != null;
                        for (DocumentSnapshot document : snapshots) {
                            Objetos objetos = document.toObject(Objetos.class);
                            assert objetos != null;
                            objetos.setId(document.getId());
                            arrObjetos.add(objetos);
                        }

                        dialog.dismiss();
                    }
                });

    }

    private boolean NombrarFile(File from, File to) {
        return from.getParentFile().exists() && from.exists() && from.renameTo(to);
    }

    public void obtenerPalabraRandom() {
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
                            if (arrPalabras != null) {
                                if (arrPalabras.isEmpty()) {
                                    Log.d("PalabraRandom", "vacio");
                                } else {
                                    palabrarandom = arrPalabras.get(generador.nextInt(arrPalabras.size())).getPalabra();
                                    Log.d("PalabraRandom", palabrarandom + "");
                                }
                            }
                        }
                    }
                });

    }

    public class MediaObserver implements Runnable {
        private AtomicBoolean stop = new AtomicBoolean(false);

        public void stop() {
            stop.set(true);
        }

        @Override
        public void run() {
            while (!stop.get()) {

                    barradebeat.setProgress(mediaPlayer.getCurrentPosition());

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }


    }

    public void descargarFotoObjeto() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        Random randomGenerator = new Random();
        int i = randomGenerator.nextInt(arrObjetos.size());
        Objetos objeto = arrObjetos.get(i);

        storageRef.child("Imagenes/" + objeto.getObjeto()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                Log.d("Foto", "success");
                Log.d("Foto", "uri: " + uri);

                Picasso.get()
                        .load(uri.toString())
                        .fit()
                        .into(fotoObjeto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.d("Foto", "fail");
            }
        });


        arrObjetos.remove(i);
    }

    DialogInterface.OnClickListener escuchadordeempezargrabacion = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            if (which == -1) {
                mediaPlayer.start();
                if(timer  != null){
                    timer.cancel();
                    empezarTimerDuracion();
                }

                btnGrabar.setImageResource(R.drawable.ic_icono_grabar_rojo);
                grabacion = new MediaRecorder();
                grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
                grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

                path_file = Environment.getExternalStorageDirectory() + carpeta;
                localfile = new File(path_file);

                if (!localfile.exists()) {
                    localfile.mkdir();
                }

                name = archivo + ".mp3";
                file = new File(localfile, name);

                grabacion.setOutputFile(file.getAbsolutePath());

                try {
                    grabacion.prepare();
                    grabacion.start();
                } catch (IOException e) {
                }
                grabacion.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                    @Override
                    public void onError(MediaRecorder mr, int what, int extra) {
                        Toast.makeText(getActivity(), "error...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if (which == -2) {
                mediaPlayer.start();
                if(timer  != null){
                    timer.cancel();
                    empezarTimerDuracion();
                }
                dialog.cancel();
            }
        }
    };

    DialogInterface.OnClickListener escuchadordesalir = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            if (which == -1) {
                mediaPlayer.stop();
                grabacion = null;
                MainActivity main = (MainActivity) getActivity();

                if(MainActivity.PosModo == -1)
                {
                    MainActivity.Frecuencia = -1;
                    MainActivity.PosModo = -1;
                    MainActivity.Minutos = -1;
                    MainActivity.Segundos = -1;
                    main.PasaraFragBases("no");
                    MainActivity.bottom.setSelectedItemId(R.id.item2);


                }else {
                    MainActivity.Frecuencia = -1;
                    MainActivity.PosModo = -1;
                    MainActivity.Minutos = -1;
                    MainActivity.Segundos = -1;
                    main.PasaraFragmentHome();
                    MainActivity.bottom.setSelectedItemId(R.id.item1);

                }

            }
            else if (which == -2) {
                dialog.cancel();
            }
        }
    };

    DialogInterface.OnClickListener escuchadordeguardargrabacion = new DialogInterface.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(DialogInterface dialog, int which) {

            if (which == -1) {
                if (input.getText().toString().trim().length() == 0) {
                    Toast.makeText(getActivity(), "Nombre de grabacion invalido", Toast.LENGTH_SHORT).show();
                } else {
                    mediaPlayer.start();
                    if(timer  != null){
                        timer.cancel();
                        empezarTimerDuracion();
                    }

                    guardarGrabacion();
                    if(input.getTag() != null){
                        MainActivity main = (MainActivity) getActivity();
                        main.PasaraFragUsuario();
                        MainActivity.bottom.setSelectedItemId(R.id.item3);

                    }

                }
            }
            else {
                mediaPlayer.start();
                if(timer  != null){
                    timer.cancel();
                    empezarTimerDuracion();
                }

                dialog.cancel();
            }
        }
    };


}