package com.example.votaciones.objetos;

public class Propuesta {
    private String id;
    private String titulo;
    private String contenido;
    private String plancha;
    private int indice;

    public Propuesta(String id, String titulo, String contenido, String plancha, int indice) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.plancha = plancha;
        this.indice = indice;
    }

    public Propuesta() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getPlancha() {
        return plancha;
    }

    public void setPlancha(String plancha) {
        this.plancha = plancha;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }
}
