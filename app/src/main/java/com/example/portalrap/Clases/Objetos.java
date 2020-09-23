package com.example.portalrap.Clases;

public class Objetos {

    private String Objeto, Id;

    public Objetos(){

    }

    public Objetos(String objeto, String id) {
        Objeto = objeto;
        Id = id;
    }

    public String getObjeto() { return Objeto; }
    public void setObjeto(String objeto) { Objeto = objeto; }

    public String getId() { return Id; }
    public void setId(String id) { Id = id; }
}
