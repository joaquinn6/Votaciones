package com.example.votaciones.objetos;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String id;
    private String nombre;
    private String apellido;
    private String correo;
    private String contraseña;
    private String carnet;
    private String carrera;
    private String foto;
    private String huella;
    private String acercade;
    private boolean verificacion;
    private String codigo;
    private String fechacaduca;
    private String twitter;
    private String instagram;
    private String facebook;
    private boolean voto;

    public Usuario(String id, String nombre, String apellido, String correo, String contraseña, String carnet, String carrera, String foto, String huella, String acercade, boolean verificacion, String codigo, String fechacaduca, String twitter, String instagram, String facebook, boolean voto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.carnet = carnet;
        this.carrera = carrera;
        this.foto = foto;
        this.huella = huella;
        this.acercade = acercade;
        this.verificacion = verificacion;
        this.codigo = codigo;
        this.fechacaduca = fechacaduca;
        this.twitter = twitter;
        this.instagram = instagram;
        this.facebook = facebook;
        this.voto = voto;
    }

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getHuella() {
        return huella;
    }

    public void setHuella(String huella) {
        this.huella = huella;
    }

    public String getAcercade() {
        return acercade;
    }

    public void setAcercade(String acercade) {
        this.acercade = acercade;
    }

    public boolean isVerificacion() {
        return verificacion;
    }

    public void setVerificacion(boolean verificacion) {
        this.verificacion = verificacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFechacaduca() {
        return fechacaduca;
    }

    public void setFechacaduca(String fechacaduca) {
        this.fechacaduca = fechacaduca;
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

    public boolean isVoto() {
        return voto;
    }

    public void setVoto(boolean voto) {
        this.voto = voto;
    }
}
