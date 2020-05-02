package com.example.votaciones.objetos;

import java.io.Serializable;
import java.util.List;

public class Plancha implements Serializable {
    private String id;
    private String presidente;
    private String vicepresidente;
    private String secretario;
    private String tesorero;
    private String ministro;
    private String vocal;
    private String color;
    private String nombrePlancha;
    private String imagen;
    private String twitter;
    private String instagram;
    private String facebook;
    private String acronimo;
    private float votos;

    public Plancha(String id, String presidente, String vicepresidente, String secretario, String tesorero, String ministro, String vocal, String color, String nombrePlancha, String imagen, String twitter, String instagram, String facebook, String acronimo, float votos) {
        this.id = id;
        this.presidente = presidente;
        this.vicepresidente = vicepresidente;
        this.secretario = secretario;
        this.tesorero = tesorero;
        this.ministro = ministro;
        this.vocal = vocal;
        this.color = color;
        this.nombrePlancha = nombrePlancha;
        this.imagen = imagen;
        this.twitter = twitter;
        this.instagram = instagram;
        this.facebook = facebook;
        this.acronimo = acronimo;
        this.votos = votos;
    }

    public String getId() {
        return id;
    }

    public String getPresidente() {
        return presidente;
    }

    public String getVicepresidente() {
        return vicepresidente;
    }

    public String getSecretario() {
        return secretario;
    }

    public String getTesorero() {
        return tesorero;
    }

    public String getMinistro() {
        return ministro;
    }

    public String getVocal() {
        return vocal;
    }

    public String getColor() {
        return color;
    }

    public String getNombrePlancha() {
        return nombrePlancha;
    }

    public String getImagen() {
        return imagen;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public float getVotos() {
        return votos;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public void setVicepresidente(String vicepresidente) {
        this.vicepresidente = vicepresidente;
    }

    public void setSecretario(String secretario) {
        this.secretario = secretario;
    }

    public void setTesorero(String tesorero) {
        this.tesorero = tesorero;
    }

    public void setMinistro(String ministro) {
        this.ministro = ministro;
    }

    public void setVocal(String vocal) {
        this.vocal = vocal;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setNombrePlancha(String nombrePlancha) {
        this.nombrePlancha = nombrePlancha;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public void setVotos(float votos) {
        this.votos = votos;
    }
}
