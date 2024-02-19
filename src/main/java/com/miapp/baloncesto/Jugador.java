package com.miapp.baloncesto;

public class Jugador {
    private String nombre;
    private int votos;

    public Jugador(String nombre, int votos) {
        this.nombre = nombre;
        this.votos = votos;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getVotos() {
        return votos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }
}
