package com.example.portalrap;

import android.app.Fragment;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class FragBases extends Fragment implements View.OnClickListener {

    int textlength=0;
    ArrayList<Base> array_sort = new ArrayList<>();
    ImageButton btnOrdenar, btnFiltrar;
    EditText edtBuscar;
    ListView listabases;
    ArrayList<Base> arrBases = new ArrayList<>();
    adaptadorBases adaptador,adaptador2;
    public static Boolean isActionMode = false;
    public static List<Base> UserSelection = new ArrayList<>();
    public static ActionMode actionMode = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_frag_bases, container, false);

        btnOrdenar = v.findViewById(R.id.ordenar);
        btnFiltrar = v.findViewById(R.id.filtrar);
        edtBuscar = v.findViewById(R.id.edtBuscar);

        btnFiltrar.setOnClickListener(this);
        btnOrdenar.setOnClickListener(this);

        adaptador = new adaptadorBases(arrBases,getActivity());


        listabases = v.findViewById(R.id.listabases);
        listabases.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listabases.setMultiChoiceModeListener(modeListener);
        listabases.setAdapter(adaptador);
            for(int i =0;i<10;i++)
            {
                Base unaBase = new Base();
                unaBase._Nombre = "Beat #"+ i;
                unaBase._Artista = "Artista #"+i;
                arrBases.add(unaBase);
            }

            edtBuscar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    textlength = edtBuscar.getText().length();
                    array_sort.clear();
                    for (int i = 0; i < arrBases.size(); i++) {
                        if (textlength <= arrBases.get(i)._Nombre.length()) {
                            if (arrBases.get(i)._Nombre.toString().contains(edtBuscar.getText().toString())) {
                                array_sort.add(arrBases.get(i));
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

        return v;
    }


    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
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
            //ir a entrenar predeterminado
            Log.d("entro?","sisiisis");
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
    };


    @Override
    public void onClick(View v) {
        ImageButton botonapretado;
        botonapretado = (ImageButton)v;
        int idbotonapretado = botonapretado.getId();
        MainActivity main=(MainActivity) getActivity();


         if(idbotonapretado == R.id.ordenar)
        {


        }
        else if(idbotonapretado == R.id.filtrar)
        {


        }

    }
}