package com.example.portalrap.FragmentsUsuario;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.example.portalrap.Adaptadores.adaptadorBases;
import com.example.portalrap.Adaptadores.adaptadorGrabacionesUsuario;
import com.example.portalrap.Clases.Base;
import com.example.portalrap.Clases.Grabacion;
import com.example.portalrap.FragMiniReproductor;
import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FragFavoritos extends Fragment implements View.OnClickListener {

    ImageButton btnVolver;
    ListView lista;
    ArrayList<Grabacion> arrGrabacionesFav = new ArrayList<>();
    adaptadorGrabacionesUsuario adaptadorGrabacionesUsuarioFav;
    ArrayList<Base> arrBasesFav = new ArrayList<>();
    adaptadorBases adaptadorBasesFav;
    public static Boolean isActionMode = false;
    public static ActionMode actionMode = null;
    public static List<Base> UserSelection = new ArrayList<>();
    FragmentManager adminFragment;
    FragmentTransaction transaccionFragment;
    FrameLayout holder;
    FirebaseFirestore db;
    FirebaseUser user;
    TabItem item1,item2;

    ImageView fotoNoHay;
    Button btnNoHay;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_favoritos, container, false);

        MainActivity main = (MainActivity) getActivity();
        user = main.obtenerUsuario();

        adminFragment = getFragmentManager();
        TabLayout tabLayout = v.findViewById(R.id.tab);
        item1 =  v.findViewById(R.id.tabitem1);
        item2 =  v.findViewById(R.id.tabitem2);
        db = FirebaseFirestore.getInstance();
        btnVolver = v.findViewById(R.id.flechitabajodefavoritos);
        lista = v.findViewById(R.id.listagrabacionesbasesfav);
        holder = v.findViewById(R.id.holder);
        fotoNoHay = v.findViewById(R.id.fotoNoDeFav);
        btnNoHay = v.findViewById(R.id.btnNoDeFav);

        fotoNoHay.setVisibility(View.GONE);
        btnNoHay.setVisibility(View.GONE);

        Log.d("Adaptador" , "" + lista.getAdapter());
        btnVolver.setOnClickListener(this);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0)
                {
                    lista.setAdapter(adaptadorGrabacionesUsuarioFav);
                    lista.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
                    lista.setOnItemClickListener(new AbsListView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //reproducir

                        }
                    });

                    if(arrGrabacionesFav.size() == 0){
                        fotoNoHay.setVisibility(View.VISIBLE);
                        btnNoHay.setVisibility(View.VISIBLE);

                        fotoNoHay.setImageResource(R.drawable.ic_nolikegrab);
                        btnNoHay.setText("Ir a grabaciones");

                        btnNoHay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MainActivity main = (MainActivity) getActivity();
                                main.PasaraFragUsuario();
                            }
                        });


                    } else {
                        fotoNoHay.setVisibility(View.GONE);
                        btnNoHay.setVisibility(View.GONE);
                    }

                }
                if(tab.getPosition() == 1)
                {
                    lista.setAdapter(adaptadorBasesFav);
                    lista.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

                    lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //reproducir
                            Fragment fragminireproductor;
                            fragminireproductor = new FragMiniReproductor();
                            Bundle args = new Bundle();
                            args.putString("Nombre", arrBasesFav.get(position).getNombre());
                            args.putString("Url", arrBasesFav.get(position).getUrl());
                            args.putString("Artista", arrBasesFav.get(position).getArtista());
                            args.putString("Duracion", arrBasesFav.get(position).getDuracion());

                            fragminireproductor.setArguments(args);
                            transaccionFragment = adminFragment.beginTransaction();
                            transaccionFragment.replace(R.id.holder, fragminireproductor);
                            transaccionFragment.commit();
                            if(FragMiniReproductor.mediaplayer != null)
                            {
                                FragMiniReproductor.mediaplayer.stop();
                                FragMiniReproductor.mediaplayer.reset();
                            }
                            holder.setVisibility(View.VISIBLE);
                            lista.setPadding(0,0, 0, 160);
                        }
                    });

                    lista.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                        @Override
                        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                        }

                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            Log.d("mensaje", "hola");
                            MenuInflater inflater = mode.getMenuInflater();
                            inflater.inflate(R.menu.actionbar_menu, menu);
                            isActionMode = true;
                            actionMode = mode;
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            return false;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            if (item.getItemId() == R.id.usar && adaptadorBases.aja > 0) {
                                //mandarAFragmentEntrenar
                                MainActivity main = (MainActivity) getActivity();
                                main.PasaraFragTodoListo("no");
                                mode.finish();


                                return true;
                            } else {
                                Toast toast1 = Toast.makeText(getActivity(), "Ninguna Base fue seleccionada", Toast.LENGTH_SHORT);
                                toast1.show();
                                return false;
                            }
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            isActionMode = false;
                            actionMode = null;
                            //UserSelection.clear();
                        }
                    });

                    if(arrBasesFav.size() == 0){
                        fotoNoHay.setVisibility(View.VISIBLE);
                        btnNoHay.setVisibility(View.VISIBLE);

                        fotoNoHay.setImageResource(R.drawable.ic_nolikebase);
                        btnNoHay.setText("Ir a bases");

                        btnNoHay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MainActivity main = (MainActivity) getActivity();
                                main.PasaraFragBases("no");
                            }
                        });


                    } else {
                        fotoNoHay.setVisibility(View.GONE);
                        btnNoHay.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        obtenerListaBeatsfav();
        obtenerListaGrabacionesfav();



        return v;
    }

    @Override
    public void onClick(View v) {

        ImageButton botonapretado;
        botonapretado = (ImageButton) v;
        int idbotonapretado = botonapretado.getId();
        MainActivity main = (MainActivity) getActivity();

        if (idbotonapretado == R.id.flechitabajodefavoritos) {
            main.PasaraFragUsuario();
        }

    }

    public void obtenerListaGrabacionesfav() {

        db.collection("Usuarios").document(user.getUid()).collection("Grabaciones")
                .whereEqualTo("Favoritos", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        arrGrabacionesFav.clear();
                        lista.setAdapter(null);
                        for (DocumentSnapshot document : snapshots) {
                            Grabacion grab = document.toObject(Grabacion.class);
                            assert grab != null;
                            grab.setId(document.getId());
                            arrGrabacionesFav.add(grab);
                        }

                        if(arrGrabacionesFav.size() == 0){
                            fotoNoHay.setVisibility(View.VISIBLE);
                            btnNoHay.setVisibility(View.VISIBLE);

                            fotoNoHay.setImageResource(R.drawable.ic_nolikegrab);
                            btnNoHay.setText("Ir a grabaciones");

                            btnNoHay.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    MainActivity main = (MainActivity) getActivity();
                                    main.PasaraFragUsuario();
                                }
                            });
                        } else {
                            fotoNoHay.setVisibility(View.GONE);
                            btnNoHay.setVisibility(View.GONE);
                        }
                        Log.d("Adaptador" , "" + lista.getAdapter());



                        adaptadorGrabacionesUsuarioFav = new adaptadorGrabacionesUsuario(arrGrabacionesFav, getActivity());
                        lista.setAdapter(adaptadorGrabacionesUsuarioFav);
                    }

                });

    }

    public void obtenerListaBeatsfav() {

        db.collection("Usuarios").document(user.getUid()).collection("BeatsFav")
                .whereEqualTo("Favoritos", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        arrBasesFav.clear();
                        //lista.setAdapter(null);
                        for (DocumentSnapshot document : snapshots) {
                            Base base = document.toObject(Base.class);
                            assert base != null;
                            base.setId(document.getId());
                            arrBasesFav.add(base);
                        }
                        adaptadorBasesFav = new adaptadorBases(arrBasesFav, getActivity());
                        if(lista.getAdapter() != adaptadorGrabacionesUsuarioFav)
                        {
                            lista.setAdapter(adaptadorBasesFav);
                        }
                    }
                });

    }


}