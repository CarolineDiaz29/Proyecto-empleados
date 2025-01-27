package com.profamilia.profamilia.repositorio;

import com.profamilia.profamilia.dto.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio para la entidad empleado
 * Gestiona las operaciones de bases de datos para empleados
 */
public interface EmpleadoRepositorio extends JpaRepository<Empleado, Long> {
    List<Empleado> findBySedeIdsede(int idsede);
}
