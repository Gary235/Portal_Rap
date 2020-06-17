package com.example.portalrap;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class FragMiniReproductor extends Fragment implements View.OnClickListener{

    ImageButton btnplay;
    TextView txtnombre;
    Boolean f = false;
    MediaPlayer mediaplayer = new MediaPlayer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_mini_reproductor, container, false);

        btnplay = v.findViewById(R.id.btnPlaydeholder);
        txtnombre = v.findViewById(R.id.txtdeholder);

        btnplay.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        ImageButton botonapretado;
        botonapretado = (ImageButton)v;
        int idbotonapretado = botonapretado.getId();
        MainActivity main=(MainActivity) getActivity();

        if(idbotonapretado == R.id.btnPlaydeholder) {
            mediaplayer = MediaPlayer.create(getActivity(), R.raw.beat);
            txtnombre.setText("beat");

            if(!f){
                btnplay.setImageResource(R.drawable.ic_icono_pausa);
                mediaplayer.start();
                f = true;
            }
            else {
                btnplay.setImageResource(R.drawable.ic_icono_play);
                Log.d("Entrenamiento: ","Predeterminado");

                mediaplayer.pause();

                f = false;
            }


        }

    }
}