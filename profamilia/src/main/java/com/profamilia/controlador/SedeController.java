package com.profamilia.controlador;

import com.profamilia.dao.SedeDao;
import com.profamilia.dto.Sede;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;


@ManagedBean(name = "sedeController")
@SessionScoped
public class SedeController implements Serializable {
    private static final Logger logger = LogManager.getLogger(EmpleadoController.class);
    private static final long SerialVersionUID = 1L;
    private Sede sede = new Sede();
    private List<Sede> sedes;
    private SedeDao sedeDao = new SedeDao();

    public SedeController(){
        sedes = sedeDao.obtenerTodasLasSedes();
    }

    public Sede getSede(){
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public List<Sede> getSedes() {
        return sedes;
    }

    public void setSedes(List<Sede> sedes) {
        this.sedes = sedes;
    }

    public String crearSede() {
        try {
            sedeDao.crearSede(sede);
            sedes = sedeDao.obtenerTodasLasSedes();
            sede = new Sede();
            agregarMensaje("Sede Creada", "Sede Creada", FacesMessage.SEVERITY_WARN);
            logger.info("Sede creada exitosamente");
        } catch (Exception e) {
            agregarMensaje("Error", "Sede no Creada", FacesMessage.SEVERITY_ERROR);
            logger.error("Error al crear la sede: ", e);
        }
        return "sedes";
    }

    //Metodo editar sede
    public String editarSede() {
        try {
            sedeDao.editarSede(sede);
            sedes = sedeDao.obtenerTodasLasSedes();
            agregarMensaje("Sede actualizado", "El usuario fue agregado exitosamente", FacesMessage.SEVERITY_WARN);
            logger.info("Sede actualizada exitosamente");
        } catch (Exception e) {
            agregarMensaje("Error", "No se pudo agregar el usuario", FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
            logger.error("Error al actualizar la sede: ", e);
        }
        return "sedes";
    }

    //Metodo cargar sede
    public void cargarSede(int idSede) {
        try {
            this.sede = sedeDao.obtenerSedePorId(idSede);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Metodo para los mensajes
    private void agregarMensaje(String titulo, String detalle, FacesMessage.Severity severidad) {
        FacesMessage mensaje = new FacesMessage(severidad, titulo, detalle);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
    }
}