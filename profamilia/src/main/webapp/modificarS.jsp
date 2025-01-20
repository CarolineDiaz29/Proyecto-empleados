<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.profamilia.Dto.Sede" %>
<%
    Sede sede = (Sede) request.getAttribute("sede");
    if(sede == null) {
        response.sendRedirect("sede");
        return;
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
    <link rel="icon" href="img/logo.png" type="image/png">
    <title>Profamilia - Modificar Sede</title>
</head>
<body>
    <header class="navbar">
        <div class="logo">
            <img src="img/logo-nuevo-profamilia-verde.png" alt="Logo Profamilia">
        </div>
        <div class="buttons">
            <a href="sede">Volver</a>
        </div>
    </header>
    <section>
        <h2>Modificar Sede</h2>
        <form action="sede" method="POST" id="modificacion-sedes" onsubmit="handleSubmit(event)">
            <input type="hidden" name="_method" value="PUT">
            <input type="hidden" name="idsede" value="<%= sede.getIdsede() %>">

            <div>
                <label for="nombre-sede">Nombre de la Sede</label>
                <input type="text" id="nombre-sede" name="nombre_sede" value="<%= sede.getNombre() %>" required>
            </div>
            <div>
                <label for="direccion">Dirección</label>
                <input type="text" id="direccion" name="direccion" value="<%= sede.getDireccion() %>" required>
            </div>
            <div>
                <label for="ciudad">Ciudad</label>
                <input type="text" id="ciudad" name="ciudad" value="<%= sede.getCiudad() %>" required>
            </div>
            <div>
                <label for="telefono">Teléfono</label>
                <input type="tel" id="telefono" name="telefono" value="<%= sede.getTelefono() %>" required>
            </div>
            <div>
                <label for="capacidad">Capacidad Máxima</label>
                <input type="number" id="capacidad" name="capacidad" value="<%= sede.getCapacidad() %>" required>
            </div>
            <button type="submit">Guardar Cambios</button>
        </form>
        <script>
        function handleSubmit(event) {
            event.preventDefault();
            const form = event.target;
            const formData = new FormData(form);

            const data = new URLSearchParams();
            for (const [key, value] of formData) {
                data.append(key, value);
            }

            fetch('sede', {
                method: 'PUT',
                body: data,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
            .then(response => {
                if (response.ok) {
                    return response.text();
                }
                throw new Error('Error en la actualización');
            })
            .then(text => {
                alert('Sede actualizada correctamente');
                window.location.href = "sede";
            })
            .catch(error => {
                alert('Error al actualizar la sede: ' + error.message);
                console.error('Error:', error);
            });
        }
        </script>
    </section>
</body>
</html>