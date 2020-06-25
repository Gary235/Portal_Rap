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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.portalrap.Adaptadores.adaptadorBases;
import com.example.portalrap.Adaptadores.adaptadorGrabacionesUsuario;
import com.example.portalrap.Clases.Base;
import com.example.portalrap.Clases.Grabacion;
import com.example.portalrap.Clases.Palabras;
import com.example.portalrap.FragMiniReproductor;
import com.example.portalrap.MainActivity;
import com.example.portalrap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FragFavoritos extends Fragment implements View.OnClickListener {

    ImageView delineado, delineado2;
    ImageButton btnGrabado, btnBeats, btnVolver;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_favoritos, container, false);

        adminFragment = getFragmentManager();
        Fragment fragminireproductor;
        fragminireproductor = new FragMiniReproductor();
        transaccionFragment = adminFragment.beginTransaction();
        transaccionFragment.replace(R.id.holder, fragminireproductor);
        transaccionFragment.commit();

        db = FirebaseFirestore.getInstance();
        btnBeats = v.findViewById(R.id.grabadofavoritos);
        btnGrabado = v.findViewById(R.id.beatsfavoritos);
        btnVolver = v.findViewById(R.id.flechitabajodefavoritos);
        lista = v.findViewById(R.id.listagrabacionesbasesfav);
        delineado = v.findViewById(R.id.lineadelineado);
        delineado2 = v.findViewById(R.id.lineadelineado2);
        holder = v.findViewById(R.id.holder);

        btnGrabado.setOnClickListener(this);
        btnBeats.setOnClickListener(this);
        btnVolver.setOnClickListener(this);

        adaptadorBasesFav = new adaptadorBases(arrBasesFav, getActivity());

        obtenerListaGrabacionesfav();
        obtenerListaBeatsfav();

        return v;
    }

    @Override
    public void onClick(View v) {

        ImageButton botonapretado;
        botonapretado = (ImageButton) v;
        int idbotonapretado = botonapretado.getId();
        MainActivity main = (MainActivity) getActivity();

        if (idbotonapretado == R.id.grabadofavoritos) {

            delineado2.setVisibility(View.GONE);
            delineado.setVisibility(View.VISIBLE);
            lista.setAdapter(adaptadorGrabacionesUsuarioFav);
            lista.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
            lista.setOnItemClickListener(new AbsListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //reproducir

                }
            });
        } else if (idbotonapretado == R.id.beatsfavoritos) {

            delineado.setVisibility(View.GONE);
            delineado2.setVisibility(View.VISIBLE);
            lista.setAdapter(adaptadorBasesFav);
            lista.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //reproducir
                    holder.setVisibility(View.VISIBLE);
                    lista.setPadding(0, 0, 0, 100);

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


        } else if (idbotonapretado == R.id.flechitabajodefavoritos) {
            main.PasaraFragUsuario();
        }
    }

    public void obtenerListaGrabacionesfav() {
        db.collection("Grabaciones")
                .whereEqualTo("Favoritos", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentSnapshot document : snapshots) {
                            Grabacion grab = document.toObject(Grabacion.class);
                            assert grab != null;
                            grab.setId(document.getId());
                            arrGrabacionesFav.add(grab);
                        }
                        adaptadorGrabacionesUsuarioFav = new adaptadorGrabacionesUsuario(arrGrabacionesFav, getActivity());
                        lista.setAdapter(adaptadorGrabacionesUsuarioFav);

                    }
                });

    }

    public void obtenerListaBeatsfav() {
        db.collection("Beats")
                .whereEqualTo("Favoritos", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentSnapshot document : snapshots) {
                            Base base = document.toObject(Base.class);
                            assert base != null;
                            base.setId(document.getId());
                            arrBasesFav.add(base);
                        }
                        adaptadorBasesFav = new adaptadorBases(arrBasesFav, getActivity());
                        lista.setAdapter(adaptadorBasesFav);

                    }
                });

    }


}