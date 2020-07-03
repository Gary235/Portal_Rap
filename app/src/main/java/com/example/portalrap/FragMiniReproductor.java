package com.example.portalrap;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class FragMiniReproductor extends Fragment implements View.OnClickListener {

    ImageButton btnplay;
    TextView txtnombre;
    public static MediaPlayer mediaplayer;
    String Nombre, Url;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_mini_reproductor, container, false);

        Nombre = getArguments().getString("Nombre");
        Url = getArguments().getString("Url");
        mediaplayer = new MediaPlayer();

        btnplay = v.findViewById(R.id.btnPlaydeholder);
        txtnombre = v.findViewById(R.id.txtdeholder);
        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        txtnombre.setText(Nombre);


        fetchAudioUrlFromFirebase();




        btnplay.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {

        ImageButton botonapretado;
        botonapretado = (ImageButton) v;
        int idbotonapretado = botonapretado.getId();


        if (idbotonapretado == R.id.btnPlaydeholder) {
            Log.d("TAGERROR", "error: " );

            if(!mediaplayer.isPlaying())
            {
                Log.d("TAGERROR", "error: " );
                btnplay.setImageResource(R.drawable.ic_icono_pausa);
                Toast toast1 = Toast.makeText(getActivity(), "Reproduciendo", Toast.LENGTH_SHORT);
                toast1.show();
                    while (!mediaplayer.isPlaying()) {
                    mediaplayer.start();
                }
            }
            else {
                btnplay.setImageResource(R.drawable.ic_icono_pausa);
                Toast toast1 = Toast.makeText(getActivity(), "Pausa", Toast.LENGTH_SHORT);
                toast1.show();
                mediaplayer.pause();
                btnplay.setImageResource(R.drawable.ic_icono_play);
            }
        }
    }

    private void fetchAudioUrlFromFirebase() {

        final FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://portal-rap-4b1fe.appspot.com/Beats/" + Url);
        Log.d("TAGERROR", "error:3 " );

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    // Download url of file
                    Log.d("TAGERROR", "error: " );
                    Toast toast1 = Toast.makeText(getActivity(), "Cargando Base", Toast.LENGTH_SHORT);
                    toast1.show();

                    final String url = uri.toString();
                    mediaplayer.setDataSource(url);
                    mediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                        }
                    });
                    // wait for media player to get prepare
                    mediaplayer.prepareAsync();

                } catch (IOException e) {
                    Log.d("TAGERROR", "error: " + e.getMessage());
                }

            }

        });
        storageRef.getDownloadUrl().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAGERROR", "error: " + e.getMessage());

            }
        });

    }

}