package com.example.votaciones.objetos;

public class Respuesta {
    private boolean permitir;
    private String error;

    public Respuesta(boolean permitir, String error) {
        this.permitir = permitir;
        this.error = error;
    }

    public Respuesta() {
    }

    public boolean isPermitir() {
        return permitir;
    }

    public void setPermitir(boolean permitir) {
        this.permitir = permitir;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
