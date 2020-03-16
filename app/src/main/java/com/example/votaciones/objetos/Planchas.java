package com.example.votaciones.objetos;

import java.io.Serializable;
import java.util.List;

public class Planchas implements Serializable {
    private String id;
    private Usuario presidente;
    private Usuario vicepresidente;
    private Usuario secretario;
    private Usuario tesorero;
    private Usuario ministro;
    private Usuario vocal;
    private String color;
    private String nombrePlancha;
    private String imagen;
    private String twitter;
    private String instagram;
    private String facebook;
    private String acronimo;
    private List<Propuesta> propuestas;
    private float votos;

    public Planchas(String id, Usuario presidente, Usuario vicepresidente, Usuario secretario, Usuario tesorero,Usuario ministro,Usuario vocal, String color, String nombrePlancha, String imagen, String twitter, String instagram, String facebook, String acronimo, List<Propuesta> propuestas, float votos) {
        this.id = id;
        this.presidente = presidente;
        this.vicepresidente = vicepresidente;
        this.secretario = secretario;
        this.tesorero = tesorero;
        this.ministro=ministro;
        this.vocal=vocal;
        this.color = color;
        this.nombrePlancha = nombrePlancha;
        this.imagen = imagen;
        this.twitter = twitter;
        this.instagram = instagram;
        this.facebook = facebook;
        this.acronimo = acronimo;
        this.propuestas = propuestas;
        this.votos = votos;
    }

    public Planchas() {
    }

    public Usuario getMinistro() {
        return ministro;
    }

    public Usuario getVocal() {
        return vocal;
    }

    public void setMinistro(Usuario ministro) {
        this.ministro = ministro;
    }

    public void setVocal(Usuario vocal) {
        this.vocal = vocal;
    }

    public float getVotos() {
        return votos;
    }

    public void setVotos(float votos) {
        this.votos = votos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Usuario getPresidente() {
        return presidente;
    }

    public void setPresidente(Usuario presidente) {
        this.presidente = presidente;
    }

    public Usuario getVicepresidente() {
        return vicepresidente;
    }

    public void setVicepresidente(Usuario vicepresidente) {
        this.vicepresidente = vicepresidente;
    }

    public Usuario getSecretario() {
        return secretario;
    }

    public void setSecretario(Usuario secretario) {
        this.secretario = secretario;
    }

    public Usuario getTesorero() {
        return tesorero;
    }

    public void setTesorero(Usuario tesorero) {
        this.tesorero = tesorero;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombrePlancha() {
        return nombrePlancha;
    }

    public void setNombrePlancha(String nombrePlancha) {
        this.nombrePlancha = nombrePlancha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }

    public List<Propuesta> getPropuestas() {
        return propuestas;
    }

    public void setPropuestas(List<Propuesta> propuestas) {
        this.propuestas = propuestas;
    }
}
