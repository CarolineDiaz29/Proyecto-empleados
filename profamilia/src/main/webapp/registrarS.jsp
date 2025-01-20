<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <link rel="icon" href="img/logo.png" type="image/png">
    <title>Profamilia</title>
</head>
<body>
    <header class="navbar">
        <div class="logo">
            <img src="img/logo-nuevo-profamilia-verde.png" alt="Logo Profamilia">
        </div>
        <div class="buttons">
            <a href="sede.jsp">Volver</a>
        </div>
    </header>
    <section>
        <h2>Registro de Sedes</h2>
        <form action="sede" method="post" id="registro-sedes">
            <div>
                <label for="nombre-sede">Nombre de la Sede</label>
                <input type="text" id="nombre-sede" name="nombre_sede" placeholder="Ejemplo: Sede Central" required>
            </div>
            <div>
                <label for="direccion">Dirección</label>
                <input type="text" id="direccion" name="direccion" placeholder="Ejemplo: Calle 123 #45-67" required>
            </div>
            <div>
                <label for="ciudad">Ciudad</label>
                <input type="text" id="ciudad" name="ciudad" placeholder="Ejemplo: Bogota" required>
            </div>
            <div>
                <label for="telefono">Teléfono</label>
                <input type="tel" id="telefono" name="telefono" placeholder="Ejemplo: 3101234567" required>
            </div>
            <div>
                <label for="capacidad">Capacidad Máxima</label>
                <input type="number" id="capacidad" name="capacidad" placeholder="Ejemplo: 100" required>
            </div>
            <button type="submit">Registrar Sede</button>
        </form>
    </section>
</body>
</html>
