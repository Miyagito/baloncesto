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
    <a href="index.html">Ir al comienzo</a>
</body>
</html>


