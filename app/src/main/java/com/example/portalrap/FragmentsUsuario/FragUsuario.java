package com.example.portalrap.FragmentsUsuario;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.example.portalrap.Adaptadores.adaptadorGrabacionesUsuario;
import com.example.portalrap.Clases.Grabacion;
import com.example.portalrap.FragMiniReproductor;
import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FragUsuario extends Fragment implements View.OnClickListener{

    ImageButton btneditar,btnfav, logout;
    public static ImageView fotoperfil;
    ListView lista;
    adaptadorGrabacionesUsuario adaptador;
    FirebaseFirestore db;
    FirebaseUser user;
    ArrayList<Grabacion> Grabaciones = new ArrayList<>();
    TextView txtUsuario,txtArchivos;
    private FirebaseAuth mAuth;
    ImageView fotoNograb;
    Button btnNograb;
    public static FrameLayout holder;
    FragmentManager adminFragment;
    FragmentTransaction transaccionFragment;
    Bitmap bm;

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
        //txtArchivos = v.findViewById(R.id.txtVerArchivos);
        logout = v.findViewById(R.id.logout);
        btnNograb = v.findViewById(R.id.btnNoGrabUsuario);
        fotoNograb = v.findViewById(R.id.fotoUsuarioNoGrab);
        setearInfo();

        btneditar.setOnClickListener(this);
        btnfav.setOnClickListener(this);
        logout.setOnClickListener(this);
        fotoNograb.setVisibility(View.GONE);
        btnNograb.setVisibility(View.GONE);


        /*txtArchivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                //intent.setType(String.valueOf(FragEntrenar.localfile));
                startActivity(intent);
            }
        });*/


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
            if(user != null)
                main.PasaraFragEditarPerfil();
            else {
                Toast toast1 = Toast.makeText(getContext(),"Registrate para poder editar tu perfil", Toast.LENGTH_SHORT);
                toast1.show();
            }
        }
        else if(idbotonapretado == R.id.btnfav)
        {
            if(user != null)
                main.PasaraFragFavoritos();
            else {
                Toast toast1 = Toast.makeText(getContext(),"Registrate para acceder a favoritos", Toast.LENGTH_SHORT);
                toast1.show();
            }
        }
        else if(idbotonapretado == R.id.logout){

            AlertDialog.Builder mensaje;
            mensaje = new AlertDialog.Builder(getActivity());
            mensaje.setTitle("Cerrar Sesion");
            mensaje.setMessage(" \n ¿Seguro que quieres cerrar sesion? \n \n");
            mensaje.setPositiveButton("Si", escuchador);
            mensaje.setNegativeButton("No", escuchador);
            mensaje.create();
            mensaje.show();

        }
    }

    DialogInterface.OnClickListener escuchador = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            if(which == -1)
            {
                MainActivity main = (MainActivity) getActivity();
                user = null;
                main.actualizarUsuario(user);
                mAuth.signOut();
                main.PasaraFragmentIniciarSesion();

            }
            else if(which == -2)
            {
                dialog.cancel();
            }

        }
    };

    private void obtenerListaGrabaciones() {

        if(user != null){
            db.collection("Usuarios").document(user.getUid()).collection("Grabaciones")
                    .whereGreaterThan("Nombre","")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                            Grabaciones.clear();
                            lista.setAdapter(null);
                            if(user != null){
                            for (DocumentSnapshot document : snapshots) {
                                Grabacion grab = document.toObject(Grabacion.class);
                                assert grab != null;
                                grab.setId(document.getId());
                                Grabaciones.add(grab);
                            }
                            if(Grabaciones.size() == 0){
                                fotoNograb.setVisibility(View.VISIBLE);
                                btnNograb.setVisibility(View.VISIBLE);

                                btnNograb.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        MainActivity main = (MainActivity) getActivity();
                                        main.PasaraFragmentModo();
                                    }
                                });
                            } else {

                                fotoNograb.setVisibility(View.GONE);
                                btnNograb.setVisibility(View.GONE);
                            }
                            }

                            adaptador = new adaptadorGrabacionesUsuario(Grabaciones,getActivity());
                            lista.setAdapter(adaptador);
                        }
                    });




        }
        else {
            fotoNograb.setImageResource(R.drawable.ic_foto_nousuario);
            btnNograb.setText("Ir a registro");
            btnNograb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity main = (MainActivity) getActivity();
                    main.PasaraFragmentRegistro();
                }
            });

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


            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/Portal Rap/Fotos");
            String fname = "FotoDePerfil: " + user.getUid() + ".jpg";
            File file = new File (myDir, fname);

            if(file.exists())
                descargarFotoPerfil();

        }
        else {
            emailCortado = "anonimo";
        }
        txtUsuario.setText(emailCortado);

        //fotoperfil.setImageBitmap(user.getPhotoUrl());



    }


    private void descargarFotoPerfil(){



        Log.d("FotoPerfil", "url: " + user.getPhotoUrl());

        buscarfotoPerfil buscarfotoPerfil = new buscarfotoPerfil();
        buscarfotoPerfil.execute();



    }

    private class buscarfotoPerfil extends AsyncTask<Void, Void, Bitmap> {
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;


            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/Portal Rap/Fotos");
            String fname = "FotoDePerfil: " + user.getUid() + ".jpg";
            File file = new File (myDir, fname);
            Uri uri = Uri.fromFile(file);

            try {
                bitmap = getBitmapFromUri(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            fotoperfil.setImageBitmap(result);
            fotoperfil.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        }

    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}