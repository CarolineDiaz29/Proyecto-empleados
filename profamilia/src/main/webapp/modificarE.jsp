<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.profamilia.Dto.Sede" %>
<%@ page import="com.profamilia.Dto.Empleado" %>
<%
    Empleado emp = (Empleado) request.getAttribute("empleado");
    if (request.getAttribute("listaSedes") == null || emp == null) {
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
            <a href="consultar.jsp">Volver</a>
        </div>
    </header>
    <section>
        <h2>Modificar Empleado</h2>
        <form action="empleado" method="POST" id="modificar-empleados" onsubmit="handleSubmit(event)">
            <input type="hidden" name="_method" value="PUT">
            <div>
                <label for="id-empleado">Documento</label>
                <input type="number" id="id-empleado" name="id_empleado" value="<%= emp.getIdempleado() %>" readonly
                class=readonly-field" tabindex="-1" onfocus="this.blur();" onkeydown="return false;">
            </div>
            <div>
                <label for="nombre-empleado">Nombre Completo</label>
                <input type="text" id="nombre-empleado" name="nombrempleado" value="<%= emp.getNombre() %>" required>
            </div>
            <div>
                <label for="edad">Edad</label>
                <input type="number" id="edad" name="edad" value="<%= emp.getEdad() %>" required>
            </div>
            <div>
                <label for="telefono">Teléfono</label>
                <input type="number" id="telefono" name="telefono" value="<%= emp.getTelefono() %>" required>
            </div>
            <div>
                <label for="cargo">Cargo</label>
                <input type="text" id="cargo" name="cargo" value="<%= emp.getCargo() %>" required>
            </div>
            <div>
                <label for="sueldo">Sueldo</label>
                <input type="number" id="sueldo" name="sueldo" value="<%= emp.getSalario() %>" required>
            </div>
            <div>
                <label for="id-sede">Sede</label>
                <select id="id-sede" name="id_sede" required>
                    <%
                    List<Sede> listaSedes = (List<Sede>) request.getAttribute("listaSedes");
                    if (listaSedes != null) {
                        for (Sede s : listaSedes) {
                    %>
                        <option value="<%= s.getIdsede() %>" <%= (emp.getIdsede() == s.getIdsede() ? "selected" : "") %>>
                            <%= s.getNombre() %>
                        </option>
                    <%
                        }
                    }
                    %>
                </select>
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

            fetch(form.action, {
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
                alert('Empleado actualizado correctamente');
                window.location.href = "sede?accion=consultarEmpleados" + formData.get("id_sede");
            })
            .catch(error => {
                alert('Error al actualizar el empleado');
                console.error('Error:', error);
            });
        }
        </script>
    </section>
</body>
</html>