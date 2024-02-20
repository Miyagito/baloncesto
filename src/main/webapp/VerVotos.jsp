<%@ page import="java.util.List" %>
<%@ page import="com.miapp.baloncesto.Jugador" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Votación Mejor Jugador Liga ACB</title>
    <link href="estilos.css" rel="stylesheet" type="text/css" />
</head>
<body class="resultadoVotos">
    <h1 class="center-text">Votación</h1>
    <hr>
    <% 
        List jugadores = (List) request.getAttribute("listaJugadores");
    %>
    <br>

    <h2 class="center-text">Tabla de Votos</h2>
    <table border="1">
        <tr>
            <th>Jugador</th>
            <th>Votos</th>
        </tr>
        <% 
        if (jugadores != null && !jugadores.isEmpty()) {
            for (Object object : jugadores) {
                Jugador jugador = (Jugador) object;
        %>
                <tr>
                    <td><%= jugador.getNombre() %></td>
                    <td><%= jugador.getVotos() %></td>
                </tr>
        <%
            }
        } else {
        %>
            <tr>
                <td colspan="2">No hay votos registrados.</td>
            </tr>
        <%
        }
        %>
    </table>

    <a href="index.html">Volver al inicio</a>
</body>
</html>
