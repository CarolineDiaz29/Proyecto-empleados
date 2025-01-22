package com.profamilia.servlet;

import com.profamilia.dao.SedeDao;
import com.profamilia.dto.Empleado;
import com.profamilia.dao.EmpleadoDao;
import com.profamilia.dto.Sede;

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

@WebServlet("/sede")
public class SedeController extends HttpServlet {

    private final SedeDao sedeDAO = new SedeDao();
    private final EmpleadoDao empleadoDAO = new EmpleadoDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nombreSede = request.getParameter("nombre_sede");
            String direccion = request.getParameter("direccion");
            String ciudad = request.getParameter("ciudad");
            String telefonoStr = request.getParameter("telefono");
            String capacidadStr = request.getParameter("capacidad");

            if (nombreSede == null || nombreSede.trim().isEmpty() ||
                    direccion == null || direccion.trim().isEmpty() ||
                    ciudad == null || ciudad.trim().isEmpty() ||
                    telefonoStr == null || telefonoStr.trim().isEmpty() ||
                    capacidadStr == null || capacidadStr.trim().isEmpty()) {
                request.setAttribute("error", "Todos los campos son obligatorios.");
                request.getRequestDispatcher("registrarS.jsp").forward(request, response);
                return;
            }
            long telefono = 0;
            int capacidad = 0;
            try {
                telefono = Long.parseLong(telefonoStr);
                capacidad = Integer.parseInt(capacidadStr);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Teléfono y capacidad deben ser números válidos.");
                request.getRequestDispatcher("registrarS.jsp").forward(request, response);
                return;
            }
            Sede sede = new Sede();
            sede.setNombre(nombreSede);
            sede.setDireccion(direccion);
            sede.setCiudad(ciudad);
            sede.setTelefono(telefono);
            sede.setCapacidad(capacidad);

            SedeDao sedeDAO = new SedeDao();
            sedeDAO.crearSede(sede);

            request.setAttribute("success", "Registro exitoso de la sede.");
            request.getRequestDispatcher("registrarS.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error al procesar la solicitud.");
            request.getRequestDispatcher("registrarS.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String accion = request.getParameter("accion");


            List<Sede> sedes = sedeDAO.obtenerTodasLasSedes();
            request.setAttribute("sedes", sedes);

            if (accion == null) {
                request.getRequestDispatcher("sede.jsp").forward(request, response);
                return;
            }

            switch (accion) {
                case "modificar":
                    String idSedeStr = request.getParameter("idsede");
                    if (idSedeStr != null && !idSedeStr.trim().isEmpty()) {
                        try {
                            int idSede = Integer.parseInt(idSedeStr);
                            Sede sede = sedeDAO.obtenerSedePorId(idSede);
                            if (sede != null) {
                                request.setAttribute("sede", sede);
                                request.getRequestDispatcher("modificarS.jsp").forward(request, response);
                                return;
                            }
                        } catch (NumberFormatException e) {
                            throw new ServletException("ID de sede inválido");
                        }
                    }
                    break;

                case "consultarSedes":
                    request.getRequestDispatcher("sede.jsp").forward(request, response);
                    break;

                case "consultarEmpleados":
                    String idSedeParam = request.getParameter("id_sede");

                    if (idSedeParam != null && !idSedeParam.isEmpty()) {
                        try {
                            int idSede = Integer.parseInt(idSedeParam);
                            List<Empleado> empleados = empleadoDAO.obtenerEmpleadosPorSede(idSede);
                            request.setAttribute("empleados", empleados);
                        } catch (Exception e) {
                            System.out.println("Error al obtener empleados: " + e.getMessage());
                        }
                    }
                    request.getRequestDispatcher("consultar.jsp").forward(request, response);
                    break;

                default:
                    request.getRequestDispatcher("sede.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException("Error al procesar la solicitud: " + e.getMessage(), e);
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

            // Obtener los datos del empleado del Map
            int idsede = Integer.parseInt(paramMap.get("idsede"));
            String nombre = paramMap.get("nombre_sede");
            String ciudad = paramMap.get("ciudad");
            String direccion = paramMap.get("direccion");
            long telefono = Long.parseLong(paramMap.get("telefono"));
            int capacidad = Integer.parseInt(paramMap.get("capacidad"));

            Sede sede = new Sede();
            sede.setIdsede(idsede);
            sede.setNombre(nombre);
            sede.setCiudad(ciudad);
            sede.setTelefono(telefono);
            sede.setDireccion(direccion);
            sede.setCapacidad(capacidad);

            sedeDAO.editarSede(sede);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Sede actualizado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al actualizar la sede: " + e.getMessage());
        }
    }
}