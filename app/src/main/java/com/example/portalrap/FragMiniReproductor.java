package com.example.portalrap;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.portalrap.FragmentsUsuario.FragFavoritos;
import com.example.portalrap.FragmentsUsuario.FragUsuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class FragMiniReproductor extends Fragment {

    ImageButton btnplay,btnEsconder;
    TextView txtnombre,txtArtista, txtDuracion;
    public static MediaPlayer mediaplayer;
    String Nombre, Url, Artista, Duracion;
    ProgressBar progressBar;
    FirebaseUser user;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_mini_reproductor, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();
        user = mainActivity.obtenerUsuario();
        Nombre = getArguments().getString("Nombre");
        Url = getArguments().getString("Url");
        Artista = getArguments().getString("Artista");
        Duracion = getArguments().getString("Duracion");

        mediaplayer = new MediaPlayer();
        btnEsconder = v.findViewById(R.id.flechitabajodemini);
        btnplay = v.findViewById(R.id.btnPlaydeholder);
        txtDuracion = v.findViewById(R.id.txtdeholderDuracion);
        txtnombre = v.findViewById(R.id.txtdeholderBase);
        txtArtista = v.findViewById(R.id.txtdeholderArtista);
        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setMax(10);

        fetchAudioUrlFromFirebase();

        txtnombre.setText(Nombre);
        txtArtista.setText(Artista);
        txtDuracion.setText(Duracion);



        btnplay.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View v) {


                if(!mediaplayer.isPlaying())
                {
                    while (!mediaplayer.isPlaying()) {

                        mediaplayer.start();
                    }
                    btnplay.setImageResource(R.drawable.ic_icono_pausa);
                    Toast toast1 = Toast.makeText(getActivity(), "Reproduciendo", Toast.LENGTH_SHORT);
                    toast1.show();


                }
                else {
                    Toast toast1 = Toast.makeText(getActivity(), "Pausa", Toast.LENGTH_SHORT);
                    toast1.show();
                    mediaplayer.pause();
                    btnplay.setImageResource(R.drawable.ic_icono_play);
                }

            }
        });


        btnEsconder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FragUsuario.holder != null) {
                    if (FragUsuario.holder.getVisibility() == View.VISIBLE){
                        FragUsuario.holder.setVisibility(View.GONE);
                    }}

                if(FragFavoritos.holder != null) {
                    if (FragFavoritos.holder.getVisibility() == View.VISIBLE){
                        FragFavoritos.holder.setVisibility(View.GONE);
                    }}

                if(FragBases.holder != null){
                    if (FragBases.holder.getVisibility() == View.VISIBLE){
                        FragBases.holder.setVisibility(View.GONE);

                        if(FragBases.desdedur.equals("si")) {
                            FragBases.listabases.setPadding(0, 0, 0, 300);

                        }
                        else
                            FragBases.listabases.setPadding(0,0, 0, 0);

                    } }

                mediaplayer.stop();
            }
        });

        return v;
    }




    private void fetchAudioUrlFromFirebase() {

        final FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = null;
        mediaplayer = new MediaPlayer();
        if(Duracion.equals("")){
            storageRef = storage.getReferenceFromUrl("gs://portal-rap-4b1fe.appspot.com/Grabaciones/" + user.getUid() + "/" + Url);
            //gs://portal-rap-4b1fe.appspot.com/Grabaciones/QN0UtiL1VMQZ4MuD7pcAiBOTp7i1/pruebaDeGrabacion.mp3
        }   else {
            storageRef = storage.getReferenceFromUrl("gs://portal-rap-4b1fe.appspot.com/Beats/" + Url);
        }

        Log.d("TAGERROR", "error:3 " );

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    // Download url of file
                    Log.d("TAGERROR", "error: " );

                    //Toast toast1 = Toast.makeText(getActivity(), "Cargando Base", Toast.LENGTH_SHORT);
                    //toast1.show();

                    final String url = uri.toString();
                    mediaplayer.setDataSource(url);
                    mediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                        }
                    });
                    mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                           // btnplay.setImageResource(R.drawable.ic_icono_play);

                        }
                    });
                    // wait for media player to get prepare
                    mediaplayer.prepareAsync();

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
        storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }


}