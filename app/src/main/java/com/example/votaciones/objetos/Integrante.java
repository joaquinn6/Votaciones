package com.example.votaciones.objetos;

import java.io.Serializable;

public class Integrante implements Serializable {
    private String puesto;
    private Usuario usuario;

    public Integrante(String puesto, Usuario usuario) {
        this.puesto = puesto;
        this.usuario = usuario;
    }

    public Integrante() {
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
