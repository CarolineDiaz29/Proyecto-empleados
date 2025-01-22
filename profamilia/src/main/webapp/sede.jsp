<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.profamilia.dto.Sede" %>
<html>
<head>
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
            <a href="registrarS.jsp">Registrar</a>
            <a href="index.jsp">Volver</a>
        </div>
    </header>
    <section>
        <h2>Lista de Sedes</h2>
        <table border="1">
            <thead>
                <tr>
                    <th>Nombre Sede</th>
                    <th>Ciudad</th>
                    <th>Dirección</th>
                    <th>Teléfono</th>
                    <th>Capacidad</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                List<Sede> sedes = (List<Sede>) request.getAttribute("sedes");
                if (sedes != null && !sedes.isEmpty()) {
                    for (Sede sede : sedes) {
                %>
                <tr>
                    <td><%= sede.getNombre() %></td>
                    <td><%= sede.getCiudad() %></td>
                    <td><%= sede.getDireccion() %></td>
                    <td><%= sede.getTelefono() %></td>
                    <td><%= sede.getCapacidad() %></td>
                <td>
                      <a href="<%= request.getContextPath() %>/sede?accion=modificar&idsede=<%= sede.getIdsede() %>">
                         <img src="img/icon-edit.png" alt="Editar" width="20">
                       </a>
                </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="7">No se encontraron sedes registradas.</td>
                </tr>
                <%
                }
                %>
            </tbody>
        </table>
    </section>
</body>
</html>