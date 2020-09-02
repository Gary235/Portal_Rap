package com.example.portalrap.FragmentsUsuario;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.portalrap.Adaptadores.adaptadorGrabacionesUsuario;
import com.example.portalrap.Clases.Grabacion;
import com.example.portalrap.FragMiniReproductor;
import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class FragUsuario extends Fragment implements View.OnClickListener{

    ImageButton btneditar,btnfav, logout;
    public static ImageView fotoperfil;
    ListView lista;
    adaptadorGrabacionesUsuario adaptador;
    FirebaseFirestore db;
    FirebaseUser user;
    ArrayList<Grabacion> Grabaciones = new ArrayList<>();
    TextView txtUsuario;
    private FirebaseAuth mAuth;
    ImageView fotoNograb;
    Button btnNograb;
    public static FrameLayout holder;
    FragmentManager adminFragment;
    FragmentTransaction transaccionFragment;


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final View v = inflater.inflate(R.layout.fragment_frag_usuario, container, false);
        adminFragment = getFragmentManager();

        TabLayout tabLayout = v.findViewById(R.id.tabusuario);
        holder = v.findViewById(R.id.holderdeusuario);
        btneditar =  v.findViewById(R.id.btneditar);
        btnfav = v.findViewById(R.id.btnfav);
        fotoperfil = v.findViewById(R.id.imgperfil);
        txtUsuario = v.findViewById(R.id.txtusuario);
        logout = v.findViewById(R.id.logout);
        btnNograb = v.findViewById(R.id.btnNoGrabUsuario);
        fotoNograb = v.findViewById(R.id.fotoUsuarioNoGrab);
        setearInfo();

        btneditar.setOnClickListener(this);
        btnfav.setOnClickListener(this);
        logout.setOnClickListener(this);
        fotoNograb.setVisibility(View.GONE);
        btnNograb.setVisibility(View.GONE);

        lista = v.findViewById(R.id.lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Log.d("Funciona","siiiiiiiiii");
                Fragment fragminireproductor;
                fragminireproductor = new FragMiniReproductor();

                Bundle args = new Bundle();
                args.putString("Artista", "Tú");
                args.putString("Nombre", Grabaciones.get(position).getNombre());
                args.putString("Url", Grabaciones.get(position).getUrl());
                args.putString("Duracion", "");

                fragminireproductor.setArguments(args);
                transaccionFragment = adminFragment.beginTransaction();
                transaccionFragment.replace(R.id.holderdeusuario, fragminireproductor);
                transaccionFragment.commit();

                if(FragMiniReproductor.mediaplayer != null) {
                    FragMiniReproductor.mediaplayer.stop();
                    FragMiniReproductor.mediaplayer.reset();
                }

                holder.setVisibility(View.VISIBLE);

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
        obtenerListaGrabaciones();


        btnNograb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main = (MainActivity) getActivity();
                main.PasaraFragmentModo();
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
        else if(idbotonapretado == R.id.logout){

            mAuth.signOut();
            main.PasaraFragmentIniciarSesion();

        }
    }


    private void obtenerListaGrabaciones() {

        db.collection("Usuarios").document(user.getUid()).collection("Grabaciones")
                .whereGreaterThan("Nombre","")
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
                adaptador = new adaptadorGrabacionesUsuario(Grabaciones,getActivity());
                lista.setAdapter(adaptador);
            }
        });

        if(Grabaciones.size() == 0){
            fotoNograb.setVisibility(View.GONE);
            btnNograb.setVisibility(View.GONE);
        } else {
            fotoNograb.setVisibility(View.VISIBLE);
            btnNograb.setVisibility(View.VISIBLE);
        }

    }

    public void setearInfo(){

        MainActivity main = (MainActivity) getActivity();
        user = main.obtenerUsuario();
        String emailCortado;
        if(user != null){
            String email = user.getEmail();
            int posArroba = email.indexOf("@");
            emailCortado = email.substring(0, posArroba);
        }
        else {
            emailCortado = "anonimo";
        }
        txtUsuario.setText(emailCortado);

        //fotoperfil.setImageBitmap(user.getPhotoUrl());

    }

}