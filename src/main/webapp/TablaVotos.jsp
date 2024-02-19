<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Votación Mejor Jugador Liga ACB</title>
    <link href="estilos.css" rel="stylesheet" type="text/css" />
</head>
<body class="resultado">
    <h1 class="center-text">Votación al Mejor Jugador de la Liga ACB</h1>
    <hr>
    <% 
        String nombreP = (String) session.getAttribute("nombreCliente");
        List jugadores = (List) request.getAttribute("listaJugadores"); // Sin especificar el tipo de la lista
    %>
    <p class="center-text">Muchas gracias <%= nombreP != null ? nombreP : "Anónimo" %> por tu voto.</p>
    <br>

    <h2 class="center-text">Tabla de Votos</h2>
    <table border="1">
        <tr>
            <th>Jugador</th>
            <th>Votos</th>
        </tr>
        <!-- Aquí se muestran los datos de los jugadores -->
        <% 
        if (jugadores != null && !jugadores.isEmpty()) {
            for (Object object : jugadores) {
                Jugador jugador = (Jugador) object
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
