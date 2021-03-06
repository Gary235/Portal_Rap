package com.example.portalrap;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.portalrap.Adaptadores.adaptadorBases;
import com.example.portalrap.Clases.Base;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FragBases extends Fragment implements View.OnClickListener {

    int textlength = 0;
    ArrayList<Base> array_sort = new ArrayList<>();
    public static ImageButton btnAnterior;
    Button btnInfo;
    ImageView fondo1,paso4;
    TextView txtTitulo,lbl,lbl2,lbl3;
    EditText edtBuscar;
    public static ListView listabases;

    adaptadorBases adaptador,adaptador2;
    public static Boolean isActionMode = false;
    public static List<Base> UserSelection = new ArrayList<>();
    public static ActionMode actionMode = null;
    FirebaseFirestore db;
    ArrayList<Base> Beats = new ArrayList<>();
    public static String desdedur;
    ScrollView scrol;
    FragmentManager adminFragment;
    FragmentTransaction transaccionFragment;
    public static FrameLayout holder;
    FirebaseUser user;


    @Override
    public void onStart() {
        super.onStart();
        MainActivity main = (MainActivity) getActivity();
        user = main.obtenerUsuario();
        db = FirebaseFirestore.getInstance();
        obtenerListaBases();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        desdedur = getArguments().getString("desdedur");
        adminFragment = getFragmentManager();

        View v = inflater.inflate(R.layout.fragment_frag_bases, container, false);
        Setear(v);
        ListenersAdicionales(v);


        if (!desdedur.equals("si")) {
            btnAnterior.setVisibility(View.GONE);
            paso4.setVisibility(View.GONE);
            scrol.setVisibility(View.GONE);
        }
        else {
            txtTitulo.setText("Elegir Base");
            listabases.setPadding(0,0,0,300);
        }

        return v;
    }

    public void Setear(View v) {
        btnInfo = v.findViewById(R.id.botoninfodebases);
        paso4 = v.findViewById(R.id.paso4);
        fondo1 = v.findViewById(R.id.fondo1debases);
        txtTitulo = v.findViewById(R.id.txtTitulodeBases);
        btnAnterior = v.findViewById(R.id.botonanteriordebases);
        edtBuscar = v.findViewById(R.id.edtBuscar);
        lbl = v.findViewById(R.id.textolabel1debases);
        lbl2 = v.findViewById(R.id.textolabel2debases);
        lbl3 = v.findViewById(R.id.textolabel3debases);
        scrol = v.findViewById(R.id.scrolviudebases);
        holder = v.findViewById(R.id.holderdebases);

        btnAnterior.setOnClickListener(this);
        btnInfo.setOnClickListener(this);

        if(MainActivity.PosModo == 0)
        { lbl.setText("Aleatorio"); }
        else if(MainActivity.PosModo == 1)
        { lbl.setText("Objetos");}
        else
        { lbl.setText("Palabras");}

        if(MainActivity.Frecuencia == 0)
        { lbl2.setText("2s"); }
        else if(MainActivity.Frecuencia == 1)
        { lbl2.setText("5s");}
        else if(MainActivity.Frecuencia == 2)
        { lbl2.setText("10s");}
        else if(MainActivity.Frecuencia == 3)
        { lbl2.setText("20s");}
        else if(MainActivity.Frecuencia == 4)
        { lbl2.setText("30s");}
        lbl3.setText(MainActivity.Minutos + "m / " + MainActivity.Segundos + "s");

        adaptadorBases.aja = 0;

        listabases = v.findViewById(R.id.listabases);
        listabases.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);


    }

    public void  ListenersAdicionales(final View v){

        listabases.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            }
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.actionbar_menu,menu);
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
                if(item.getItemId() == R.id.usar && adaptadorBases.aja > 0)
                {
                    MainActivity main=(MainActivity) getActivity();
                    if (desdedur.equals("si")){
                            main.PasaraFragTodoListo(desdedur);
                    }
                    else{
                            main.PasaraFragTodoListo("no");
                    }
                    mode.finish();
                    return true;
                }
                else {
                    Toast toast1 = Toast.makeText(getActivity(),"Ninguna Base fue seleccionada", Toast.LENGTH_SHORT);
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


        listabases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //reproducir
                Fragment fragminireproductor;
                fragminireproductor = new FragMiniReproductor();

                Bundle args = new Bundle();
                args.putString("Artista", Beats.get(position).getArtista());
                args.putString("Nombre", Beats.get(position).getNombre());
                args.putString("Url", Beats.get(position).getUrl());
                args.putString("Duracion", Beats.get(position).getDuracion());

                fragminireproductor.setArguments(args);
                transaccionFragment = adminFragment.beginTransaction();
                transaccionFragment.replace(R.id.holderdebases, fragminireproductor);
                transaccionFragment.commit();

                if(FragMiniReproductor.mediaplayer != null) {
                    FragMiniReproductor.mediaplayer.stop();
                    FragMiniReproductor.mediaplayer.reset();
                }

                holder.setVisibility(View.VISIBLE);

                if(desdedur.equals("si")) {
                    listabases.setPadding(0,0, 0, 400);
                    ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) v.findViewById(R.id.botonanteriordebases).getLayoutParams();
                    layoutParams.setMargins(0, 0, 0, 150);
                }
                else {
                    listabases.setPadding(0,0, 0, 160);
                }
            }
        });

        edtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textlength = edtBuscar.getText().length();
                array_sort.clear();
                for (int i = 0; i < Beats.size(); i++) {
                    if (textlength <= Beats.get(i).getNombre().length()) {
                        if (Beats.get(i).getNombre().toLowerCase().contains(edtBuscar.getText().toString().toLowerCase()) || Beats.get(i).getArtista().toLowerCase().contains(edtBuscar.getText().toString().toLowerCase())) {
                            array_sort.add(Beats.get(i));
                        }
                    }
                }
                adaptador2 = new adaptadorBases(array_sort,getActivity());
                listabases.setAdapter(adaptador2);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });


        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mensaje;
                mensaje = new AlertDialog.Builder(getActivity());
                mensaje.setTitle("Elegir Bases");
                mensaje.setMessage("· Mantén apretado para seleccionar una Base\n\n· El tiempo de duración de la base no determinara el tiempo de duración TOTAL del entrenamiento\n\n· Puedes elegir más de una base\n\n· Para ir a entrenar elige todas las bases que quieras y aprieta el boton con forma de tick");
                mensaje.setPositiveButton("Aceptar",null);
                mensaje.create();
                mensaje.show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        ImageButton botonapretado;
        botonapretado = (ImageButton)v;
        int idbotonapretado = botonapretado.getId();
        MainActivity main=(MainActivity) getActivity();

         if(idbotonapretado == R.id.botonanteriordebases) {
             main.PasaraFragmentDuracion();
         }
    }

    private void obtenerListaBases() {
        db.collection("Beats")
                .orderBy("Nombre")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                        Beats.clear();
                        listabases.setAdapter(null);
                        assert snapshots != null;
                        for (DocumentSnapshot document : snapshots) {
                            Base beat = document.toObject(Base.class);
                            assert beat != null;
                            beat.setId(document.getId());
                            Beats.add(beat);
                        }

                        if(user == null){
                            adaptador = new adaptadorBases(Beats, getActivity());
                            listabases.setAdapter(adaptador);
                        }

                    }
                });

        if(user != null){
            db.collection("Usuarios").document(user.getUid()).collection("BeatsFav")
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                            listabases.setAdapter(null);
                            for (DocumentSnapshot document : snapshots) {
                                Base beat = document.toObject(Base.class);
                                assert beat != null;
                                beat.setId(document.getId());

                                for(int j = 0; j < Beats.size();j++) {
                                    if(Beats.get(j).getNombre().equals(beat.getNombre())) {
                                        Beats.get(j).setFavoritos(beat.getFavoritos());
                                    }
                                }

                            }
                            adaptador = new adaptadorBases(Beats, getActivity());
                            listabases.setAdapter(adaptador);
                        }
                    });

        }


    }


}