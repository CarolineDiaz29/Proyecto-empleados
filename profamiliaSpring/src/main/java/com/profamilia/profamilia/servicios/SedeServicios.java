package com.profamilia.profamilia.servicios;

import com.profamilia.profamilia.dto.Sede;
import com.profamilia.profamilia.repositorio.SedeRepositorio;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class SedeServicios {
    private static final Logger logger = LogManager.getLogger(SedeServicios.class);

    @Autowired
    private SedeRepositorio sedeRepositorio;

    /**
     * Lista todas las sedes
     * @return
     */
    public List<Sede> listarSedes(){
        logger.info("Obteniendo lista de todas las sedes");
        return sedeRepositorio.findAll();
    }

    /**
     * Obtiene sede por su id
     * @param idsede
     * @return
     */
    public Sede obtenerPorIdSede(Integer idsede){
        logger.info("Buscando sede con ID: {}", idsede);
        return sedeRepositorio.findById(idsede)
                .orElseThrow(() -> { logger.error("Sede no encontrada con ID: {}", idsede);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Sede no encontrada");
                });
    }

    /**
     * Crear una neva sede
     * @param sede
     * @return
     */
    public Sede crearSede(Sede sede){
        logger.info("Creando nueva Sede: {}", sede.getNombre());
        return sedeRepositorio.save(sede);
    }

    /**
     * Edita una sede existente
     * @param idsede
     * @param sede
     * @return
     */
    public Sede editarSede(Integer idsede, Sede sede) {
        logger.info("Actualizando sede con ID: {}", idsede);
        Sede sedeexistente = obtenerPorIdSede(idsede);
        sedeexistente.setNombre(sede.getNombre());
        sedeexistente.setDireccion(sede.getDireccion());
        sedeexistente.setCiudad(sede.getCiudad());
        sedeexistente.setTelefono(sede.getTelefono());
        sedeexistente.setCapacidad(sede.getCapacidad());
        return sedeRepositorio.save(sedeexistente);
    }
}
