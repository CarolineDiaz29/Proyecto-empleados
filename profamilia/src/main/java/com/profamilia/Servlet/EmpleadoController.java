package com.profamilia.Servlet;

import com.profamilia.Dao.EmpleadoDao;
import com.profamilia.Dao.SedeDao;
import com.profamilia.Dto.Empleado;
import com.profamilia.Dto.Sede;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/empleado")
public class EmpleadoController extends HttpServlet {

    private final EmpleadoDao empleadoDAO = new EmpleadoDao();
    private final SedeDao sedeDAO = new SedeDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id_empleadostr = request.getParameter("id_empleado");
            String nombrempleado = request.getParameter("nombrempleado");
            String edadsrt = request.getParameter("edad");
            String telefonosrt = request.getParameter("telefono");
            String cargo = request.getParameter("cargo");
            String sueldostr = request.getParameter("sueldo");
            String id_sedestr = request.getParameter("id_sede");

            // 2. Validar que ningún campo esté vacío
            if (id_empleadostr == null || id_empleadostr.trim().isEmpty() ||
                    nombrempleado == null || nombrempleado.trim().isEmpty() ||
                    edadsrt == null || edadsrt.trim().isEmpty() ||
                    telefonosrt == null || telefonosrt.trim().isEmpty() ||
                    sueldostr == null || sueldostr.trim().isEmpty() ||
                    id_sedestr == null || id_sedestr.trim().isEmpty()) {
                request.setAttribute("error", "Todos los campos son obligatorios.");
                request.getRequestDispatcher("registrarE.jsp").forward(request, response);
                return;
            }

            int edad, idsede;
            long id_empleado, telefono;
            double sueldo;

            try {
                id_empleado = Long.parseLong(id_empleadostr);
                edad = Integer.parseInt(edadsrt);
                telefono = Long.parseLong(telefonosrt);
                sueldo = Double.parseDouble(sueldostr);
                idsede = Integer.parseInt(id_sedestr);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID empleado, edad, teléfono y sueldo deben ser valores numéricos válidos.");
                request.getRequestDispatcher("registrarE.jsp").forward(request, response);
                return;
            }

            Sede sede = sedeDAO.obtenerSedePorId(idsede);
            if (sede == null) {
                request.setAttribute("error", "La sede con ID " + idsede + " no existe.");
                request.getRequestDispatcher("registrarE.jsp").forward(request, response);
                return;
            }

            // 5. Crear el objeto Empleado
            Empleado empleado = new Empleado();
            empleado.setIdempleado(id_empleado);
            empleado.setNombre(nombrempleado);
            empleado.setEdad(edad);
            empleado.setTelefono(telefono);
            empleado.setCargo(cargo);
            empleado.setSalario(sueldo);
            empleado.setIdsede(idsede);

            try {
                empleadoDAO.crearEmpleado(empleado, sedeDAO);
                request.setAttribute("success", "Registro exitoso del empleado.");
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", e.getMessage());
            } catch (Exception e) {
                request.setAttribute("error", "No se pudo registrar el empleado. Ocurrió un error inesperado.");
            }
            request.getRequestDispatcher("registrarE.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al procesar la solicitud.");
            request.getRequestDispatcher("registrarE.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            List<Sede> listaSedes = sedeDAO.obtenerTodasLasSedes();
            request.setAttribute("listaSedes", listaSedes);

            if (action == null) {
                request.getRequestDispatcher("registrarE.jsp").forward(request, response);
                return;
            }

            switch (action) {
                case "edit":
                    long idEdit = Long.parseLong(request.getParameter("id"));
                    Empleado emp = empleadoDAO.obtenerEmpleadoPorId(idEdit);
                    request.setAttribute("empleado", emp);
                    request.getRequestDispatcher("modificarE.jsp").forward(request, response);
                    break;

                case "delete":
                    long idDelete = Long.parseLong(request.getParameter("id"));
                    empleadoDAO.eliminarEmpleado(idDelete);
                    request.getSession().setAttribute("mensaje", "Empleado eliminado exitosamente");
                    response.sendRedirect(request.getContextPath() + "/sede?accion=buscarEmpleados");
                    break;

                default:
                    request.getRequestDispatcher("registrarE.jsp").forward(request, response);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error: " + e.getMessage());
            request.getRequestDispatcher("consultar.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            String[] params = sb.toString().split("&");
            Map<String, String> paramMap = new HashMap<>();
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    paramMap.put(keyValue[0], URLDecoder.decode(keyValue[1], "UTF-8"));
                }
            }

            long id_empleado = Long.parseLong(paramMap.get("id_empleado"));
            String nombre = paramMap.get("nombrempleado");
            int edad = Integer.parseInt(paramMap.get("edad"));
            long telefono = Long.parseLong(paramMap.get("telefono"));
            String cargo = paramMap.get("cargo");
            double sueldo = Double.parseDouble(paramMap.get("sueldo"));
            int id_sede = Integer.parseInt(paramMap.get("id_sede"));

            Sede sede = sedeDAO.obtenerSedePorId(id_sede);
            if (sede == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Error: La sede con ID " + id_sede + " no existe.");
                return;
            }

            Empleado emp = new Empleado();
            emp.setIdempleado(id_empleado);
            emp.setNombre(nombre);
            emp.setEdad(edad);
            emp.setTelefono(telefono);
            emp.setCargo(cargo);
            emp.setSalario(sueldo);
            emp.setIdsede(id_sede);


            empleadoDAO.editarEmpleado(emp, sedeDAO);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Empleado actualizado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al actualizar el empleado: " + e.getMessage());
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        try {
            long idempleado = Long.parseLong(request.getParameter("id"));
            empleadoDAO.eliminarEmpleado(idempleado);

            request.getSession().setAttribute("mensaje", "Empleado eliminado exitosamente");
            response.sendRedirect(request.getContextPath() + "/sede?accion=buscarEmpleados&id_sede=" +
                    request.getSession().getAttribute("ultimaSedeConsultada"));

            } catch (NumberFormatException e) {
                request.setAttribute("error", "ID de empleado inválido");
                request.getRequestDispatcher("consultar.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("error", "Error al eliminar el empleado: " + e.getMessage());
                request.getRequestDispatcher("consultar.jsp").forward(request, response);
            }
        }
    }

