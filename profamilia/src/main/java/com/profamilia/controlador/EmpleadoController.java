package com.profamilia.controlador;

import com.profamilia.configuracion.RestClienteEmpleado;
import com.profamilia.configuracion.RestClienteExcel;
import com.profamilia.configuracion.RestClienteSede;
import com.profamilia.dto.Empleado;
import com.profamilia.dto.Sede;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;


@ManagedBean(name = "empleadoController")
@SessionScoped
public class EmpleadoController implements Serializable {
    private RestClienteEmpleado restClienteEmpleado = new RestClienteEmpleado();
    private RestClienteSede restClientesede = new RestClienteSede();
    private RestClienteExcel restClienteExcel = new RestClienteExcel();
    private static final Logger logger = LogManager.getLogger(EmpleadoController.class);
    private Empleado empleado = new Empleado();
    private List<Empleado> empleados;
    private List<Sede> listaSedes;

    public EmpleadoController() {
        empleado.setSede(new Sede());
        listaSedes = restClientesede.obtenerTodasLasSedes();
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public List<Sede> getListaSedes() {
        return listaSedes;
    }

    public void setListaSedes(List<Sede> listaSedes) {
        this.listaSedes = listaSedes;
    }

    /**
     * Se calcula la edad segun la fecha de nacimiento
     */
    public void calcularEdad() {
        if (empleado.getFecha() != null) {
            LocalDate fechaNacimiento = new java.sql.Date(empleado.getFecha().getTime()).toLocalDate();
            LocalDate fechaActual = LocalDate.now();
            int edad = Period.between(fechaNacimiento, fechaActual).getYears();
            empleado.setEdad(edad);
            logger.info("Edad calculada para el empleado: {}", edad);
        } else {
            empleado.setEdad(0);
            logger.warn("No se pudo calcular la edad porque la fecha de nacimiento es nula.");
        }
    }

    /**
     * Metodo para crear empleado
     */
    public String crearempleado() {
        try {
            calcularEdad();
            Sede sede = restClientesede.obtenerSedePorId(empleado.getSede().getIdsede());
            if (sede == null) {
                throw new IllegalAccessException("La sede no existe.");
            }
            empleado.setSede(sede);
            restClienteEmpleado.crearEmpleado(empleado);
            empleados = restClienteEmpleado.obtenerEmpleadosPorSede(empleado.getSede().getIdsede());
            empleado = new Empleado();
            agregarMensaje("Empleado Creado", "Empleado Creado", FacesMessage.SEVERITY_WARN);
            logger.info("Empleado creado exitosamente");
        } catch (Exception e) {
            agregarMensaje("Error", "Empleado no Creado", FacesMessage.SEVERITY_ERROR);
            logger.error("Error al crear el empleado: ", e);
            e.printStackTrace();
        }
        return "consultarEmpleados";
    }

    /**
     * Metodo para consultar empleados por sede
     */
    public void consultarEmpleadosPorSede() {
        try {
            int idsede = empleado.getSede().getIdsede();

            empleados = restClienteEmpleado.obtenerEmpleadosPorSede(idsede);
            if (empleados == null || empleados.isEmpty()) {
                agregarMensaje("No se encontraron empleados para la sede seleccionada.", "No se encontraron empleados para la sede seleccionada.", FacesMessage.SEVERITY_WARN);
                logger.warn("No se encontraron empleados para la sede con ID: {}", idsede);
            }
            logger.info("{} empleados encontrados para la sede con ID: {}", empleados.size(), idsede);
        } catch (Exception e) {
            agregarMensaje("Error", "Ocurrió un error al consultar los empleados. Intente nuevamente.", FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
            logger.error("Error al consultar empleados por sede: ", e);
        }
    }

    /**
     * Metodo para consultar emplado por su id
     * @param idempleado
     */
    public void consultarEmpleadoPorId(long idempleado) {
        try {
            empleado = restClienteEmpleado.obtenerEmpleadoPorId(idempleado);
            if (empleado == null) {
                throw new RuntimeException("Empleado no encontrado");
            }
            logger.info("Empleado encontrado: {}", empleado);
        } catch (Exception e) {
            logger.error("Error al consultar empleado por ID {}: ", idempleado, e);
            e.printStackTrace();
        }
    }

    /**
     * Metodo para editar un empleado
     * @return
     */
    public String editarEmpleado() {
        try {
            calcularEdad();
            Sede sede = restClientesede.obtenerSedePorId(empleado.getSede().getIdsede());
            if (sede == null) {
                throw new IllegalArgumentException("La sede no existe.");
            }
            empleado.setSede(sede);
            restClienteEmpleado.editarEmpleado(empleado);
            empleados = restClienteEmpleado.obtenerEmpleadosPorSede(empleado.getSede().getIdsede());
            agregarMensaje("Empleado Actualizado", "Empleado Creado", FacesMessage.SEVERITY_WARN);
            logger.info("Empleado actualizado exitosamente");
        } catch (Exception e) {
            agregarMensaje("Error", "Empleado no actualizado", FacesMessage.SEVERITY_ERROR);
            logger.error("Error al actualizar el empleado: ", e);
            e.printStackTrace();
        }
        return "consultarEmpleados";
    }

    /**
     * Metodo para cargar los empleados
     * @param idEmpleado
     */
    public void cargarEmpleado(long idEmpleado) {
        try {
            this.empleado = restClienteEmpleado.obtenerEmpleadoPorId(idEmpleado);
            logger.info("Empleado cargado exitosamente: {}", empleado);
        } catch (Exception e) {
            logger.error("Error al cargar los datos del empleado con ID {}: ", idEmpleado, e);
            e.printStackTrace();
        }
    }

    /**
     * Metodo para eliminar un empleado
     * @return
     */
    public String eliminarEmpleado() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            long idEmpleado = empleado.getIdempleado();
            if (idEmpleado > 0) {
                restClienteEmpleado.eliminarEmpleado(idEmpleado);
                empleados = restClienteEmpleado.obtenerEmpleadosPorSede(empleado.getSede().getIdsede());
                agregarMensaje("Empleado Eliminado", "Empleado Eliminado", FacesMessage.SEVERITY_WARN);
                logger.info("Empleado eliminado exitosamente");
            }
        } catch (Exception e) {
            agregarMensaje("Error", "Empleado no eliminado", FacesMessage.SEVERITY_ERROR);
            logger.error("Error al eliminar el empleado: ", e);
            e.printStackTrace();
        }
        return "consultarEmpleados";
    }

    /**
     * Metodo pata generar el Excel
     */
    public void GenerarExcel() {
        try {
            if (empleados == null || empleados.isEmpty()) {
                consultarEmpleadosPorSede();
            }

            if (empleados == null || empleados.isEmpty()) {
                agregarMensaje("Error", "No hay datos para generar el reporte", FacesMessage.SEVERITY_WARN);
                return;
            }

            restClienteExcel.generarExcelEmpleados(empleados);
            agregarMensaje("Reporte generado", "El reporte Excel se ha generado exitosamente", FacesMessage.SEVERITY_INFO);
            logger.info("Reporte Excel generado exitosamente");
        } catch (Exception e) {
            agregarMensaje("Error", "Error al generar el reporte Excel", FacesMessage.SEVERITY_ERROR);
            logger.error("Error al generar el reporte Excel: ", e);
        }
    }

    /**
     * Metodo para los mensajes
     * @param titulo
     * @param detalle
     * @param severidad
     */
    private void agregarMensaje(String titulo, String detalle, FacesMessage.Severity severidad) {
        FacesMessage mensaje = new FacesMessage(severidad, titulo, detalle);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
    }
}


