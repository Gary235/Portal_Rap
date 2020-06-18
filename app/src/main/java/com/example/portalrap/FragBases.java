package com.example.portalrap;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.portalrap.Adaptadores.adaptadorBases;
import com.example.portalrap.Clases.BD;
import com.example.portalrap.Clases.Base;
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


public class FragBases extends Fragment implements View.OnClickListener {

    int textlength=0;
    ArrayList<Base> array_sort = new ArrayList<>();
    ImageButton btnOrdenar, btnFiltrar,btnSiguiente,btnAnterior,btnInfo;
    ImageView fondo1,paso4;
    TextView txtTitulo;
    EditText edtBuscar;
    ListView listabases;
    adaptadorBases adaptador,adaptador2;
    public static Boolean isActionMode = false;
    public static List<Base> UserSelection = new ArrayList<>();
    public static ActionMode actionMode = null;
    FirebaseFirestore db;
    ArrayList<Base> Beats = new ArrayList<>();
    String desdedur;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        db = FirebaseFirestore.getInstance();
        desdedur = getArguments().getString("desdedur");

        View v = inflater.inflate(R.layout.fragment_frag_bases, container, false);
        Setear(v);
        ListenersAdicionales();

        if (desdedur != "si") {
            btnSiguiente.setVisibility(View.GONE);
            btnAnterior.setVisibility(View.GONE);
            paso4.setVisibility(View.GONE);
            btnInfo.setVisibility(View.GONE);
            return v;
        }
        else {
            txtTitulo.setText("Elegir Base");
            listabases.setPadding(0,0,0,300);
            return v;
        }
    }

    public void Setear(View v) {
        btnInfo = v.findViewById(R.id.botoninfodebases);
        paso4 =v.findViewById(R.id.paso4);
        fondo1 =v.findViewById(R.id.fondo1debases);
        txtTitulo = v.findViewById(R.id.txtTitulodeBases);
        btnAnterior = v.findViewById(R.id.botonanteriordebases);
        btnSiguiente = v.findViewById(R.id.botonsiguientedebases);

        //btnOrdenar = v.findViewById(R.id.ordenar);
        //btnFiltrar = v.findViewById(R.id.filtrar);
        edtBuscar = v.findViewById(R.id.edtBuscar);

        //btnFiltrar.setOnClickListener(this);
        //btnOrdenar.setOnClickListener(this);
        btnAnterior.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
        btnInfo.setOnClickListener(this);

        listabases = v.findViewById(R.id.listabases);
        listabases.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

        obtenerListaBases();
    }

    public void  ListenersAdicionales(){

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
                    //mandarAFragmentEntrenar
                    ((AppCompatActivity)getActivity()).getSupportActionBar();
                    MainActivity main=(MainActivity) getActivity();
                    if (desdedur == "si")
                    {
                        main.PasaraFragTodoListo(desdedur);

                    }
                    else {
                        main.PasaraFragTodoListo("no");

                    }

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
                UserSelection.clear();
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
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        ImageButton botonapretado;
        botonapretado = (ImageButton)v;
        int idbotonapretado = botonapretado.getId();
        MainActivity main=(MainActivity) getActivity();


        /* if(idbotonapretado == R.id.ordenar)
        {


        }
        else if(idbotonapretado == R.id.filtrar)
        {


        }*/
        if(idbotonapretado == R.id.botonsiguientedebases)
         {
             //PasarAFragmentdeEntrenar
             main.PasaraFragTodoListo(desdedur);
         }
         else if(idbotonapretado == R.id.botonanteriordebases)
         {
             main.PasaraFragmentDuracion();
         }
         else if(idbotonapretado == R.id.botoninfodebases)
         {
             AlertDialog.Builder mensaje;
             mensaje = new AlertDialog.Builder(getActivity());
             mensaje.setTitle("Elegir Bases");
             mensaje.setMessage("Elige una base para poder entrenar sobre ella. El tiempo de duración de la base no determinara el tiempo de duración del entrenamiento. Puedes elegir más de una base.");
             mensaje.setPositiveButton("Aceptar",null);
             mensaje.create();
             mensaje.show();
         }

    }

    private void obtenerListaBases() {
        db.collection("Beats").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot document : snapshots) {
                    Base beat = document.toObject(Base.class);
                    assert beat != null;
                    beat.setId(document.getId());
                    Beats.add(beat);
                }
                adaptador = new adaptadorBases(Beats,getActivity());
                listabases.setAdapter(adaptador);

            }
        });
    }

}