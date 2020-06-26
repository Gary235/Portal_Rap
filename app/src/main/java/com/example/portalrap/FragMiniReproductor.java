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
    int Cont=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_mini_reproductor, container, false);

        Nombre = getArguments().getString("Nombre");
        Url = getArguments().getString("Url");

        btnplay = v.findViewById(R.id.btnPlaydeholder);
        txtnombre = v.findViewById(R.id.txtdeholder);
        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setMax(10);
        txtnombre.setText(Nombre);

        btnplay.setOnClickListener(this);
        mediaplayer = new MediaPlayer();
        return v;
    }

    @Override
    public void onClick(View v) {

        ImageButton botonapretado;
        botonapretado = (ImageButton) v;
        int idbotonapretado = botonapretado.getId();


        if (idbotonapretado == R.id.btnPlaydeholder) {

            if(!mediaplayer.isPlaying())
            {
                fetchAudioUrlFromFirebase();

            }
            else {
                mediaplayer.pause();
                mediaplayer.reset();
                btnplay.setImageResource(R.drawable.ic_icono_play);


            }
        }
    }

    private void fetchAudioUrlFromFirebase() {
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://portal-rap-4b1fe.appspot.com/Beats/" + Url);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    // Download url of file
                    //new MyAsyncTask().execute(15);
                    @SuppressLint("WrongConstant") Toast toast1 = Toast.makeText(getActivity(), "Cargando...", 1);
                    toast1.show();

                    final String url = uri.toString();
                    mediaplayer.setDataSource(url);
                    mediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {

                            btnplay.setImageResource(R.drawable.ic_icono_pausa);
                            mp.start();

                        }
                    });
                    // wait for media player to get prepare
                    mediaplayer.prepareAsync();

                } catch (IOException e) {
                    Log.d("TAGERROR", "error: " + e.getMessage());
                }
            }
        });
    }

    class MyAsyncTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            for (int counter = 0; counter <= params[0]; counter++) {
                try {
                    Thread.sleep(1000);
                    publishProgress(counter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Tarea completa!. =)";
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }
}


