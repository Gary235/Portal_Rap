package com.example.portalrap;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.portalrap.Adaptadores.adaptadorBases;
import com.example.portalrap.Clases.Base;
import com.example.portalrap.Clases.Palabras;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class FragHome extends Fragment implements View.OnClickListener {

    //Button btnEntrenar;
    ImageButton btnEntrenar;
    ImageView fondo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_2, container, false);


        fondo = v.findViewById(R.id.fondo);
        btnEntrenar = v.findViewById(R.id.btnentrenar);
        btnEntrenar.setOnClickListener(this);

        //if (!MainActivity.TodosPermisos) btnEntrenar.setEnabled(false);

        int aleatorio = (int) (Math.random() * 3) ;

        switch (aleatorio){
            case 0:
                fondo.setImageResource(R.drawable.ic_fondo_home_1);
                break;
            case 1:
                fondo.setImageResource(R.drawable.ic_fondo_home_2);
                break;
            case 2:
                fondo.setImageResource(R.drawable.ic_fondo_home_3);
                break;

        }
        return v;
    }

    @Override
    public void onClick(View v) {

        MainActivity main = (MainActivity) getActivity();
        main.PasaraFragmentModo();
    }



}