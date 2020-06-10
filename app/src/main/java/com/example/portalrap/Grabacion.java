package com.example.portalrap;

import android.widget.ImageButton;

public class Grabacion {

    String _Nombre;
    ImageButton _btnPlay,_btnFav;
    //Boolean _Fav;

    public Grabacion( String nom, ImageButton btnplay, ImageButton btnfav /* Boolean fav*/){

        _Nombre = nom;
        _btnPlay = btnplay;
        _btnFav = btnfav;
       // _Fav = fav;

    }
    public Grabacion( ){


    }

}
