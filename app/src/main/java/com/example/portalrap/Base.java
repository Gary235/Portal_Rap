package com.example.portalrap;

import android.widget.ImageButton;

public class Base {
    String _Nombre,_Artista;
    ImageButton _btnPlay,_btnFav;
    //Boolean _Fav;

    public Base( String nom, ImageButton btnplay, ImageButton btnfav, String artista /*Boolean fav*/){

        _Nombre = nom;
        _Artista = artista;
        _btnPlay = btnplay;
        _btnFav = btnfav;
        //_Fav = fav;
    }
    public Base( ){


    }
}
