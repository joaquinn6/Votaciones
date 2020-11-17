package com.example.votaciones.objetos;

public class Voto {
    private String plancha;
    private String color;

    public Voto(String idPlancha, String colorPlancha) {
        this.color = colorPlancha;
        this.plancha = idPlancha;
    }
    public Voto() {
    }

    public String getPlancha() {
        return plancha;
    }

    public String getColor() {
        return color;
    }

    public void setPlancha(String plancha) {
        this.plancha = plancha;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
