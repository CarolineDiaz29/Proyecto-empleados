<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.profamilia.Dto.Sede" %>
<%
    if (request.getAttribute("listaSedes") == null) {
        response.sendRedirect(request.getContextPath() + "/empleado");
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
        <h2>Registro de Empleados</h2>
        <form action="empleado" method="post">
            <div>
                <label for="id-empleado">Documento</label>
                <input type="number" id="id-empleado" name="id_empleado" placeholder="Ejemplo: 80796840" required>
            </div>
            <div>
                <label for="nombre-empleado">Nombre Completo</label>
                <input type="text" id="nombre-empleado" name="nombrempleado" placeholder="Ejemplo: Juan Marin" required>
            </div>
            <div>
                <label for="edad">Edad</label>
                <input type="number" id="edad" name="edad" placeholder="Ejemplo: 20" required>
            </div>
            <div>
                <label for="telefono">Teléfono</label>
                <input type="number" id="telefono" name="telefono" placeholder="Ejemplo: 3101234567" required>
            </div>
            <div>
                <label for="cargo">Cargo</label>
                <input type="text" id="cargo" name="cargo" placeholder="Ejemplo: Administrador" required>
            </div>
            <div>
                <label for="sueldo">Sueldo</label>
                <input type="number" id="sueldo" name="sueldo" placeholder="Ejemplo: 1.400000" required>
            </div>
            <div>
                <label for="id-sede">Sede</label>
                <select id="id-sede" name="id_sede" required>
                    <option value="" disabled selected>Seleccione una sede</option>
                    <%
                    List<Sede> listaSedes = (List<Sede>) request.getAttribute("listaSedes");
                    if (listaSedes != null) {
                        for (Sede s : listaSedes) {
                            %>
                            <option value="<%= s.getIdsede() %>"><%= s.getNombre() %></option>
                            <%
                        }
                    }
                    %>
                </select>
            </div>
            <button type="submit">Registrar Empleado</button>
        </form>
    </section>
</body>
</html>