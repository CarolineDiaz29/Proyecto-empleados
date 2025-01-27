package com.profamilia.profamilia.servicios;

import com.profamilia.profamilia.dto.Empleado;
import com.profamilia.profamilia.repositorio.EmpleadoRepositorio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class EmpleadoServicios {
    private static final Logger logger = LogManager.getLogger(EmpleadoServicios.class);

    @Autowired
    private EmpleadoRepositorio empleadoRepositorio;

    /**
     * Lista todos los empleados
     * @return
     */
    public List<Empleado> listarEmpleados(){
        logger.info("Obteniendo lista de todos los empleados");
        return empleadoRepositorio.findAll();
    }

    /**
     * Lista de empleado por sede
     * @param idsede
     * @return
     */
    public List<Empleado> listarporSede(Integer idsede){
        logger.info("Buscando empleados para la sede: {}", idsede);
        return empleadoRepositorio.findBySedeIdsede(idsede);
    }

    /**
     * Obtine un empleado por su id
     * @param idempleado
     * @return
     */
    public Empleado obtenerporId(Long idempleado){
        logger.info("Buscando empleado con ID: {}", idempleado);
        return empleadoRepositorio.findById(idempleado)
                .orElseThrow(() -> { logger.error("Empleado no encontrado con ID: {}", idempleado);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Empleado no encontrado");
                });
    }

    /**
     * Crear un nuevo empleado
     * @param empleado
     * @return
     */
    public Empleado crearEmpleado(Empleado empleado){
        logger.info("Creando nuevo empleado: {}", empleado.getNombre());
        return empleadoRepositorio.save(empleado);
    }

    /**
     * Edita un empleado existente
     * @param idempleado
     * @param empleado
     * @return
     */
    public Empleado editarEmpleado(Long idempleado, Empleado empleado){
        logger.info("Actualizando empleado con ID: {}", idempleado);
        Empleado empleadoExistente = obtenerporId(idempleado);
        empleadoExistente.setIdempleado(empleado.getIdempleado());
        empleadoExistente.setNombre(empleado.getNombre());
        empleadoExistente.setFecha(empleado.getFecha());
        empleadoExistente.setEdad(empleado.getEdad());
        empleadoExistente.setTelefono(empleado.getTelefono());
        empleadoExistente.setCargo(empleado.getCargo());
        empleadoExistente.setSalario(empleado.getSalario());
        if (!Objects.equals(empleadoExistente.getSede().getIdsede(), empleado.getSede().getIdsede())) {
            empleadoExistente.setSede(empleado.getSede());
        }
        return empleadoRepositorio.save(empleadoExistente);
    }

    /**
     * Elimina un empleado
     * @param idempleado
     */
    public void eliminarEmpleado(Long idempleado){
        logger.info("Eliminando empleado con ID: {}", idempleado);
        empleadoRepositorio.deleteById(idempleado);
    }
}
