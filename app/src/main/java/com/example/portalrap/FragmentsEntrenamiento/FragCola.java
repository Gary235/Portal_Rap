package com.example.portalrap.FragmentsEntrenamiento;

import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.portalrap.Adaptadores.adaptadorBases;
import com.example.portalrap.Adaptadores.adaptadorDeColaSiguiente;
import com.example.portalrap.Clases.Base;
import com.example.portalrap.FragBases;
import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;


public class FragCola extends Fragment{

    TextView txtTituloSonando, txtArtistaSonando;
    ImageButton btnVolver, btnFav;
    ListView listaSiguiente;
    ArrayList<Base> arrBasesSiguiente = new ArrayList<>();
    Base baseSonando = new Base();
    adaptadorDeColaSiguiente adaptador;
    int index = 0;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_cola, container, false);
        db = FirebaseFirestore.getInstance();


        Setear(v);
        CargarSonando();
        CargarArray();
        ListenersAdicionales();


        return v;
    }

    public void Setear(View v){
        btnFav = v.findViewById(R.id.btnFavdeCola);
        btnVolver = v.findViewById(R.id.btnVOlverdecola);
        listaSiguiente = v.findViewById(R.id.listaSiguiente);
        txtArtistaSonando = v.findViewById(R.id.txtArtistaBeatCola);
        txtTituloSonando = v.findViewById(R.id.nombreBeatCola);
        adaptador = new adaptadorDeColaSiguiente(arrBasesSiguiente,getActivity());
        listaSiguiente.setAdapter(adaptador);



    }
    public void ListenersAdicionales(){
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragEntrenar.holderparacola.setVisibility(View.GONE);
            }
        });
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(baseSonando.getFavoritos()!= null)
                {
                    if(baseSonando.getFavoritos()){
                        baseSonando.setFavoritos(false);
                        btnFav.setImageResource(R.drawable.ic_icono_nofav_blanco);

                    }else{
                        baseSonando.setFavoritos(true);
                        btnFav.setImageResource(R.drawable.ic_icono_fav_blanco);

                    }
                }
                actualizarFav(baseSonando.getNombre(),baseSonando.getArtista(),false,baseSonando.getId(),baseSonando.getUrl(),baseSonando.getFavoritos());
            }
        });

    }
    public void CargarSonando() {
        baseSonando = FragBases.UserSelection.get(index);
        txtTituloSonando.setText(baseSonando.getNombre());
        txtArtistaSonando.setText(baseSonando.getArtista());

        if(baseSonando.getFavoritos())
        {
            //desfavear
            btnFav.setImageResource(R.drawable.ic_icono_fav_blanco);
        }
        else if(!baseSonando.getFavoritos()) {
            //fav
            btnFav.setImageResource(R.drawable.ic_icono_nofav_blanco);

        }


    }
    public void CargarArray() {
        for(int i = 1; i < FragBases.UserSelection.size();i++)
        {
            arrBasesSiguiente.add(FragBases.UserSelection.get(i));
        }

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