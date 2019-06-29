package com.example.votaciones.objetos;

public class Voto {
    private String id;
    private String nombrePlancha;

    public Voto(String id, String nombrePlancha) {
        this.id = id;
        this.nombrePlancha = nombrePlancha;
    }

    public Voto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombrePlancha() {
        return nombrePlancha;
    }

    public void setNombrePlancha(String nombrePlancha) {
        this.nombrePlancha = nombrePlancha;
    }
}
