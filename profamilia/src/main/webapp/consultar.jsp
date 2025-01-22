<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.profamilia.dto.Sede" %>
<%@ page import="com.profamilia.dto.Empleado" %>
<%
    if (request.getAttribute("sedes") == null) {
        response.sendRedirect(request.getContextPath() + "/sede");
        return;
    }
%>
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
            <a href="empleados.jsp">Volver</a>
        </div>
    </header>
    <section>
        <h1>Empleados por Sede</h1>
        <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
        %>
        <div class="error">
            <%= error %>
        </div>
        <%
        }
        %>
        <div>
            <form action="sede" method="GET">
                <input type="hidden" name="accion" value="consultarEmpleados">
                <label for="id_sede">Sede</label>
                <select id="id_sede" name="id_sede" required>
                    <option value="" disabled selected>Seleccione una sede</option>
                    <%
                    List<Sede> sedes = (List<Sede>) request.getAttribute("sedes");
                    if (sedes != null) {
                        for (Sede s : sedes) {
                    %>
                    <option value="<%= s.getIdsede() %>"><%= s.getNombre() %></option>
                    <%
                        }
                    }
                    %>
                </select>
                <button type="submit">Ver Resultados</button>
            </form>
        </div>
        <h2>Lista de Empleados</h2>
        <table>
            <thead>
                <tr>
                    <th>Documento</th>
                    <th>Nombre</th>
                    <th>Edad</th>
                    <th>Teléfono</th>
                    <th>Cargo</th>
                    <th>Salario</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                List<Empleado> empleados = (List<Empleado>) request.getAttribute("empleados");
                if (empleados != null && !empleados.isEmpty()) {
                    for (Empleado emp : empleados) {
                %>
                <tr>
                    <td><%= emp.getIdempleado() %></td>
                    <td><%= emp.getNombre() %></td>
                    <td><%= emp.getEdad() %></td>
                    <td><%= emp.getTelefono() %></td>
                    <td><%= emp.getCargo() %></td>
                    <td><%= emp.getSalario() %></td>
                    <td>
                        <a href="empleado?action=edit&id=<%= emp.getIdempleado() %>" title="Editar">
                            <img src="img/icon-edit.png" alt="Editar" width="20">
                        </a>
                       <a href="empleado?action=delete&id=<%= emp.getIdempleado() %>" title="Eliminar">
                        <img src="img/icon-delete.png" alt="Eliminar" width="20">
                        </a>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="7">No hay empleados para mostrar.</td>
                </tr>
                <%
                }
                %>
            </tbody>
        </table>
    </section>
</body>
</html>
