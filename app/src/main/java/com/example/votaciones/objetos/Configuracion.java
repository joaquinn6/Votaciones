package com.example.votaciones.objetos;

import java.sql.Time;
import java.util.Date;

public class Configuracion {
    private String id;
    private String fechaInscripcionInicio;
    private String fechaInscripcionFin;
    private String fechaVotaciones;
    private String horaInicioVotaciones;
    private String horaFinVotaciones;

    public Configuracion(String id, String fechaInscripcionInicio, String fechaInscripcionFin, String fechaVotaciones, String horaInicioVotaciones, String horaFinVotaciones) {
        this.id = id;
        this.fechaInscripcionInicio = fechaInscripcionInicio;
        this.fechaInscripcionFin = fechaInscripcionFin;
        this.fechaVotaciones = fechaVotaciones;
        this.horaInicioVotaciones = horaInicioVotaciones;
        this.horaFinVotaciones = horaFinVotaciones;
    }

    public String getId() {
        return id;
    }

    public String getFechaInscripcionInicio() {
        return fechaInscripcionInicio;
    }

    public String getFechaInscripcionFin() {
        return fechaInscripcionFin;
    }

    public String getFechaVotaciones() {
        return fechaVotaciones;
    }

    public String getHoraInicioVotaciones() {
        return horaInicioVotaciones;
    }

    public String getHoraFinVotaciones() {
        return horaFinVotaciones;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFechaInscripcionInicio(String fechaInscripcionInicio) {
        this.fechaInscripcionInicio = fechaInscripcionInicio;
    }

    public void setFechaInscripcionFin(String fechaInscripcionFin) {
        this.fechaInscripcionFin = fechaInscripcionFin;
    }

    public void setFechaVotaciones(String fechaVotaciones) {
        this.fechaVotaciones = fechaVotaciones;
    }

    public void setHoraInicioVotaciones(String horaInicioVotaciones) {
        this.horaInicioVotaciones = horaInicioVotaciones;
    }

    public void setHoraFinVotaciones(String horaFinVotaciones) {
        this.horaFinVotaciones = horaFinVotaciones;
    }
}
