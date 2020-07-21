package com.example.portalrap.FragmentsUsuario;

import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.portalrap.Adaptadores.adaptadorBases;
import com.example.portalrap.Adaptadores.adaptadorGrabacionesUsuario;
import com.example.portalrap.Clases.Grabacion;
import com.example.portalrap.FragMiniReproductor;
import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FragUsuario extends Fragment implements View.OnClickListener{

    ImageButton btneditar,btnfav;
    public static ImageView fotoperfil;
    ListView lista;
    adaptadorGrabacionesUsuario adaptador;
    FirebaseFirestore db;
    ArrayList<Grabacion> Grabaciones = new ArrayList<>();


    @Override
    public void onStart() {
        super.onStart();


        obtenerListaGrabaciones();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        View v=inflater.inflate(R.layout.fragment_frag_usuario, container, false);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabusuario);
        btneditar =  v.findViewById(R.id.btneditar);
        btnfav = v.findViewById(R.id.btnfav);
        fotoperfil = v.findViewById(R.id.imgperfil);

        btneditar.setOnClickListener(this);
        btnfav.setOnClickListener(this);


        lista = v.findViewById(R.id.lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Log.d("Funciona","siiiiiiiiii");
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0)
                {
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return v;
    }

    @Override
    public void onClick(View v) {

        ImageButton botonapretado;
        botonapretado = (ImageButton)v;
        int idbotonapretado = botonapretado.getId();
        MainActivity main=(MainActivity) getActivity();

        if(idbotonapretado == R.id.btneditar)
        {
            main.PasaraFragEditarPerfil();
        }
        else if(idbotonapretado == R.id.btnfav)
        {
            main.PasaraFragFavoritos();
        }
    }


    private void obtenerListaGrabaciones() {

        db.collection("Grabaciones")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                Grabaciones.clear();
                lista.setAdapter(null);

                for (DocumentSnapshot document : snapshots) {
                    Grabacion grab = document.toObject(Grabacion.class);
                    assert grab != null;
                    grab.setId(document.getId());
                    Grabaciones.add(grab);
                }
                adaptador = new adaptadorGrabacionesUsuario(Grabaciones,getActivity(),adaptador,lista);
                lista.setAdapter(adaptador);
            }
        });


    }

}