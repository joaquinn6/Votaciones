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
    private String acercade;
    private boolean verificacion;
    private String codigo;
    private String fechacaduca;
    private String twitter;
    private String instagram;
    private String facebook;
    private boolean voto;
    private String facultad;
    private String roles;
    private String pin;

    public Usuario(String id, String nombre, String apellido, String correo, String contraseña, String carnet, String carrera, String foto, String acercade, boolean verificacion, String codigo, String fechacaduca, String twitter, String instagram, String facebook, boolean voto, String facultad, String roles, String pin) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.carnet = carnet;
        this.carrera = carrera;
        this.foto = foto;
        this.acercade = acercade;
        this.verificacion = verificacion;
        this.codigo = codigo;
        this.fechacaduca = fechacaduca;
        this.twitter = twitter;
        this.instagram = instagram;
        this.facebook = facebook;
        this.voto = voto;
        this.facultad = facultad;
        this.roles = roles;
        this.pin = pin;
    }

    public Usuario() {
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getFacultad() {
        return facultad;
    }

    public String getRoles() {
        return roles;
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
