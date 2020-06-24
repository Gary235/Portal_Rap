package com.example.portalrap.Clases;


import java.util.HashMap;
import java.util.Map;

public class Base {

    private String Artista,Nombre,Url;
    private Boolean Destacado;
    private String Id;

    //Constructores
    public Base(String artista, String nombre, String url, Boolean destacado, String id) {
        Artista = artista;
        Nombre = nombre;
        Url = url;
        Destacado = destacado;
        Id = id;
    }
    public Base( ){


    }

    //map
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("Nombre", this.Nombre);
        result.put("Artista", this.Artista);
        result.put("Destacado", this.Destacado);
        return result;
    }


    //Getter y Setter
    public String getArtista() {
        return Artista;
    }
    public void setArtista(String artista) {
        Artista = artista;
    }

    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUrl() {
        return Url;
    }
    public void setUrl(String url) {
        Url = url;
    }

    public Boolean getDestacado() {
        return Destacado;
    }
    public void setDestacado(Boolean destacado) {
        Destacado = destacado;
    }

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }



}
