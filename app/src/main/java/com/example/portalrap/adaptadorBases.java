package com.example.portalrap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorBases extends BaseAdapter {
    private ArrayList<Base> arrBases;
    private Context miContexto;
    Base miBase = new Base();
    TextView Nombre,Artista;
    ImageButton btnFav;
    CheckBox check;
    //Boolean Fav;
    public static int aja = 0;

    public adaptadorBases(ArrayList<Base> arrayBases,Context contexto)
    {
        arrBases = arrayBases;
        miContexto = contexto;
    }

    @Override
    public int getCount() { return arrBases.size(); }

    @Override
    public Base getItem(int position) {
        Base bas;
        bas = arrBases.get(position);
        return bas;
    }


    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista;

        LayoutInflater inflador;
        inflador = (LayoutInflater) miContexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vista = inflador.inflate(R.layout.lista_bases, parent, false);


        btnFav = vista.findViewById(R.id.btnFavlista);
        Nombre = vista.findViewById(R.id.nombreBase);
        Artista = vista.findViewById(R.id.nombreArtista);
        check = vista.findViewById(R.id.checkbox);
        check.setTag(position);

        btnFav.setFocusable(false);
        check.setFocusable(false);
        Nombre.setFocusable(false);
        Artista.setFocusable(false);
        miBase = getItem(position);
        Nombre.setText(miBase._Nombre);
        Artista.setText(miBase._Artista);

        if(FragBases.isActionMode)
        {
            check.setVisibility(View.VISIBLE);
        }
        else
        {
            check.setVisibility(View.GONE);
        }

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int position = (int) buttonView.getTag();

                if(FragBases.UserSelection.contains(arrBases.get(position)))
                {
                    FragBases.UserSelection.remove(arrBases.get(position));
                    aja = aja - 1;
                }
                else
                {
                    FragBases.UserSelection.add(arrBases.get(position));
                    aja = aja + 1;
                }

                FragBases.actionMode.setTitle(FragBases.UserSelection.size()+ " Bases seleccionadas");

            }
        });



        return vista;
    }
}
