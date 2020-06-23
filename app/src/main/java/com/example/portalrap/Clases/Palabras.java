package com.example.portalrap.Clases;

public class Palabras {

    private String Palabra,id;

    public Palabras() {
    }

    public Palabras(String palabra, String id) {
        this.Palabra = palabra;
        this.id = id;
    }


    public String getPalabra() { return Palabra; }
    public void setPalabra(String palabra) { this.Palabra = palabra; }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
