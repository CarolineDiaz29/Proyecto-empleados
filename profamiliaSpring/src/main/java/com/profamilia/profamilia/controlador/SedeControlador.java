package com.profamilia.profamilia.controlador;


import com.profamilia.profamilia.dto.Sede;
import com.profamilia.profamilia.servicios.SedeServicios;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sedes")
public class SedeControlador {
    private static final Logger logger = LogManager.getLogger(SedeControlador.class);

    @Autowired
    private SedeServicios sedeServicios;

    /**
     * Obtiene todas las sedes
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Sede>> listarSedes(){
        logger.info("GET /api/sedes - Listando todas los sedes");
        return ResponseEntity.ok(sedeServicios.listarSedes());
    }

    /**
     * Obtiene una sede por su Id
     * @param idsede
     * @return
     */
    @GetMapping("/{idsede}")
    public ResponseEntity<Sede> obtenerporidsede(@PathVariable int idsede){
        logger.info("GET /api/sedes/{} - Obteniendo sede por ID", idsede);
        return ResponseEntity.ok(sedeServicios.obtenerPorIdSede(idsede));
    }

    /**
     * Crea una nueva sede
     * @param sede
     * @return
     */
    @PostMapping
    public ResponseEntity<Sede> crearsede(@RequestBody Sede sede){
        logger.info("POST /api/sedes - Creando nueva sede");
        return ResponseEntity.ok(sedeServicios.crearSede(sede));
    }

    /**
     * Edita una sede existente
     * @param idsede
     * @param sede
     * @return
     */
    @PutMapping("/{idsede}")
    public ResponseEntity<Sede> editarSede(@PathVariable int idsede, @RequestBody Sede sede){
        logger.info("PUT /api/sedes/{} - Actualizando sede", idsede);
        return ResponseEntity.ok(sedeServicios.editarSede(idsede, sede));
    }
}
