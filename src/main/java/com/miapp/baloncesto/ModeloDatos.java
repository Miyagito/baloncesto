package com.miapp.baloncesto;

import com.miapp.baloncesto.Jugador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloDatos {

    private Connection con;
    private Statement set;
    private ResultSet rs;

    public void abrirConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
    
            String dbPort = "3306";
            String dbName = "baloncesto";
            String dbUser = "tu_usuario";
            String dbPass = "tu_contrasea";
    
            // Asegúrate de que la URL de conexión esté correctamente formada
            String url = "jdbc:mysql://127.0.0.1:" + dbPort + "/" + dbName + "?useSSL=false";
            con = DriverManager.getConnection(url, dbUser, dbPass);
    
            System.out.println("Conexión establecida con éxito.");
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean existeJugador(String nombre) {
        boolean existe = false;
        String cad;
        try {
            set = con.createStatement();
            rs = set.executeQuery("SELECT * FROM Jugadores");
            while (rs.next()) {
                cad = rs.getString("Nombre");
                cad = cad.trim();
                if (cad.compareTo(nombre.trim()) == 0) {
                    existe = true;
                }
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            // No lee de la tabla
            System.out.println("No lee de la tabla");
            System.out.println("El error es: " + e.getMessage());
        }
        return (existe);
    }

    public void actualizarJugador(String nombre) {
        try {
            set = con.createStatement();
            set.executeUpdate("UPDATE Jugadores SET votos=votos+1 WHERE nombre LIKE '%" + nombre + "%'");
            set.close();
        } catch (Exception e) {
            System.out.println("No se pudo actualizar el jugador: " + e.getMessage());
        }
    }
    

    public void insertarJugador(String nombre) {
        try {
            set = con.createStatement();
            set.executeUpdate("INSERT INTO Jugadores " + " (nombre,votos) VALUES ('" + nombre + "',1)");
            rs.close();
            set.close();
        } catch (Exception e) {
            // No inserta en la tabla
            System.out.println("No inserta en la tabla");
            System.out.println("El error es: " + e.getMessage());
        }
    }

    public void resetearVotos() {
        try {
            set = con.createStatement();
            set.executeUpdate("UPDATE Jugadores SET votos=0");
            set.close();
        } catch (Exception e) {
            System.out.println("Error al resetear votos: " + e.getMessage());
        }
    }

    public List<Jugador> obtenerJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        try {
            set = con.createStatement();
            rs = set.executeQuery("SELECT nombre, votos FROM Jugadores");
            while (rs.next()) {
                Jugador jugador = new Jugador(rs.getString("nombre"), rs.getInt("votos"));
                jugadores.add(jugador);
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            System.out.println("Error al obtener jugadores: " + e.getMessage());
        }
        return jugadores;
    }

    public void setConnection(Connection connection) {
        this.con = connection;
    }

    public void cerrarConexion() {
        try {
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}