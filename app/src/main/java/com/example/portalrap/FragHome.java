package com.example.portalrap;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    Button btnEntrenar;
    FirebaseFirestore db;
    ArrayList<Palabras> arrPalabras = new ArrayList<>();
    Random generador = new Random();
    private DatabaseReference mDatabase;// ...

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home, container, false);

        btnEntrenar = v.findViewById(R.id.btnentrenar);
        btnEntrenar.setOnClickListener(this);
        db = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        obtenerListaPalabras();


        if (arrPalabras.isEmpty()) {
            Log.d("PalabraRandom", "no");

        } else {
            String palabrarandom = arrPalabras.get(generador.nextInt(arrPalabras.size())).getPalabra();
            Log.d("PalabraRandom", palabrarandom + "");

        }


        return v;
    }

    @Override
    public void onClick(View v) {

        MainActivity main = (MainActivity) getActivity();
        main.PasaraFragmentModo();
    }


    private void obtenerListaPalabras() {

        db.collection("Palabras")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Palabras pal = document.toObject(Palabras.class);
                                assert pal != null;
                                pal.setId(document.getId());
                                arrPalabras.add(pal);
                            }

                        } else {
                        }
                    }
                });
    }



}