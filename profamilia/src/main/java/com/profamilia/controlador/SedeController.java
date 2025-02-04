package com.profamilia.controlador;

import com.profamilia.dto.Sede;
import com.profamilia.configuracion.RestClienteSede;
import jakarta.annotation.PostConstruct;
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
    private RestClienteSede restClientesede = new RestClienteSede();
    private static final Logger logger = LogManager.getLogger(SedeController.class);
    private Sede sede = new Sede();
    private List<Sede> sedes;

    public List<Sede> getSedes() {
        return sedes;
    }

    public void setSedes(List<Sede> sedes) {
        this.sedes = sedes;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    @PostConstruct
    public void init() {
        actualizarListaSedes();
    }

    /**
     * Actualiza lista de sedes
     */
    public void actualizarListaSedes() {
        sedes = restClientesede.obtenerTodasLasSedes();
    }

    /**
     * Crea la sedes
     * @return
     */
    public String crearSede() {
        try {
            restClientesede.crearSede(sede);
            sedes = restClientesede.obtenerTodasLasSedes();
            sede = new Sede();
            agregarMensaje("Sede Creada", "Sede Creada", FacesMessage.SEVERITY_WARN);
            logger.info("Sede creada exitosamente");
        } catch (Exception e) {
            agregarMensaje("Error", "Sede no Creada", FacesMessage.SEVERITY_ERROR);
            logger.error("Error al crear la sede: ", e);
        }
        return "sedes";
    }

    /**
     * Edita la sede
     * @return
     */
    public String editarSede() {
        try {
            restClientesede.editarSede(sede);
            sedes = restClientesede.obtenerTodasLasSedes();
            agregarMensaje("Sede actualizado", "El usuario fue agregado exitosamente", FacesMessage.SEVERITY_WARN);
            logger.info("Sede actualizada exitosamente");
        } catch (Exception e) {
            agregarMensaje("Error", "No se pudo agregar el usuario", FacesMessage.SEVERITY_ERROR);
            e.printStackTrace();
            logger.error("Error al actualizar la sede: ", e);
        }
        return "sedes";
    }

    /**
     * Cargar las sedes donde se necesita
     * @param idSede
     */
    public void cargarSede(int idSede) {
        try {
            this.sede = restClientesede.obtenerSedePorId(idSede);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para que tenga un mensaje
     * @param titulo
     * @param detalle
     * @param severidad
     */
    private void agregarMensaje(String titulo, String detalle, FacesMessage.Severity severidad) {
        FacesMessage mensaje = new FacesMessage(severidad, titulo, detalle);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
    }
}