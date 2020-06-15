package com.example.portalrap.Clases;

import android.widget.ImageButton;

public class Grabacion {



    private String _Nombre;
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
    public String get_Nombre() {
        return _Nombre;
    }

    public void set_Nombre(String _Nombre) {
        this._Nombre = _Nombre;
    }
}
