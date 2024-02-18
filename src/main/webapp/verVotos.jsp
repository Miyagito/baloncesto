<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="ModeloDatos" %>
<%@ page import="ModeloDatos.Jugador" %>


<!DOCTYPE html>
<html lang="es">
<head>
    <title>Ver Votos</title>
    <link href="estilos.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <h1 class="center-text">Tabla de Votos</h1>
    <table border="1">
        <tr>
            <th>Jugador</th>
            <th>Votos</th>
        </tr>
        <%
        ModeloDatos bd = new ModeloDatos();
        try {
            bd.abrirConexion();
            List<Jugador> jugadores = bd.obtenerJugadores();
            // ... (tu código actual para mostrar jugadores)
        } catch (Exception e) {
            // Manejo de excepciones
            out.println("Error al recuperar los datos: " + e.getMessage());
        } finally {
            bd.cerrarConexion();
        }
    %>
    </table>
    <a href="index.html">Volver a la página principal</a>
</body>
</html>