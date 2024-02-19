<!DOCTYPE html>
<html lang="es">
<head>
    <title>Votacion mejor jugador liga ACB</title>
    <link href="estilos.css" rel="stylesheet" type="text/css" />
</head>
<body class="resultado">
    <h1 class="center-text">Votacion al mejor jugador de la liga ACB</h1>
    <hr>
    <% String nombreP = (String) session.getAttribute("nombreCliente"); %>
    <p class="center-text">Muchas gracias <%= nombreP %> por su voto</p>
    <br>

    <h1 class="center-text">Tabla de Votos</h1>
    <table border="1">
        <tr>
            <th>Jugador</th>
            <th>Votos</th>
        </tr>
        <!-- Aquí comienza la tabla vacía -->
        <tr>
            <td colspan="2">No hay votos registrados.</td>
        </tr>
        <!-- Aquí termina la tabla vacía -->
    </table>

    <a href="index.html">Ir al comienzo</a>
</body>
</html>