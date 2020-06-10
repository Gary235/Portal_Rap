package com.example.portalrap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class adaptadorBases extends BaseAdapter {
    private ArrayList<Base> arrBases;
    private Context miContexto;
    Base miBase = new Base();
    TextView Nombre,Artista;
    ImageButton btnPlay,btnFav;
    //Boolean Fav;
    public adaptadorBases(ArrayList<Base> arrayBases,Context contexto)
    {
        arrBases = arrayBases;
        miContexto = contexto;
    }

    @Override
    public int getCount() {

        Log.d("Tamnio","Size: " + arrBases.size());

        return arrBases.size(); }

    @Override
    public Base getItem(int position) {
        Base bas;
        bas = arrBases.get(position);
        return bas;
    }

    @Override
    public long getItemId(int position) {

        return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vista;

        LayoutInflater inflador;
        inflador = (LayoutInflater) miContexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vista = inflador.inflate(R.layout.lista_bases, parent, false);


        btnFav = vista.findViewById(R.id.btnFavlista);
        btnPlay = vista.findViewById(R.id.btnPlaylista);
        Nombre = vista.findViewById(R.id.nombreBase);
        Artista = vista.findViewById(R.id.nombreArtista);

        btnFav.setFocusable(false);
        btnPlay.setFocusable(false);
        /*miBase = getItem(position);
        Nombre.setText(miBase._Nombre);
        Artista.setText(miBase._Artista);
*/


        return vista;
    }
}
