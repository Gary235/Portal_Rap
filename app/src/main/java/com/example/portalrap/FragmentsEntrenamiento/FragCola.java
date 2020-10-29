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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;


public class FragCola extends Fragment{

    TextView txtTituloSonando, txtArtistaSonando,txtSiguiente;
    ImageButton btnVolver, btnFav;
    ListView listaSiguiente;
    ArrayList<Base> arrBasesSiguiente = new ArrayList<>();
    Base baseSonando = new Base();
    adaptadorDeColaSiguiente adaptador;
    int index = 0;
    private FirebaseFirestore db;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_cola, container, false);
        db = FirebaseFirestore.getInstance();
        MainActivity main = (MainActivity) getActivity();
        user = main.obtenerUsuario();

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
        txtSiguiente = v.findViewById(R.id.siguiente);
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

                MainActivity main = (MainActivity) getActivity();
                if(baseSonando.getFavoritos()!= null)
                {
                    if(baseSonando.getFavoritos()){
                        baseSonando.setFavoritos(false);
                        btnFav.setImageResource(R.drawable.ic_icono_nofav_blanco);
                        main.eliminarFav(FragBases.UserSelection.get(index).getId());

                    }else{
                        baseSonando.setFavoritos(true);
                        btnFav.setImageResource(R.drawable.ic_icono_fav_blanco);
                        main.agregarFav(FragBases.UserSelection.get(index).getNombre(),FragBases.UserSelection.get(index).getArtista(),false,FragBases.UserSelection.get(index).getId(),FragBases.UserSelection.get(index).getUrl(),FragBases.UserSelection.get(index).getFavoritos(),FragBases.UserSelection.get(index).getDuracion());

                    }
                }
            }
        });

    }
    public void CargarSonando() {
        baseSonando = FragBases.UserSelection.get(index);
        txtTituloSonando.setText(baseSonando.getNombre());
        txtArtistaSonando.setText(baseSonando.getArtista());

        if(baseSonando.getFavoritos() && user != null)
        {
            //desfavear
            btnFav.setImageResource(R.drawable.ic_icono_fav_blanco);
        }
        else if(!baseSonando.getFavoritos() && user != null) {
            //fav
            btnFav.setImageResource(R.drawable.ic_icono_nofav_blanco);

        }


    }
    public void CargarArray() {
        for(int i = 1; i < FragBases.UserSelection.size();i++)
        {
            arrBasesSiguiente.add(FragBases.UserSelection.get(i));
        }


        if(arrBasesSiguiente.size() == 0)
            txtSiguiente.setVisibility(View.GONE);
        else
            txtSiguiente.setVisibility(View.VISIBLE);

    }


}