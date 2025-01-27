package com.profamilia.profamilia.controlador;


import com.profamilia.profamilia.dto.Empleado;
import com.profamilia.profamilia.servicios.EmpleadoServicios;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoControlador {
    private static final Logger logger = LogManager.getLogger(EmpleadoControlador.class);

    @Autowired
    private EmpleadoServicios empleadoServicios;

    /**
     * Obtiene todos los empleados
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Empleado>> listarEmpleados(){
        logger.info("GET /api/empleados - Listando todos los empleados");
        return ResponseEntity.ok(empleadoServicios.listarEmpleados());
    }

    /**
     * Obtine empleados por sede
     * @param idsede
     * @return
     */
    @GetMapping("/sedes/{idsede}")
    public ResponseEntity<List<Empleado>> listarporsede(@PathVariable int idsede){
        logger.info("GET /api/empleados/sedes/{} - Listando empleados por sede", idsede);
        return ResponseEntity.ok(empleadoServicios.listarporSede(idsede));
    }

    /**
     * Obtiene un empleado por su Id
     * @param idempleado
     * @return
     */
    @GetMapping("/{idempleado}")
    public ResponseEntity<Empleado> obtenerporidEmpleado(@PathVariable Long idempleado){
        logger.info("GET /api/empleados/{} - Obteniendo empleado por ID", idempleado);
        return ResponseEntity.ok(empleadoServicios.obtenerporId(idempleado));
    }

    /**
     * Crea un nuevo empleado
     * @param empleado
     * @return
     */
    @PostMapping
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado empleado) {
        logger.info("POST /api/empleados - Creando nuevo empleado");
        return ResponseEntity.ok(empleadoServicios.crearEmpleado(empleado));
    }

    /**
     * Edita un empleado existente
     * @param idempleado
     * @param empleado
     * @return
     */
    @PutMapping("/{idempleado}")
    public ResponseEntity<Empleado> editarEmpleado(@PathVariable Long idempleado, @RequestBody Empleado empleado){
        logger.info("PUT /api/empleados/{} - Actualizando empleado", idempleado);
        return ResponseEntity.ok(empleadoServicios.editarEmpleado(idempleado, empleado));
    }

    /**
     * Elimina un empleado
     * @param idempleado
     * @return
     */
    @DeleteMapping("/{idempleado}")
    public  ResponseEntity<Void> Eliminarempleado(@PathVariable Long idempleado){
        logger.info("DELETE /api/empleados/{} - Eliminando empleado", idempleado);
        empleadoServicios.eliminarEmpleado(idempleado);
        return ResponseEntity.ok().build();
    }
}
